package diones.filmes.com.filmes.mvp.views;

import android.widget.ImageView;

import java.util.List;

import diones.filmes.com.filmes.model.entities.Movie;

public interface MovieListView extends View {

    void showConnectionErrorMessage();

    void showServerErrorMessage();

    void showUknownErrorMessage();

    void showLightError();

    void bindMovieList(List<Movie> movies);

    void showDetailScreen(Movie movie, ImageView imageViewMovie);

    void updateMoviesList(int moviesLimit);

    void hideErrorView();
}
