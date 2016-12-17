package udacity.nanodegree.android.popularmovies.backend;

import android.content.Context;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.model.MoviesResponse;
import udacity.nanodegree.android.popularmovies.rest.ApiClient;
import udacity.nanodegree.android.popularmovies.rest.ApiInterface;

/**
 * Created by yehia on 12/12/16.
 */

public class ApiRequests {

    public static Observable<MoviesResponse> getTopRatedObservable(Context context){
        return ApiClient.getClient()
                .create(ApiInterface.class)
                .getTopRatedMovies(context.getString(R.string.api_key))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
