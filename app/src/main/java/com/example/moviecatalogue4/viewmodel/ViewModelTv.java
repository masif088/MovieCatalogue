package com.example.moviecatalogue4.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalogue4.BuildConfig;
import com.example.moviecatalogue4.model.ModelTv;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ViewModelTv extends ViewModel {
    private MutableLiveData<ArrayList<ModelTv>> listTv = new MutableLiveData<>();

    public ViewModelTv() {
        String language;
        if (Locale.getDefault().getDisplayLanguage().equals("English")) {
            language = "en";
        } else {
            language = "id";
        }
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ModelTv> listItem = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/on_the_air?api_key=" + BuildConfig.TMDB_API_KEY + "&language=" + language + "-US&page=1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray list = object.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tv = list.getJSONObject(i);
                        ModelTv tvItem = new ModelTv(tv);
                        listItem.add(tvItem);
                    }
                    listTv.postValue(listItem);
                } catch (Exception ignored) {

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
    }

    public LiveData<ArrayList<ModelTv>> getListTv() {
        return listTv;
    }
}
