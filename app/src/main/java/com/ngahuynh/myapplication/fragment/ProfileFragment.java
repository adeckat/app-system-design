package com.ngahuynh.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.activity.DetailActivity;
import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.activity.CreateActivity;
import com.ngahuynh.myapplication.activity.StartActivity;
import com.ngahuynh.myapplication.viewmodel.DetailViewModel;

import java.util.regex.Pattern;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView title, tVusername, tVname, tVage, tVbirthday, tVgender,  tVheight, tVweight,  tvlocation;
    Button btn_edit, btn_logout;

    private DetailViewModel profileVM;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileVM = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        profileVM.getUserData().observe(getViewLifecycleOwner(), profileObserver);

        //Get stuff
        title = view.findViewById(R.id.tv_bar_title);
        if (title != null) {
            title.setText("My Information");
        }

        tVusername = view.findViewById(R.id.tv_username);
        tVname = view.findViewById(R.id.tv_name);
        tVage = view.findViewById(R.id.tv_age);
        tVbirthday = view.findViewById(R.id.tv_dob);
        tVgender = view.findViewById(R.id.tv_gender);
        tVheight = view.findViewById(R.id.tv_height);
        tVweight = view.findViewById(R.id.tv_weight);
        tvlocation = view.findViewById(R.id.tv_location);
        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);

        btn_edit.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:{
                Intent intent = new Intent(getActivity(), StartActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_edit:{
                Intent intent = new Intent(getActivity(), CreateActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    final Observer<User> profileObserver = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User appUser) {
            tVusername.setText(appUser.getUsername());
            tVname.setText(appUser.getName());
            tVage.setText(appUser.getAge());
            tVbirthday.setText(appUser.getBirthday());
            tVgender.setText(appUser.getGender());
            tVheight.setText(appUser.getFeet() +" ft " + appUser.getInches() + " in");
            tVweight.setText(appUser.getWeight() + " lbs");
            tvlocation.setText(appUser.getLocation());
        }
    };
}
