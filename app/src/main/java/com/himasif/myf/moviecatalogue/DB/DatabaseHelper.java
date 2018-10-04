package com.himasif.myf.moviecatalogue.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.himasif.myf.moviecatalogue.DB.DatabaseContract.*;

/**
 * Created by Dul mu'in on 04/10/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "db_movie_catalogue";
    private static final int DATABASE_VERSION = 1;
    private static String CREATE_TABLE_FAVOURITE_MOVIE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s INT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL" +
                    "%s TEXT NOT NULL)",
            TABLE_FAVOURITE,
            FavouriteColumn.MOVIE_ID,
            FavouriteColumn.TITLE,
            FavouriteColumn.LANGUAGE,
            FavouriteColumn.RATING,
            FavouriteColumn.RELEASE_DATE,
            FavouriteColumn.OVERVIEW,
            FavouriteColumn.IMAGE_PATH
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVOURITE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
        onCreate(sqLiteDatabase);
    }
}
