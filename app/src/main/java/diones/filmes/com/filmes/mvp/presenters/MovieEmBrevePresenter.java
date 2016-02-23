package diones.filmes.com.filmes.mvp.presenters;

import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import diones.filmes.com.filmes.domain.RecenteEmBreveUsecase;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.mvp.views.MovieView;
import diones.filmes.com.filmes.mvp.views.View;
import rx.Subscription;

public class MovieEmBrevePresenter implements Presenter {

    private final RecenteEmBreveUsecase mMoviesUseCase;
    private Subscription mFilmesSubscription;

    private List<Movie> mMovies;
    private MovieView mMovieView;

    @Inject
    public MovieEmBrevePresenter(RecenteEmBreveUsecase moviesUseCase){
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
        askForEmBreve();

    }

    private void askForEmBreve() {
        mFilmesSubscription = mMoviesUseCase.execute()
                .subscribe(movies -> {
                    Log.d("FILMES EM BREVE LOADED", movies.toString());
                    mMovies.addAll(movies);
                    mMovieView.bindMovieList(mMovies);
                }, error -> {

                });
    }

    public void onElementClick(int position, ImageView imageViewMovie) {
        //int characterId = Integer.parseInt(mMovies.get(position).getId());
        mMovieView.showDetailScreen(mMovies.get(position), imageViewMovie);
    }
}
