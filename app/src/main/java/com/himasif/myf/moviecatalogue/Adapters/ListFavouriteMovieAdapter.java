package com.himasif.myf.moviecatalogue.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.himasif.myf.moviecatalogue.DB.MovieHelper;
import com.himasif.myf.moviecatalogue.DetailActivity;
import com.himasif.myf.moviecatalogue.Models.Movie;
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
    public void bindView(View view, final Context context, final Cursor cursor) {
        if(cursor != null){
            final MovieHelper helper = new MovieHelper(context);
            helper.open();
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            TextView tvOverview = (TextView) view.findViewById(R.id.tv_overview);
            final TextView tvReleaseDate = (TextView) view.findViewById(R.id.tv_releaseDate);
            final ImageView imgPoster = (ImageView) view.findViewById(R.id.img_photo);
            Button btnShare = (Button) view.findViewById(R.id.btn_share);
            Button btnDetail = (Button) view.findViewById(R.id.btn_detail);

            tvTitle.setText(getColumnString(cursor, FavouriteColumn.TITLE));
            tvOverview.setText(getColumnString(cursor, FavouriteColumn.OVERVIEW));
            tvReleaseDate.setText(getColumnString(cursor, FavouriteColumn.RELEASE_DATE));
            btnDetail.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent = new Intent(context, DetailActivity.class);
                                                 intent.putExtra(DetailActivity.EXTRA_MOVIE, helper.getMovie(cursor));
                                                 context.startActivity(intent);
                                             }
                                         });

            Glide.with(context)
                    .load(POSTER_URL_W342 + getColumnString(cursor, FavouriteColumn.IMAGE_PATH))
                    .into(imgPoster);
            helper.close();

        }
    }
}