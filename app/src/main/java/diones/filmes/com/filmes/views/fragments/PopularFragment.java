package diones.filmes.com.filmes.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import diones.filmes.com.filmes.MoviesApplication;
import diones.filmes.com.filmes.R;
import diones.filmes.com.filmes.injector.components.DaggerMoviesComponent;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.mvp.presenters.MoviePresenter;
import diones.filmes.com.filmes.mvp.views.MovieView;
import diones.filmes.com.filmes.views.activities.DetailMovieActivity;
import diones.filmes.com.filmes.views.adapter.MoviesListAdapter;


public class PopularFragment extends Fragment implements MovieView{

    @Bind(R.id.recyclerViewMovies)      RecyclerView mRecyclerViewMovies;

    @Inject MoviePresenter mMoviePresenter;
    private MoviesListAdapter mMovieListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        initUi(view);
        initializeDependencyInjector();
        initializeRecyclerView();
        initializePresenter();

        return view;
    }

    private void initUi(View view) {
        ButterKnife.bind(this, view);
    }

    private void initializeRecyclerView() {
        mRecyclerViewMovies.setHasFixedSize(true);
        mRecyclerViewMovies.setLayoutManager(new LinearLayoutManager(getContext()));
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

    @Override
    public void showConnectionErrorMessage() {

    }

    @Override
    public void showServerErrorMessage() {

    }

    @Override
    public void showUknownErrorMessage() {

    }

    @Override
    public void bindFilmeList(List<Movie> movies) {
        mMovieListAdapter = new MoviesListAdapter(movies, getContext(), (position, sharedView, characterImageView) -> {
            mMoviePresenter.onElementClick(position);
        });
        mRecyclerViewMovies.setAdapter(mMovieListAdapter);
    }

    @Override
    public void showDetailScreen(int characterId) {
        DetailMovieActivity.start(getContext(), characterId);
    }
}
