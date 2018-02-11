package movieapp.mina.com.themoviedbapp.screens.movies;

import java.util.Date;
import java.util.List;

import movieapp.mina.com.themoviedbapp.BaseContract;
import movieapp.mina.com.themoviedbapp.models.Movie;
import movieapp.mina.com.themoviedbapp.models.MovieResponse;

/**
 * Created by mina on 11/30/17.
 */

public interface MoviesContract {
    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadMovies(int page, Date filter);
    }

    interface View extends BaseContract.BaseView {
        void updateMovieList(MovieResponse movieResponse);
    }
}
