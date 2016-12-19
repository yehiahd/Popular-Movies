package udacity.nanodegree.android.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.model.Movie;

/**
 * Created by yehia on 19/12/16.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> moviesList;
    private Context mContext;
    private int width,height;

    public MoviesAdapter(List<Movie> moviesList, Context context) {
        this.moviesList = moviesList;
        this.mContext = context;
        this.width = context.getResources().getDisplayMetrics().widthPixels;
        this.height = context.getResources().getDisplayMetrics().heightPixels;
    }


    public void updateMoviesList(List<Movie> newList) {
        moviesList.clear();
        moviesList.addAll(newList);
        this.notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row,parent,false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        Picasso.with(mContext)
                .load(mContext.getString(R.string.base_image_url)+movie.getPosterPath())
                .resize(width/2,height/2)
                .placeholder(R.drawable.progress_placeholder)
                .error(R.drawable.error)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{

        private ImageView movieImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.movie_image);
        }
    }
}
