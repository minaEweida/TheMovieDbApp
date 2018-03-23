package movieapp.mina.com.themoviedbapp.screens.details;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import movieapp.mina.com.themoviedbapp.R;
import movieapp.mina.com.themoviedbapp.data.MoviesRepository;

/**
 * Created by mina on 12/1/17.
 */

public class DetailsPresenter implements DetailsContract.Presenter {
    private static final String TAG = DetailsPresenter.class.getSimpleName();

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
        mMoviesRepository.getMovieDetails(movieId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (movie, throwable) -> {
                            if(throwable != null) {
                                Log.e(TAG, "Error occurred while fetching movie details", throwable);
                                mView.showErrorMessage(R.string.error_fetching_movie_details);
                                return;
                            }

                            if(movie != null) {
                                Log.d(TAG, "Movie details Response Success");
                                mView.updateMovieDetails(movie);
                            } else {
                                Log.d(TAG, "No Movie details returned");
                                mView.showErrorMessage(R.string.error_fetching_movie_details);
                            }
                        });
    }
}
