package diones.filmes.com.filmes.mvp.presenters;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import diones.filmes.com.filmes.domain.GetMoviesUseCase;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.mvp.views.MovieView;
import diones.filmes.com.filmes.mvp.views.View;
import rx.Subscription;

public class MoviePresenter implements Presenter {

    private final GetMoviesUseCase mFilmesUseCase;
    private boolean mIsTheFilmeResquestRunning;
    private Subscription mFilmesSubscription;

    private List<Movie> mMovies;
    private MovieView mFilmeView;

    @Inject
    public MoviePresenter(GetMoviesUseCase filmesUseCase){
        mFilmesUseCase = filmesUseCase;
        mMovies = new ArrayList<>();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {
        mFilmesSubscription.unsubscribe();
        mIsTheFilmeResquestRunning = false;
    }

    @Override
    public void attachView(View v) {
        mFilmeView = (MovieView) v;
    }

    @Override
    public void onCreate() {
        askForFilmes();
        mFilmeView.showWelcomeMessage("App Iniciado");
    }

    private void askForFilmes() {
        mIsTheFilmeResquestRunning = true;

        mFilmesSubscription = mFilmesUseCase.execute()
                .subscribe(filmes -> {
                    mMovies.addAll(filmes);
                    mFilmeView.bindFilmeList(mMovies);
                    mIsTheFilmeResquestRunning = false;
                }, error -> {

                });
    }
}
