package diones.filmes.com.filmes;

import android.app.Application;

import diones.filmes.com.filmes.injector.modules.AppModule;
import diones.filmes.com.filmes.injector.components.AppComponent;
import diones.filmes.com.filmes.injector.components.DaggerAppComponent;

public class MoviesApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {

        return mAppComponent;
    }
}
