package com.ngahuynh.myapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.helper.Utils;

public class MenuFragment extends Fragment {

    private MenuClickListener mMenuClickListener;

    public MenuFragment() {
        // Required empty public constructor
    }

    public void setMenuClickListener(MenuClickListener mMenuClickListener) {
        this.mMenuClickListener = mMenuClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_profile).setOnClickListener(v -> {
            mMenuClickListener.onClick(Utils.PROFILE_ID);
        });
        view.findViewById(R.id.btn_bmi).setOnClickListener(v -> {
            mMenuClickListener.onClick(Utils.BMI_ID);
        });
        view.findViewById(R.id.btn_activity).setOnClickListener(v -> {
            mMenuClickListener.onClick(Utils.ACTIVITY_ID);
        });
        view.findViewById(R.id.btn_goal).setOnClickListener(v -> {
            mMenuClickListener.onClick(Utils.GOAL_ID);
        });
        view.findViewById(R.id.btn_hike).setOnClickListener(v -> {
            mMenuClickListener.onClick(Utils.HIKES_ID);
        });
        view.findViewById(R.id.btn_weather).setOnClickListener(v -> {
            mMenuClickListener.onClick(Utils.WEATHER_ID);
        });
    }

    public interface MenuClickListener {
        void onClick(int menu);
    }
}