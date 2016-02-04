package diones.filmes.com.filmes.injector.components;

import javax.inject.Singleton;

import dagger.Component;
import diones.filmes.com.filmes.FilmesApplication;
import diones.filmes.com.filmes.injector.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    FilmesApplication app();
    //Repository dataRepository();
}