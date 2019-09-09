package com.example.moviecatalogue4.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue4.R;
import com.example.moviecatalogue4.model.ModelMovie;
import com.example.moviecatalogue4.model.ModelTv;
import com.example.moviecatalogue4.view.activity.Detail;
import com.example.moviecatalogue4.view.fragment.FragmentSetting;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRecycleView extends RecyclerView.Adapter<AdapterRecycleView.ViewHolder> {

    private ArrayList<ModelMovie> movies = new ArrayList<>();
    private ArrayList<ModelTv> tvs = new ArrayList<>();
    private String TYPE = "MOVIE";
    private Context context;

    public AdapterRecycleView(Context context) {
        this.context = context;
    }

    public void AdapterMovie(ArrayList<ModelMovie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
        TYPE = "MOVIE";
    }

    public void AdapterTv(ArrayList<ModelTv> tvs) {
        this.tvs.clear();
        this.tvs.addAll(tvs);
        notifyDataSetChanged();
        TYPE = "TV";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (FragmentSetting.LAYOUT.equals("Grid Layout")) {
            view = LayoutInflater.from(context).inflate(R.layout.item_grid, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        if (TYPE.equals("TV")) {
            holder.title.setText(tvs.get(i).getTitle());
            holder.released.setText(tvs.get(i).getReleaseDate());
            Glide.with(context).load(tvs.get(i).getPoster()).into(holder.poster);
            holder.rating.setText(tvs.get(i).getRating());
            holder.ratingBar.setRating(Float.parseFloat(tvs.get(i).getRating()) / 2);
            holder.itemView.setOnClickListener(view -> {
                Intent intentTv = new Intent(context, Detail.class);
                intentTv.putExtra(Detail.DATA, tvs.get(holder.getAdapterPosition()));
                context.startActivity(intentTv);
            });
        } else if (TYPE.equals("MOVIE")) {
            holder.title.setText(movies.get(i).getTitle());
            holder.released.setText(movies.get(i).getReleaseDate());
            Glide.with(context).load(movies.get(i).getPoster()).into(holder.poster);
            holder.rating.setText(movies.get(i).getRating());
            holder.ratingBar.setRating(Float.parseFloat(movies.get(i).getRating()) / 2);
            holder.itemView.setOnClickListener(view -> {
                Intent intentMovie = new Intent(context, Detail.class);
                intentMovie.putExtra(Detail.DATA, movies.get(holder.getAdapterPosition()));
                context.startActivity(intentMovie);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (TYPE.equals("TV")) {
            return tvs.size();
        } else {
            return movies.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

