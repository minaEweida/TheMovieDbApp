package movieapp.mina.com.themoviedbapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by mina on 11/30/17.
 *
 *
 *         {
             "vote_count": 2064,
             "id": 284053,
             "video": false,
             "vote_average": 7.5,
             "title": "Thor: Ragnarok",
             "popularity": 743.969937,
             "poster_path": "/oSLd5GYGsiGgzDPKTwQh7wamO8t.jpg",
             "original_language": "en",
             "original_title": "Thor: Ragnarok",
             "genre_ids": [
             28,
             12,
             35,
             14,
             878
             ],
             "backdrop_path": "/5wNUJs23rT5rTBacNyf5h83AynM.jpg",
             "adult": false,
             "overview": "Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the prophecy of destruction to his homeworld and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela.",
             "release_date": "2017-10-25"
            }
 */

public class Movie implements Parcelable {

    public final static long EMPTY_MOVIE_ID = -1;

    private long id;
    private String title;
    private String overview;
    private double popularity;
    private List<Genre> genres;

    @SerializedName("vote_count")
    private long voteCount;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private Date releaseDate;

    public Movie() {

    }

    protected Movie(Parcel in) {
        id = in.readLong();

        // if EMPTY_MOVIE_ID, then it was just used for loading so do not read
        if(id == EMPTY_MOVIE_ID)
            return;

        voteCount = in.readLong();
        title = in.readString();
        originalTitle = in.readString();
        releaseDate = (Date) in.readSerializable();
        posterPath = in.readString();
        overview = in.readString();
        popularity = in.readDouble();
        voteAverage = in.readDouble();

        in.readList(genres, List.class.getClassLoader());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);

        // if EMPTY_MOVIE_ID, then it was just used for loading so do not write
        if(id == EMPTY_MOVIE_ID)
            return;

        dest.writeLong(voteCount);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeSerializable(releaseDate);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeDouble(popularity);
        dest.writeDouble(voteAverage);

        if(genres != null) {
            dest.writeList(genres);
        }
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nOverview: " + overview + "\nPopularity: " + popularity + "\nRelease Date: " + releaseDate;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public List<Genre> getGenres() {
        return genres;
    }
}
