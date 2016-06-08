package diones.filmes.com.filmes.model.repository;

import java.util.List;

import diones.filmes.com.filmes.model.entities.Movie;
import rx.Observable;

public interface MovieRepository {

    Observable<List<Movie>> getPopularMovies (String apiKey, int page);

    Observable<List<Movie>> getEmBreveMovies (String apiKey, int page);

    Observable<Movie> getMovie(int mMovieId, String apiKey);
}
