package com.himasif.myf.moviecatalogue.Fragments;


import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.himasif.myf.moviecatalogue.Adapters.ListMovieCardAdapter;
import com.himasif.myf.moviecatalogue.BuildConfig;
import com.himasif.myf.moviecatalogue.Models.DownloadResultAsyncTaskLoader;
import com.himasif.myf.moviecatalogue.Models.Movie;
import com.himasif.myf.moviecatalogue.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    @BindView(R.id.rv_upcoming) RecyclerView mRecycleView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    private ListMovieCardAdapter mUpComingCardAdapter;
    private String UPCOMING_URL;
    private ArrayList<Movie> mMovieArrayList;

    public UpComingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(MainFragment.EXTRA_MOVIES_LIST, mMovieArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        ButterKnife.bind(this, view);
        showRecycleCardView();
        if (savedInstanceState != null){
            mMovieArrayList = (ArrayList<Movie>) savedInstanceState.getSerializable(MainFragment.EXTRA_MOVIES_LIST);
            mUpComingCardAdapter.setmMoviesArrayList(mMovieArrayList);
            mRecycleView.setAdapter(mUpComingCardAdapter);
            progressBar.setVisibility(View.INVISIBLE);
            Log.d(TAG, "onCreateView: Saved Instanced " + mMovieArrayList);
        } else{
            UPCOMING_URL = String.format(getResources().getString(R.string.upcoming_url), BuildConfig.API_KEY);
            getLoaderManager().initLoader(0, null, this);
            Log.d(TAG, "onCreateView: Init Loader");
        }

        return view;
    }

    private void showRecycleCardView(){
        mUpComingCardAdapter = new ListMovieCardAdapter(getContext());
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new DownloadResultAsyncTaskLoader(getContext(), UPCOMING_URL);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        mMovieArrayList = data;
        mUpComingCardAdapter.setmMoviesArrayList(mMovieArrayList);
        mRecycleView.setAdapter(mUpComingCardAdapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<ArrayList<Movie>> loader) {
        mUpComingCardAdapter.setmMoviesArrayList(null);
    }
}
