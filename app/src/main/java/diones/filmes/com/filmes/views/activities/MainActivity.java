package diones.filmes.com.filmes.views.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import diones.filmes.com.filmes.FilmesApplication;
import diones.filmes.com.filmes.injector.components.DaggerFilmesComponent;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.model.entities.Filme;
import diones.filmes.com.filmes.mvp.presenters.FilmePresenter;
import diones.filmes.com.filmes.mvp.views.FilmesView;
import diones.filmes.com.filmes.views.adapter.FilmesListAdapter;
import diones.filmes.com.filmes.views.fragments.PopularFragment;
import diones.filmes.com.filmes.R;

public class MainActivity extends AppCompatActivity implements FilmesView{

    @Bind(R.id.drawer_layout)     DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar)           Toolbar mToolbar;
    @Bind(R.id.nav_view)          NavigationView mNavigationView;

    @Inject
    FilmePresenter mFilmePresenter;

    private FilmesListAdapter mFilmeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initializeToolbar();
        initializeNavigationView();
        //initializeRecyclerView();
        initializeDependencyInjector();
        initializePresenter();

    }

    private void initializeNavigationView() {
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }
    }

    private void initializeDependencyInjector() {
        FilmesApplication filmesApplication = (FilmesApplication) getApplication();

        DaggerFilmesComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(filmesApplication.getAppComponent())
                .build().inject(this);
    }

    private void initializePresenter() {
        mFilmePresenter.attachView(this);
        mFilmePresenter.onCreate();
    }

    private void initializeToolbar() {
        setSupportActionBar(mToolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initUi() {
        setContentView(R.layout.activity_filme);
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

        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_movie:
                fragmentClass = PopularFragment.class;
                break;
            case R.id.nav_series:
                fragmentClass = PopularFragment.class;
            default:
                fragmentClass = PopularFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayoutContent, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
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
    //    Snackbar.make(mFloatingActionButton, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void bindFilmeList(List<Filme> filmes) {
        Toast.makeText(getApplicationContext(), filmes.toString(),Toast.LENGTH_LONG).show();
        mFilmeListAdapter = new FilmesListAdapter(filmes, this);
    }

}
