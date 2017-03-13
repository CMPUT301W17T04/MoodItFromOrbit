package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends ActivityInstrumentationTestCase2<MoodMainActivity> {
    private Solo solo;

    public ExampleInstrumentedTest() {
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
     * tests if new mood was added
     */
    public void testNewMood(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "Greg");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_add_mood));
        solo.assertCurrentActivity("Wrong activity", AddMood.class);
        String text = "abcde1";
        solo.enterText((EditText) solo.getView(R.id.comment), text);
        solo.clickOnButton("Done");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);

        MainController mc = MainApplication.getMainController();

        Mood mood = mc.getMe().getMoods().getMood(0);

        assertEquals(text,mood.getMessage());

        solo.goBack();
    }



}
