package com.example.moviecatalogue4.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviecatalogue4.model.ModelMovie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM ModelMovie")
    List<ModelMovie> SelectMovie();

    @Query("SELECT * FROM ModelMovie")
    Cursor SelectMovieProvider();

    @Insert
    void insertMovie(ModelMovie... movie);

    @Delete
    void deleteMovie(ModelMovie movie);

    @Query("SELECT * FROM ModelMovie WHERE title = :nama")
    List<ModelMovie> favCheck(String nama);

}
