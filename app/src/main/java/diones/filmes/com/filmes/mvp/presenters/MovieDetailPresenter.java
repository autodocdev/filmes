package diones.filmes.com.filmes.mvp.presenters;

import android.util.Log;

import javax.inject.Inject;

import diones.filmes.com.filmes.domain.MovieDetailsUsecase;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.mvp.views.MovieDetailView;
import diones.filmes.com.filmes.mvp.views.View;
import rx.Subscription;

public class MovieDetailPresenter implements Presenter{

    private MovieDetailView mMovieDetailView;
    private final MovieDetailsUsecase mGetMovieInformationUsecase;
    private Subscription mMovieSubscription;
    private int mMovieId;
    private String mOriginalTitle;

    @Inject
    public MovieDetailPresenter(MovieDetailsUsecase getMovieInformationUsecase) {
        mGetMovieInformationUsecase = getMovieInformationUsecase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (!mMovieSubscription.isUnsubscribed())
            mMovieSubscription.unsubscribe();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mMovieDetailView = (MovieDetailView) v;
    }

    @Override
    public void onCreate() {
        askForCharacterDetails();
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public void initializePresenter(int characterId, String originalTitle) {
        mMovieId = characterId;
        mOriginalTitle = originalTitle;
    }

    public void askForCharacterDetails() {
        mMovieSubscription = mGetMovieInformationUsecase.execute()
                .subscribe(movie -> {
                    Log.d("FILME ID", movie.toString());
                    mMovieDetailView.bindCharacter(movie);
                }, error -> {

                });
    }
}
