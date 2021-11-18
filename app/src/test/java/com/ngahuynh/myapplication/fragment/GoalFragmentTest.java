package com.ngahuynh.myapplication.fragment;

import com.ngahuynh.myapplication.fragment.GoalFragment;

import org.junit.Test;
import static org.junit.Assert.*;

public class GoalFragmentTest {

    @Test
    public void testCheckRecGoal() {
        GoalFragment goal = new GoalFragment();

        String testNegative = goal.checkRecGoal(-10);
        assertTrue(testNegative.equals(new String("Gain Weight")));

        // Underweight
        String testGainWeight = goal.checkRecGoal(18);
        assertTrue(testGainWeight.equals(new String("Gain Weight")));

        // Normal Weight
        String testMaintainWeightLowEnd = goal.checkRecGoal(18.5);
        assertTrue(testMaintainWeightLowEnd.equals(new String("Maintain Weight")));

        String testMaintainWeightHighEnd = goal.checkRecGoal(24.95);
        assertTrue(testMaintainWeightHighEnd.equals(new String("Maintain Weight")));

        // Overweight
        String testLoseWeightLowEnd = goal.checkRecGoal(25);
        assertTrue(testLoseWeightLowEnd.equals(new String("Lose Weight")));

        String testLoseWeightHighEnd = goal.checkRecGoal(29.95);
        assertTrue(testLoseWeightHighEnd.equals(new String("Lose Weight")));

        // Obese
        String testLoseWeightObese = goal.checkRecGoal(30);
        assertTrue(testLoseWeightObese.equals(new String("Lose Weight")));
    }

}
