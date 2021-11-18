package com.ngahuynh.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.room.AppRepository;

public class StartViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> auth;
    private final AppRepository repo;

    public StartViewModel(@NonNull Application application) {
        super(application);

        repo = AppRepository.getInstance(application);
    }

    public MutableLiveData<Boolean> getAuth() {
        return repo.getAuth();
    }

    //username and password passed when StartViewModel.authUser() is called in StartActivity
    public void authUser(String username, String password) {
        repo.authUser(username,password);
    }
}
