package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * tests the map of profile moods
 * Created by brianlu on 2017-03-13.
 */

public class GeoAndMapTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public GeoAndMapTest() {
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


    /** US 06.01.01
     * As a participant,
     * I want to optionally attach my current location to a mood event.
     */

    public void testCurrentLocation(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.action_add_mood));
        solo.assertCurrentActivity("Wrong activity", AddMood.class);
        solo.clickOnView(solo.getView(R.id.locations));
        solo.clickOnButton("Done");
        solo.waitForText("No");
        solo.clickOnButton("No");
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        MainController mc = MainApplication.getMainController();
        final MoodList profileMoodList = mc.getMe().getMoods();
        Mood mood = profileMoodList.getMood(0);
        assertTrue(mood.getLatitude()!=null && mood.getLongitude() != null);
    }

    /**
     * US 06.02.01
     * As a participant,
     * I want to see a map of the mood events (showing their emotional states) from my filtered mood history list (that have locations).
     */
    public void testShowMap(){
            solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
            solo.clickOnButton("Sign In");
            solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
            solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
            solo.clickOnButton("Log In");
            solo.assertCurrentActivity("Wrong activity", DashBoard.class);
            solo.clickOnView(solo.getView(R.id.action_profile));
            solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
            solo.clickOnView(solo.getView((R.id.action_map)));
            solo.assertCurrentActivity("Wrong activity", ProfileMap.class);
    }


    /**
     * US 06.03.01 As a participant,
     * I want to see a map of the mood events (showing their emotional states and the username)
     * from my filtered mood following list (that have locations).
     *
     * Notes: you can click on the marker to see the mood details
     */
    public void testMapMarker(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView((R.id.action_map)));
        solo.assertCurrentActivity("Wrong activity", ProfileMap.class);
        solo.sleep(5000);
    }

    /**
     * US 06.04.01
     * As a participant,
     * I want to see a map of the most recent mood event of every participant (showing their emotional states)
     * if the event has a location and is within 5 km of my current location.
     *
     * Note: after pose, you can check there are markers gone
     */
    public void testMapFilter(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView((R.id.action_map)));
        solo.assertCurrentActivity("Wrong activity", ProfileMap.class);
        solo.clickOnMenuItem("Filter");
        solo.clickOnCheckBox(0);
        solo.clickOnButton(2);
        solo.sleep(5000);
    }

}
