package diones.filmes.com.filmes.injector.components;

import android.content.Context;

import dagger.Component;
import diones.filmes.com.filmes.injector.Activity;
import diones.filmes.com.filmes.injector.modules.ActivityModule;

@Activity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Context context();
}
