package com.himasif.myf.moviecatalogue;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.himasif.myf.moviecatalogue.DB.MovieHelper;
import com.himasif.myf.moviecatalogue.Models.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final String POSTER_URL_W500 = "http://image.tmdb.org/t/p/w500"; // image for details
    public static final String EXTRA_MOVIE = "extra_movie";
    private Movie movie;
    @BindView(R.id.img_poster_detail)
    ImageView imgPoster;
    @BindView(R.id.tv_title_detail)
    TextView tvTitle;
    @BindView(R.id.tv_rating_detail)
    TextView tvRating;
    @BindView(R.id.tv_releaseDate_detail)
    TextView tvReleaseDate;
    @BindView(R.id.tv_orilang_detail)
    TextView tvOriLang;
    @BindView(R.id.tv_overview_detail)
    TextView tvOverview;
    @BindView(R.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.fab_detail)
    FloatingActionButton favFab;
    private boolean isFavourite;
    private MovieHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        helper = new MovieHelper(this);
        helper.open();
        setSupportActionBar(toolbar);

        movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        isFavourite = helper.getMovie(movie.getIdMovie()) != null;
        setAll();
    }

    private void setAll() {
        Log.d(TAG, "onCreate: Poster URL Detail : " + POSTER_URL_W500 + movie.getPosterPath());
        Glide.with(this)
                .load(POSTER_URL_W500 + movie.getPosterPath())
                .fitCenter()
                .into(imgPoster);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        tvTitle.setText(movie.getTitle());
        tvRating.setText(tvRating.getText().toString() + " " + movie.getVoteAvg());
        tvReleaseDate.setText(tvReleaseDate.getText().toString() + " " + movie.getReleaseDate());
        tvOriLang.setText(tvOriLang.getText().toString() + " " + movie.getOriginalLanguage());
        tvOverview.setText(movie.getOverview());
        favFab.setImageDrawable(isFavourite ? getResources().getDrawable(R.drawable.ic_favorite_black_24dp) : getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));

        favFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavourite){
                    helper.delete(movie.getIdMovie());
                    favFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                    Toast.makeText(DetailActivity.this, "Remove From Favorite", Toast.LENGTH_SHORT).show();
                    isFavourite = false;
                } else{
                    helper.insert(movie);
                    favFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                    Toast.makeText(DetailActivity.this, "Add To Favorite", Toast.LENGTH_SHORT).show();
                    isFavourite = true;
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        helper.close();
    }
}
