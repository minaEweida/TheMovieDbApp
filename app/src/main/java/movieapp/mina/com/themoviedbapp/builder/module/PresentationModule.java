package movieapp.mina.com.themoviedbapp.builder.module;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import movieapp.mina.com.themoviedbapp.data.MoviesRepository;
import movieapp.mina.com.themoviedbapp.screens.details.DetailsContract;
import movieapp.mina.com.themoviedbapp.screens.details.DetailsPresenter;
import movieapp.mina.com.themoviedbapp.screens.movies.MoviesContract;
import movieapp.mina.com.themoviedbapp.screens.movies.MoviesPresenter;

/**
 * Created by mina on 11/30/17.
 */
@Module
public class PresentationModule {

    @Inject
    MoviesRepository moviesRepository;

    public PresentationModule() {
    }

    @Provides
    @Singleton
    MoviesContract.Presenter provideMoviesPresenter() {
        return new MoviesPresenter(moviesRepository);
    }

    @Provides
    @Singleton
    DetailsContract.Presenter provideDetailsPresenter() {
        return new DetailsPresenter(moviesRepository);
    }
}
