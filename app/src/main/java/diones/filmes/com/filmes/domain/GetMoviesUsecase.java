package diones.filmes.com.filmes.domain;

import java.util.List;

import javax.inject.Inject;

import diones.filmes.com.filmes.BuildConfig;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.model.repository.Repository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetMoviesUsecase implements Usecase<List<Movie>> {

    public final static int CHARACTERS_LIMIT = 20;

    private final Repository mRepository;
    private int offSet = CHARACTERS_LIMIT;

    @Inject
    public GetMoviesUsecase(Repository repository) {
        mRepository = repository;
    }

    @Override
    public Observable<List<Movie>> execute() {
        return mRepository.getMovies(BuildConfig.MOVIE_PUBLIC_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
