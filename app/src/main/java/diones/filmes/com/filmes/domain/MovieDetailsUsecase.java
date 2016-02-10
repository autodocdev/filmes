package diones.filmes.com.filmes.domain;

import javax.inject.Inject;
import javax.inject.Named;

import diones.filmes.com.filmes.BuildConfig;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.model.repository.Repository;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieDetailsUsecase implements Usecase <Movie>{

    private int mMovieId;
    private final Repository mRepository;

    @Inject
    public MovieDetailsUsecase(int characterId,
                               Repository repository) {

        mMovieId = characterId;
        mRepository = repository;
    }

    @Override
    public Observable<Movie> execute() {
        return mRepository.getMovie(mMovieId, BuildConfig.MOVIE_PUBLIC_KEY)
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
