package udacity.nanodegree.android.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import udacity.nanodegree.android.popularmovies.R;
import udacity.nanodegree.android.popularmovies.model.Movie;

/**
 * Created by yehia on 12/12/16.
 */

public class MoviesAdapter extends BaseAdapter {

    private ArrayList<Movie> list;
    private Context mContext;
    private int width,height;

    public MoviesAdapter(List<Movie> list, Context context) {
        this.list = new ArrayList<>(list);
        this.mContext = context;
        this.width = context.getResources().getDisplayMetrics().widthPixels;
        this.height = context.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateMoviesList(List<Movie> newList) {
        list.clear();
        list.addAll(newList);
        this.notifyDataSetChanged();
    }

    class ViewHolder{

        private ImageView movieImageView;

        public ViewHolder(View view){
            movieImageView = (ImageView) view.findViewById(R.id.movie_image);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.movie_row,parent,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        else
            holder = (ViewHolder) view.getTag();

        Picasso.with(mContext)
                .load(mContext.getString(R.string.base_image_url)+list.get(position).getPosterPath())
                .resize(width/2,height/2)
                .placeholder(R.drawable.progress_placeholder)
                .error(R.drawable.error)
                .into(holder.movieImageView);

        return view;
    }
}
