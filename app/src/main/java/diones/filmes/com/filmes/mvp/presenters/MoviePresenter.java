package diones.filmes.com.filmes.mvp.presenters;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import diones.filmes.com.filmes.domain.GetMoviesUsecase;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.mvp.views.MovieView;
import diones.filmes.com.filmes.mvp.views.View;
import rx.Subscription;

public class MoviePresenter implements Presenter {

    private final GetMoviesUsecase mFilmesUseCase;
    private boolean mIsTheFilmeResquestRunning;
    private Subscription mFilmesSubscription;

    private List<Movie> mMovies;
    private MovieView mMovieView;

    @Inject
    public MoviePresenter(GetMoviesUsecase filmesUseCase){
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
        mMovieView = (MovieView) v;
    }

    @Override
    public void onCreate() {
        askForFilmes();
    }

    private void askForFilmes() {
        mIsTheFilmeResquestRunning = true;

        mFilmesSubscription = mFilmesUseCase.execute()
                .subscribe(filmes -> {
                    Log.d("FILMES LOADED", filmes.toString());
                    mMovies.addAll(filmes);
                    mMovieView.bindFilmeList(mMovies);
                    mIsTheFilmeResquestRunning = false;
                }, error -> {

                });
    }

    public void onElementClick(int position) {
        int characterId = Integer.parseInt(mMovies.get(position).getId());
        String originalTitle = mMovies.get(position).getOriginal_title();
        mMovieView.showDetailScreen(originalTitle, characterId);
    }
}
