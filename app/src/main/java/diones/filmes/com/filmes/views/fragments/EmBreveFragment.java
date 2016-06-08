package diones.filmes.com.filmes.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import diones.filmes.com.filmes.MoviesApplication;
import diones.filmes.com.filmes.R;
import diones.filmes.com.filmes.injector.components.DaggerMoviesComponent;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.mvp.presenters.MovieEmBrevePresenter;
import diones.filmes.com.filmes.mvp.views.MovieView;
import diones.filmes.com.filmes.views.activities.DetailMovieActivity;
import diones.filmes.com.filmes.views.adapter.MoviesListAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


public class EmBreveFragment extends Fragment implements MovieView{

    @BindView(R.id.recyclerViewMoviesEmBreve)      RecyclerView mRecyclerViewMovies;
    @BindView(R.id.movie_error_view)               View mErrorView;

    @Inject MovieEmBrevePresenter mMoviePresenter;
    private MoviesListAdapter mMovieListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_em_breve, container, false);

        initUi(view);
        initializeDependencyInjector();
        initializeRecyclerView();
        initializePresenter();

        return view;
    }

    @Override
    public void onPause() {
        super.onStop();
        mMoviePresenter.onPause();
    }

    private void initUi(View view) {
        ButterKnife.bind(this, view);
    }

    private void initializeRecyclerView() {
        mRecyclerViewMovies.setHasFixedSize(true);
        mRecyclerViewMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewMovies.addOnScrollListener(mOnScrollListener);
    }

    private void initializePresenter() {
        mMoviePresenter.attachView(this);
        mMoviePresenter.onCreate();
    }

    private void initializeDependencyInjector() {
        MoviesApplication moviesApplication = (MoviesApplication) getActivity().getApplication();

        DaggerMoviesComponent.builder()
                .activityModule(new ActivityModule(getActivity().getApplicationContext()))
                .appComponent(moviesApplication.getAppComponent())
                .build().inject(this);
    }

    @OnClick(R.id.view_error_retry_button) void onRetryButtonClicked(View v) {
        mMoviePresenter.onErrorRetryRequest();
    }

    @Override
    public void showConnectionErrorMessage() {

    }

    @Override
    public void showServerErrorMessage() {

    }

    @Override
    public void showUknownErrorMessage() {
        TextView errorTextView = ButterKnife.findById(mErrorView, R.id.view_error_message);
        errorTextView.setText(R.string.error_loading_movies);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLightError() {
        Snackbar.make(mRecyclerViewMovies, getString(R.string.error_loading_movies), Snackbar.LENGTH_LONG)
                .setAction(R.string.try_again, v -> mMoviePresenter.onErrorRetryRequest())
                .show();
    }

    @Override
    public void bindMovieList(List<Movie> movies) {
        mMovieListAdapter = new MoviesListAdapter(movies, getContext(), (position, sharedView, imageViewMovie) -> mMoviePresenter.onElementClick(position, imageViewMovie));
        mRecyclerViewMovies.setAdapter(new ScaleInAnimationAdapter(mMovieListAdapter));
    }

    @Override
    public void showDetailScreen(Movie movie, ImageView imageViewMovie) {
        DetailMovieActivity.start(getContext(), movie, imageViewMovie);
    }

    @Override
    public void updateMoviesList(int moviesAdded) {
        mMovieListAdapter.notifyItemRangeInserted(mMovieListAdapter.getItemCount() + moviesAdded, moviesAdded);
    }

    @Override
    public void hideErrorView() {
        mErrorView.setVisibility(View.GONE);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemsCount   = layoutManager.getChildCount();
            int totalItemsCount     = layoutManager.getItemCount();
            int firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();

            if (visibleItemsCount + firstVisibleItemPos >= totalItemsCount) {
                mMoviePresenter.onListEndReached();
            }
        }
    };
}
