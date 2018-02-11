package movieapp.mina.com.themoviedbapp.screens.details;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import movieapp.mina.com.themoviedbapp.MoviesDbApplication;
import movieapp.mina.com.themoviedbapp.R;
import movieapp.mina.com.themoviedbapp.models.Genre;
import movieapp.mina.com.themoviedbapp.models.Movie;
import movieapp.mina.com.themoviedbapp.screens.BaseActivity;

public class DetailsActivity extends BaseActivity implements DetailsContract.View {

    private static final String MOVIE = "movie";

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    @BindView(R.id.movie_details_layout)
    ConstraintLayout mDetailsLayout;

    @BindView(R.id.movie_title_text_view)
    AppCompatTextView mTitleTextView;

    @BindView(R.id.movie_vote_text_view)
    AppCompatTextView mVotesTextView;

    @BindView(R.id.movie_vote_count_text_view)
    AppCompatTextView mVoteCountTextView;

    @BindView(R.id.movie_release_date_text_view)
    AppCompatTextView mReleaseDateTextView;

    @BindView(R.id.movie_overview_text_view)
    AppCompatTextView mOverviewTextView;

    @BindView(R.id.movie_genre_text_view)
    AppCompatTextView mGenreTextView;

    @BindView(R.id.movie_poster_image_view)
    AppCompatImageView mPosterImageView;

    @Inject
    DetailsPresenter mPresenter;

    private SimpleDateFormat sdf;

    private long mMovieId;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.details_screen_title);

        ((MoviesDbApplication) getApplication()).getApplicationComponent().inject(this);

        sdf = new SimpleDateFormat(getString(R.string.release_date_format));

        mPresenter.attachView(this);

        mMovieId = getIntent().getLongExtra(EXTRA_MOVIE_ID, -1);

        if( savedInstanceState != null && savedInstanceState.containsKey(MOVIE) ) {
            mMovie = savedInstanceState.getParcelable(MOVIE);
            updateMovieDetails(mMovie);
        } else {
            loadMovieDetails(mMovieId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_details;
    }

    @Override
    public void onReloadButtonClick() {
        loadMovieDetails(mMovieId);
    }

    private void loadMovieDetails(long movieId) {
        hideError();

        showProgressBar();
        mPresenter.loadMovieDetails(movieId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if( mMovie != null ) {
            outState.putParcelable(MOVIE, mMovie);
        }
    }

    /**
     * This method updates the view with the movie details
     *
     * Callback called by presenter
     * when fetching movies details service succeeds
     *
     * @param movie
     *          Movie details
     */
    @Override
    public void updateMovieDetails(Movie movie) {
        hideError();

        if(movie != null) {
            hideProgressBar();
            mDetailsLayout.setVisibility(View.VISIBLE);

            mMovie = movie;
            String voteAverageString = String.format(getString(R.string.vote_average_text), Math.round(mMovie.getVoteAverage()));
            String voteCountString = String.format(getString(R.string.vote_count_text), mMovie.getVoteCount());

            StringBuilder sb = new StringBuilder();

            List<Genre> genreList = mMovie.getGenres();
            if (genreList != null && !genreList.isEmpty()) {
               for(int i = 0; i < genreList.size(); i++) {
                   sb.append(genreList.get(i).getName());

                   if(i < genreList.size() - 1) {
                       sb.append(", ");
                   }
               }
            }

            mTitleTextView.setText(mMovie.getTitle());
            mVotesTextView.setText(voteAverageString);
            mVoteCountTextView.setText(voteCountString);
            mReleaseDateTextView.setText(sdf.format(mMovie.getReleaseDate()));
            mOverviewTextView.setText(mMovie.getOverview());
            mGenreTextView.setText(sb.toString());

            Glide.with(this)
                    .load("http://image.tmdb.org/t/p/w500/" + mMovie.getPosterPath())
                    .into(mPosterImageView);
        }
    }

    /**
     * This method is used to show error message
     *
     * Callback called by the presenter when fetch movie details service fails
     * @param error
     *          error resource sent by presenter to be displayed by the view
     */
    @Override
    public void showErrorMessage(int error) {
        showError(getString(error));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
