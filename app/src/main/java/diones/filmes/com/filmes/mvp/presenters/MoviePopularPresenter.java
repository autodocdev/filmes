package diones.filmes.com.filmes.mvp.presenters;

import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import diones.filmes.com.filmes.domain.PopularMoviesUsecase;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.mvp.views.MovieView;
import diones.filmes.com.filmes.mvp.views.View;
import rx.Subscription;

public class MoviePopularPresenter implements Presenter {

    private final PopularMoviesUsecase mMoviesUseCase;
    private boolean mIsTheCharacterRequestRunning;
    private Subscription mFilmesSubscription;

    private List<Movie> mMovies;
    private MovieView mMovieView;

    @Inject
    public MoviePopularPresenter(PopularMoviesUsecase moviesUseCase){
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
        mMovieView = (MovieView) v;
    }

    @Override
    public void onCreate() {
        askForPopular();

    }

    public void onListEndReached() {
        if (!mIsTheCharacterRequestRunning) askForNewPopular();
    }

    private void askForPopular() {
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

    }

    public void onNewMoviesReceived(List<Movie> movies) {
        mMovies.addAll(movies);
        mMovieView.updateMoviesList(PopularMoviesUsecase.DEFAULT_MOVIES_LIMIT);
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
