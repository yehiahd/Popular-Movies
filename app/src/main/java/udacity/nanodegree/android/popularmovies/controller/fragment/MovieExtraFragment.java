package udacity.nanodegree.android.popularmovies.controller.fragment;


import android.content.Intent;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.adapter.MovieExtraAdapter;
import udacity.nanodegree.android.popularmovies.backend.ApiRequests;
import udacity.nanodegree.android.popularmovies.callback.RecyclerClickListener;
import udacity.nanodegree.android.popularmovies.model.Trailer;
import udacity.nanodegree.android.popularmovies.util.Connection;
import udacity.nanodegree.android.popularmovies.util.RecyclerTouchListener;

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
    private List<Trailer> trailerList;
    public MovieExtraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_extra, container, false);
        ButterKnife.bind(this,view);

        receiveIntents();

        if (!Connection.isNetworkAvailable(getActivity()) && savedInstanceState == null){
            showView(noInternetLayout);
            hideView(progressBar);
        }

        else if (getActivity().getIntent() != null){
            initializeContent();
        }

        return view;
    }

    private void receiveIntents() {
        requestType = getActivity().getIntent().getExtras().getString(getString(R.string.request_type));
        movieID = getActivity().getIntent().getExtras().getInt(getString(R.string.id));

        if (requestType.equals(getString(R.string.reviews))) {
            getActivity().setTitle(getString(R.string.reviews));
        } else if (requestType.equals(getString(R.string.trailers))) {
            getActivity().setTitle(getString(R.string.trailers));
        }
    }

    private void initializeContent() {
        hideView(noInternetLayout);
        showView(progressBar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        movieExtraRecycler.setLayoutManager(mLayoutManager);
        movieExtraRecycler.setItemAnimator(new DefaultItemAnimator());

        if (requestType.equals(getString(R.string.reviews))){
            getReviews(movieID);
        }

        else if (requestType.equals(getString(R.string.trailers))){
            getTrailers(movieID);

            movieExtraRecycler.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), movieExtraRecycler, new RecyclerClickListener() {
                @Override
                public void onClick(View view, int position) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.base_trailer_url)+trailerList.get(position).getKey())));
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }

    }

    private void getReviews(Integer id) {
        ApiRequests.getMovieReviewObservable(String.valueOf(id))
                .compose(bindToLifecycle())
                .subscribe(reviewResponse -> {
                    adapter = new MovieExtraAdapter(getActivity(),reviewResponse.getReviews(), new ArrayList<>(),getString(R.string.reviews));
                    movieExtraRecycler.setAdapter(adapter);
                    hideView(noInternetLayout);
                    hideView(progressBar);
                },throwable -> {
                    showView(noInternetLayout);
                    hideView(movieExtraRecycler);
                });
    }

    private void getTrailers(Integer id) {
        ApiRequests.getMovieTrailerObservable(String.valueOf(id))
                .compose(bindToLifecycle())
                .subscribe(trailerResponse -> {
                    trailerList = trailerResponse.getTrailers();
                    adapter = new MovieExtraAdapter(getActivity(), new ArrayList<>(),trailerResponse.getTrailers(),getString(R.string.trailers));
                    movieExtraRecycler.setAdapter(adapter);
                    hideView(noInternetLayout);
                    hideView(progressBar);
                },throwable -> {
                    showView(noInternetLayout);
                    hideView(movieExtraRecycler);
                });
    }

}
