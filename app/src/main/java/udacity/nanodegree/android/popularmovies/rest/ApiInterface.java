package udacity.nanodegree.android.popularmovies.rest;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import udacity.nanodegree.android.popularmovies.model.MoviesResponse;

/**
 * Created by yehia on 17/12/16.
 */

public interface ApiInterface {

    @GET("movie/top_rated")
    Observable<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
