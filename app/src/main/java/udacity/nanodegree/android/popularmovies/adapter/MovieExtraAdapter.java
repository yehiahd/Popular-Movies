package udacity.nanodegree.android.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.model.Review;
import udacity.nanodegree.android.popularmovies.model.Trailer;

/**
 * Created by yehia on 20/12/16.
 */

public class MovieExtraAdapter extends RecyclerView.Adapter<MovieExtraAdapter.MovieExtraViewHolder> {

    private List<Review> reviews;
    private List<Trailer> trailers;
    private Context mContext;
    private String requestType;

    public MovieExtraAdapter(Context context, List<Review> reviews, List<Trailer> trailers, String requestType){
        this.mContext = context;
        this.reviews = new ArrayList<>(reviews);
        this.trailers = new ArrayList<>(trailers);
        this.requestType = requestType;
    }

    @Override
    public MovieExtraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = null;

        if (requestType.equals(mContext.getString(R.string.reviews))){
            view = layoutInflater.inflate(R.layout.review_row,parent,false);
        }

        else if (requestType.equals(mContext.getString(R.string.trailers))){
            view = layoutInflater.inflate(R.layout.trailer_row,parent,false);
        }
        return new MovieExtraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieExtraViewHolder holder, int position) {
        if (requestType.equals(mContext.getString(R.string.reviews))){
            Review review = reviews.get(position);
            holder.authorName.setText(review.getAuthor());
            holder.content.setText(review.getContent());
        }

        else if (requestType.equals(mContext.getString(R.string.trailers))){
            Trailer trailer = trailers.get(position);
            holder.trailerName.setText(trailer.getName());
        }
    }

    @Override
    public int getItemCount() {

        if (requestType.equals(mContext.getString(R.string.reviews))){
            return reviews.size();
        }

        else {
            return trailers.size();
        }

    }

    public class MovieExtraViewHolder extends RecyclerView.ViewHolder {

        private TextView authorName,content;
        private TextView trailerName;

        public MovieExtraViewHolder(View itemView) {
            super(itemView);

            if (requestType.equals(mContext.getString(R.string.reviews))){
                authorName = (TextView) itemView.findViewById(R.id.author_name);
                content = (TextView) itemView.findViewById(R.id.content);
            }

            else if (requestType.equals(mContext.getString(R.string.trailers))){
                trailerName = (TextView) itemView.findViewById(R.id.trailer_name);
            }
        }
    }
}
