package udacity.nanodegree.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yehia on 11/12/16.
 */
public class MovieInfo implements Parcelable {
    private String poster_path;
    private String overview;
    private String release_date;
    private String id;
    private String original_title;
    private String title;
    private String backdrop_path;
    private double popularity;
    private int vote_count;
    private String vote_average;

    public MovieInfo(){
        this.poster_path="";
        this.overview = "";
        this.release_date = "";
        this.id="";
        this.original_title="";
        this.title="";
        this.backdrop_path="";
        this.popularity=0.0;
        this.vote_count=0;
        this.vote_average="";
    }

    public MovieInfo(String poster_path,String overview,String release_date,String id,String original_title,String title
            ,String backdrop_path, double popularity,int vote_count , String vote_average){
        this.poster_path=poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.id=id;
        this.original_title=original_title;
        this.title=title;
        this.backdrop_path=backdrop_path;
        this.popularity=popularity;
        this.vote_count=vote_count;
        this.vote_average=vote_average;
    }

    private MovieInfo(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        id = in.readString();
        original_title = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        popularity = in.readDouble();
        vote_count = in.readInt();
        vote_average = in.readString();
    }

    public static final Parcelable.Creator<MovieInfo> CREATOR  = new Parcelable.Creator<MovieInfo>() {
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(id);
        dest.writeString(original_title);
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeDouble(popularity);
        dest.writeInt(vote_count);
        dest.writeString(vote_average);
    }


    public void readFromParcel(Parcel in ) {

        poster_path = in .readString();
        overview = in .readString();
        release_date = in .readString();
        id = in .readString();
        original_title= in .readString();
        title = in .readString();
        backdrop_path = in .readString();
        popularity = in.readDouble();
        vote_count = in .readInt();
        vote_average =in.readString();
    }


}