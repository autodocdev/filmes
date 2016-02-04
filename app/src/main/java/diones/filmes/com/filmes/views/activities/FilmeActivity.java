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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import diones.filmes.com.filmes.FilmesApplication;
import diones.filmes.com.filmes.injector.components.DaggerFilmesComponent;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.mvp.presenters.FilmePresenter;
import diones.filmes.com.filmes.mvp.views.FilmesView;
import diones.filmes.com.filmes.views.fragments.LancamentoFragment;
import diones.filmes.com.filmes.R;

public class FilmeActivity extends AppCompatActivity implements FilmesView{

    @Bind(R.id.drawer_layout)     DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar)           Toolbar mToolbar;
    @Bind(R.id.viewpager)         ViewPager mViewPager;
    @Bind(R.id.nav_view)          NavigationView mNavigationView;
    @Bind(R.id.fab)               FloatingActionButton mFloatingActionButton;

    @Inject
    FilmePresenter mFilmePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initializeToolbar();
        initializeNavigationView();
        initializeTabLayout();
        //initializeRecyclerView();
        initializeDependencyInjector();
        initializePresenter();

    }

    private void initializeTabLayout() {

        if (mViewPager != null)
            setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new LancamentoFragment(), "Category 1");
        adapter.addFragment(new LancamentoFragment(), "Category 2");
        adapter.addFragment(new LancamentoFragment(), "Category 3");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    return true;
                });
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
        Snackbar.make(mFloatingActionButton, message, Snackbar.LENGTH_LONG).show();
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
