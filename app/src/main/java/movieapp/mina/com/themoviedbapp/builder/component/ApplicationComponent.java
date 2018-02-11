package movieapp.mina.com.themoviedbapp.builder.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import movieapp.mina.com.themoviedbapp.builder.module.ApplicationModule;
import movieapp.mina.com.themoviedbapp.builder.module.NetworkModule;
import movieapp.mina.com.themoviedbapp.screens.details.DetailsActivity;
import movieapp.mina.com.themoviedbapp.screens.movies.MoviesActivity;

/**
 * Created by mina on 11/30/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    Application application();

    void inject(MoviesActivity moviesActivity);
    void inject(DetailsActivity detailsActivity);
}
