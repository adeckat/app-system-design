package com.ngahuynh.myapplication.room;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.MutableLiveData;

import com.ngahuynh.myapplication.helper.Hike;
import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.helper.Weather;

import java.util.HashMap;
import java.util.List;

public class AppRepository {
    private static AppRepository instance;
    private WeatherDAO weatherDAO;
    private HikeDAO hikeDAO;
    private ProfileDAO profileDAO;
    private final MutableLiveData<HikeTable> hikeData = new MutableLiveData<HikeTable>();
    private final MutableLiveData<WeatherTable> weatherData = new MutableLiveData<WeatherTable>();
    private final MutableLiveData<Boolean> authorized = new MutableLiveData<Boolean>();
    //User object that gets assigned to appUser to update all listeners
    private MutableLiveData<User> userData = new MutableLiveData<User>();
    private MutableLiveData<Weather> wData = new MutableLiveData<Weather>();
    private MutableLiveData<Hike> hData = new MutableLiveData<Hike>();
    //User object that get's updated throughout the app
    private User appUser;
    private Weather weather;
    private Hike hike;

    //private String weatherLoc = "";

    private MutableLiveData<User> activityData = new MutableLiveData<User>();

    private AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        weatherDAO = db.weatherDao();
        hikeDAO = db.hikeDao();
        profileDAO = db.profileDao();
        appUser = new User();
        userData.setValue(appUser);
        weather = new Weather();
        wData.setValue(weather);
        hike = new Hike();
        hData.setValue(hike);
    }

    public static synchronized AppRepository getInstance(Application application){
        if(instance == null){
            instance = new AppRepository(application);
        }
        return instance;
    }

    public MutableLiveData<HikeTable> getHikeData() {
        return hikeData;
    }
    public MutableLiveData<WeatherTable> getWeatherData() { return weatherData; }
    public MutableLiveData<User> getUserData() {
        return userData;
    }
    public MutableLiveData<Weather> getWData() { return wData; }
    public MutableLiveData<Hike> getHData() { return hData; }
//    public String getWeatherLoc() { return weatherLoc; }

//------------- STARTUPACTIVITY--------------//
    private class AuthorizeUser{
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        public void execute(String username, String password) {
            AppDatabase.databaseExecutor.execute(() -> {
                ProfileTable user = profileDAO.findUser(username);
                if (user != null) {
                    checkDatabase(user, password);
                }
            });
        }
        private void checkDatabase(ProfileTable user, String password){
            if (user.getUsername() == null) {
                mainThreadHandler.post(() -> {
                    authorized.setValue(false);
                });
            } else {
                mainThreadHandler.post(() -> {
                    authorized.setValue(user.getPasswordJson().equals(password));
                });
            }
        }
    }
    //Check if entered username & password == Database username & password
    public void authUser(String username, String password) {
        new AuthorizeUser().execute(username, password);
    }
    //So that StartActivity.Authorized gets updated
    public MutableLiveData<Boolean> getAuth() {
        return authorized;
    }

//------------- SIGNUPACTIVITY -------------//
    public void logUser(String username, String password) {
        appUser.setUsername(username);
        ProfileTable newUser = new ProfileTable(username, password);
        AppDatabase.databaseExecutor.execute(() -> {
            profileDAO.insert(newUser);
        });
    }
//------------- CREATEACTIVITY ------------//
    public void setAppUserName(String name) {
        appUser.setName(name);
    }
    public void setAppUserDOB(String dob) {
        appUser.setBirthday(dob);
    }
    public void setAppUserProfPic(Bitmap profPic) {
        appUser.setProfPic(profPic);
    }
    public void setAppUserFeet(int feet) {
        appUser.setFeet(feet);
    }
    public void setAppUserInches(int inches) {
        appUser.setInches(inches);
    }
    public void setAppUserLBS(int lbs) {
        appUser.setLbs(lbs);
    }
    public void setAppUserDecimals(int decimals) {
        appUser.setDecimal(decimals);
    }
    public void setAppUserGender(String gender) {
        appUser.setGender(gender);
    }
    public void setAppUserCheckedGender(int checkedGender) {
        appUser.setCheckedGender(checkedGender);
    }
    public void setAppUserAge(String age) {
        appUser.setAge(age);
    }
    public void setAppUserCity(String city) {
        appUser.setCity(city);
    }
    public void setAppUserState(String state) {
        appUser.setState(state);
    }
    public void setAppUserCountry(String country) {
        appUser.setCountry(country);
    }
    public void setAppUserHeight(double height) {
        appUser.setHeight(height);
    }
    public void setAppUserWeight(double weight) {
        appUser.setWeight(weight);
    }
    public void setAppUserBMI(double bmi) {
        appUser.setBmi(bmi);
    }
    public void setAppUserLocation(String location) {
        appUser.setLocation(location);
    }
    public void setAppUserCityPosition(int cityPosition) {
        appUser.setCityPosition(cityPosition);
    }
    public void setAppUserStatePosition(int statePosition) {
        appUser.setStatePosition(statePosition);
    }
    public void setAppUserCountryPosition(int countryPosition) {
        appUser.setCountryPosition(countryPosition);
    }

    //------------- ACTIVITYFRAGMENT ------------//
    public void setAppUserIntensityPosition(int intensityPosition) { appUser.setIntensityPosition(intensityPosition);}
    public void setAppUserIntensity(String intensity) { appUser.setIntensity(intensity);}
    public void setAppUserFrequency(int frequency) { appUser.setFrequency(frequency);}
    public void setAppUserDuration(int duration) { appUser.setDuration(duration);}
    public void setAppUserStep(int step) { appUser.setStep(step);}

    //------------- GOALFRAGMENT ------------//
    public void setAppUserGoalPosition (int goalPosition) { appUser.setGoalPosition(goalPosition);}
    public void setAppUserGoal(String goal) { appUser.setGoal(goal);}
    public void setAppUserGoalLbs(int goalLbs) { appUser.setGoalLbs(goalLbs);}
    public void setAppUserCalories(double calories) { appUser.setCalories(calories);}

    //------------- WEATHERFRAGMENT --------------//
//    public void setWeatherLoc (String location) {weatherLoc = location;}
    public void setWeatherCity(String city) {weather.setCity(city); }

    public void setWeatherCountry(String country) {weather.setCountry(country);}
    public void setWeatherLastUpdated(String lastUpdated) {weather.setLastUpdated(lastUpdated);}
    public void setWeatherTemp(String temperature) {weather.setTemp(temperature);}
    public void setWeatherCast(String cast) {weather.setCast(cast);}
    public void setWeatherHumidity(String humidity) {weather.setHumidity(humidity);}
    public void setWeatherMinTemp(String minTemp) {weather.setMinTemp(minTemp);}
    public void setWeatherMaxTemp(String maxTemp) {weather.setMaxTemp(maxTemp);}
    public void setWeatherSunrise(String sunrise) {weather.setSunrise(sunrise);}
    public void setWeatherSunset(String sunset) {weather.setSunset(sunset);}

    public void setHikeData(List<HashMap<String, String>> mapList) {hike.setMapList(mapList);}
}


