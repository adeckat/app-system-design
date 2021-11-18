package com.ngahuynh.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.viewmodel.DetailViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherFragmentTest {

    @Mock Context context;
    @Mock DetailViewModel detailViewModel;
    @Mock EditText editText;
    @Mock Editable editable;
    @Mock View view;
    @Mock MutableLiveData<String> weatherLoc;
    WeatherFragment underTest = new WeatherFragment();

    @Test
    public void onClick_imgSearch() {
        when(editText.getText()).thenReturn(editable);
        when(editable.toString()).thenReturn("foo");
        underTest.etCity = editText;

        when(view.getId()).thenReturn(R.id.img_search);
        when(detailViewModel.getWeatherLoc()).thenReturn(weatherLoc);
        underTest.weatherVM = detailViewModel;
        underTest.onClick(view);

        assertEquals(underTest.CITY, "foo");

        verify(detailViewModel).setWeatherLoc("foo");
        verify(weatherLoc).setValue("foo");
    }
}
