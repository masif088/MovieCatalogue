package com.example.moviecatalogue4.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue4.database.MyApp;
import com.example.moviecatalogue4.model.ModelMovie;
import com.example.moviecatalogue4.model.ModelTv;
import com.example.moviecatalogue4.R;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Detail extends AppCompatActivity {

    public static final String DATA = "NULL";
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.poster)
    RoundedImageView poster;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tv_release)
    TextView released;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.tv_rating)
    TextView rating;
    @BindView(R.id.tv_overview)
    TextView overview;
    @BindView(R.id.favorite)
    ImageView favorite;
    private ModelMovie movie;
    private ModelTv tv;
    private String TYPE = "";
    private String titleCheck;
    private String titles = "WRONG";
    private int ids = 0;

    @SuppressLint({"Recycle", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        try {
            tv = getIntent().getParcelableExtra(DATA);
            assert tv != null;
            title.setText(tv.getTitle());
            released.setText(tv.getReleaseDate());
            overview.setText(tv.getOverview());
            Glide.with(getApplicationContext()).load(tv.getPoster()).into(poster);
            Glide.with(getApplicationContext()).load(tv.getBackdrop()).into(backdrop);
            ratingBar.setRating(Float.parseFloat(tv.getRating()) / 2);
            rating.setText(tv.getRating());
            TYPE = "TV";
            titleCheck = tv.getTitle();
            try {
                titles = MyApp.db.tvDao().favCheck(titleCheck).get(0).getTitle();
                favorite.setImageResource(getResources().obtainTypedArray(R.array.fav).getResourceId(0, -1));
            } catch (Exception ignored) {
                favorite.setImageResource(getResources().obtainTypedArray(R.array.fav).getResourceId(1, -1));
            }
        } catch (Exception e) {
            movie = getIntent().getParcelableExtra(DATA);
            assert movie != null;
            title.setText(movie.getTitle());
            released.setText(movie.getReleaseDate());
            overview.setText(movie.getOverview());
            Glide.with(getApplicationContext()).load(movie.getPoster()).into(poster);
            Glide.with(getApplicationContext()).load(movie.getBackdrop()).into(backdrop);
            ratingBar.setRating(Float.parseFloat(movie.getRating()) / 2);
            rating.setText(movie.getRating());
            TYPE = "MOVIE";
            titleCheck = movie.getTitle();
            try {
                titles = MyApp.db.movieDao().favCheck(titleCheck).get(0).getTitle();
                favorite.setImageResource(getResources().obtainTypedArray(R.array.fav).getResourceId(0, -1));
            } catch (Exception ignored) {
                favorite.setImageResource(getResources().obtainTypedArray(R.array.fav).getResourceId(1, -1));
            }
        }
    }

    @SuppressLint({"ResourceType", "Recycle"})
    @OnClick(R.id.favorite)
    public void onViewClicked() {
        if (TYPE.equals("TV")) {
            try {
                titles = MyApp.db.tvDao().favCheck(titleCheck).get(0).getTitle();
                ids = MyApp.db.tvDao().favCheck(titleCheck).get(0).getId();
                favorite.setImageResource(getResources().obtainTypedArray(R.array.fav).getResourceId(0, -1));
            } catch (Exception ignored) {
                favorite.setImageResource(getResources().obtainTypedArray(R.array.fav).getResourceId(1, -1));
            }
            if (!titleCheck.equals(titles)) {
                MyApp.db.tvDao().insertTv(tv);
                Toast.makeText(getApplicationContext(), getResources().getStringArray(R.array.add_remove)[0], Toast.LENGTH_SHORT).show();
            } else {
                ModelTv tvs = new ModelTv(ids);
                MyApp.db.tvDao().deleteTv(tvs);
                Toast.makeText(getApplicationContext(), getResources().getStringArray(R.array.add_remove)[1], Toast.LENGTH_SHORT).show();
            }
        } else if (TYPE.equals("MOVIE")) {
            try {
                titles = MyApp.db.movieDao().favCheck(titleCheck).get(0).getTitle();
                ids = MyApp.db.movieDao().favCheck(titleCheck).get(0).getId();
                favorite.setImageResource(getResources().obtainTypedArray(R.array.fav).getResourceId(0, -1));
            } catch (Exception ignored) {
                favorite.setImageResource(getResources().obtainTypedArray(R.array.fav).getResourceId(1, -1));
            }
            if (!titleCheck.equals(titles)) {
                MyApp.db.movieDao().insertMovie(movie);
                Toast.makeText(getApplicationContext(), getResources().getStringArray(R.array.add_remove)[0], Toast.LENGTH_SHORT).show();
            } else {
                ModelMovie movies = new ModelMovie(ids);
                MyApp.db.movieDao().deleteMovie(movies);
                Toast.makeText(getApplicationContext(), getResources().getStringArray(R.array.add_remove)[1], Toast.LENGTH_SHORT).show();
            }
        }
        onRestart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (TYPE.equals("TV")) {
            Intent i = new Intent(Detail.this, Detail.class);
            i.putExtra(Detail.DATA, tv);
            startActivity(i);
            finish();
        } else if (TYPE.equals("MOVIE")) {
            Intent i = new Intent(Detail.this, Detail.class);
            i.putExtra(Detail.DATA, movie);
            startActivity(i);
            finish();
        }
    }
}
