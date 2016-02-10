package diones.filmes.com.filmes.views.activities;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.ButterKnife;
import diones.filmes.com.filmes.MoviesApplication;
import diones.filmes.com.filmes.R;
import diones.filmes.com.filmes.injector.components.DaggerMovieInformationComponent;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.injector.modules.MovieInformationModule;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.mvp.presenters.MovieDetailPresenter;
import diones.filmes.com.filmes.mvp.views.MovieDetailView;
import diones.filmes.com.filmes.utils.OnMovieImageCallback;

public class DetailMovieActivity extends AppCompatActivity implements MovieDetailView {

    private static final String EXTRA_CHARACTER_NAME    = "character.name";
    public static final String EXTRA_MOVIE_ID           = "movie.id";
    @BindInt(R.integer.duration_medium)                 int mAnimMediumDuration;
    @BindInt(R.integer.duration_huge)                   int mAnimHugeDuration;
    @BindColor(R.color.colorPrimaryDark)                int mColorPrimaryDark;
    @Bind(R.id.collapsing_toolbar)                      CollapsingToolbarLayout collapsingToolbar;

    @Inject   MovieDetailPresenter mMovieDetailPresenter;

    private OnMovieImageCallback onMovieImageCallback = bitmap -> initActivityColors(bitmap);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initActivityColors(Bitmap sourceBitmap) {
        Palette.from(sourceBitmap)
                .generate(palette -> {
                    int darkVibrant = palette.getDarkVibrantColor(mColorPrimaryDark);

                    ValueAnimator colorAnimation = ValueAnimator.ofArgb(mColorPrimaryDark, darkVibrant);
                    colorAnimation.setDuration(mAnimHugeDuration);
                    colorAnimation.start();
                    getWindow().setStatusBarColor(darkVibrant);
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initializeDependencyInjector();
        initializeToolbar();
        initializeBinding();
        initializePresenter();
    }

    private void initializeDependencyInjector() {
        MoviesApplication avengersApplication = (MoviesApplication) getApplication();

        int avengerId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);

        DaggerMovieInformationComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(avengersApplication.getAppComponent())
                .movieInformationModule(new MovieInformationModule(avengerId))
                .build().inject(this);
    }

    private void initUi() {
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
    }

    private void initializeToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeBinding() {

    }

    private void initializePresenter() {
        int characterId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);
        String characterName = getIntent().getStringExtra(EXTRA_CHARACTER_NAME);

        mMovieDetailPresenter.attachView(this);
        mMovieDetailPresenter.setMovieId(characterId);
        mMovieDetailPresenter.initializePresenter(characterId, characterName);
        mMovieDetailPresenter.onCreate();
    }

    public static void start(Context context, String characterName, int movieId) {
        Intent movieDetailItent = new Intent(context, DetailMovieActivity.class);
        movieDetailItent.putExtra(EXTRA_CHARACTER_NAME, characterName);
        movieDetailItent.putExtra(EXTRA_MOVIE_ID, movieId);
        context.startActivity(movieDetailItent);
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void bindCharacter(Movie movie) {
        collapsingToolbar.setTitle(movie.getOriginal_title());
        Toast.makeText(getApplicationContext(),movie.getOriginal_title(), Toast.LENGTH_SHORT).show();
    }
}
