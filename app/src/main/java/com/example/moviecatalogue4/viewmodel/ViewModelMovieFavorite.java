package com.example.moviecatalogue4.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalogue4.database.MyApp;
import com.example.moviecatalogue4.model.ModelMovie;

import java.util.ArrayList;

public class ViewModelMovieFavorite extends ViewModel {
    private MutableLiveData<ArrayList<ModelMovie>> listMovie = new MutableLiveData<>();

    public ViewModelMovieFavorite() {
        listMovie.postValue((ArrayList<ModelMovie>) MyApp.db.movieDao().SelectMovie());
    }

    public LiveData<ArrayList<ModelMovie>> getListMovie() {
        return listMovie;
    }
}
