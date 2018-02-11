package movieapp.mina.com.themoviedbapp.builder.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import movieapp.mina.com.themoviedbapp.api.MoviesApi;
import movieapp.mina.com.themoviedbapp.api.MoviesApiFactory;
import movieapp.mina.com.themoviedbapp.data.MoviesRepository;
import movieapp.mina.com.themoviedbapp.data.MoviesRepositoryImpl;

/**
 * Created by mina on 11/30/17.
 */
@Module
public class NetworkModule {
    private String mBaseUrl;
    private String mApiKey;
    private boolean mDebug;

    public NetworkModule(String baseUrl, String apiKey, boolean debug) {
        this.mBaseUrl = baseUrl;
        this.mApiKey = apiKey;
        this.mDebug = debug;
    }

    @Provides
    @Singleton
    MoviesApi provideMvpStarterService() {
        return MoviesApiFactory.createRetroFitService(mBaseUrl, mDebug);
    }

    @Provides
    @Singleton
    MoviesRepository provideMoviesRepository(final MoviesApi api) {
        return new MoviesRepositoryImpl(mApiKey, api);
    }
}
