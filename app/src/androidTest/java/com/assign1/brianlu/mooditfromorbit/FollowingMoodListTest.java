package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * Created by brianlu on 2017-04-03.
 */

public class FollowingMoodListTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public FollowingMoodListTest() {
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







    /** UI testing for Mood Following and Sharing 4-6*/


    /** US 05.04.01
     * As a participant,
     * I want to filter my mood following list to show only mood events that occurred in the most recent week.
     */
    public void testFilterRecent(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
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
        assertTrue(solo.waitForText("Happy"));
    }



    public void testFilterMood(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnMenuItem("Filter");
        solo.pressSpinnerItem(0,1);
        solo.clickOnButton(2);
        solo.scrollToTop();
        assertTrue(solo.waitForText("Angry"));
        assertFalse(solo.waitForText("Happy"));
    }

    public void testFilterText(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnMenuItem("Filter");
        solo.enterText((EditText) solo.getView(R.id.searchText),"23");
        solo.clickOnButton(2);
        solo.scrollToTop();
        assertTrue(solo.waitForText("Happy"));
        assertTrue(solo.waitForText("123"));
        assertFalse(solo.waitForText("Angry"));
    }
}
