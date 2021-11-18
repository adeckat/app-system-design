package com.ngahuynh.myapplication.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProfileDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProfileTable profileTable);

    @Query("DELETE FROM profile_table")
    void deleteAll();

    @Query("SELECT * from profile_table ORDER BY password ASC")
    LiveData<List<ProfileTable>> getAll();

    @Query("SELECT * from profile_table WHERE username = :username")
    ProfileTable findUser(String username);
}

