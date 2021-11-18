package com.ngahuynh.myapplication.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HikeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HikeTable hikeTable);

    @Query("DELETE FROM hike_table")
    void deleteAll();

    @Query("SELECT * from hike_table ORDER BY hikedata ASC")
    LiveData<List<HikeTable>> getAll();
}




