package com.ngahuynh.myapplication.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ngahuynh.myapplication.fragment.ActivityFragment;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ActivityFragmentTest {

    @Test
    public void testCreateStringArrayofInts() {
        ActivityFragment activity = new ActivityFragment();

        String[] emptyArray = activity.createStringArrayofInts(0);
        String[] emptyArrayTest = new String[] {String.valueOf(0)};
        assertEquals(emptyArray, emptyArrayTest);

        String[] shortArray = activity.createStringArrayofInts(7);
        String[] shortArrayTest = new String[] {String.valueOf(0), String.valueOf(1),
                String.valueOf(2), String.valueOf(3), String.valueOf(4), String.valueOf(5), String.valueOf(6), String.valueOf(7)};
        assertEquals(shortArray, shortArrayTest);

        String[] longArray = activity.createStringArrayofInts(180);
        String[] longArrayTest = new String[181];
        longArrayTest[180] = String.valueOf(180);
        assertEquals(longArray.length, longArrayTest.length);
        assertEquals(longArray[180], longArrayTest[180]);
    }
}