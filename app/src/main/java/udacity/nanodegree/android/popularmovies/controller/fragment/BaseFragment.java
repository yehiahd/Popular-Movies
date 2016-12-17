package udacity.nanodegree.android.popularmovies.controller.fragment;


import android.support.v4.app.Fragment;
import android.view.View;

import com.trello.rxlifecycle.components.RxFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends RxFragment {


    public BaseFragment() {
        // Required empty public constructor
    }

    protected void showView(View view){
        view.setVisibility(View.VISIBLE);
    }

    protected void hideView(View view){
        view.setVisibility(View.GONE);
    }

}
