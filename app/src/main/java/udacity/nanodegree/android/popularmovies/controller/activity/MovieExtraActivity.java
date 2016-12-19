package udacity.nanodegree.android.popularmovies.controller.activity;

import android.os.Bundle;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import udacity.nanodegree.android.popularmovies.R;

public class MovieExtraActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_extra);
    }
}
