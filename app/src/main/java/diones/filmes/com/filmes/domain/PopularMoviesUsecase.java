package diones.filmes.com.filmes.domain;

import java.util.List;

import javax.inject.Inject;

import diones.filmes.com.filmes.BuildConfig;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.model.repository.MovieRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PopularMoviesUsecase implements Usecase<List<Movie>> {

    public final static int CHARACTERS_LIMIT = 20;

    private final MovieRepository mMovieRepository;
    private int offSet = CHARACTERS_LIMIT;

    @Inject
    public PopularMoviesUsecase(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    @Override
    public Observable<List<Movie>> execute() {
        return mMovieRepository.getPopularMovies(BuildConfig.MOVIE_PUBLIC_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }
}
