package com.himasif.myf.moviecatalogue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.himasif.myf.moviecatalogue.Build.Config;
import com.himasif.myf.moviecatalogue.Model.Movie;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final String EXTRA_MOVIE = "extra_movie";
    private Movie movie;
    private ImageView imgPoster;
    private TextView tvTitle, tvRating, tvReleaseDate, tvOriLang, tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imgPoster = (ImageView) findViewById(R.id.img_poster_detail);
        tvTitle = (TextView) findViewById(R.id.tv_title_detail);
        tvRating = (TextView) findViewById(R.id.tv_rating_detail);
        tvReleaseDate = (TextView) findViewById(R.id.tv_releaseDate_detail);
        tvOriLang = (TextView) findViewById(R.id.tv_orilang_detail);
        tvOverview = (TextView) findViewById(R.id.tv_overview_detail);
        movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        setAll();
    }

    private void setAll() {
        Log.d(TAG, "onCreate: Poster URL Detail : " + Config.POSTER_URL_W780 + movie.getPosterPath());
        Glide.with(this)
                .load(Config.POSTER_URL_W780 + movie.getPosterPath())
                .fitCenter()
                .into(imgPoster);
        tvTitle.setText(movie.getTitle());
        tvRating.setText("Rating : " + movie.getVoteAvg());
        tvReleaseDate.setText("Release on : " + movie.getReleaseDate());
        tvOriLang.setText("Language : " + movie.getOriginalLanguage());
        tvOverview.setText(movie.getOverview());
    }
}
