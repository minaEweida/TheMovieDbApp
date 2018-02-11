package movieapp.mina.com.themoviedbapp.data;

import java.util.Date;

import movieapp.mina.com.themoviedbapp.models.Movie;
import movieapp.mina.com.themoviedbapp.models.MovieResponse;

/**
 * Created by mina on 11/30/17.
 */

public interface MoviesRepository {
    void getMovies(int page, Date filter, MovieListListener movieListListener);
    void getMovieDetails(long movieId, MovieDetailsListener movieDetailsListener);

    interface MovieListListener {
        void onMovieListResponse(MovieResponse movieResponse);
        void onError(Throwable t);
    }

    interface MovieDetailsListener {
        void OnMovieDetailsReady(Movie movie);
        void onError(Throwable t);
    }
}
