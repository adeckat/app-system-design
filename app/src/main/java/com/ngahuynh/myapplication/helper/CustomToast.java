package com.ngahuynh.myapplication.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ngahuynh.myapplication.R;

public class CustomToast extends Toast {

    private Context context;
    private String message;

    public CustomToast(Context context, String message) {
        super(context);
        this.context = context;
        this.message = message;
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
        TextView txtMsg = view.findViewById(R.id.custom_toast_text);
        txtMsg.setText(message);
        setView(view);
        setDuration(CustomToast.LENGTH_LONG);

    }
}