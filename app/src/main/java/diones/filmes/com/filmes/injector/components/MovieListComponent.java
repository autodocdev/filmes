package diones.filmes.com.filmes.injector.components;

import dagger.Component;
import diones.filmes.com.filmes.injector.Activity;
import diones.filmes.com.filmes.injector.modules.ActivityModule;
import diones.filmes.com.filmes.views.activities.MainActivity;
import diones.filmes.com.filmes.views.fragments.MovieListBreveFragment;
import diones.filmes.com.filmes.views.fragments.MovieListPopularFragment;

@Activity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface MovieListComponent extends ActivityComponent {

    void inject (MainActivity activity);
    void inject (MovieListPopularFragment fragmentPopular);
    void inject (MovieListBreveFragment fragmentEmBreve);

}

