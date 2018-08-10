package com.himasif.myf.moviecatalogue;

import android.util.Log;

import com.himasif.myf.moviecatalogue.Build.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie implements Serializable{

    private static final String TAG = Movie.class.getSimpleName();
    private String title;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private int voteAvg;
    private String originalLanguage;

    public Movie(JSONObject jsonObject) {

        try {
            this.title = jsonObject.getString("title");
            this.overview = jsonObject.getString("overview");
            this.voteAvg = jsonObject.getInt("vote_average");
            this.originalLanguage = jsonObject.getString("original_language");
            this.posterPath = jsonObject.getString("poster_path");
            String dateRaw = jsonObject.getString("release_date");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(dateRaw);
                SimpleDateFormat newFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                String dateFix = newFormat.format(date);
                this.releaseDate = dateFix;
            } catch (ParseException e) {
                e.printStackTrace();
                this.releaseDate = dateRaw;
            }

        } catch (JSONException e) {
            Log.e(TAG, "Movie: " + e);
        }

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
}
