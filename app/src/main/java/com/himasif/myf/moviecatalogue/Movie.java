package com.himasif.myf.moviecatalogue;

import android.util.Log;

import com.himasif.myf.moviecatalogue.Build.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {

    private static final String TAG = Movie.class.getSimpleName();
    private String title;
    private String overview;
    private String posterPath;
    private String releaseDate;

    public Movie(JSONObject jsonObject) {

        try {
            this.title = jsonObject.getString("title");
            this.overview = jsonObject.getString("overview");
            this.posterPath = Config.POSTER_URL + jsonObject.getString("poster_path");
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

}
