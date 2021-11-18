package com.ngahuynh.myapplication.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.room.AppRepository;

public class CreateViewModel extends AndroidViewModel {
    MutableLiveData<User> userData;
    private AppRepository repo;

    public CreateViewModel(@NonNull Application application) {
        super(application);

        repo = AppRepository.getInstance(application);
        userData = repo.getUserData();
    }

    public LiveData<User> getUserData() {
        return userData;
    }

    public void setAppUserName(String name) {
        repo.setAppUserName(name);
    }
    public void setAppUserDOB(String dob) {
        repo.setAppUserDOB(dob);
    }
    public void setAppUserProfPic(Bitmap profPic) {
        repo.setAppUserProfPic(profPic);
    }
    public void setAppUserFeet(int feet) {
        repo.setAppUserFeet(feet);
    }
    public void setAppUserInches(int inches) {
        repo.setAppUserInches(inches);
    }
    public void setAppUserLBS(int lbs) {
        repo.setAppUserLBS(lbs);
    }
    public void setAppUserDecimals(int decimals) {
        repo.setAppUserDecimals(decimals);
    }
    public void setAppUserGender(String gender) {
        repo.setAppUserGender(gender);
    }
    public void setAppUserCheckedGender(int checkedGender) {
        repo.setAppUserCheckedGender(checkedGender);
    }
    public void setAppUserAge(String age) {
        repo.setAppUserAge(age);
    }
    public void setAppUserCity(String city) {
        repo.setAppUserCity(city);
    }
    public void setAppUserState(String state) {
        repo.setAppUserState(state);
    }
    public void setAppUserCountry(String country) {
        repo.setAppUserCountry(country);
    }
    public void setAppUserHeight(double height) {
        repo.setAppUserHeight(height);
    }
    public void setAppUserWeight(double weight) {
        repo.setAppUserWeight(weight);
    }
    public void setAppUserBMI(double bmi) {
        repo.setAppUserBMI(bmi);
    }
    public void setAppUserLocation(String location) {
        repo.setAppUserLocation(location);
    }
    public void setAppUserCityPosition(int cityPosition) {
        repo.setAppUserCityPosition(cityPosition);
    }
    public void setAppUserStatePosition(int statePosition) {
        repo.setAppUserStatePosition(statePosition);
    }
    public void setAppUserCountryPosition(int countryPosition) {
        repo.setAppUserCountryPosition(countryPosition);
    }
}
