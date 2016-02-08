package diones.filmes.com.filmes.views.activities;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import diones.filmes.com.filmes.MoviesApplication;
import diones.filmes.com.filmes.injector.components.DaggerMoviesComponent;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.model.entities.Movie;
import diones.filmes.com.filmes.mvp.presenters.MoviePresenter;
import diones.filmes.com.filmes.mvp.views.MovieView;
import diones.filmes.com.filmes.views.adapter.MoviesListAdapter;
import diones.filmes.com.filmes.views.fragments.MovieFragment;
import diones.filmes.com.filmes.R;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)             Toolbar mToolbar;
    @Bind(R.id.drawer_layout)       DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)            NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initializeToolbar();
        initializeNavigationView();
        initializeDependencyInjector();

    }

    private void initializeNavigationView() {
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }
    }

    private void initializeToolbar() {
        setSupportActionBar(mToolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initializeDependencyInjector() {
        MoviesApplication moviesApplication = (MoviesApplication) getApplication();

        DaggerMoviesComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(moviesApplication.getAppComponent())
                .build().inject(this);
    }

    private void initUi() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;

        switch(menuItem.getItemId()) {
            case R.id.nav_movie:
                fragment = new MovieFragment();
                break;
            case R.id.nav_series:
                fragment = new MovieFragment();
            default:
                fragment = new MovieFragment();
        }

        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.frameLayoutContent, fragment).commit();

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}
