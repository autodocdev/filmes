package diones.filmes.com.filmes.mvp.views;

import diones.filmes.com.filmes.model.entities.Movie;

public interface MovieDetailView extends View{

    void showError(String s);

    void bindCharacter(Movie movie);
}
