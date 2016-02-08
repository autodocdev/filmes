package diones.filmes.com.filmes.mvp.views;

import java.util.List;

import diones.filmes.com.filmes.model.entities.Movie;

/**
 * Created by diones_xxx on 04/02/16.
 */
public interface MovieView extends View {

    void showConnectionErrorMessage();

    void showServerErrorMessage();

    void showUknownErrorMessage();

    void showWelcomeMessage(String message);

    void bindFilmeList(List<Movie> movies);

}
