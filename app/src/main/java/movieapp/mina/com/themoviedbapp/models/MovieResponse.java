package movieapp.mina.com.themoviedbapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mina on 11/30/17.
 */

public class MovieResponse implements Parcelable {
    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    private int page;

    private List<Movie> results;

    public MovieResponse() {

    }

    protected MovieResponse(Parcel in) {
        totalResults = in.readInt();
        totalPages = in.readInt();
        page = in.readInt();
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalResults);
        dest.writeInt(totalPages);
        dest.writeInt(page);
        dest.writeTypedList(results);
    }
}
