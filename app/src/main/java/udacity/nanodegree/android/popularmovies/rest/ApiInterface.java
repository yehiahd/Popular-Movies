package udacity.nanodegree.android.popularmovies.rest;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import udacity.nanodegree.android.popularmovies.model.MoviesResponse;
import udacity.nanodegree.android.popularmovies.model.ReviewResponse;
import udacity.nanodegree.android.popularmovies.model.TrailerResponse;

/**
 * Created by yehia on 17/12/16.
 */

public interface ApiInterface {

    @GET("movie/{type}")
    Observable<MoviesResponse> getMovies(@Path("type") String moviesType, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Observable<ReviewResponse> getMovieReview(@Path("id") String id, @Query("api_key") String apiKey );

    @GET("movie/{id}/videos")
    Observable<TrailerResponse> getMovieTrailer(@Path("id") String id, @Query("api_key") String apiKey );

}
