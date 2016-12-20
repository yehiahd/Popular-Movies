package udacity.nanodegree.android.popularmovies.controller.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import udacity.nanodegree.android.popularmovies.callback.RecyclerClickListener;
import udacity.nanodegree.android.popularmovies.controller.activity.DetailsActivity;
import udacity.nanodegree.android.popularmovies.database.DBConnection;
import udacity.nanodegree.android.popularmovies.database.DatabaseHelper;
import udacity.nanodegree.android.popularmovies.model.Movie;
import udacity.nanodegree.android.popularmovies.util.Connection;
import udacity.nanodegree.android.popularmovies.util.RecyclerTouchListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.progress_bar) ProgressBar progressBar;
    @Bind(R.id.no_internet_connection_layout) LinearLayout noInternetLayout;
    @Bind(R.id.retry_to_connect) Button retryButton;
    @Bind(R.id.movies_recycler) RecyclerView moviesRecycler;
    private MoviesAdapter adapter;
    private ArrayList<Movie> moviesList;
    private String oldPref;
    public static final int NUMBER_OF_ROW_ITEMS = 2;

    private DBConnection databaseReference;
    private SharedPreferences preferences ;
    private String tempPref;


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

        getPref();

        initializeContent();


        if ((!Connection.isNetworkAvailable(getActivity()) && savedInstanceState == null) && !tempPref.equals(getString(R.string.favorites))){
            showView(noInternetLayout);
            hideView(progressBar);
        }

        else {

            if (savedInstanceState != null){
                moviesList = savedInstanceState.getParcelableArrayList(getString(R.string.cashed_movies_list));
                adapter.updateMoviesList(moviesList);
                hideView(progressBar);
            }

        }


        return view;
    }

    private void getPref() {

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);

        Preference<String> sortPreference = rxPreferences.getString(getString(R.string.sort_by_key),getString(R.string.top_rated));

        sortPreference.asObservable()
                .compose(bindToLifecycle())
                .subscribe(s -> {
                    tempPref = s;
                },throwable -> {
                    Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onResume() {
            super.onResume();

            RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);

            Preference<String> sortPreference = rxPreferences.getString(getString(R.string.sort_by_key),getString(R.string.top_rated));



            sortPreference.asObservable()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(bindToLifecycle())
                    .subscribe(s -> {
                        if (!s.equals(oldPref)){
                            if (s.equals(getString(R.string.top_rated)) && Connection.isNetworkAvailable(getActivity())){
                                fetchMovies(getString(R.string.top_rated));
                                oldPref = getString(R.string.top_rated);
                            }

                            else if (s.equals(getString(R.string.popular)) && Connection.isNetworkAvailable(getActivity())){
                                fetchMovies(getString(R.string.popular));
                                oldPref = getString(R.string.popular);

                            }

                            else if (s.equals(getString(R.string.favorites))){
                                displayFavoriteMovies();
                                oldPref = getString(R.string.favorites);
                            }
                        }

                        else if (s.equals(getString(R.string.favorites))){
                            if ((moviesList.size()!= databaseReference.getAllMovies().size())){
                                displayFavoriteMovies();
                            }
                        }

                    });


    }


    private void initializeContent() {
        hideView(noInternetLayout);
        showView(progressBar);

        databaseReference = DatabaseHelper.getInstance(getActivity());
        moviesList = new ArrayList<>();
        adapter = new MoviesAdapter(moviesList,getActivity());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),NUMBER_OF_ROW_ITEMS);
        moviesRecycler.setLayoutManager(mLayoutManager);
        moviesRecycler.setItemAnimator(new DefaultItemAnimator());
        moviesRecycler.setAdapter(adapter);
        adapter.updateMoviesList(moviesList);

        moviesRecycler.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), moviesRecycler, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(getActivity(), DetailsActivity.class).putExtra(getString(R.string.movie_extra),moviesList.get(position)));
            }

            @Override
            public void onLongClick(View view, int position) {
//                moviesList.remove(position);
//                adapter.updateMoviesList(moviesList);
            }
        }));

    }

    private void fetchMovies(String fetchType) {
        ApiRequests.getMoviesObservable(fetchType)
                .compose(bindToLifecycle())
                .subscribe(moviesResponse -> {
                    moviesList = (ArrayList<Movie>) moviesResponse.getResults();
                    adapter.updateMoviesList(moviesResponse.getResults());
                    hideView(progressBar);
                },throwable -> {
                    hideView(progressBar);
                    hideView(moviesRecycler);
                    showView(noInternetLayout);
                    ((TextView)noInternetLayout.findViewById(R.id.error_message)).setText(R.string.network_error);
                });
    }


    private void displayFavoriteMovies() {
        showView(progressBar);
        moviesList = databaseReference.getAllMovies();
        adapter.updateMoviesList(moviesList);
        hideView(progressBar);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.cashed_movies_list), moviesList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.retry_to_connect:
                if (moviesList == null){ // this check if the app is opened when no internet connection not error handing

                    if (Connection.isNetworkAvailable(getActivity())){
                        initializeContent();
                        fetchMovies(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getString(R.string.sort_by_key),getString(R.string.top_rated)));
                        showView(moviesRecycler);
                    }
                }

                else {
                    fetchMovies(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getString(R.string.sort_by_key),getString(R.string.top_rated)));
                    showView(moviesRecycler);
                    hideView(noInternetLayout);
                    showView(progressBar);
                }


                break;
        }
    }

}