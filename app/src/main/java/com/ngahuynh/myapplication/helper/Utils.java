package com.ngahuynh.myapplication.helper;

import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.ngahuynh.myapplication.fragment.BMIFragment;
import com.ngahuynh.myapplication.fragment.GoalFragment;
import com.ngahuynh.myapplication.fragment.HikesFragment;
import com.ngahuynh.myapplication.fragment.ActivityFragment;
import com.ngahuynh.myapplication.fragment.ProfileFragment;
import com.ngahuynh.myapplication.fragment.WeatherFragment;

public class Utils {
    public static final int PROFILE_ID = 1;
    public static final int BMI_ID = 2;
    public static final int ACTIVITY_ID = 3;
    public static final int GOAL_ID = 4;
    public static final int HIKES_ID = 5;
    public static final int WEATHER_ID = 6;

    public static void displayDetailFragment(FragmentManager fm, int viewId, int menuId) {
        switch (menuId) {
            case PROFILE_ID:
                ProfileFragment profileFragment = new ProfileFragment();
                fm.beginTransaction().replace(viewId, profileFragment, "PROFILE").commit();
                break;
            case BMI_ID:
                BMIFragment bmiFragment = new BMIFragment();
                fm.beginTransaction().replace(viewId, bmiFragment, "BMI").commit();
                break;
            case ACTIVITY_ID:
                ActivityFragment activityFragment = new ActivityFragment();
                fm.beginTransaction().replace(viewId, activityFragment, "ACTIVITY").commit();
                break;
            case GOAL_ID:
                GoalFragment goalFragment = new GoalFragment();
                fm.beginTransaction().replace(viewId, goalFragment, "GOAL").commit();
                break;
            case HIKES_ID:
                HikesFragment hikesFragment = new HikesFragment();
                fm.beginTransaction().replace(viewId, hikesFragment, "HIKES").commit();
                break;
            case WEATHER_ID:
                WeatherFragment weatherFragment = new WeatherFragment();
                fm.beginTransaction().replace(viewId, weatherFragment, "WEATHER").commit();
                break;
        }
    }

    public static void setTitle(TextView textView, int menuId) {
        switch (menuId) {
            case PROFILE_ID:
                textView.setText("My Information");
                break;
            case BMI_ID:
                textView.setText("My BMI");
                break;
            case ACTIVITY_ID:
                textView.setText("My Activity");
                break;
            case GOAL_ID:
                textView.setText("My Goal");
                break;
            case HIKES_ID:
                textView.setText("Hikes");
                break;
            case WEATHER_ID:
                textView.setText("Weather");
                break;
        }
    }
}
