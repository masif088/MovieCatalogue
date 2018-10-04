package com.himasif.myf.moviecatalogue.DB;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Dul mu'in on 04/10/2018.
 */

public class DatabaseContract {
    public static String TABLE_FAVOURITE = "favourite_movie";

    public static final class FavouriteColumn implements BaseColumns {
        public static final String MOVIE_ID = "movie_id";
        public static final String TITLE = "title";
        public static final String LANGUAGE = "language";
        public static final String RATING = "rating";
        public static final String RELEASE_DATE = "release_date";
        public static final String OVERVIEW = "overview";
        public static final String IMAGE_PATH = "image_path";
    }

    public static final String AUTHORITY = "com.himasif.myf.moviecatalogue";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVOURITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }
}
