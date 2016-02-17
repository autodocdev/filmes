package diones.filmes.com.filmes.injector.components;

import android.content.Context;

import dagger.Component;
import diones.filmes.com.filmes.injector.Activity;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.views.activities.MainActivity;
import diones.filmes.com.filmes.views.fragments.EmBreveFragment;
import diones.filmes.com.filmes.views.fragments.PopularFragment;

@Activity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface MoviesComponent extends ActivityComponent {

    void inject (MainActivity activity);
    void inject (PopularFragment fragmentPopular);
    void inject (EmBreveFragment fragmentEmBreve);

}

