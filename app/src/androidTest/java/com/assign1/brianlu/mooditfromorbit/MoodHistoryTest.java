/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * Created by Gregory on 2017-03-10.
 */

public class MoodHistoryTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public MoodHistoryTest() {
        super(com.assign1.brianlu.mooditfromorbit.MoodMainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.assign1.brianlu.mooditfromorbit", appContext.getPackageName());
    }


    /**
     * UI test for Mood History
     */



    /** US 04.01.01As a participant,
     * I want to view as a list my mood history, sorted by date and time, in reverse chronological order (most recent coming first).
     */
    public void testChronological(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        MainController mc = MainApplication.getMainController();
        final MoodList profileMoodList = mc.getMe().getMoods();
        Mood tempMood = profileMoodList.getMood(0);
        for(int i =1;i<profileMoodList.getCount();i++){
            Mood checkMood = profileMoodList.getMood(i);
            assertTrue(checkMood.getDate().before(tempMood.getDate()));
            tempMood = checkMood;
        }
    }

    /** US 04.02.01 As a participant,
     * I want to filter my mood history list to show only mood events that occurred in the most recent week.
     */
    public void testFilterRecent(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnMenuItem("Filter");
        solo.clickOnCheckBox(0);
        solo.clickOnButton(2);
        solo.scrollToBottom();
        // after filter, Fear mood is gone
        assertFalse(solo.waitForText("Fear"));

        // after cancel filter, Fear comes back
        solo.clickOnMenuItem("Filter");
        solo.clickOnButton(1);
        solo.scrollToBottom();
        assertTrue(solo.waitForText("Fear"));
    }


    /** US 04.03.01
     * As a participant, I want to filter my mood history list to show only mood events with a particular emotional state.
     */
    public void testFilterMood(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnMenuItem("Filter");
        solo.pressSpinnerItem(0,1);
        solo.clickOnButton(2);
        solo.scrollToTop();
        assertTrue(solo.waitForText("Angry"));
        assertFalse(solo.waitForText("Fear"));
    }


    /** US 04.04.01
     * As a participant, I want to filter my mood history list to show only mood events whose reason why text contains a given word.
     */
    public void testFilterText(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnMenuItem("Filter");
        solo.enterText((EditText) solo.getView(R.id.searchText),"fail");
        solo.clickOnButton(2);
        solo.scrollToTop();
        assertTrue(solo.waitForText("Shame"));
        assertTrue(solo.waitForText("failed my exam"));

    }


}
