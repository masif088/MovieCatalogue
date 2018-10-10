package com.himasif.myf.favouritemovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.himasif.myf.favouritemovies.R;

import static com.himasif.myf.favouritemovies.DB.DatabaseContract.*;

/**
 * Created by Dul mu'in on 10/10/2018.
 */

public class FavouriteMoviesAdapter extends CursorAdapter{

    public static final String POSTER_URL_W342 = "http://image.tmdb.org/t/p/w342";

    public FavouriteMoviesAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cardview_movie, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(cursor != null){
            ImageView imgPhoto = (ImageView) view.findViewById(R.id.img_photo);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            TextView tvOverview = (TextView) view.findViewById(R.id.tv_overview);
            TextView tvReleaseDate = (TextView) view.findViewById(R.id.tv_releaseDate);

            tvTitle.setText(getColumnString(cursor, FavouriteColumn.TITLE));
            tvOverview.setText(getColumnString(cursor, FavouriteColumn.OVERVIEW));
            tvReleaseDate.setText(getColumnString(cursor, FavouriteColumn.RELEASE_DATE));
            Glide.with(context)
                    .load(POSTER_URL_W342 + getColumnString(cursor, FavouriteColumn.IMAGE_PATH))
                    .into(imgPhoto);
        }
    }
}
