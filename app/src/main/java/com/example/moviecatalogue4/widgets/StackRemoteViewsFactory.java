package com.example.moviecatalogue4.widgets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.moviecatalogue4.R;
import com.example.moviecatalogue4.database.MyApp;
import com.example.moviecatalogue4.model.ModelMovie;
import com.example.moviecatalogue4.model.ModelTv;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private List<Bitmap> bitmapList = new ArrayList<>();
    private ArrayList<ModelMovie> listMovie = new ArrayList<>();
    private ArrayList<ModelTv> listTv= new ArrayList<>();

    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        listMovie.addAll(MyApp.db.movieDao().SelectMovie());
        for (int counter =0 ; counter < listMovie.size(); counter++){
            Bitmap bitmap=null;
            try {
                bitmap=Glide.with(context).asBitmap().load(listMovie.get(counter).getPoster()).into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bitmapList.add(bitmap);
        }
        listTv.addAll(MyApp.db.tvDao().SelectTv());
        for (int counter =0 ; counter < listTv.size(); counter++){
            Bitmap bitmap=null;
            try {
                bitmap=Glide.with(context).asBitmap().load(listTv.get(counter).getPoster()).into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bitmapList.add(bitmap);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return bitmapList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView,bitmapList.get(i));
        Bundle extras = new Bundle();
        extras.putInt(StackWidget.EXTRA_ITEM, i);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
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
}
