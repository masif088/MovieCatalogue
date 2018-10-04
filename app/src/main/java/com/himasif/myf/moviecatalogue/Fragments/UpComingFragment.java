package com.himasif.myf.moviecatalogue.Fragments;


import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.himasif.myf.moviecatalogue.Adapters.UpComingCardAdapter;
import com.himasif.myf.moviecatalogue.BuildConfig;
import com.himasif.myf.moviecatalogue.Models.DownloadResultAsyncTaskLoader;
import com.himasif.myf.moviecatalogue.Models.Movie;
import com.himasif.myf.moviecatalogue.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    @BindView(R.id.rv_upcoming) RecyclerView mRecycleView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    private UpComingCardAdapter mUpComingCardAdapter;
    private String UPCOMING_URL;

    public UpComingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        UPCOMING_URL = String.format(getResources().getString(R.string.upcoming_url), BuildConfig.API_KEY);
        ButterKnife.bind(this, view);
        showRecycleCardView();
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    private void showRecycleCardView(){
        mUpComingCardAdapter = new UpComingCardAdapter(getContext());
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new DownloadResultAsyncTaskLoader(getContext(), UPCOMING_URL);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        mUpComingCardAdapter.setmMovieArrayList(data);
        mRecycleView.setAdapter(mUpComingCardAdapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<ArrayList<Movie>> loader) {
        mUpComingCardAdapter.setmMovieArrayList(null);
    }
}
