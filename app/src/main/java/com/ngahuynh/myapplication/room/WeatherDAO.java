package com.ngahuynh.myapplication.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherTable weatherTable);

    @Query("DELETE FROM weather_table")
    void deleteAll();

    @Query("SELECT * from weather_table ORDER BY weatherdata ASC")
    LiveData<List<WeatherTable>> getAll();
}
