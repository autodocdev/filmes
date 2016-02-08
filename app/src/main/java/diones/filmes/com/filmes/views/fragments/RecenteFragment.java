package diones.filmes.com.filmes.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import diones.filmes.com.filmes.R;

/**
 * Created by diones_xxx on 08/02/16.
 */
public class RecenteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recente, container, false);

        return view;
    }
}
