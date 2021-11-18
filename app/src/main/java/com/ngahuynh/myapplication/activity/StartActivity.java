package com.ngahuynh.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ngahuynh.myapplication.helper.CustomToast;
import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.helper.User;
import com.ngahuynh.myapplication.viewmodel.StartViewModel;

public class StartActivity extends AppCompatActivity {
    EditText eTusername;
    EditText eTpassword;
    boolean authorized;
    private StartViewModel startVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        eTusername = (EditText) findViewById(R.id.edt_user_name);
        eTpassword = (EditText) findViewById(R.id.edt_password);

        startVM = new ViewModelProvider(this).get(StartViewModel.class);

        (startVM.getAuth()).observe(this, authObserver);

        eTpassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                authUser(eTusername.getText().toString(), eTpassword.getText().toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        findViewById(R.id.btn_login).setOnClickListener(v -> {
            //If provided username & password match the database, then authorized should be automatically updated
            if(authorized) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                new CustomToast(this, "Username or password is incorrect!").show();
            }
        });

        findViewById(R.id.btn_create).setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    //AUTHUSER() THAT CALLS STARTVIEWMODEL.AUTHUSER() WHICH CALLS REPOSITORY.AUTHUSER()
    public void authUser(String username, String password) {
        startVM.authUser(username,password);
    }

    final Observer<Boolean> authObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable final Boolean auth) {
            if(auth != null) {
                authorized = auth;
            }
        }
    };
}