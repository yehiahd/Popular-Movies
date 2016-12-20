package udacity.nanodegree.android.popularmovies.database;

import android.content.Context;

/**
 * Created by yehia on 20/12/16.
 */

public class DatabaseHelper {

    private static DBConnection mDbConnection;

    public static DBConnection getInstance(Context mContext){

        if (mDbConnection == null){
            mDbConnection = new DBConnection(mContext);
        }

        return mDbConnection;
    }
}
