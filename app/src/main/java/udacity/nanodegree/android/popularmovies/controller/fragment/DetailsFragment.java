package udacity.nanodegree.android.popularmovies.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends BaseFragment {

    @Bind(R.id.movie_cover) ImageView movieCover;
    @Bind(R.id.movie_title) TextView movieTitle;
    @Bind(R.id.movie_image) ImageView movieImage;
    @Bind(R.id.movie_date) TextView movieDate;
    @Bind(R.id.movie_vote_average) TextView movieVoteAverage;
    @Bind(R.id.movie_mark_as_favorite) Button markAsFavorite;
    @Bind(R.id.movie_description) TextView movieDescription;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this,view);
        if (getActivity().getIntent() !=null){
            populateDetails();
        }
        return view;
    }

    private void populateDetails() {
        Movie movie = getActivity().getIntent().getExtras().getParcelable(getString(R.string.movie_extra));
        if (movie != null) {
            Picasso.with(getActivity()).load(getString(R.string.base_image_url)+movie.getBackdropPath())
                    .placeholder(R.drawable.progress_placeholder).error(R.drawable.error).into(movieCover);

            movieTitle.setText(movie.getTitle());

            Picasso.with(getActivity()).load(getString(R.string.base_image_url)+movie.getPosterPath())
                    .placeholder(R.drawable.progress_placeholder).error(R.drawable.error).into(movieImage);

            movieDate.setText((movie.getReleaseDate().split("-"))[0]);

            movieVoteAverage.setText(String.valueOf(movie.getVoteAverage())+"/10");

            movieDescription.setText(movie.getOverview());

        }

    }

}
