package com.ngahuynh.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.ngahuynh.myapplication.helper.CustomToast;
import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.viewmodel.SignUpViewModel;
import com.ngahuynh.myapplication.viewmodel.StartViewModel;

public class SignUpActivity extends AppCompatActivity {
    String username, password_one, password_two;
    EditText eTusername,eTpassword_one,eTpassword_two;
    private SignUpViewModel signUpVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        eTusername = (EditText) findViewById(R.id.edt_user_name);
        eTpassword_one = (EditText) findViewById(R.id.edt_password);
        eTpassword_two = (EditText) findViewById(R.id.edt_confirm_password);

        signUpVM = new ViewModelProvider(this).get(SignUpViewModel.class);

        findViewById(R.id.btn_next).setOnClickListener(v -> {
            username = eTusername.getText().toString();
            password_one = eTpassword_one.getText().toString();
            password_two = eTpassword_two.getText().toString();
            if(!confirmPassword(password_one, password_two)){
                new CustomToast(this, "Passwords do not match!").show();
            } else {
                logUser(username, password_one);
                Intent intent = new Intent(this, CreateActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        });
    }

    //NEW LOGUSER THAT CALLS SIGNUPVIEWMODEL.LOGUSER() WHICH CALLS REPOSITORY.LOGUSER()
    public void logUser(String username, String password) {
        signUpVM.logUser(username,password);
    }

    public boolean confirmPassword(String password_one, String password_two) {
        return password_one.equals(password_two);
    }
}