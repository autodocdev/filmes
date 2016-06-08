package diones.filmes.com.filmes.mvp.presenters;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import diones.filmes.com.filmes.domain.MovieListBreveUsecase;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.mvp.views.MovieListView;
import diones.filmes.com.filmes.mvp.views.View;
import rx.Subscription;

public class MovieListBrevePresenter implements Presenter {

    private final MovieListBreveUsecase mMoviesUseCase;
    private boolean mIsTheCharacterRequestRunning;
    private Subscription mFilmesSubscription;

    private List<Movie> mMovies;
    private MovieListView mMovieView;

    @Inject
    public MovieListBrevePresenter(MovieListBreveUsecase moviesUseCase){
        mMoviesUseCase = moviesUseCase;
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
        mIsTheCharacterRequestRunning = false;
    }

    @Override
    public void attachView(View v) {
        mMovieView = (MovieListView) v;
    }

    @Override
    public void onCreate() {
        askForEmBreve();

    }

    public void onListEndReached() {
        if (!mIsTheCharacterRequestRunning) askForNewEmBreve();
    }

    private void askForEmBreve() {
        mMovieView.hideErrorView();
        mIsTheCharacterRequestRunning = true;
        mFilmesSubscription = mMoviesUseCase.execute()
                .subscribe(this::onMoviesReceived, this::showErrorView);
    }

    private void askForNewEmBreve() {
        mFilmesSubscription = mMoviesUseCase.execute()
                .subscribe(this::onNewMoviesReceived, this::onNewMoviesError);
    }

    public void onMoviesReceived(List<Movie> movies) {
        mMovies.addAll(movies);
        mMovieView.bindMovieList(mMovies);
        mIsTheCharacterRequestRunning = false;
    }

    public void showErrorView(Throwable error) {
        mMovieView.showUknownErrorMessage();
    }

    public void onNewMoviesReceived(List<Movie> movies) {
        mMovies.addAll(movies);
        mMovieView.updateMoviesList(MovieListBreveUsecase.DEFAULT_MOVIES_LIMIT);
        mIsTheCharacterRequestRunning = false;
    }

    private void onNewMoviesError(Throwable error) {
        showGenericError();
        mIsTheCharacterRequestRunning = false;
    }

    private void showGenericError() {
        mMovieView.showLightError();
    }

    public void onErrorRetryRequest() {
        if (mMovies.isEmpty())
            askForEmBreve();
        else
            askForNewEmBreve();
    }

    public void onElementClick(int position, ImageView imageViewMovie) {
        mMovieView.showDetailScreen(mMovies.get(position), imageViewMovie);
    }
}
