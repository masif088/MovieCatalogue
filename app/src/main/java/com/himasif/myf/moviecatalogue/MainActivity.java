package com.himasif.myf.moviecatalogue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.himasif.myf.moviecatalogue.Build.Config;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnSearch;
    private EditText edtSearch;
    private RecyclerView rvSearchResult;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvSearchResult = findViewById(R.id.rv_search_result);
        btnSearch = findViewById(R.id.btn_search);
        edtSearch = findViewById(R.id.edt_search);
        movies = new ArrayList<Movie>();
    }

    private void showResultMovies(){
        btnSearch.setOnClickListener(this);
        rvSearchResult.setHasFixedSize(true);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(this);
        listMovieAdapter.setMovieArrayList(movies);
        rvSearchResult.setAdapter(listMovieAdapter);
    }

    private void getMovieSearchResult(String input) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String url = Config.SEARCH_URL + input;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                String input = edtSearch.getText().toString();
                if(!input.equals("")){
                    getMovieSearchResult(input);
                }
                break;
        }
    }
}
