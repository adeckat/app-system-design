package com.ngahuynh.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ngahuynh.myapplication.helper.Hike;
import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.helper.Weather;
import com.ngahuynh.myapplication.room.AppRepository;

import java.util.HashMap;
import java.util.List;


public class DetailViewModel extends AndroidViewModel {
    MutableLiveData<User> userData;
    MutableLiveData<Weather> weatherData;
    MutableLiveData<Hike> hikeData;

//    MutableLiveData<String> weatherLoc;
    private AppRepository repo;

    public DetailViewModel(@NonNull Application application) {
        super(application);

        repo = AppRepository.getInstance(application);
        userData = repo.getUserData();
        weatherData = repo.getWData();
        hikeData = repo.getHData();
    }

    public LiveData<User> getUserData() {
        return userData;
    }

    public MutableLiveData<Weather> getWeatherData() { return weatherData; }

    public LiveData<Hike> getHikeData() {
        return hikeData;
    }

//    public MutableLiveData<String> getWeatherLoc() {
//        if (weatherLoc == null) {
//            weatherLoc = new MutableLiveData<>();
//        }
//        return weatherLoc;
//    }
//
//    public String getWeatherLocStr(){
//        return repo.getWeatherLoc();
//    }

    public void setAppUserIntensityPosition(int intensityPosition) {repo.setAppUserIntensityPosition(intensityPosition);}
    public void setAppUserIntensity(String intensity) { repo.setAppUserIntensity(intensity);}
    public void setAppUserFrequency(int frequency) { repo.setAppUserFrequency(frequency);}
    public void setAppUserDuration(int duration) {repo.setAppUserDuration(duration);}

    public void setAppUserGoalPosition(int goalPosition) {repo.setAppUserGoalPosition(goalPosition);}
    public void setAppUserGoal(String goal) { repo.setAppUserGoal(goal);}
    public void setAppUserGoalLbs(int goalLbs) { repo.setAppUserGoalLbs(goalLbs);}
    public void setAppUserCalories(double calories) { repo.setAppUserCalories(calories);}

    public void setAppUserStep(int step) {repo.setAppUserStep(step);}

//    public void setWeatherLoc(String location) {repo.setWeatherLoc(location);}

    public void setWeatherCity(String city) {repo.setWeatherCity(city);}
    public void setWeatherCountry(String country) {repo.setWeatherCountry(country);}
    public void setWeatherLastUpdated(String lastUpdated) {repo.setWeatherLastUpdated(lastUpdated);}
    public void setWeatherTemp(String temperature) {repo.setWeatherTemp(temperature);}
    public void setWeatherCast(String cast) {repo.setWeatherCast(cast);}
    public void setWeatherHumidity(String humidity) {repo.setWeatherHumidity(humidity);}
    public void setWeatherMinTemp(String minTemp) {repo.setWeatherMinTemp(minTemp);}
    public void setWeatherMaxTemp(String maxTemp) {repo.setWeatherMaxTemp(maxTemp);}
    public void setWeatherSunrise(String sunrise) {repo.setWeatherSunrise(sunrise);}
    public void setWeatherSunset(String sunset) {repo.setWeatherSunset(sunset);}

    public void setHikeData(List<HashMap<String, String>> mapList){ repo.setHikeData(mapList); }
}
