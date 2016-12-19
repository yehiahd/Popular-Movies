package udacity.nanodegree.android.popularmovies.callback;

import android.view.View;

/**
 * Created by yehia on 19/12/16.
 */

public interface RecyclerClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}