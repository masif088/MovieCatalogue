package com.himasif.myf.moviecatalogue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.himasif.myf.moviecatalogue.Models.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final String POSTER_URL_W780 = "http://image.tmdb.org/t/p/w500"; // image for details
    public static final String EXTRA_MOVIE = "extra_movie";
    private Movie movie;
    @BindView(R.id.img_poster_detail) ImageView imgPoster;
    @BindView(R.id.tv_title_detail) TextView tvTitle;
    @BindView(R.id.tv_rating_detail) TextView tvRating;
    @BindView(R.id.tv_releaseDate_detail) TextView tvReleaseDate;
    @BindView(R.id.tv_orilang_detail) TextView tvOriLang;
    @BindView(R.id.tv_overview_detail) TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        setAll();
    }

    private void setAll() {
        Log.d(TAG, "onCreate: Poster URL Detail : " + POSTER_URL_W780 + movie.getPosterPath());
        Glide.with(this)
                .load(POSTER_URL_W780 + movie.getPosterPath())
                .fitCenter()
                .into(imgPoster);
        tvTitle.setText(movie.getTitle());
        tvRating.setText("Rating : " + movie.getVoteAvg());
        tvReleaseDate.setText("Release on : " + movie.getReleaseDate());
        tvOriLang.setText("Language : " + movie.getOriginalLanguage());
        tvOverview.setText(movie.getOverview());
    }
}
