package com.ngahuynh.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.helper.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_profile).setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("MENU_ID", Utils.PROFILE_ID);
            startActivity(intent);
        });
        findViewById(R.id.btn_bmi).setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("MENU_ID", Utils.BMI_ID);
            startActivity(intent);
        });
        findViewById(R.id.btn_activity).setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("MENU_ID", Utils.ACTIVITY_ID);
            startActivity(intent);
        });
        findViewById(R.id.btn_goal).setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("MENU_ID", Utils.GOAL_ID);
            startActivity(intent);
        });
        findViewById(R.id.btn_hike).setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("MENU_ID", Utils.HIKES_ID);
            startActivity(intent);
        });
        findViewById(R.id.btn_weather).setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("MENU_ID", Utils.WEATHER_ID);
            startActivity(intent);
        });

        ImageButton cornerProfilePic = (ImageButton) findViewById(R.id.btn_profile_pic);
        if(User.profPic != null) {
            cornerProfilePic.setImageBitmap(User.profPic);
        }
        ImageButton logout = (ImageButton) findViewById(R.id.btn_logout);

        cornerProfilePic.setOnClickListener(this);
        logout.setOnClickListener(this);

        try{
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("sKoolKidsApp", "Initialized Amplify");

//            Amplify.Auth.signInWithWebUI(
//              this,
//              result -> Log.i("AuthQuickStart", result.toString()),
//                    error -> Log.e("AuthQuickStart", error.toString())
//            );
        } catch (AmplifyException error) {
            Log.e("sKoolKidsApp", "Could not initialize Amplify", error);
        }
        System.out.println(getApplicationContext().getFilesDir());
        uploadFile("myApp.db", "Database");
        uploadFile("myApp.db-shm", "SHM");
        uploadFile("myApp.db-wal", "WAL");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_profile_pic: {
                //Start Profile Fragment
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("MENU_ID", Utils.PROFILE_ID);
                startActivity(intent);
                break;
            }
            case R.id.btn_logout: {
                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void uploadFile(String filename, String keyStr) {
        File uploadedFile = new File(getApplicationContext().getFilesDir(), filename);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(uploadedFile));
            writer.close();
        } catch (Exception exception) {
            Log.e("MyAmplifyApp", "Upload failed", exception);
        }

        Amplify.Storage.uploadFile(
                keyStr,
                uploadedFile,
                result -> Log.i("sKoolKidsApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("sKoolKidsApp", "Upload failed", storageFailure)
        );
    }
}