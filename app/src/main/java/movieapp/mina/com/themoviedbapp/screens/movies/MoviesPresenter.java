package movieapp.mina.com.themoviedbapp.screens.movies;

import java.util.Date;

import javax.inject.Inject;

import movieapp.mina.com.themoviedbapp.R;
import movieapp.mina.com.themoviedbapp.data.MoviesRepository;
import movieapp.mina.com.themoviedbapp.models.MovieResponse;

/**
 * Created by mina on 11/30/17.
 */

public class MoviesPresenter implements MoviesContract.Presenter {

    private final MoviesRepository mMoviesRepository;

    private MoviesContract.View mView;

    @Inject
    public MoviesPresenter(MoviesRepository moviesRepository) {
        mMoviesRepository = moviesRepository;
    }

    @Override
    public void attachView(MoviesContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void loadMovies(int page, Date dateFilter) {
        mMoviesRepository.getMovies(page, dateFilter, new MoviesRepository.MovieListListener() {
            @Override
            public void onMovieListResponse(MovieResponse movieResponse) {
                mView.updateMovieList(movieResponse);
            }

            @Override
            public void onError(Throwable t) {
                mView.showErrorMessage(R.string.error_fetching_movies);
            }
        });
    }
}
