package diones.filmes.com.filmes.injector;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import diones.filmes.com.filmes.FilmesApplication;

@Module
public class AppModule {

    private final FilmesApplication mFilmesApplication;

    public AppModule(FilmesApplication filmesApplication) {

        this.mFilmesApplication = filmesApplication;
    }

    @Provides
    @Singleton
    FilmesApplication provideFilmesApplicationContext () { return mFilmesApplication; }

    /*@Provides
    @Singleton
    Repository provideDataRepository (RestDataSource restDataSource) { return restDataSource; }
    */
}
