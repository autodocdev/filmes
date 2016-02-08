package diones.filmes.com.filmes.injector.components;

import android.content.Context;

import dagger.Component;
import diones.filmes.com.filmes.injector.Activity;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.views.activities.MainActivity;

@Activity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface MoviesComponent extends ActivityComponent {

    void inject (MainActivity activity);

    Context activityContext();

}

