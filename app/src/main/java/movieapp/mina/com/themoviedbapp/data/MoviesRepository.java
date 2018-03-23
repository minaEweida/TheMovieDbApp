package movieapp.mina.com.themoviedbapp.data;

import java.util.Date;

import io.reactivex.Single;
import movieapp.mina.com.themoviedbapp.models.Movie;
import movieapp.mina.com.themoviedbapp.models.MovieResponse;

/**
 * Created by mina on 11/30/17.
 */

public interface MoviesRepository {
    Single<MovieResponse> getMovies(int page, Date filter);
    Single<Movie> getMovieDetails(long movieId);
}
