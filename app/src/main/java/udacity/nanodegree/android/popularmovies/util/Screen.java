package udacity.nanodegree.android.popularmovies.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by yehia on 20/12/16.
 */

public class Screen {

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
