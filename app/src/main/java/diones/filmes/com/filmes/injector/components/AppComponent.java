package diones.filmes.com.filmes.injector.components;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import diones.filmes.com.filmes.MoviesApplication;
import diones.filmes.com.filmes.injector.modules.AppModule;
import diones.filmes.com.filmes.model.repository.Repository;
import rx.Scheduler;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    MoviesApplication app();
    Repository dataRepository();
}