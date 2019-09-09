package com.example.moviecatalogue4.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.moviecatalogue4.database.AppDatabase;

import java.util.Objects;

public class MovieProvider extends ContentProvider {
    private static final String AUTHORITY ="com.example.moviecatalogue4";
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "ModelMovie", 1);
        sUriMatcher.addURI(AUTHORITY, "ModelTv", 2);
    }

    private AppDatabase appDatabase;

    @Override
    public boolean onCreate() {
        String DBNAME = "movie_catalogue";
        appDatabase = Room.databaseBuilder(Objects.requireNonNull(getContext()), AppDatabase.class, DBNAME).build();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor = null;
        switch (sUriMatcher.match(uri)) {
            case 1:
                cursor = appDatabase.movieDao().SelectMovieProvider();
                break;
            case 2:
                cursor = appDatabase.tvDao().SelectTvProvider();
                break;
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
