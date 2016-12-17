package udacity.nanodegree.android.popularmovies.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.adapter.MoviesAdapter;
import udacity.nanodegree.android.popularmovies.backend.ApiRequests;
import udacity.nanodegree.android.popularmovies.model.Movie;
import udacity.nanodegree.android.popularmovies.util.Connection;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {

    @Bind(R.id.movies_grid) GridView moviesGridView;
    @Bind(R.id.progress_bar) ProgressBar progressBar;
    @Bind(R.id.no_internet_connection_layout) LinearLayout noInternetLayout;
    private MoviesAdapter adapter;
    private ArrayList<Movie> moviesList;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,view);

        if (!Connection.isNetworkAvailable(getActivity()) && savedInstanceState == null){
            showView(noInternetLayout);
            hideView(progressBar);
        }

        else {
            initializeContent();

            if (savedInstanceState == null){
                createObservables();
            }

            else {
                moviesList = savedInstanceState.getParcelableArrayList(getString(R.string.cashed_movies_list));
                adapter.updateMoviesList(moviesList);
                hideView(progressBar);
            }
        }


        return view;
    }

    private void initializeContent() {
        hideView(noInternetLayout);
        showView(progressBar);
        moviesList = new ArrayList<>();
        adapter = new MoviesAdapter(moviesList,getActivity());
        moviesGridView.setAdapter(adapter);
    }

    private void createObservables() {
        ApiRequests.getMoviesObservable(getActivity(), getString(R.string.top_rated))
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

}
