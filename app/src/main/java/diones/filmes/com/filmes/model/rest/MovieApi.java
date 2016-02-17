package diones.filmes.com.filmes.model.rest;


import java.util.List;

import diones.filmes.com.filmes.model.entities.Movie;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MovieApi {

    String END_POINT       = "http://api.themoviedb.org/";
    String PARAM_API_KEY   = "api_key";


    @GET("/3/movie/popular")
    Observable<List<Movie>> getPopularMovies (@Query("api_key") String apiKey);

    @GET("/3/movie/latest")
    Observable<Movie> getRecenteMovies (@Query("api_key") String apiKey);

    @GET("/3/movie/upcoming")
    Observable<List<Movie>> getEmBreveMovies (@Query("api_key") String apiKey);

    @GET("/3/movie/{id}")
    Observable<Movie> getMovieById(@Path("id") int id, @Query("api_key") String apiKey);

}
