package udacity.nanodegree.android.popularmovies.backend;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import udacity.nanodegree.android.popularmovies.BuildConfig;
import udacity.nanodegree.android.popularmovies.model.MoviesResponse;
import udacity.nanodegree.android.popularmovies.model.ReviewResponse;
import udacity.nanodegree.android.popularmovies.model.TrailerResponse;
import udacity.nanodegree.android.popularmovies.rest.ApiClient;
import udacity.nanodegree.android.popularmovies.rest.ApiInterface;

/**
 * Created by yehia on 12/12/16.
 */

public class ApiRequests {

    public static Observable<MoviesResponse> getMoviesObservable(String moviesSortType){
        return ApiClient.getClient()
                .create(ApiInterface.class)
                .getMovies(moviesSortType, BuildConfig.MOVIE_DB_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .timeout(15, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<ReviewResponse> getMovieReviewObservable(String movieId){
        return ApiClient.getClient()
                .create(ApiInterface.class)
                .getMovieReview(movieId,BuildConfig.MOVIE_DB_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .timeout(15,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<TrailerResponse> getMovieTrailerObservable(String movieId){
        return ApiClient.getClient()
                .create(ApiInterface.class)
                .getMovieTrailer(movieId,BuildConfig.MOVIE_DB_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .timeout(15,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
