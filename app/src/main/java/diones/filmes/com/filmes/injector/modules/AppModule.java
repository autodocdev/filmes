package diones.filmes.com.filmes.injector.modules;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import diones.filmes.com.filmes.MoviesApplication;
import diones.filmes.com.filmes.model.repository.Repository;
import diones.filmes.com.filmes.model.rest.RestDataSource;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class AppModule {

    private final MoviesApplication mMoviesApplication;

    public AppModule(MoviesApplication moviesApplication) {

        this.mMoviesApplication = moviesApplication;
    }

    @Provides
    @Singleton
    MoviesApplication provideFilmesApplicationContext () { return mMoviesApplication; }

    @Provides
    @Singleton
    Repository provideDataRepository (RestDataSource restDataSource) { return restDataSource; }

}
