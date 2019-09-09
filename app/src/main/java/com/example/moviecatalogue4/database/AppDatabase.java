package com.example.moviecatalogue4.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.moviecatalogue4.model.ModelMovie;
import com.example.moviecatalogue4.model.ModelTv;

@Database(entities = {ModelTv.class, ModelMovie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract TvDao tvDao();
}
