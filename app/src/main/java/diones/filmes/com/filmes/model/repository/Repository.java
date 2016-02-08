package diones.filmes.com.filmes.model.repository;

import java.util.List;

import diones.filmes.com.filmes.model.entities.Movie;
import rx.Observable;

public interface Repository {

    Observable<List<Movie>> getMovies (String apiKey);

}
