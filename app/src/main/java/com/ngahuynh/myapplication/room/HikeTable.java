package com.ngahuynh.myapplication.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hike_table")
public class HikeTable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "location")
    private String location;

    @NonNull
    @ColumnInfo(name = "hikedata")
    private String hikeJson;

    public HikeTable(@NonNull String location, @NonNull String hikeJson) {
        this.location = location;
        this.hikeJson = hikeJson;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHikeJson(String hikedata) {
        this.hikeJson = hikedata;
    }

    public String getLocation() {
        return location;
    }

    public String getHikeJson() {
        return hikeJson;
    }
}
