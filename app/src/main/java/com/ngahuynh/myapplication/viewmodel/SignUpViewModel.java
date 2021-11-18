package com.ngahuynh.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ngahuynh.myapplication.room.AppRepository;

public class SignUpViewModel extends AndroidViewModel {
    private AppRepository repo;

    public SignUpViewModel(@NonNull Application application) {
        super(application);

        repo = AppRepository.getInstance(application);
    }

    //username and password passed when StartViewModel.logUser() is called in SignUpActivity
    public void logUser(String username, String password) {
        repo.logUser(username, password);
    }
}
