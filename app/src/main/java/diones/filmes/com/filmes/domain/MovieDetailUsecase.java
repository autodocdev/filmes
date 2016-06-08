package diones.filmes.com.filmes.domain;

import javax.inject.Inject;

import diones.filmes.com.filmes.BuildConfig;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.model.repository.MovieRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieDetailUsecase implements Usecase <Movie>{

    private int mMovieId;
    private final MovieRepository mMovieRepository;

    @Inject
    public MovieDetailUsecase(int movieId, MovieRepository movieRepository) {

        mMovieId = movieId;
        mMovieRepository = movieRepository;
    }

    @Override
    public Observable<Movie> execute() {
        return mMovieRepository.getMovie(mMovieId, BuildConfig.MOVIE_PUBLIC_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
