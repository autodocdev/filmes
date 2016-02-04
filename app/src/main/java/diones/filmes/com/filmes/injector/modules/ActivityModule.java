package diones.filmes.com.filmes.injector.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import diones.filmes.com.filmes.injector.Activity;

@Module
public class ActivityModule {

    private final Context mContext;

    public ActivityModule(Context mContext) {

        this.mContext = mContext;
    }

    @Provides
    @Activity
    Context provideAcitivtyContext() {
        return  mContext;
    }
}