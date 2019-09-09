package com.example.moviecatalogue4.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalogue4.database.MyApp;
import com.example.moviecatalogue4.model.ModelTv;

import java.util.ArrayList;

public class ViewModelTvFavorite extends ViewModel {
    private MutableLiveData<ArrayList<ModelTv>> listTv = new MutableLiveData<>();

    public ViewModelTvFavorite() {
        listTv.postValue((ArrayList<ModelTv>) MyApp.db.tvDao().SelectTv());
    }

    public LiveData<ArrayList<ModelTv>> getListMovie() {
        return listTv;
    }
}
