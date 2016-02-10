package diones.filmes.com.filmes.model.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.model.repository.Repository;
import diones.filmes.com.filmes.model.rest.exceptions.ServerErrorException;
import diones.filmes.com.filmes.model.rest.exceptions.UknownErrorException;
import diones.filmes.com.filmes.model.rest.interceptors.MovieSigningInterceptor;
import diones.filmes.com.filmes.model.rest.utils.deserializers.MovieResultsDeserializer;
import diones.filmes.com.filmes.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

public class RestDataSource implements Repository {

    private final MovieApi mMovieApi;
    public final static int MAX_ATTEMPS = 3;

    @Inject
    public RestDataSource() {

        MovieSigningInterceptor movieSigningInterceptor = new MovieSigningInterceptor(BuildConfig.MOVIE_PUBLIC_KEY);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(movieSigningInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Gson customGsonInstance = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<Movie>>() {}.getType(),
                        new MovieResultsDeserializer<Movie>())
                .create();

        Retrofit movieApiAdapter = new Retrofit.Builder()
                .baseUrl(MovieApi.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(customGsonInstance))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        mMovieApi =  movieApiAdapter.create(MovieApi.class);
    }

    @Override
    public Observable<List<Movie>> getMovies(String apiKey) {
        return mMovieApi.getPopularMovies(apiKey)
                .onErrorResumeNext(throwable -> {
                    boolean serverError = throwable.getMessage().equals(HttpErrors.SERVER_ERROR);
                    return Observable.error((serverError) ? new ServerErrorException() : new UknownErrorException());
                });
    }

    @Override
    public Observable<Movie> getMovie(int mMovieId, String apiKey) {
        return mMovieApi.getMovieById(mMovieId, apiKey);
    }
}
