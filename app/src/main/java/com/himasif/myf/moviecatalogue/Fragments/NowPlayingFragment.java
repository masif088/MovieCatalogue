package com.himasif.myf.moviecatalogue.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.himasif.myf.moviecatalogue.Adapters.NowPlayingCardAdapter;
import com.himasif.myf.moviecatalogue.BuildConfig;
import com.himasif.myf.moviecatalogue.DetailActivity;
import com.himasif.myf.moviecatalogue.Models.DownloadResultAsyncTaskLoader;
import com.himasif.myf.moviecatalogue.Models.Movie;
import com.himasif.myf.moviecatalogue.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    @BindView(R.id.rv_now_playing) RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    private static final String TAG = NowPlayingFragment.class.getSimpleName();
    private String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" +
            BuildConfig.API_KEY + "&language=en-US";
    private NowPlayingCardAdapter nowPlayingCardAdapter;
    private ArrayList<Movie> mMovieArrayList;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, view);
        showRecycleCardView();
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    private void showRecycleCardView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        nowPlayingCardAdapter = new NowPlayingCardAdapter(getContext());
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new DownloadResultAsyncTaskLoader(getContext(), "", NOW_PLAYING_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        mMovieArrayList = data;
        nowPlayingCardAdapter.setmMoviesArrayList(mMovieArrayList);
        mRecyclerView.setAdapter(nowPlayingCardAdapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        nowPlayingCardAdapter.setmMoviesArrayList(null);
    }
}
