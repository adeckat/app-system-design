package com.ngahuynh.myapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.viewmodel.DetailViewModel;

public class BMIFragment extends Fragment {

    double bmi;
    TextView title, displayBMI, rangeBMI;

    private DetailViewModel bmiVM;

    public BMIFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bmiVM = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        bmiVM.getUserData().observe(getViewLifecycleOwner(), bmiObserver);

        //Get stuff
        title = view.findViewById(R.id.tv_bar_title);
        if (title != null) {
            title.setText("My BMI");
        }
        displayBMI = (TextView) view.findViewById(R.id.tv_calculated_bmi);
        rangeBMI = (TextView) view.findViewById(R.id.tv_checked_bmi);
    }

    final Observer<User> bmiObserver = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User appUser) {
            displayBMI.setText(String.format("%.1f",appUser.getBmi()));
            rangeBMI.setText(checkBMIRange(appUser.getBmi()));
        }
    };


    public String checkBMIRange(double bmi) {
        String result = "";
        String recGoal = "";
        //Underweight = <18.5
        if (bmi < 18.5) {
            result = "Underweight";

        }
        //Normal weight = 18.5 - 24.9
        else if (bmi >= 18.5 && bmi < 25) {
            result = "Normal Weight";
        }
        //Overweight = 25-29.9
        else if (bmi >= 25 && bmi < 30) {
            result = "Overweight";
        }
        //Obesity = >30
        else {
            result = "Obese";
        }
        return result;
    }
}