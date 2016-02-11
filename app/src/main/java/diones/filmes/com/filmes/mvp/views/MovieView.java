package diones.filmes.com.filmes.mvp.views;

import java.util.List;

import diones.filmes.com.filmes.model.entities.Movie;

public interface MovieView extends View {

    void showConnectionErrorMessage();

    void showServerErrorMessage();

    void showUknownErrorMessage();

    void bindFilmeList(List<Movie> movies);

    void showDetailScreen(int characterId);

}
