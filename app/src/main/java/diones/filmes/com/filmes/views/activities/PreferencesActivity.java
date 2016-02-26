package diones.filmes.com.filmes.views.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import diones.filmes.com.filmes.R;

public class PreferencesActivity extends AppCompatActivity {
    
    @Bind(R.id.toolbar_configuracao) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initializeToolbar();
        initializePreferenceFragment();
        
    }

    private void initializePreferenceFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.content_configuracao, new PreferencesFragment())
                .commit();
    }

    private void initializeToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.label_settings));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initUi() {
        setContentView(R.layout.activity_preferences);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PreferencesFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}
