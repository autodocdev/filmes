package diones.filmes.com.filmes.mvp.presenters;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import diones.filmes.com.filmes.domain.MovieListPopularUsecase;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.mvp.views.MovieListView;
import diones.filmes.com.filmes.mvp.views.View;
import rx.Subscription;

public class MovieListPopularPresenter implements Presenter {

    private final MovieListPopularUsecase mMoviesUseCase;
    private boolean mIsTheCharacterRequestRunning;
    private Subscription mFilmesSubscription;

    private List<Movie> mMovies;
    private MovieListView mMovieView;

    @Inject
    public MovieListPopularPresenter(MovieListPopularUsecase moviesUseCase){
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
        askForPopular();

    }

    public void onListEndReached() {
        if (!mIsTheCharacterRequestRunning) askForNewPopular();
    }

    private void askForPopular() {
        mMovieView.hideErrorView();
        mIsTheCharacterRequestRunning = true;
        mFilmesSubscription = mMoviesUseCase.execute()
                .subscribe(this::onMoviesReceived, this::showErrorView);
    }

    private void askForNewPopular() {
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
        mMovieView.updateMoviesList(MovieListPopularUsecase.DEFAULT_MOVIES_LIMIT);
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
            askForPopular();
        else
            askForNewPopular();
    }

    public void onElementClick(int position, ImageView imageViewMovie) {
        mMovieView.showDetailScreen(mMovies.get(position), imageViewMovie);
    }
}
