package com.ngahuynh.myapplication.fragment;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import com.ngahuynh.myapplication.activity.MainActivity;
import com.ngahuynh.myapplication.fragment.ProfileFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import android.content.Intent;

public class ProfileFragmentTest {

    @Test
    public void testPopulateInformation() {
        ProfileFragment profile = new ProfileFragment();
        TextView textView = new TextView(profile.getContext());
        String testString = "Test";
//        profile.populateInformation(textView, testString);
//        assertTrue(textView.getText().equals(testString));

        testString = "DifferentTest";
//        profile.populateInformation(textView, testString);
//        assertTrue(textView.getText().equals(testString));
    }
}
