package diones.filmes.com.filmes.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
import diones.filmes.com.filmes.utils.Utils;

public class DetailMovieActivity extends AppCompatActivity implements MovieDetailView {

    public static final String EXTRA_MOVIE_ID           = "movie.id";
    public static final String EXTRA_MOVIE_IMAGE        = "movie.image";
    @BindInt(R.integer.duration_medium)                 int mAnimMediumDuration;
    @BindInt(R.integer.duration_huge)                   int mAnimHugeDuration;
    @BindColor(R.color.colorPrimaryDark)                int mColorPrimaryDark;
    @Bind(R.id.collapsing_toolbar)                      CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.overview)                                TextView mOverview;
    @Bind(R.id.ratingMovie)                             RatingBar mRatingMovie;
    @Bind(R.id.imageMovie)                              ImageView mImageMovie;

    @Inject   MovieDetailPresenter mMovieDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initializeSharedImage();
        initializeDependencyInjector();
        initializeToolbar();
        initializeBinding();
        initializePresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeSharedImage() {
        String imagePoster = getIntent().getStringExtra(EXTRA_MOVIE_IMAGE);
        Glide.with(this)
                .load(Utils.getImageUrl(imagePoster))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) 
                .crossFade()
                .into(mImageMovie);
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

        mMovieDetailPresenter.attachView(this);
        mMovieDetailPresenter.setMovieId(characterId);
        mMovieDetailPresenter.initializePresenter(characterId);
        mMovieDetailPresenter.onCreate();
    }

    public static void start(Context context, Movie movie, ImageView imageViewMovie) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, imageViewMovie, "poster");

        Intent movieDetailItent = new Intent(context, DetailMovieActivity.class);
        movieDetailItent.putExtra(EXTRA_MOVIE_ID, movie.getId());
        movieDetailItent.putExtra(EXTRA_MOVIE_IMAGE, movie.getPoster_path());
        ActivityCompat.startActivity((Activity) context, movieDetailItent, options.toBundle());
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void bindMovie(Movie movie) {

        collapsingToolbar.setTitle(movie.getOriginal_title());
        mOverview.setText(movie.getOverview());
        mRatingMovie.setRating(Float.parseFloat(movie.getVote_average()));

    }
}
