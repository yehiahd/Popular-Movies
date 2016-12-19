package udacity.nanodegree.android.popularmovies.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.adapter.MovieExtraAdapter;
import udacity.nanodegree.android.popularmovies.backend.ApiRequests;
import udacity.nanodegree.android.popularmovies.util.Connection;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieExtraFragment extends BaseFragment {

    @Bind(R.id.movie_extra_recycler) RecyclerView movieExtraRecycler;
    @Bind(R.id.extra_progress_bar) ProgressBar progressBar;
    @Bind(R.id.no_internet_connection_layout) LinearLayout noInternetLayout;

    private MovieExtraAdapter adapter;
    private int movieID;
    private String requestType;

    public MovieExtraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_extra, container, false);
        ButterKnife.bind(this,view);

        if (!Connection.isNetworkAvailable(getActivity()) && savedInstanceState == null){
            showView(noInternetLayout);
            hideView(progressBar);
        }

        else if (getActivity().getIntent() != null){
            initializeContent();
        }

        return view;
    }

    private void initializeContent() {
        hideView(noInternetLayout);
        showView(progressBar);

        requestType = getActivity().getIntent().getExtras().getString(getString(R.string.request_type));
        movieID = getActivity().getIntent().getExtras().getInt(getString(R.string.id));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        movieExtraRecycler.setLayoutManager(mLayoutManager);
        movieExtraRecycler.setItemAnimator(new DefaultItemAnimator());

        if (requestType.equals(getString(R.string.reviews))){
            getReviews(movieID);
        }

        else if (requestType.equals(getString(R.string.trailers))){
            getTrailers(movieID);
        }

    }

    private void getReviews(Integer id) {
        ApiRequests.getMovieReviewObservable(getActivity(),String.valueOf(id))
                .subscribe(reviewResponse -> {
                    adapter = new MovieExtraAdapter(getActivity(),reviewResponse.getReviews(), new ArrayList<>(),getString(R.string.reviews));
                    movieExtraRecycler.setAdapter(adapter);
                    hideView(noInternetLayout);
                    hideView(progressBar);
                },throwable -> {
                    Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    showView(noInternetLayout);
                    hideView(movieExtraRecycler);
                });
    }

    private void getTrailers(Integer id) {
        ApiRequests.getMovieTrailerObservable(getActivity(), String.valueOf(id))
                .subscribe(trailerResponse -> {
                    adapter = new MovieExtraAdapter(getActivity(), new ArrayList<>(),trailerResponse.getTrailers(),getString(R.string.trailers));
                    movieExtraRecycler.setAdapter(adapter);
                    hideView(noInternetLayout);
                    hideView(progressBar);
                },throwable -> {
                    Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    showView(noInternetLayout);
                    hideView(movieExtraRecycler);
                });
    }

}
