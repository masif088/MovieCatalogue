package com.example.moviecatalogue4.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviecatalogue4.model.ModelTv;

import java.util.List;

@Dao
public interface TvDao {
    @Query("SELECT * FROM ModelTv")
    List<ModelTv> SelectTv();

    @Query("SELECT * FROM ModelTv")
    Cursor SelectTvProvider();

    @Insert
    void insertTv(ModelTv modelTv);

    @Delete
    void deleteTv(ModelTv modelTv);

    @Query("SELECT * FROM ModelTv WHERE title LIKE :nama")
    List<ModelTv> favCheck(String nama);

}