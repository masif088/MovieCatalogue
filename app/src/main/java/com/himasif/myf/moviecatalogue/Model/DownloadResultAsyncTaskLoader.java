package com.himasif.myf.moviecatalogue.Model;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.himasif.myf.moviecatalogue.Build.Config;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DownloadResultAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private static final String TAG = DownloadResultAsyncTaskLoader.class.getSimpleName();
    private String input;
    private boolean hasResult = false;
    private ArrayList<Movie> movieArrayList;

    public DownloadResultAsyncTaskLoader(Context context, String input) {
        super(context);
        onContentChanged();
        this.input = input;
        Log.d(TAG, "DownloadResultAsyncTaskLoader: Constructor");
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) forceLoad();
        else if (hasResult) deliverResult(movieArrayList);
    }

    @Override
    public void deliverResult(ArrayList<Movie> data) {
        movieArrayList = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            onReleaseResources(movieArrayList);
            movieArrayList = null;
            hasResult = false;
        }
    }

    protected void onReleaseResources(ArrayList<Movie> data) {
        //nothing to do.
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient syncHttpClient = new SyncHttpClient();
        final ArrayList<Movie> movies = new ArrayList<Movie>();
        String url = Config.SEARCH_URL + input;
        Log.d(TAG, "loadInBackground: URL : " + url);

        syncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "onSuccess: Download");
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray list = jsonObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movieResponse = list.getJSONObject(i);
                        Movie movie = new Movie(movieResponse);
                        movies.add(movie);
                    }
                    Log.d(TAG, "onSuccess: Json List : " + list.toString());
                } catch (JSONException e) {
                    Log.e(TAG, "onSuccess: " + e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "onFailure: Download");
            }
        });
        return movies;
    }
}
