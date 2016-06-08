package diones.filmes.com.filmes.mvp.presenters;

import javax.inject.Inject;

import diones.filmes.com.filmes.domain.MovieDetailUsecase;
import diones.filmes.com.filmes.mvp.views.MovieDetailView;
import diones.filmes.com.filmes.mvp.views.View;
import rx.Subscription;

public class MovieDetailPresenter implements Presenter{

    private MovieDetailView mMovieDetailView;
    private final MovieDetailUsecase mGetMovieInformationUsecase;
    private Subscription mMovieSubscription;
    private int mMovieId;
    private String mOriginalTitle;

    @Inject
    public MovieDetailPresenter(MovieDetailUsecase getMovieInformationUsecase) {
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

    public void initializePresenter(int characterId) {
        mMovieId = characterId;
    }

    public void askForCharacterDetails() {
        mMovieSubscription = mGetMovieInformationUsecase.execute()
                .subscribe(movie -> {
                    mMovieDetailView.bindMovie(movie);
                }, error -> {

                });
    }
}
