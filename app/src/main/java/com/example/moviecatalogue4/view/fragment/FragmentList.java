package com.example.moviecatalogue4.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue4.R;
import com.example.moviecatalogue4.adapter.AdapterRecycleView;
import com.example.moviecatalogue4.model.ModelMovie;
import com.example.moviecatalogue4.model.ModelTv;
import com.example.moviecatalogue4.viewmodel.ViewModelMovie;
import com.example.moviecatalogue4.viewmodel.ViewModelMovieFavorite;
import com.example.moviecatalogue4.viewmodel.ViewModelMovieSearch;
import com.example.moviecatalogue4.viewmodel.ViewModelTv;
import com.example.moviecatalogue4.viewmodel.ViewModelTvFavorite;
import com.example.moviecatalogue4.viewmodel.ViewModelTvSearch;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentList extends Fragment {

    private AdapterRecycleView adapter;
    private ProgressBar progressBar;

    private Observer<ArrayList<ModelTv>> getTv = new Observer<ArrayList<ModelTv>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelTv> tvs) {
            if (tvs != null) {
                adapter.AdapterTv(tvs);
                showLoading(false);
            }
        }
    };

    private Observer<ArrayList<ModelMovie>> getMovie = new Observer<ArrayList<ModelMovie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelMovie> movies) {
            if (movies != null) {
                adapter.AdapterMovie(movies);
                showLoading(false);
            }
        }
    };


    public FragmentList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        if (Objects.equals(getArguments().getString("TYPE"), "MOVIE")) {
            ViewModelMovie viewModelMovie = ViewModelProviders.of(this).get(ViewModelMovie.class);
            viewModelMovie.getListMovie().observe(this, getMovie);
        } else if (Objects.equals(getArguments().getString("TYPE"), "TV")) {
            ViewModelTv viewModelTv = ViewModelProviders.of(this).get(ViewModelTv.class);
            viewModelTv.getListTv().observe(this, getTv);
        } else if (Objects.equals(getArguments().getString("TYPE"), "MOVIEPAGER")) {
            ViewModelMovieFavorite viewModelMovie = ViewModelProviders.of(this).get(ViewModelMovieFavorite.class);
            viewModelMovie.getListMovie().observe(this, getMovie);
        } else if (Objects.equals(getArguments().getString("TYPE"), "TVPAGER")) {
            ViewModelTvFavorite viewModelMovie = ViewModelProviders.of(this).get(ViewModelTvFavorite.class);
            viewModelMovie.getListMovie().observe(this, getTv);
        } else if (Objects.equals(getArguments().getString("TYPE"), "MOVIESEARCH")) {
            ViewModelMovieSearch viewModelMovieSearch = ViewModelProviders.of(this).get(ViewModelMovieSearch.class);
            viewModelMovieSearch.setURL(getArguments().getString("URL"));
            viewModelMovieSearch.getListMovie().observe(this, getMovie);
        } else if (Objects.equals(getArguments().getString("TYPE"), "TVSEARCH")) {
            ViewModelTvSearch viewModelTvSearch = ViewModelProviders.of(this).get(ViewModelTvSearch.class);
            viewModelTvSearch.setURL(getArguments().getString("URL"));
            viewModelTvSearch.getListTv().observe(this, getTv);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);

        adapter = new AdapterRecycleView(getContext());
        adapter.notifyDataSetChanged();

        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        if (FragmentSetting.LAYOUT.equals("Grid Layout")) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
