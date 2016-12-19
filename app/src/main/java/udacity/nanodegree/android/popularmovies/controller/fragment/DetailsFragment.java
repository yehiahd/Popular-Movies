package udacity.nanodegree.android.popularmovies.controller.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.controller.activity.MovieExtraActivity;
import udacity.nanodegree.android.popularmovies.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.movie_cover) ImageView movieCover;
    @Bind(R.id.movie_image) ImageView movieImage;
    @Bind(R.id.movie_date) TextView movieDate;
    @Bind(R.id.movie_vote_average) TextView movieVoteAverage;
    @Bind(R.id.movie_description) TextView movieDescription;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.favorite_fab) FloatingActionButton fab;
    @Bind(R.id.trailers_button) Button trailersButton;
    @Bind(R.id.reviews_button) Button reviewsButton;

    private int movieID;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        ButterKnife.bind(this,view);

        initializeContent();

        if (getActivity().getIntent() !=null){
            populateDetails();
        }
        return view;
    }

    private void initializeContent() {
        fab.setOnClickListener(this);
        trailersButton.setOnClickListener(this);
        reviewsButton.setOnClickListener(this);

        ((AppCompatActivity) getActivity()).supportPostponeEnterTransition();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void populateDetails() {



        Movie movie = getActivity().getIntent().getExtras().getParcelable(getString(R.string.movie_extra));
        if (movie != null) {
            Picasso.with(getActivity()).load(getString(R.string.base_image_url)+movie.getBackdropPath())
                    .placeholder(R.drawable.progress_placeholder).error(R.drawable.error).into(movieCover);

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(movie.getTitle());

            Picasso.with(getActivity()).load(getString(R.string.base_image_url)+movie.getPosterPath())
                    .placeholder(R.drawable.progress_placeholder).error(R.drawable.error).into(movieImage);

            movieDate.setText((movie.getReleaseDate().split(getString(R.string.splitter)))[0]);

            movieVoteAverage.setText(String.valueOf(movie.getVoteAverage())+getString(R.string.divideByTen));

            movieDescription.setText(movie.getOverview() + movie.getOverview() + movie.getOverview() + movie.getOverview());

            movieID = movie.getId();

        }

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.favorite_fab:
                Toast.makeText(getActivity(), "fab Clicked ! Wait for favorites", Toast.LENGTH_SHORT).show();
                break;

            case R.id.reviews_button:
                startActivity(new Intent(getActivity(), MovieExtraActivity.class)
                        .putExtra(getString(R.string.id),movieID)
                        .putExtra(getString(R.string.request_type),getString(R.string.reviews))
                );
                break;

            case R.id.trailers_button:
                startActivity(new Intent(getActivity(), MovieExtraActivity.class)
                .putExtra(getString(R.string.id),movieID)
                .putExtra(getString(R.string.request_type),getString(R.string.trailers))
                );
                break;
        }
    }
}
