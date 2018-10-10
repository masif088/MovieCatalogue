package com.himasif.myf.moviecatalogue.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.himasif.myf.moviecatalogue.DB.MovieHelper;

import static com.himasif.myf.moviecatalogue.DB.DatabaseContract.*;

/**
 * Created by Dul mu'in on 05/10/2018.
 */

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_FAVOURITE, MOVIE);

        uriMatcher.addURI(AUTHORITY, TABLE_FAVOURITE + "/#", MOVIE_ID);
    }

    private MovieHelper helper;

    @Override
    public boolean onCreate() {
        helper = new MovieHelper(getContext());
        helper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case MOVIE:
                cursor = helper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = helper.queryProviderById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        long added;

        if (uriMatcher.match(uri) == MOVIE) {
            added = helper.insertProvider(contentValues);
        } else {
            added = 0;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        if(uriMatcher.match(uri) == MOVIE_ID){
            deleted = helper.deleteProvider(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }

        if(deleted > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
