package udacity.nanodegree.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yehia on 19/12/16.
 */

public class ReviewResponse
{
    @SerializedName("id")
    private String id;

    @SerializedName("results")
    private List<Review> reviews;

    @SerializedName("page")
    private String page;

    @SerializedName("total_pages")
    private String totalPages;

    @SerializedName("total_results")
    private String totalResults;


    public void setId(String id) {
        this.id = id;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getId() {
        return id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getPage() {
        return page;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public String getTotalResults() {
        return totalResults;
    }
}
