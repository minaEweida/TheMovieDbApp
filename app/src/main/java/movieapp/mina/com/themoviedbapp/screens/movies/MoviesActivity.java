package movieapp.mina.com.themoviedbapp.screens.movies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import movieapp.mina.com.themoviedbapp.MoviesDbApplication;
import movieapp.mina.com.themoviedbapp.R;
import movieapp.mina.com.themoviedbapp.adapter.MoviesAdapter;
import movieapp.mina.com.themoviedbapp.models.Movie;
import movieapp.mina.com.themoviedbapp.models.MovieResponse;
import movieapp.mina.com.themoviedbapp.screens.BaseActivity;
import movieapp.mina.com.themoviedbapp.screens.details.DetailsActivity;
import movieapp.mina.com.themoviedbapp.screens.filter.FilterActivity;

public class MoviesActivity extends BaseActivity implements MoviesContract.View {

    /**
     * Bundle constants
     */
    private static final String MOVIES = "movies";
    private static final String PAGE = "page";
    private static final String TOTAL_PAGES = "total_pages";

    /**
     * Filter constants
     */
    public static final String FILTER_DATE_DATA = "filter_date_data";
    public static final int FILTER_REQUEST_CODE = 101;


    /**
     * View members
     */
    @BindView(R.id.movies_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.filter_fab)
    FloatingActionButton mFilterFab;

    private LinearLayoutManager mLayoutManager;
    private MoviesAdapter mAdapter;



    /**
     * Presenter Members
     */
    @Inject
    MoviesPresenter mPresenter;
    private int mPage;
    private int mTotalPages;
    private Date mDateFilter;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        private boolean loading = false;
        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (mPage < mTotalPages) { // if pages to be fetched still in the range --> get next page
                // if not loading in progress and scroll is at the bottom, get new page
                if (!loading && mLayoutManager.getItemCount() > 0 && mLayoutManager.getItemCount() - mLayoutManager.findLastCompletelyVisibleItemPosition() <= 2) {
                    loading = true;
                    mAdapter.showLoading();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading = false;
                            loadMovies(mPage + 1, mDateFilter);
                        }
                    }, 1000);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MoviesDbApplication) getApplication()).getApplicationComponent().inject(this);

        mPresenter.attachView(this);

        mAdapter = new MoviesAdapter(getApplicationContext(), new MoviesAdapter.MovieClickListener() {
            @Override
            public void onMovieClickListener(Movie movie) {
                Intent intent = new Intent(MoviesActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_MOVIE_ID, movie.getId());
                startActivity(intent);
            }
        });

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        if(savedInstanceState != null && savedInstanceState.containsKey(MOVIES)) {
            MovieResponse movieResponse = new MovieResponse();
            movieResponse.setResults(savedInstanceState.<Movie>getParcelableArrayList(MOVIES));
            movieResponse.setPage(savedInstanceState.getInt(PAGE));
            movieResponse.setTotalPages(savedInstanceState.getInt(TOTAL_PAGES));
            updateMovieList(movieResponse);
        } else {
            initMovies();
        }

        mFilterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoviesActivity.this, FilterActivity.class);
                startActivityForResult(intent, FILTER_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRecyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mRecyclerView.removeOnScrollListener(onScrollListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PAGE, mPage);
        outState.putInt(TOTAL_PAGES, mTotalPages);
        if (mAdapter != null) {
            outState.putParcelableArrayList(MOVIES, (ArrayList<? extends Parcelable>) mAdapter.getMovies());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }

    private void initMovies() {
        mAdapter.clear();
        showProgressBar();
        loadMovies(1, mDateFilter);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_movies;
    }

    @Override
    public void onReloadButtonClick() {
        if(mAdapter != null) {
            // if there is data already in adapter, continue fetching next page, else call initMovies
            if(mAdapter.getItemCount() > 0) {
                loadMovies(mPage, mDateFilter);
            } else {
                initMovies();
            }
        }
    }

    /**
     * This method updated the list of movies
     *
     * Callback called by the presenter when discover service succeeds
     * @param movieResponse
     *              response from discover service containing the list of movies
     */
    @Override
    public void updateMovieList(MovieResponse movieResponse) {
        mPage = movieResponse.getPage();
        mAdapter.addMovies(movieResponse.getResults());
        mTotalPages = movieResponse.getTotalPages();
        hideProgressBar();

    }

    /**
     * This method is used to show error message
     *
     * Callback called by the presenter when discover service fails
     * @param error
     *          error resource sent by presenter to be displayed by the view
     */
    @Override
    public void showErrorMessage(int error) {
        mAdapter.hideLoading();
        showError(getString(error));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FILTER_REQUEST_CODE) {
            if(resultCode == FilterActivity.FILTER_RESULT_CODE) {
                // clear filter first
                mDateFilter = null;

                // set filter if returned
                if (data != null && data.hasExtra(FILTER_DATE_DATA)) {
                    mDateFilter = (Date) data.getSerializableExtra(FILTER_DATE_DATA);
                }

                initMovies();
            }
        }
    }

    /**
     * This method calls the presenter to fetch movies from Discover service
     * @param page
     *          indicates which page to be fetched from server
     * @param dateFilter
     *          if not null, used to be filter for primary_release_date
     */
    private void loadMovies(int page, Date dateFilter) {
        hideError();
        mPresenter.loadMovies(page, dateFilter);
    }
}
