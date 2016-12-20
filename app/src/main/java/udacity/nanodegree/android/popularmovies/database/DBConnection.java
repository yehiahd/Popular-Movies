package udacity.nanodegree.android.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import udacity.nanodegree.android.popularmovies.model.Movie;

/**
 * Created by yehia on 20/12/16.
 */

public class DBConnection extends SQLiteOpenHelper {

    public static final int VERSION = 2;
    public static final String DBName = "favorites.db";
    public static final String CREATE_STATEMENT = "create table IF NOT EXISTS favorites (id TEXT primary key,url TEXT,date TEXT,rate TEXT,description TEXT,title TEXT,dropBack TEXT)";
    public static final String DROP_STATEMENT = "Drop table if EXISTS favorites";
    public static final String TABLE_NAME = "favorites";


    private String idColumnName = "id";
    private String posterUrlColumnName = "url";
    private String dateColumnName = "date";
    private String rateColumnName = "rate";
    private String descriptionColumnName = "description";
    private String titleColumnName = "title";
    private String backDropPathColumnName = "dropBack";


    public DBConnection(Context context) {
        super(context, DBName, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STATEMENT);
        onCreate(db);
    }

    public long addMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(idColumnName,movie.getId());
        contentValues.put(posterUrlColumnName,movie.getPosterPath());
        contentValues.put(dateColumnName,movie.getReleaseDate());
        contentValues.put(rateColumnName,movie.getVoteAverage());
        contentValues.put(descriptionColumnName, movie.getOverview());
        contentValues.put(titleColumnName, movie.getTitle());
        contentValues.put(backDropPathColumnName, movie.getBackdropPath());
        Log.d("Yehia","Added");
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Movie> getAllMovies(){

        int i=0,moviesCount = getMoviesCount();
        ArrayList<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        res.moveToFirst();

        while (!res.isAfterLast()){
            Movie movie = new Movie();

            movie.setId(res.getInt(res.getColumnIndex(idColumnName)));
            movie.setPosterPath(res.getString(res.getColumnIndex(posterUrlColumnName)));
            movie.setReleaseDate(res.getString(res.getColumnIndex(dateColumnName)));
            movie.setVoteAverage(res.getDouble(res.getColumnIndex(rateColumnName)));
            movie.setOverview(res.getString(res.getColumnIndex(descriptionColumnName)));
            movie.setTitle(res.getString(res.getColumnIndex(titleColumnName)));
            movie.setBackdropPath(res.getString(res.getColumnIndex(backDropPathColumnName)));
            if(i == moviesCount)
                break;
            movies.add(movie);
            res.moveToNext();
            i++;

        }

        return movies;
    }

    public int getMoviesCount() {
        String countQuery = "SELECT * FROM "+TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }


    public boolean isMovieFound(String id){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE id="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()==0)
            return false;
        else return true;
    }

    public boolean removeMovie(int id){
        String query = "DELETE FROM "+TABLE_NAME+" WHERE id="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return true;
    }

    public boolean isFavorite(int id){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE id="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()!=0)
            return true;
        else
            return false;
    }
}