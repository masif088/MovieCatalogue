package com.himasif.myf.favouritemovies;

import android.database.Cursor;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.himasif.myf.favouritemovies.DB.DatabaseContract.*;

public class Movie implements Serializable {

    private static final String TAG = Movie.class.getSimpleName();
    private String title;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private int voteAvg;
    private String originalLanguage;
    private int idMovie;

    public Movie(){

    }

    public Movie(Cursor cursor){
        this.idMovie = getColumnInt(cursor, FavouriteColumn.MOVIE_ID);
        this.title = getColumnString(cursor, FavouriteColumn.TITLE);
        this.overview = getColumnString(cursor, FavouriteColumn.OVERVIEW);
        this.voteAvg = getColumnInt(cursor, FavouriteColumn.RATING);
        this.originalLanguage = getColumnString(cursor, FavouriteColumn.LANGUAGE);
        this.releaseDate = getColumnString(cursor, FavouriteColumn.RELEASE_DATE);
        this.posterPath = getColumnString(cursor, FavouriteColumn.IMAGE_PATH);
        Log.d(TAG, "Movie: DB Poster : " + posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getVoteAvg() {
        return voteAvg;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVoteAvg(int voteAvg) {
        this.voteAvg = voteAvg;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }
}
