package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * Created by brianlu on 2017-03-13.
 */

public class ModifyMoodTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public ModifyMoodTest() {
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


    public void testClickMoodList(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnMenuItem("Profile");
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.action_add_mood));
        solo.assertCurrentActivity("Wrong activity", AddMood.class);
        String text = "this is test";
        solo.enterText((EditText) solo.getView(R.id.comment), text);
        solo.clickOnButton("Done");
        solo.waitForText("this is test");
//        ProfileActivity activity = (ProfileActivity) solo.getCurrentActivity();
        MainController mc = MainApplication.getMainController();
        final MoodList profileMoodList = mc.getMe().getMoods();
        Mood mood = profileMoodList.getMood(0);
        assertEquals("this is test", mood.getMessage());

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", EditMood.class);
        assertTrue(solo.waitForText("this is test"));

        solo.goBack();
        solo.assertCurrentActivity("wrong activity", ProfileActivity.class);
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", EditMood.class);
        solo.clickOnButton("Delete");



    }
    public void testDeleteMoodList() {
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnMenuItem("Profile");
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.action_add_mood));
        solo.assertCurrentActivity("Wrong activity", AddMood.class);
        String text = "this is test22";
        solo.enterText((EditText) solo.getView(R.id.comment), text);
        solo.clickOnButton("Done");
        solo.waitForText("this is test22");

        MainController mc = MainApplication.getMainController();
        final MoodList profileMoodList = mc.getMe().getMoods();
        Mood mood = profileMoodList.getMood(0);
        assertEquals("this is test22", mood.getMessage());
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", EditMood.class);
        assertTrue(solo.waitForText("this is test22"));
        String text2 = "make Changes";
        solo.clearEditText((EditText) solo.getView(R.id.Ecomment));
        solo.enterText((EditText) solo.getView(R.id.Ecomment), text2);
        solo.clickOnButton("Update");
        solo.waitForText("make Changes");

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", EditMood.class);
        solo.clickOnButton("Delete");
    }
}
