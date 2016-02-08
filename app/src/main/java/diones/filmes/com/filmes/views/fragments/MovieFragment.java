
package diones.filmes.com.filmes.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import diones.filmes.com.filmes.R;
import diones.filmes.com.filmes.views.adapter.MovieAdapter;

public class MovieFragment extends Fragment {

    @Bind(R.id.viewpagerMovie)    ViewPager mViewPager;
    @Bind(R.id.tabsMovie)         TabLayout mTabLayout;
    @Bind(R.id.fabMovie)          FloatingActionButton mFloatingActionButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        initUi(view);
        initializeToolbar();
        initializeTabLayout();

        return view;
    }

    private void initUi(View view) {
        ButterKnife.bind(this, view);
    }

    private void initializeToolbar() {

    }

    private void initializeTabLayout() {

        if (mViewPager != null)
            setupViewPager(mViewPager);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        MovieAdapter adapter = new MovieAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new MovieFragment(), "Popular");
        adapter.addFragment(new MovieFragment(), "Recente");
        adapter.addFragment(new MovieFragment(), "Em Breve");
        viewPager.setAdapter(adapter);
    }

}
