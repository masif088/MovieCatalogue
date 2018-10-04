package com.himasif.myf.moviecatalogue.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.himasif.myf.moviecatalogue.Models.Movie;

import java.util.ArrayList;

import static com.himasif.myf.moviecatalogue.DB.DatabaseContract.*;

/**
 * Created by Dul mu'in on 04/10/2018.
 */

public class MovieHelper {
    private static String TABLE = TABLE_FAVOURITE;
    private Context context;
    private DatabaseHelper helper;
    private SQLiteDatabase sqLiteDatabase;

    public MovieHelper(Context context){
        this.context = context;
    }

    public MovieHelper open(){
        helper = new DatabaseHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();
        return this;
    }

    public void close(){
        helper.close();
    }

    public ArrayList<Movie> getFavouriteQuery(){
        ArrayList<Movie> list = new ArrayList<Movie>();
        Cursor cursor = sqLiteDatabase.query(TABLE, null, null, null, null, null, FavouriteColumn.MOVIE_ID + " DESC", null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                Movie movie = new Movie();
                movie.setIdMovie(cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteColumn.MOVIE_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(FavouriteColumn.TITLE)));
                movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(FavouriteColumn.LANGUAGE)));
                movie.setVoteAvg(cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteColumn.RATING)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(FavouriteColumn.RELEASE_DATE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(FavouriteColumn.OVERVIEW)));
                movie.setPosterPath(cursor.getColumnName(cursor.getColumnIndexOrThrow(FavouriteColumn.IMAGE_PATH)));

                list.add(movie);
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return list;
    }

    public long insert(Movie movie){
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavouriteColumn.MOVIE_ID, movie.getIdMovie());
        contentValues.put(FavouriteColumn.TITLE, movie.getTitle());
        contentValues.put(FavouriteColumn.LANGUAGE, movie.getOriginalLanguage());
        contentValues.put(FavouriteColumn.RATING, movie.getVoteAvg());
        contentValues.put(FavouriteColumn.RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(FavouriteColumn.OVERVIEW, movie.getOverview());
        contentValues.put(FavouriteColumn.IMAGE_PATH, movie.getPosterPath());

        return sqLiteDatabase.insert(TABLE, null, contentValues);
    }

    public int delete(int id){
        String clauses = String.format("%s = '%d'", FavouriteColumn.MOVIE_ID, id);
        return sqLiteDatabase.delete(TABLE, clauses, null);
    }

    public Cursor queryProvider(){
        return sqLiteDatabase.query(TABLE, null, null, null, null, null, FavouriteColumn.MOVIE_ID + " DESC");
    }

    public Cursor queryProviderById(String id){
        return sqLiteDatabase.query(TABLE, null, FavouriteColumn.MOVIE_ID + " = ?", new String[]{id}, null, null, null);
    }

    public long insertProvider(ContentValues contentValues){
        return sqLiteDatabase.insert(TABLE, null, contentValues);
    }

    public int deleteProvider(String id){
        return sqLiteDatabase.delete(TABLE, "id = " + id, null);
    }
}
