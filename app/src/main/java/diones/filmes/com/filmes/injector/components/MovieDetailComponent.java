package diones.filmes.com.filmes.injector.components;

import dagger.Component;
import diones.filmes.com.filmes.injector.Activity;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.injector.modules.MovieInformationModule;
import diones.filmes.com.filmes.views.activities.MovieDetailActivity;

@Activity
@Component(dependencies = AppComponent.class, modules = {MovieInformationModule.class, ActivityModule.class})
public interface MovieDetailComponent extends ActivityComponent{

    void inject (MovieDetailActivity detailActivity);

}
