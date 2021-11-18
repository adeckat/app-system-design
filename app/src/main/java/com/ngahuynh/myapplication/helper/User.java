package com.ngahuynh.myapplication.helper;

import android.graphics.Bitmap;

public class User {
    public String username, name, age, gender, birthday, location = "", intensity, country = "", state = "", city = "", goal = "";
    public int duration, frequency, goalLbs, feet = 0, inches = 0, lbs = 0, decimal = 0, checkedGender = 0, countryPosition = 0, statePosition = 0, cityPosition = 0, intensityPosition = 0, goalPosition = 0, step = 0;
    public double height, weight, bmi = 0.0, Calories = 0.0;
    public static Bitmap profPic;

    //Setters and Getters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCheckedGender() {
        return checkedGender;
    }
    public void setCheckedGender(int checkedGender) {
        this.checkedGender = checkedGender;
    }

    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public int getGoalPosition() {return goalPosition;}
    public void setGoalPosition(int goalPosition) { this.goalPosition = goalPosition;}

    public String getGoal() {
        return goal;
    }
    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getGoalLbs() {
        return goalLbs;
    }
    public void setGoalLbs(int goalLbs) {
        this.goalLbs = goalLbs;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public String getIntensity() {
        return intensity;
    }
    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public int getIntensityPosition() {return intensityPosition;}
    public void setIntensityPosition(int intensityPosition) {this.intensityPosition = intensityPosition;}

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFrequency() {
        return frequency;
    }
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFeet() {
        return feet;
    }
    public void setFeet(int feet) {
        this.feet = feet;
    }

    public int getInches() {
        return inches;
    }
    public void setInches(int inches) {
        this.inches = inches;
    }

    public int getLbs() {
        return lbs;
    }
    public void setLbs(int lbs) {
        this.lbs = lbs;
    }

    public int getDecimal() {
        return decimal;
    }
    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }
    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public double getCalories() {
        return Calories;
    }
    public void setCalories(double calories) {
        this.Calories = calories;
    }

    public Bitmap getProfPic() {
        return profPic;
    }
    public void setProfPic(Bitmap profPic) {
        this.profPic = profPic;
    }

    public int getCountryPosition() {return countryPosition;}
    public void setCountryPosition(int countryPosition) {this.countryPosition = countryPosition;}

    public int getStatePosition() {return statePosition;}
    public void setStatePosition(int statePosition) {this.statePosition = statePosition;}

    public int getCityPosition() {return cityPosition;}
    public void setCityPosition(int cityPosition) {this.cityPosition = cityPosition;}

    public int getStep() {
        return step;
    }
    public void setStep(int step) {
        this.step = step;
    }
}
