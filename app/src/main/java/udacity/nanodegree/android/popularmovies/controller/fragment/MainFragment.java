package udacity.nanodegree.android.popularmovies.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.util.JsonParsing;

import static udacity.nanodegree.android.popularmovies.backend.ApiRequests.fetchData;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    //@Bind(R.id.movies_grid) GridView moviesGridView;
    @Bind(R.id.text_view) TextView text_view;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this,view);

        createObservables();

        return view;
    }

    private void createObservables() {

        fetchData(getString(R.string.top_rated_base_url),getString(R.string.api_key))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> JsonParsing.getPosterMoviesFromJson(s,getActivity()))
                .subscribe(movieInfos -> {
                    text_view.setText(movieInfos[0].getTitle());
                });
    }



}
