package movieapp.mina.com.themoviedbapp.data;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.Single;
import movieapp.mina.com.themoviedbapp.api.MoviesApi;
import movieapp.mina.com.themoviedbapp.models.Movie;
import movieapp.mina.com.themoviedbapp.models.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is responsible for calling tmdb api and fetch movies
 *
 * Note: sorting was done by popularity because results looked more familiar
 * So for the sake of making the app look good, sort by popularity is used
 *
 * sortBy=popularity.desc
 *
 * However to see latest movies disregarding popularity then change sorting to be by release_date.desc
 *
 * sortBy=release_date.desc
 */

public class MoviesRepositoryImpl implements MoviesRepository {
    private final String TAG = MoviesRepositoryImpl.class.getSimpleName();

    private final String mApiKey;
    private final MoviesApi mApi;
    private SimpleDateFormat sdf;

    /**
     * Setting upper bound to release date to date to limit the latest movies
     *
     * Otherwise it will return movies planned in 2026 which is irrelevant for us in this app
     */
    private String upperBoundDate;

    public MoviesRepositoryImpl(String apiKey, MoviesApi api) {
        mApiKey = apiKey;
        mApi = api;
        sdf = new SimpleDateFormat("YYYY-MM-dd");
        Calendar c = Calendar.getInstance();
        upperBoundDate = sdf.format(c.getTime());
    }

    /**
     * Get movie list
     *
     * Service returns 20 results per page
     *
     * @param page
     *          page number to be fetched from the list
     * @param filter
     *          lower bound date filter for primary_release_date
     */
    @Override
    public Single<MovieResponse> getMovies(int page, Date filter) {
        //Create a retrofit call object
        String dateFilter = null;

        if (filter != null) {
            dateFilter = sdf.format(filter);
        }


        return mApi.getMovies(mApiKey, page, upperBoundDate, dateFilter);
    }

    /**
     * Fetches a specific movie detail
     *
     * @param movieId
     *          Movie Id for the movie to be fetched
     */
    @Override
    public Single<Movie> getMovieDetails(long movieId) {
        //Create a retrofit call object
        return mApi.getMovie(movieId, mApiKey);
    }
}
