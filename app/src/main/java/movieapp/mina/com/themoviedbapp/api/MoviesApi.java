package movieapp.mina.com.themoviedbapp.api;

import io.reactivex.Single;
import movieapp.mina.com.themoviedbapp.models.Movie;
import movieapp.mina.com.themoviedbapp.models.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mina on 11/30/17.
 */

public interface MoviesApi {
    @GET("discover/movie?language=en-US&sort_by=popularity.desc&include_video=false&include_adult=false")
    Single<MovieResponse> getMovies(@Query("api_key") String apiKey,
                                    @Query("page") int page,
                                    @Query("primary_release_date.lte") String upperBound,
                                    @Query("primary_release_date.gte") String dateFilter);

    @GET("movie/{movie_id}")
    Single<Movie> getMovie(@Path("movie_id") long movieId,
                         @Query("api_key") String apiKey);

}
