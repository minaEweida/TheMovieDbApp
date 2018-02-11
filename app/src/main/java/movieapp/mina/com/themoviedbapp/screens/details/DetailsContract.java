package movieapp.mina.com.themoviedbapp.screens.details;

import java.util.List;

import movieapp.mina.com.themoviedbapp.BaseContract;
import movieapp.mina.com.themoviedbapp.models.Movie;
import movieapp.mina.com.themoviedbapp.screens.movies.MoviesContract;

/**
 * Created by mina on 12/1/17.
 */

public interface DetailsContract {
    interface Presenter extends BaseContract.BasePresenter<DetailsContract.View> {
        void loadMovieDetails(long movieId);
    }

    interface View extends BaseContract.BaseView {
        void updateMovieDetails(Movie movie);
    }
}
