package com.ngahuynh.myapplication.fragment;

import com.ngahuynh.myapplication.fragment.BMIFragment;
import org.junit.Test;
import static org.junit.Assert.*;

public class BMIFragmentTest {

    @Test
    public void testCheckBMIRange() {
        BMIFragment bmi = new BMIFragment();

        String testNegative = bmi.checkBMIRange(-10);
        assertTrue(testNegative.equals(new String("Underweight")));

        // Underweight
        String testUnderweight = bmi.checkBMIRange(18);
        assertTrue(testUnderweight.equals(new String("Underweight")));

        // Normal Weight
        String testNormalWeightLowEnd = bmi.checkBMIRange(18.5);
        assertTrue(testNormalWeightLowEnd.equals(new String("Normal Weight")));

        String testNormalWeightHighEnd = bmi.checkBMIRange(24.95);
        assertTrue(testNormalWeightHighEnd.equals(new String("Normal Weight")));

        // Overweight
        String testOverweightLowEnd = bmi.checkBMIRange(25);
        assertTrue(testOverweightLowEnd.equals(new String("Overweight")));

        String testOverweightHighEnd = bmi.checkBMIRange(29.95);
        assertTrue(testOverweightHighEnd.equals(new String("Overweight")));

        // Obese
        String testObese = bmi.checkBMIRange(30);
        assertTrue(testObese.equals(new String("Obese")));
    }
}
