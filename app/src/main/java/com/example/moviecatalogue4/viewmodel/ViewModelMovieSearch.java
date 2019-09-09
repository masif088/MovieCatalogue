package com.example.moviecatalogue4.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalogue4.BuildConfig;
import com.example.moviecatalogue4.model.ModelMovie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ViewModelMovieSearch extends ViewModel {
    private MutableLiveData<ArrayList<ModelMovie>> listMovie = new MutableLiveData<>();

    public void setURL(String query){
        String checkLanguage = Locale.getDefault().getDisplayLanguage();
        String language;
        if (checkLanguage.equals("English")) {
            language = "en";
        } else {
            language = "id";
        }
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ModelMovie> listItem = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.TMDB_API_KEY + "&language=" + language + "-US&query="+query;
        Log.d("Cek",url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject Object = new JSONObject(result);
                    JSONArray list = Object.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        ModelMovie movieItem = new ModelMovie(movie);
                        listItem.add(movieItem);
                    }
                    listMovie.postValue(listItem);
                } catch (Exception ignored) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
    }

    public LiveData<ArrayList<ModelMovie>> getListMovie() {
        return listMovie;
    }

}
