package diones.filmes.com.filmes.views.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import diones.filmes.com.filmes.MoviesApplication;
import diones.filmes.com.filmes.injector.components.DaggerMovieListComponent;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.views.fragments.MovieListFragment;
import diones.filmes.com.filmes.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)             Toolbar mToolbar;
    @BindView(R.id.drawer_layout)       DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)            NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initializeFirstFragment();
        initializeToolbar();
        initializeNavigationView();
        initializeDependencyInjector();

    }

    private void initUi() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void initializeFirstFragment() {
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.frameLayoutContent, new MovieListFragment()).commit();
    }

    private void initializeToolbar() {
        setSupportActionBar(mToolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initializeNavigationView() {
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);

            mActionBarDrawerToggle = setupDrawerToggle();
            mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        }
    }

    private void initializeDependencyInjector() {
        MoviesApplication moviesApplication = (MoviesApplication) getApplication();

        DaggerMovieListComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(moviesApplication.getAppComponent())
                .build().inject(this);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

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

        Intent intent;
        FragmentTransaction fragmentManager;

        switch(menuItem.getItemId()) {
            case R.id.nav_movie:
                fragmentManager = getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.frameLayoutContent, new MovieListFragment()).commit();
                break;
            case R.id.nav_settings:
                intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);

            default:
                fragmentManager = getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.frameLayoutContent, new MovieListFragment()).commit();
        }


        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

}
