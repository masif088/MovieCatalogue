package com.himasif.myf.moviecatalogue.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.himasif.myf.moviecatalogue.DetailActivity;
import com.himasif.myf.moviecatalogue.Models.Movie;
import com.himasif.myf.moviecatalogue.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dul mu'in on 05/09/2018.
 */

public class UpComingCardAdapter extends RecyclerView.Adapter<UpComingCardAdapter.CardViewHolder>{

    private Context mContext;
    private ArrayList<Movie> mMovieArrayList;
    public static final String POSTER_URL_W342 = "http://image.tmdb.org/t/p/w342";

    public UpComingCardAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_movie, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, final int position) {
        Movie movie = mMovieArrayList.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());
        holder.tvReleaseDate.setText(movie.getReleaseDate());
        Glide.with(mContext)
                .load(POSTER_URL_W342 + movie.getPosterPath())
                .into(holder.imgPhoto);
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, mMovieArrayList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieArrayList.size();
    }

    public void setmMovieArrayList(ArrayList<Movie> mMovieArrayList) {
        this.mMovieArrayList = mMovieArrayList;
    }

    class CardViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_overview) TextView tvOverview;
        @BindView(R.id.tv_releaseDate) TextView tvReleaseDate;
        @BindView(R.id.img_photo) ImageView imgPhoto;
        @BindView(R.id.btn_detail) Button btnDetail;
        @BindView(R.id.btn_share) Button btnShare;

        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
