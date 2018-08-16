package com.himasif.myf.moviecatalogue.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.himasif.myf.moviecatalogue.Build.Config;
import com.himasif.myf.moviecatalogue.Model.Movie;
import com.himasif.myf.moviecatalogue.R;

import java.util.ArrayList;

public class ListMovieAdapter extends BaseAdapter{

    private static final String TAG = ListMovieAdapter.class.getSimpleName();
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Movie> movieArrayList;

    public ListMovieAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(movieArrayList == null) return 0;
        return movieArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_movie, null);
            holder.imgPoster = (ImageView) view.findViewById(R.id.img_poster);
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.tvOverview = (TextView) view.findViewById(R.id.tv_overview);
            holder.tvReleaseDate = (TextView) view.findViewById(R.id.tv_releaseDate);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        Log.d(TAG, "getView: " + Config.POSTER_URL_W185 + movieArrayList.get(position).getPosterPath());
        holder.tvTitle.setText(movieArrayList.get(position).getTitle());
        holder.tvOverview.setText(movieArrayList.get(position).getOverview());
        holder.tvReleaseDate.setText(movieArrayList.get(position).getReleaseDate());
        Glide.with(context)
                .load(Config.POSTER_URL_W185 + movieArrayList.get(position).getPosterPath())
                .override(70, 100)
                .crossFade()
                .into(holder.imgPoster);

        return view;
    }


    private static class ViewHolder{
        ImageView imgPoster;
        TextView tvTitle;
        TextView tvOverview;
        TextView tvReleaseDate;
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
        notifyDataSetChanged();
    }

    public void clearData(){
        movieArrayList.clear();
    }
}
