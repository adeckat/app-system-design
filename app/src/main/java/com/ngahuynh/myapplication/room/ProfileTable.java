package com.ngahuynh.myapplication.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile_table")
public class ProfileTable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @NonNull
    @ColumnInfo(name = "password")
    private String passwordJson;

    public ProfileTable(@NonNull String username, @NonNull String passwordJson) {
        this.username = username;
        this.passwordJson = passwordJson;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordJson(String passwordJson) {
        this.passwordJson = passwordJson;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordJson() {
        return passwordJson;
    }
}



