package udacity.nanodegree.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yehia on 19/12/16.
 */

public class TrailerResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("results")
    private List<Trailer> trailers;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public List<Trailer> getTrailers()
    {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers)
    {
        this.trailers = trailers;
    }

}
