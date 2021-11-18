package com.ngahuynh.myapplication.helper;

import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ngahuynh.myapplication.fragment.ActivityFragment;
import com.ngahuynh.myapplication.fragment.BMIFragment;
import com.ngahuynh.myapplication.fragment.GoalFragment;
import com.ngahuynh.myapplication.fragment.HikesFragment;
import com.ngahuynh.myapplication.fragment.ProfileFragment;
import com.ngahuynh.myapplication.fragment.WeatherFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {

    @Mock private FragmentManager fragmentManager;
    @Mock private FragmentTransaction fragmentTransaction;
    @Mock private TextView textView;

    @Before
    public void setUp() {
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);
        when(fragmentTransaction.replace(any(int.class), any(Fragment.class), any(String.class)))
                .thenReturn(fragmentTransaction);
    }

    @Test
    public void displayDetailFragment_profile() {
        Utils.displayDetailFragment(fragmentManager, 123, 1);

        verify(fragmentTransaction).replace(
                eq(123), // should equal the view id we passed in
                any(ProfileFragment.class), // should be a ProfileFragment class
                eq("PROFILE")
        );
    }

    @Test
    public void displayDetailFragment_bmi() {
        Utils.displayDetailFragment(fragmentManager, 123, 2);

        verify(fragmentTransaction).replace(
                eq(123), // should equal the view id we passed in
                any(BMIFragment.class), // should be a ProfileFragment class
                eq("BMI")
        );
    }

    @Test
    public void displayDetailFragment_activity() {
        Utils.displayDetailFragment(fragmentManager, 123, 3);

        verify(fragmentTransaction).replace(
                eq(123), // should equal the view id we passed in
                any(ActivityFragment.class), // should be a ProfileFragment class
                eq("ACTIVITY")
        );
    }

    @Test
    public void displayDetailFragment_goal() {
        Utils.displayDetailFragment(fragmentManager, 123, 4);

        verify(fragmentTransaction).replace(
                eq(123), // should equal the view id we passed in
                any(GoalFragment.class), // should be a ProfileFragment class
                eq("GOAL")
        );
    }

    @Test
    public void displayDetailFragment_hikes() {
        Utils.displayDetailFragment(fragmentManager, 123, 5);

        verify(fragmentTransaction).replace(
                eq(123), // should equal the view id we passed in
                any(HikesFragment.class), // should be a ProfileFragment class
                eq("HIKES")
        );
    }

    @Test
    public void displayDetailFragment_weather() {
        Utils.displayDetailFragment(fragmentManager, 123, 6);

        verify(fragmentTransaction).replace(
                eq(123), // should equal the view id we passed in
                any(WeatherFragment.class), // should be a ProfileFragment class
                eq("WEATHER")
        );
    }

    @Test
    public void setTitle_profile() {
        Utils.setTitle(textView, 1);

        verify(textView).setText("My Information");
    }

    @Test
    public void setTitle_bmi() {
        Utils.setTitle(textView, 2);

        verify(textView).setText("My BMI");
    }

    @Test
    public void setTitle_activity() {
        Utils.setTitle(textView, 3);

        verify(textView).setText("My Activity");
    }

    @Test
    public void setTitle_goal() {
        Utils.setTitle(textView, 4);

        verify(textView).setText("My Goal");
    }

    @Test
    public void setTitle_hikes() {
        Utils.setTitle(textView, 5);

        verify(textView).setText("Hikes");
    }

    @Test
    public void setTitle_weather() {
        Utils.setTitle(textView, 6);

        verify(textView).setText("Weather");
    }

}
