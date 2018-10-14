package com.himasif.myf.moviecatalogue.Widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.himasif.myf.moviecatalogue.DB.DatabaseContract;
import com.himasif.myf.moviecatalogue.Models.Movie;

import com.bumptech.glide.request.target.Target;
import com.himasif.myf.moviecatalogue.R;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class StackViewRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private ArrayList<Movie> movieArrayList;
    private int appWidgetId;
    public static final String POSTER_URL_W342 = "http://image.tmdb.org/t/p/w342";
    private Cursor cursor;

    public StackViewRemoteFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    private Movie getMovie(int pos){
        if(!cursor.moveToPosition(pos)){
            Log.e(TAG, "getMovie: cursor out of bound" );
        }
        return new Movie(cursor);
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(
                DatabaseContract.CONTENT_URI, null, null, null, null
        );
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(
                DatabaseContract.CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if(cursor != null){
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Movie movie = getMovie(i);
        Log.d(TAG, "getViewAt: Movie Title on index " + i + " is : " + movie.getTitle());
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .load(POSTER_URL_W342+movie.getPosterPath())
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

            remoteViews.setImageViewBitmap(R.id.image_view_widget, bitmap);
            remoteViews.setTextViewText(R.id.tv_release_date_widget, movie.getReleaseDate());
        } catch (Exception e){

        }
//        remoteViews.setTextViewText(R.id.image_view_widget, movie.getTitle());
//        remoteViews.setImageViewBitmap(R.id.image_view_widget, bitmap);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }



    public ArrayList<Movie> getMovieArrayList() {
        return movieArrayList;
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }
}
