package movieapp.mina.com.themoviedbapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

import movieapp.mina.com.themoviedbapp.builder.component.ApplicationComponent;
import movieapp.mina.com.themoviedbapp.builder.component.DaggerApplicationComponent;
import movieapp.mina.com.themoviedbapp.builder.module.ApplicationModule;
import movieapp.mina.com.themoviedbapp.builder.module.NetworkModule;

/**
 * Created by mina on 11/30/17.
 */

public class MoviesDbApplication extends Application {

    protected ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }

    public ApplicationComponent getApplicationComponent() {
        if(mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .networkModule(new NetworkModule(BuildConfig.BASE_URL, getString(R.string.TMDB_API_KEY), BuildConfig.DEBUG))
                    .build();
        }

        return mApplicationComponent;
    }
}
