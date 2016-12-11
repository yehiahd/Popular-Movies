package udacity.nanodegree.android.popularmovies.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.model.MovieInfo;

/**
 * Created by yehia on 12/12/16.
 */

public class JsonParsing {

    public static MovieInfo[] getPosterMoviesFromJson(String posterJsonStr, Context mContext) {

        final String POSTER_PATH="poster_path";
        final String OWN_LIST ="results";
        final String OVER_VIEW="overview";
        final String RELEASE_DATE = "release_date";
        final String ID = "id";
        final String ORIGINAL_TITLE="original_title";
        final String TITLE ="title";
        final String BACKDROP_PATH ="backdrop_path";
        final String POPULARITY ="popularity";
        final String VOTE_COUNT = "vote_count";
        final String VOTE_AVERAGE= "vote_average";

        StringBuilder path ,backpath;
        String overView ,releaseDate,id,originalTitle,title,backdropPath,voteAverage;
        double popularity;
        int voteCount;

        JSONObject jsonRootObject = null;
        try {
            jsonRootObject = new JSONObject(posterJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = jsonRootObject.optJSONArray(OWN_LIST);
        MovieInfo [] movieInfosArr = new MovieInfo[jsonArray.length()];

        for(int i=0;i<jsonArray.length();i++){

            path = new StringBuilder();
            backpath = new StringBuilder();
            movieInfosArr[i] = new MovieInfo();

            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            path.append(mContext.getString(R.string.base_image_url)).append(jsonObject.optString(POSTER_PATH));
            overView = jsonObject.optString(OVER_VIEW);
            releaseDate = jsonObject.optString(RELEASE_DATE);
            id = jsonObject.optString(ID);
            originalTitle = jsonObject.optString(ORIGINAL_TITLE);
            title = jsonObject.optString(TITLE);
            backpath.append(mContext.getString(R.string.base_image_url)).append(jsonObject.optString(BACKDROP_PATH));
            backdropPath = String.valueOf(backpath);
            popularity = jsonObject.optDouble(POPULARITY);
            voteCount = jsonObject.optInt(VOTE_COUNT);
            voteAverage = jsonObject.optString(VOTE_AVERAGE);


            movieInfosArr[i].setPoster_path(String.valueOf(path));
            movieInfosArr[i].setOverview(overView);
            movieInfosArr[i].setRelease_date(releaseDate);
            movieInfosArr[i].setId(id);
            movieInfosArr[i].setOriginal_title(originalTitle);
            movieInfosArr[i].setTitle(title);
            movieInfosArr[i].setBackdrop_path(backdropPath);
            movieInfosArr[i].setPopularity(popularity);
            movieInfosArr[i].setVote_count(voteCount);
            movieInfosArr[i].setVote_average(voteAverage);
        }

        return movieInfosArr;
    }
}
