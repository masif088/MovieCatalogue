package com.himasif.myf.moviecatalogue;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.himasif.myf.moviecatalogue.Adapter.ListMovieAdapter;
import com.himasif.myf.moviecatalogue.Model.DownloadResultAsyncTaskLoader;
import com.himasif.myf.moviecatalogue.Model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<ArrayList<Movie>>, AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnSearch;
    private EditText edtSearch;
    private ListView lvSearchResult;
    private ProgressBar progressBar;
    private ListMovieAdapter adapter;
    public static final String EXTRA_INPUT = "extra_input";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ListMovieAdapter(this);
        adapter.notifyDataSetChanged();
        lvSearchResult = (ListView) findViewById(R.id.lv_search_result);
        btnSearch = (Button) findViewById(R.id.btn_search);
        edtSearch = (EditText) findViewById(R.id.edt_search);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        btnSearch.setOnClickListener(this);
        lvSearchResult.setAdapter(adapter);
        lvSearchResult.setOnItemClickListener(this);
        edtSearch.clearFocus();
        String input = edtSearch.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_INPUT, input);
        getLoaderManager().initLoader(0, bundle, this);
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
                    getLoaderManager().restartLoader(0, bundle, MainActivity.this);
                    edtSearch.clearFocus();
                }
                break;
        }
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
        String input = "";
        if (bundle != null) {
            input = bundle.getString(EXTRA_INPUT);
        }
        return new DownloadResultAsyncTaskLoader(this, input);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        adapter.setMovieArrayList(movies);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        adapter.setMovieArrayList(null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE, ((Movie) adapter.getItem(i)));
        startActivity(intent);
    }
}
