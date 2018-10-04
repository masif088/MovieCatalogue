package com.himasif.myf.moviecatalogue.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.himasif.myf.moviecatalogue.Adapters.ListMovieAdapter;
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
public class SearchFragment extends Fragment implements View.OnClickListener, android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Movie>>, AdapterView.OnItemClickListener{

    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.lv_search_result)
    ListView lvSearchResult;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private ListMovieAdapter adapter;
    public static final String EXTRA_INPUT = "extra_input";
    private Context mContext;
    private static final String TAG = SearchFragment.class.getSimpleName();
    private String SEARCH_URL;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        mContext = getContext();
        SEARCH_URL = String.format(getResources().getString(R.string.search_url), BuildConfig.API_KEY);

        adapter = new ListMovieAdapter(mContext);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);

        btnSearch.setOnClickListener(this);
        lvSearchResult.setAdapter(adapter);
        lvSearchResult.setOnItemClickListener(this);
        edtSearch.clearFocus();
        String input = edtSearch.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_INPUT, input);
        getLoaderManager().initLoader(0, bundle, this);
        return view;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
        String input = "";
        if (bundle != null) {
            input = bundle.getString(EXTRA_INPUT);
        }
        SEARCH_URL += input;
        return new DownloadResultAsyncTaskLoader(mContext, SEARCH_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        adapter.setMovieArrayList(data);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        adapter.setMovieArrayList(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                progressBar.setVisibility(View.VISIBLE);
                Log.d(TAG, "onClick: Clicked");
                String input = edtSearch.getText().toString();
                if (!input.equals("")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRA_INPUT, input);
                    getLoaderManager().restartLoader(0, bundle, this);
                    edtSearch.clearFocus();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE, ((Movie) adapter.getItem(i)));
        startActivity(intent);
    }
}
