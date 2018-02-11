package movieapp.mina.com.themoviedbapp.screens.details;

import javax.inject.Inject;

import movieapp.mina.com.themoviedbapp.R;
import movieapp.mina.com.themoviedbapp.data.MoviesRepository;
import movieapp.mina.com.themoviedbapp.models.Movie;

/**
 * Created by mina on 12/1/17.
 */

public class DetailsPresenter implements DetailsContract.Presenter {

    private final MoviesRepository mMoviesRepository;
    private DetailsContract.View mView;

    @Inject
    public DetailsPresenter(MoviesRepository moviesRepository) {
        mMoviesRepository = moviesRepository;
    }

    @Override
    public void attachView(DetailsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void loadMovieDetails(long movieId) {
        mMoviesRepository.getMovieDetails(movieId, new MoviesRepository.MovieDetailsListener() {
            @Override
            public void OnMovieDetailsReady(Movie movie) {
                mView.updateMovieDetails(movie);
            }

            @Override
            public void onError(Throwable t) {
                mView.showErrorMessage(R.string.error_fetching_movie_details);
            }
        });
    }
}
