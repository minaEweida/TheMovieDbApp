package movieapp.mina.com.themoviedbapp.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import movieapp.mina.com.themoviedbapp.R;
import movieapp.mina.com.themoviedbapp.models.Movie;

/**
 * Adapter Class for Movies
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final MovieClickListener mMovieClickListener;

    private final static int EMPTY_MOVIE_TYPE = 0;
    private final static int FILLED_MOVIE_TYPE = 1;
    
    private LayoutInflater mLayoutInflater;

    private List<Movie> mMovies;

    private SimpleDateFormat sdf;

    public interface MovieClickListener {
        void onMovieClickListener(Movie movie);
    }

    public MoviesAdapter(Context context, MovieClickListener movieClickListener) {
        mLayoutInflater = LayoutInflater.from(context);
        sdf = new SimpleDateFormat(context.getString(R.string.release_date_format));
        mMovieClickListener = movieClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = viewType == FILLED_MOVIE_TYPE ? R.layout.movie_view_holder : R.layout.layout_loading_item;
        View item = mLayoutInflater.inflate(layout, parent, false);

        return new MovieViewHolder(item);

    }

    /**
     * Returns the view type
     *
     *
     * @param position
     * @return
     * If the movie coressponding to the position is empty then return view type as EMPTY_MOVIE_TYPE
     * Else, returns FILLED_MOVIE_TYPE
     */
    @Override
    public int getItemViewType(int position) {
        if(mMovies != null && mMovies.size() > position) {
            if(mMovies.get(position).getId() == Movie.EMPTY_MOVIE_ID) {
                return EMPTY_MOVIE_TYPE;
            }
        }
        
        return FILLED_MOVIE_TYPE;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if (mMovies != null && mMovies.size() > position) {
            Movie movie = mMovies.get(position);

            // if EMPTY_MOVIE_ID, then it was used for loading, do not bind
            if(movie.getId() == Movie.EMPTY_MOVIE_ID)
                return;

            holder.setMovie(movie, mMovieClickListener);
        }
    }

    public void showLoading() {
        // if loading is already showing, do not show again
        if(loadingInProgress())
            return;

        if(mMovies != null) {
            Movie emptyMovie = new Movie();
            emptyMovie.setId(Movie.EMPTY_MOVIE_ID);
            
            mMovies.add(emptyMovie);
            notifyItemInserted(getItemCount() - 1);
        }
    }

    /**
     * Removes the last item in the list if it was a loading item
     */
    public void hideLoading() {
        if(loadingInProgress()) {
            mMovies.remove(getItemCount() - 1);
            notifyItemRemoved(getItemCount() - 1);
        }
    }

    /**
     * Checks if new page is being loaded
     *
     * @return
     *      true if loading is in progress
     */
    private boolean loadingInProgress() {
        if(mMovies != null) {
            if(mMovies.size() > 0 && mMovies.get(getItemCount() - 1).getId() == Movie.EMPTY_MOVIE_ID) {
                return true;
            }
        }

        return false;
    }
    
    public void addMovies(List<Movie> movies) {

        if(mMovies == null) {
            mMovies = new ArrayList<>();
        }

        if(loadingInProgress()) {
            mMovies.remove(getItemCount() - 1);
        }
        
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public void clear() {
        mMovies = null;
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        View mView;
        AppCompatTextView titleTextView;
        AppCompatTextView votesTextView;
        AppCompatTextView voteCountTextView;
        AppCompatTextView releaseDateTextView;
        AppCompatImageView posterImageView;
        ProgressBar progressBar;

        Movie movie;


        public MovieViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            progressBar = mView.findViewById(R.id.movies_progress_bar);

            // if progress bar found, then it's a loading item
            if(progressBar != null)
                return;

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMovieClickListener.onMovieClickListener(movie);
                }
            });
            titleTextView = mView.findViewById(R.id.movie_title_text_view);
            votesTextView = mView.findViewById(R.id.movie_vote_text_view);
            voteCountTextView = mView.findViewById(R.id.movie_vote_count_text_view);
            posterImageView = mView.findViewById(R.id.movie_poster_image_view);
            releaseDateTextView = mView.findViewById(R.id.movie_release_date_text_view);
        }

        public void setMovie(Movie movie, MovieClickListener movieClickListener) {
            this.movie = movie;

            String voteAverageString = String.format(mView.getContext().getString(R.string.vote_average_text), Math.round(movie.getVoteAverage()));
            String voteCountString = String.format(mView.getContext().getString(R.string.vote_count_text), movie.getVoteCount());

            titleTextView.setText(movie.getTitle());
            votesTextView.setText(voteAverageString);
            voteCountTextView.setText(voteCountString);

            releaseDateTextView.setText(sdf.format(movie.getReleaseDate()));

            Glide.with(mView.getContext())
                    .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
                    .into(posterImageView);
        }
    }
}
