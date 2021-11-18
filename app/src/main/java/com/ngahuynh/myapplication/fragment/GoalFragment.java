package com.ngahuynh.myapplication.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ngahuynh.myapplication.helper.CustomToast;
import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.viewmodel.DetailViewModel;

import java.util.regex.Pattern;

public class GoalFragment extends Fragment implements View.OnClickListener {
    String[] goalOptions = {"Lose Weight", "Maintain Weight", "Gain Weight"};
    String[] lbsOptions = createStringArrayofInts(5);
    double bmi;

    String goal;
    int lbs, goalPosition;

    TextView title, myBMI, recGoal, calories;
    Spinner spinGoal, spinLbs;
    Button save;

    private DetailViewModel goalVM;

    public GoalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goalVM = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        goalVM.getUserData().observe(getViewLifecycleOwner(), goalObserver);

        //Get stuff
        title = view.findViewById(R.id.tv_bar_title);
        if (title != null) {
            title.setText("My Goal");
        }
        myBMI = view.findViewById(R.id.tv_calculated_bmi);
        recGoal = view.findViewById(R.id.tv_rcm_goal);
        calories = view.findViewById(R.id.tv_rcm_calories);
        spinGoal = (Spinner) view.findViewById(R.id.spn_goal);
        spinLbs = (Spinner) view.findViewById(R.id.spn_lbs);
        save = (Button) view.findViewById(R.id.btn_save);

        ArrayAdapter goalAdapter = new ArrayAdapter(this.getContext(),R.layout.spinner_item, goalOptions);
        spinGoal.setAdapter(goalAdapter);
        spinGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                goal = parent.getItemAtPosition(position).toString();
                goalPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter lbsAdapter = new ArrayAdapter(this.getContext(), R.layout.spinner_item, lbsOptions);
        spinLbs.setAdapter(lbsAdapter);
        spinLbs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lbs = Integer.parseInt(parent.getItemAtPosition(position).toString());
                if(Integer.valueOf(lbs) > 2){
                    new CustomToast(getContext(),"Losing/Gaining more than 2 lbs a week is unhealthy and is not advised").show();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:{
                setAppUserGoalPosition(goalPosition);
                setAppUserGoal(goal);
                setAppUserGoalLbs(lbs);
                setAppUserCalories(CalcRecCalories());
                calories.setText(String.valueOf(CalcRecCalories()));
              }
        }
    }

    final Observer<User> goalObserver = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User appUser) {
            myBMI.setText(String.format("%.1f",appUser.getBmi()));
            recGoal.setText(checkRecGoal(appUser.getBmi()));

            spinGoal.setSelection(appUser.getGoalPosition());
            spinLbs.setSelection(appUser.getGoalLbs());
            calories.setText(String.format("%.1f",appUser.getCalories()));
//            goalVM.setAppUserCalories(appUser.getCalories());
            Log.i("TAG", String.valueOf(appUser.getCalories()));
        }
    };
    private double CalcRecCalories(){
        double CaloriesForChanges = 0.0, CaloriesToMaintain = CalcCalories();

        if(goal == "Lose Weight"){
            CaloriesForChanges = CaloriesToMaintain - (500*Double.valueOf(lbs));
        } else if(goal == "Gain Weight"){
            CaloriesForChanges = CaloriesToMaintain + (500*Double.valueOf(lbs));
        } else if(goal == "Maintain Weight"){
            CaloriesForChanges = CaloriesToMaintain;
        }


        Log.i("TAG", String.valueOf(CaloriesForChanges));
        return CaloriesForChanges;
    }

    private double CalcCalories() {
        double Calories;
        double BMR;

        //Get Height
        int feet = goalVM.getUserData().getValue().getFeet();
        int inches = goalVM.getUserData().getValue().getInches();

        //Get Weight
        double heightInCenti = (double)(feet*12 + inches) * 2.54;
        double weightInKg = goalVM.getUserData().getValue().getWeight() * 0.45359237;

        if(goalVM.getUserData().getValue().getGender() == "Male"){
            BMR = (10 * weightInKg) + (6.25 * heightInCenti) - (5 * Integer.parseInt(goalVM.getUserData().getValue().getAge())) + 5;
        } else{
            BMR = (10 * weightInKg) + (6.25 * heightInCenti) - (5 * Integer.parseInt(goalVM.getUserData().getValue().getAge())) - 161;
        }

        double multiplier = calcActivityMultiplier();

        Calories = Math.round(BMR * multiplier);

        Log.i("TAG", String.valueOf(Calories));
        return Calories;

    }

    private double calcActivityMultiplier(){
        double multiplier = 0;
        String intensity = goalVM.getUserData().getValue().getIntensity();
        int frequency = goalVM.getUserData().getValue().getFrequency();
        int duration = goalVM.getUserData().getValue().getDuration();

        if(intensity == "High" && frequency >= 6){
            multiplier = 1.75;
        } else if (intensity == "High" && frequency >=3) {
            multiplier = 1.70;
        } else if (intensity == "Moderate" && frequency >= 6){
            multiplier = 1.65;
        } else if (intensity == "High" && frequency == 2){
            multiplier = 1.60;
        } else if (intensity == "Moderate" && frequency >= 3){
            multiplier = 1.55;
        } else if (intensity == "Moderate" && frequency == 2){
            multiplier = 1.50;
        } else if (intensity == "Low" && frequency >= 6){
            multiplier = 1.45;
        } else if (intensity == "High" && frequency == 1){
            multiplier = 1.40;
        } else if (intensity == "Low" && frequency >= 3){
            multiplier = 1.40;
        } else if (intensity == "Moderate" && frequency == 1){
            multiplier = 1.35;
        } else if (intensity == "Low" && frequency == 2){
            multiplier = 1.30;
        } else if (intensity == "Low" && frequency == 1) {
            multiplier = 1.25;
        } else {
            multiplier= 1.20;
        }

        if(duration > 60){
            multiplier += 0.025;
        }

        return multiplier;
    }

    public String checkRecGoal(double bmi) {
        String recGoal = "";
        //Underweight = <18.5
        if (bmi < 18.5) {
            recGoal = "Gain Weight";

        }
        //Normal weight = 18.5 - 25
        else if (bmi >= 18.5 && bmi < 25) {
            recGoal = "Maintain Weight";
        }
        //Overweight = 25-30
        else if (bmi >= 25 && bmi < 30) {
            recGoal = "Lose Weight";
        }
        //Obesity = >30
        else {
            recGoal = "Lose Weight";
        }
        return recGoal;
    }

    public String[] createStringArrayofInts(int x) {
        int[] a = new int[x+1];
        for (int i = 1; i < x+1; ++i) {
            a[i] = i;
        }

        String[] b = new String[a.length];
        for (int i=0; i<a.length; i++) {
            b[i] = String.valueOf(a[i]);
        }

        return b;
    }

    void setAppUserGoalPosition(int goalPosition) { goalVM.setAppUserGoalPosition(goalPosition);}
    void setAppUserGoal(String goal) { goalVM.setAppUserGoal(goal);}
    void setAppUserGoalLbs(int goalLbs) { goalVM.setAppUserGoalLbs(goalLbs);}
    void setAppUserCalories(double calories) { goalVM.setAppUserCalories(calories);}

}