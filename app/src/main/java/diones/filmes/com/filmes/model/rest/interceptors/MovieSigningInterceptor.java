package diones.filmes.com.filmes.model.rest.interceptors;


import java.io.IOException;

import diones.filmes.com.filmes.model.rest.MovieApi;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MovieSigningInterceptor implements Interceptor {

    private final String mApiKey;

    public MovieSigningInterceptor(String apiKey){
        mApiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        request = request.newBuilder()
                .header("Accept", "application/json")
                .header(MovieApi.PARAM_API_KEY, mApiKey)
                .build();

        Response response = chain.proceed(request);

        return response;

    }
}
