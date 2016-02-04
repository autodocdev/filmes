package diones.filmes.com.filmes.injector.components;

import android.content.Context;

import dagger.Component;
import diones.filmes.com.filmes.injector.Activity;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.views.activities.FilmeActivity;

@Activity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface FilmesComponent extends ActivityComponent {

    void inject (FilmeActivity activity);

    Context activityContext();

}

