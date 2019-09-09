package com.example.moviecatalogue4.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

@Entity
public class ModelTv implements Parcelable {
    public static final Creator<ModelTv> CREATOR = new Creator<ModelTv>() {
        @Override
        public ModelTv createFromParcel(Parcel source) {
            return new ModelTv(source);
        }

        @Override
        public ModelTv[] newArray(int size) {
            return new ModelTv[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String overview;
    @ColumnInfo
    private String poster;
    @ColumnInfo
    private String releaseDate;
    @ColumnInfo
    private String backdrop;
    @ColumnInfo
    private String rating;

    public ModelTv(int id, String title, String overview, String poster, String releaseDate, String backdrop, String rating) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster = poster;
        this.releaseDate = releaseDate;
        this.backdrop = backdrop;
        this.rating = rating;
    }

    @Ignore
    public ModelTv(JSONObject object) {
        try {
            title = object.getString("original_name");
            overview = object.getString("overview");
            poster = "https://image.tmdb.org/t/p/w500" + object.getString("poster_path");
            releaseDate = object.getString("first_air_date");
            backdrop = "https://image.tmdb.org/t/p/w780" + object.getString("backdrop_path");
            rating = object.getString("vote_average");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Ignore
    public ModelTv(int id) {
        this.id = id;
    }

    protected ModelTv(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.releaseDate = in.readString();
        this.backdrop = in.readString();
        this.rating = in.readString();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster() {
        return poster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getRating() {
        return rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
        dest.writeString(this.releaseDate);
        dest.writeString(this.backdrop);
        dest.writeString(this.rating);
    }
}
