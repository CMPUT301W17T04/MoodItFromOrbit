package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * Created by brianlu on 2017-04-03.
 */

public class OtherDetialsTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public OtherDetialsTest() {
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

    // UI testing for Other Details

    /** US 02.01.01 As a participant, I want to express the reason why for a mood event using a brief textual explanation
     * (no more than 20 characters or 3 words).
     */
    public void testMoodComment(){
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
        Mood mood = profileMoodList.getMood(0);
        solo.waitForText(mood.getMessage());
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong activity", EditMood.class);
        solo.clearEditText((EditText) solo.getView(R.id.Ecomment));
        solo.enterText((EditText) solo.getView(R.id.Ecomment),"this is a long texts here will not display");

        solo.clickOnButton("Update");
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.waitForText("this is a long texts");
    }

    /** US 02.02.01 As a participant,
     * I want to express the reason why for a mood event using a photograph.
     *
     * Don't know how to test camera activity, you can try attach pic during the 6s waiting time
     */
    public void testCameraPic(){
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
        Mood mood = profileMoodList.getMood(0);
        solo.waitForText(mood.getMessage());
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong activity", EditMood.class);
        solo.sleep(6000);
        solo.clickOnButton("Update");
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
    }

    /** US 02.04.01
     * As a participant,
     * I want to specify the social situation for a mood event to be one of:
     * alone, with one other person, with two to several people, or with a crowd.
     */
    public void testImageSize(){
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
        Mood mood = profileMoodList.getMood(0);
        solo.waitForText(mood.getMessage());
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong activity", EditMood.class);
        solo.clickOnView(solo.getView(R.id.Egroupstatus));
        assertTrue(solo.waitForText("Alone"));
        assertTrue(solo.waitForText("With One Other"));
        assertTrue(solo.waitForText("2 to Several"));
        assertTrue(solo.waitForText("Crowd"));
        solo.clickOnView(solo.getView(TextView.class,0));
    }

}
