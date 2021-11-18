package com.ngahuynh.myapplication.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.helper.CustomToast;
import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.viewmodel.DetailViewModel;

public class ActivityFragment extends Fragment implements View.OnClickListener{
    String[] intensityOptions = {"Low", "Moderate", "High"};
    String[] durationOptions = createStringArrayofInts(180);
    String[] frequencyOptions = createStringArrayofInts(7);

    String intensity;
    int duration, frequency, intensityPosition;

    TextView title, stepTV;
    Spinner spinIntensity, spinFrequency, spinDuration;
    Button save;

    private DetailViewModel activityVM;
    private SensorManager mSensorManager;
    private Sensor mStepCounter;
    private Sensor mLinearAccelerometer;
    private final double mThreshold = 0.0003;
    private CircularProgressBar mProgressBar;

    //Previous positions
    private double prev_x, prev_y, prev_z;
    private double current_x, current_y, current_z;
    private boolean mNotFirstTime;

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        //mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mLinearAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityVM = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        activityVM.getUserData().observe(getViewLifecycleOwner(), activityObserver);
        //Get stuff
        title = view.findViewById(R.id.tv_bar_title);
        if (title != null) {
            title.setText("My Activity");
        }
        stepTV = view.findViewById(R.id.tv_step);
        stepTV.setText(String.valueOf((activityVM.getUserData().getValue().step)));
        spinIntensity = (Spinner) view.findViewById(R.id.spn_intensity);
        spinDuration = (Spinner) view.findViewById(R.id.spn_duration);
        spinFrequency = (Spinner) view.findViewById(R.id.spn_frequency);
        save = (Button) view.findViewById(R.id.btn_save);
        mProgressBar = view.findViewById(R.id.circularStepBar);


        ArrayAdapter intensityAdapter = new ArrayAdapter(this.getContext(),R.layout.spinner_item, intensityOptions);
        spinIntensity.setAdapter(intensityAdapter);
        spinIntensity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intensity = parent.getItemAtPosition(position).toString();
                intensityPosition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter durationAdapter = new ArrayAdapter(this.getContext(),R.layout.spinner_item, durationOptions);
        spinDuration.setAdapter(durationAdapter);
        spinDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                duration = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter frequencyAdapter = new ArrayAdapter(this.getContext(),R.layout.spinner_item, frequencyOptions);
        spinFrequency.setAdapter(frequencyAdapter);
        spinFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                frequency = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //mSensorManager.registerListener(mListener,mLinearAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        save.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:{
                setAppUserIntensityPosition(intensityPosition);
                setAppUserIntensity(intensity);
                setAppUserFrequency(frequency);
                setAppUserDuration(duration);
                CustomToast toast = new CustomToast(getContext(), "Your information has been saved!");
                toast.show();
            }
        }
    }
    final Observer<User> activityObserver = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User appUser) {
            spinIntensity.setSelection(appUser.getIntensityPosition());
            spinFrequency.setSelection(appUser.getFrequency());
            spinDuration.setSelection(appUser.getDuration());
//            stepTV.setText(String.valueOf(appUser.getStep()));
        }
    };
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

    private SensorEventListener mListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //Fetch acceleration rates
            current_x = sensorEvent.values[0];
            current_y = sensorEvent.values[1];
            current_z = sensorEvent.values[2];

            if(mNotFirstTime){
                double dx = Math.abs(prev_x - current_x);
                double dy = Math.abs(prev_y - current_y);
                double dz = Math.abs(prev_z - current_z);

                //Check if the values of accelerations have changed on any of the axes
                if ((dx > mThreshold && dy > mThreshold) ||
                        (dx > mThreshold && dz > mThreshold) || (dy > mThreshold && dz > mThreshold)){
                    int step = activityVM.getUserData().getValue().step;
                    setAppUserStep(step + 1);
                    Log.i("TAG", String.valueOf(step));
                }
            }
            stepTV.setText(String.valueOf((activityVM.getUserData().getValue().step)));
            mProgressBar.setProgress(activityVM.getUserData().getValue().step);
            prev_x = current_x;
            prev_y = current_y;
            prev_z = current_z;
            mNotFirstTime = true;



        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if(mLinearAccelerometer!=null){
            mSensorManager.registerListener(mListener,mLinearAccelerometer, SensorManager.SENSOR_DELAY_UI);
        }
        Log.i("TAG", "Resumed");
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mLinearAccelerometer!=null){
            mSensorManager.unregisterListener(mListener);
        }
    }

    void setAppUserIntensityPosition(int intensityPosition) { activityVM.setAppUserIntensityPosition(intensityPosition);}
    void setAppUserIntensity(String intensity) { activityVM.setAppUserIntensity(intensity);}
    void setAppUserFrequency(int frequency) { activityVM.setAppUserFrequency(frequency);}
    void setAppUserDuration(int duration) { activityVM.setAppUserDuration(duration);}
    void setAppUserStep(int step) { activityVM.setAppUserStep(step);}
}

