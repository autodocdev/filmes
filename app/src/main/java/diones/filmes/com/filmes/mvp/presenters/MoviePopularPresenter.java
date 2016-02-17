package diones.filmes.com.filmes.mvp.presenters;

import android.util.Log;

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
        mFilmesSubscription.unsubscribe();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mMovieView = (MovieView) v;
    }

    @Override
    public void onCreate() {
        askForPopular();

    }

    private void askForPopular() {
        Log.d("FILMES POPULAR PEDIDO", "PEDINDO FILMES>>>>");
        mFilmesSubscription = mMoviesUseCase.execute()
                .subscribe(movies -> {
                    Log.d("FILMES POPULAR LOADED", movies+"");
                    mMovies.addAll(movies);
                    mMovieView.bindMovieList(mMovies);
                }, error -> {

                });
    }

    public void onElementClick(int position) {
        int characterId = Integer.parseInt(mMovies.get(position).getId());
        mMovieView.showDetailScreen(characterId);
    }
}
