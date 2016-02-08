
package diones.filmes.com.filmes.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import diones.filmes.com.filmes.views.activities.MainActivity;
import diones.filmes.com.filmes.views.adapter.MovieAdapter;
import diones.filmes.com.filmes.views.adapter.MoviesListAdapter;

public class MovieFragment extends Fragment implements MovieView{

    @Bind(R.id.viewpagerMovie)     ViewPager mViewPager;
    @Bind(R.id.tabsMovie)          TabLayout mTabLayout;
    @Bind(R.id.fabMovie)           FloatingActionButton mFloatingActionButton;
    @Bind(R.id.recyclerViewMovies) RecyclerView mRecyclerViewMovies;

    @Inject
    MoviePresenter mMoviePresenter;

    private MoviesListAdapter mMovieListAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        initUi(view);
        initializeToolbar();
        initializeTabLayout();
        initializeRecyclerView();
        initializeDependencyInjector();
        initializePresenter();

        return view;
    }

    private void initUi(View view) {
        ButterKnife.bind(this, view);
    }

    private void initializeDependencyInjector() {
        MoviesApplication moviesApplication = (MoviesApplication) getActivity().getApplication();

        DaggerMoviesComponent.builder()
                .activityModule(new ActivityModule(getActivity().getApplicationContext()))
                .appComponent(moviesApplication.getAppComponent())
                .build().inject(this);
    }

    private void initializeToolbar() {

    }

    private void initializePresenter() {
        mMoviePresenter.attachView(this);
        mMoviePresenter.onCreate();
    }

    private void initializeTabLayout() {

        if (mViewPager != null)
            setupViewPager(mViewPager);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        MovieAdapter adapter = new MovieAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new PopularFragment(), "Popular");
        adapter.addFragment(new RecenteFragment(), "Recente");
        adapter.addFragment(new EmBreveFragment(), "Em Breve");
        viewPager.setAdapter(adapter);
    }

    private void initializeRecyclerView() {
        mRecyclerViewMovies.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public void showWelcomeMessage(String message) {

    }

    @Override
    public void bindFilmeList(List<Movie> movies) {
        mMovieListAdapter = new MoviesListAdapter(movies, getContext());
        mRecyclerViewMovies.setAdapter(mMovieListAdapter);
    }
}
