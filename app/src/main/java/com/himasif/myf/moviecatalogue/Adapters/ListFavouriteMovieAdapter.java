package com.himasif.myf.moviecatalogue.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.himasif.myf.moviecatalogue.R;

import static com.himasif.myf.moviecatalogue.DB.DatabaseContract.*;

/**
 * Created by Dul mu'in on 05/10/2018.
 */

public class ListFavouriteMovieAdapter extends CursorAdapter{

    public static final String POSTER_URL_W342 = "http://image.tmdb.org/t/p/w342";

    public ListFavouriteMovieAdapter(Context context, Cursor cursor, boolean autoQuery){
        super(context, cursor, autoQuery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cardview_movie, viewGroup, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(cursor != null){
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            TextView tvOverview = (TextView) view.findViewById(R.id.tv_overview);
            TextView tvReleaseDate = (TextView) view.findViewById(R.id.tv_releaseDate);
            ImageView imgPoster = (ImageView) view.findViewById(R.id.img_photo);

            tvTitle.setText(getColumnString(cursor, FavouriteColumn.TITLE));
            tvOverview.setText(getColumnString(cursor, FavouriteColumn.OVERVIEW));
            tvReleaseDate.setText(getColumnString(cursor, FavouriteColumn.RELEASE_DATE));

            Glide.with(context)
                    .load(POSTER_URL_W342 + getColumnString(cursor, FavouriteColumn.IMAGE_PATH))
                    .into(imgPoster);
        }
    }
}
