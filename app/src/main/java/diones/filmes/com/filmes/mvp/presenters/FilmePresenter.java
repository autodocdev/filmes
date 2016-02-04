package diones.filmes.com.filmes.mvp.presenters;

import javax.inject.Inject;

import diones.filmes.com.filmes.mvp.views.FilmesView;
import diones.filmes.com.filmes.mvp.views.View;

/**
 * Created by diones_xxx on 04/02/16.
 */
public class FilmePresenter implements Presenter {

    private FilmesView mFilmeView;

    @Inject
    public FilmePresenter(){

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mFilmeView = (FilmesView) v;
    }

    @Override
    public void onCreate() {
        mFilmeView.showWelcomeMessage("App Iniciado");
    }
}
