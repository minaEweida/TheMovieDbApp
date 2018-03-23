package movieapp.mina.com.themoviedbapp.screens.movies;

import android.util.Log;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import movieapp.mina.com.themoviedbapp.R;
import movieapp.mina.com.themoviedbapp.data.MoviesRepository;

/**
 * Created by mina on 11/30/17.
 */

public class MoviesPresenter implements MoviesContract.Presenter {
    private static final String TAG = MoviesPresenter.class.getSimpleName();

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
        mMoviesRepository.getMovies(page, dateFilter).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (movieResponse, throwable) -> {
                            if(throwable != null) {
                                Log.e(TAG, "Error occurred while fetching movies", throwable);
                                mView.showErrorMessage(R.string.error_fetching_movies);
                                return;
                            }

                            if(movieResponse != null) {
                                Log.d(TAG, "Movies Response Success");
                                mView.updateMovieList(movieResponse);
                            } else {
                                Log.d(TAG, "No Movies returned");
                                mView.showErrorMessage(R.string.error_fetching_movies);
                            }
                        });
    }
}
