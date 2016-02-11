package diones.filmes.com.filmes.mvp.views;

import android.graphics.Bitmap;

import diones.filmes.com.filmes.model.entities.Movie;

public interface MovieDetailView extends View{

    void showError(String s);

    void bindMovie(Movie movie);

}
