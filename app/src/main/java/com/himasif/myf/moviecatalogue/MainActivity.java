package com.himasif.myf.moviecatalogue;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.himasif.myf.moviecatalogue.Build.Config;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnSearch;
    private EditText edtSearch;
    private ListView lvSearchResult;
    private ArrayList<Movie> movies;
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

        btnSearch.setOnClickListener(this);
        movies = new ArrayList<Movie>();
        lvSearchResult.setAdapter(adapter);
        String input = edtSearch.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_INPUT, input);
        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                Log.d(TAG, "onClick: Clicked");
                String input = edtSearch.getText().toString();
                if(!input.equals("")){
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRA_INPUT, input);
                    getLoaderManager().restartLoader(0, bundle, MainActivity.this);
                }
                break;
        }
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
        String input = "";
        if(bundle != null){
            input = bundle.getString(EXTRA_INPUT);
        }
        return new DownloadResultAsyncTaskLoader(this, input);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        adapter.setMovieArrayList(movies);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        adapter.setMovieArrayList(null);
    }
}