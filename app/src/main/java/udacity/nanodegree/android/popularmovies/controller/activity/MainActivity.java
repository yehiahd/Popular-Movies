package udacity.nanodegree.android.popularmovies.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.controller.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.activity_main,new MainFragment()).commit();

    }


}