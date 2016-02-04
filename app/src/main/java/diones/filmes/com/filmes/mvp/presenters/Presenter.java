package diones.filmes.com.filmes.mvp.presenters;


import diones.filmes.com.filmes.mvp.views.View;

public interface Presenter {
    void onStart();

    void onStop();

    void onPause();

    void attachView (View v);

    void onCreate();
}
