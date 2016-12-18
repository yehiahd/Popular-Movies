package udacity.nanodegree.android.popularmovies.controller.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.adapter.MoviesAdapter;
import udacity.nanodegree.android.popularmovies.backend.ApiRequests;
import udacity.nanodegree.android.popularmovies.controller.activity.DetailsActivity;
import udacity.nanodegree.android.popularmovies.model.Movie;
import udacity.nanodegree.android.popularmovies.util.Connection;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    @Bind(R.id.movies_grid) GridView moviesGridView;
    @Bind(R.id.progress_bar) ProgressBar progressBar;
    @Bind(R.id.no_internet_connection_layout) LinearLayout noInternetLayout;
    @Bind(R.id.retry_to_connect) Button retryButton;
    private MoviesAdapter adapter;
    private ArrayList<Movie> moviesList;
    private String oldPref;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,view);
        retryButton.setOnClickListener(this);


        if (!Connection.isNetworkAvailable(getActivity()) && savedInstanceState == null){
            showView(noInternetLayout);
            hideView(progressBar);
        }

        else {
            initializeContent();

            if (savedInstanceState != null){
                moviesList = savedInstanceState.getParcelableArrayList(getString(R.string.cashed_movies_list));
                adapter.updateMoviesList(moviesList);
                hideView(progressBar);
            }
        }


        return view;
    }

    @Override
    public void onResume() {
            super.onResume();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);

            Preference<String> sortPreference = rxPreferences.getString(getString(R.string.sort_by_key),getString(R.string.top_rated));

            sortPreference.asObservable()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        if (!s.equals(oldPref)){
                            if (s.equals(getString(R.string.top_rated))){
                                oldPref = getString(R.string.top_rated);

                            }

                            else if (s.equals(getString(R.string.popular))){
                                oldPref = getString(R.string.popular);

                            }
                            if (Connection.isNetworkAvailable(getActivity()))
                                fetchMovies(oldPref);
                            else {
                                showView(noInternetLayout);
                                hideView(moviesGridView);
                            }
                        }

                    });

    }

    private void initializeContent() {
        hideView(noInternetLayout);
        showView(progressBar);
        moviesList = new ArrayList<>();
        adapter = new MoviesAdapter(moviesList,getActivity());
        moviesGridView.setAdapter(adapter);
        moviesGridView.setOnItemClickListener(this);
    }

    private void fetchMovies(String fetchType) {
        ApiRequests.getMoviesObservable(getActivity(), fetchType)
                .compose(bindToLifecycle())
                .subscribe(moviesResponse -> {
                    moviesList = (ArrayList<Movie>) moviesResponse.getResults();
                    adapter.updateMoviesList(moviesResponse.getResults());
                    hideView(progressBar);
                });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.cashed_movies_list), moviesList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        startActivity(new Intent(getActivity(), DetailsActivity.class).putExtra(getString(R.string.movie_extra),moviesList.get(position)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.retry_to_connect:
                if (Connection.isNetworkAvailable(getActivity())){
                    initializeContent();
                    fetchMovies(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getString(R.string.sort_by_key),getString(R.string.top_rated)));
                    showView(moviesGridView);
                }
                break;
        }
    }

}