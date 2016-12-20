package udacity.nanodegree.android.popularmovies.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.callback.Communicator;
import udacity.nanodegree.android.popularmovies.controller.fragment.DetailsFragment;
import udacity.nanodegree.android.popularmovies.model.Movie;

public class MainActivity extends RxAppCompatActivity implements Communicator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_menu_item:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void sendMovie(Movie movie) {
        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment_tablet);
        detailsFragment.populateDetailsForTablet(movie);
    }
}