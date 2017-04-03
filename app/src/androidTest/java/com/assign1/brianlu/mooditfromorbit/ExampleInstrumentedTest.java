package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
//tests if the spinners are working
    public void testSpinners(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "Cameron");
        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_add_mood));
        solo.assertCurrentActivity("Wrong activity", AddMood.class);



        // 0 is the first spinner in the layout
        View view1 = solo.getView(Spinner.class, 0);
        solo.clickOnView(view1);
        solo.scrollToTop(); // I put this in here so that it always keeps the list at start
        // select the 3rd item in the spinner
        solo.clickOnView(solo.getView(TextView.class, 3));

        String text = "I am disgusted";

        View view2 = solo.getView(Spinner.class, 1);
        //solo.scrollToTop();
        solo.clickOnView(view2);
        solo.clickOnView(solo.getView(TextView.class, 3));


        solo.enterText((EditText) solo.getView(R.id.comment), text);

        solo.clickOnButton("Done");
        solo.clickOnButton("No");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));

       // solo.clickOnMenuItem("Profile");


        solo.goBack();

        MainController mc = MainApplication.getMainController();

        Mood mood = mc.getMe().getMoods().getMood(0);

        assertEquals(text,mood.getMessage());

        solo.goBack();

    }


    //Test for if camera works
    public void testCamera(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "Cameron");
        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_add_mood));
        solo.assertCurrentActivity("Wrong activity", AddMood.class);

        // tODO
        solo.clickOnButton("Take Photo");
        // This is where you capture a picture manually and press the checkmark



        solo.clickOnButton("Done");
        solo.clickOnButton("No");

        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
     //   solo.clickOnMenuItem("Profile");


        solo.goBack();
        solo.goBack();
    }

    // wow factor test
    // solo.goback() wont work for apps external to this app so i ended it there.
    public void testWowFactor(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "Cameron");
        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_add_mood));
        solo.assertCurrentActivity("Wrong activity", AddMood.class);



        // 0 is the first spinner in the layout
        View view1 = solo.getView(Spinner.class, 0);
        solo.clickOnView(view1);
        solo.scrollToTop(); // I put this in here so that it always keeps the list at start
        // select the 3rd item in the spinner
        solo.clickOnView(solo.getView(TextView.class, 3));

        String text = "I am disgusted";

        View view2 = solo.getView(Spinner.class, 1);
        //solo.scrollToTop();
        solo.clickOnView(view2);
        solo.clickOnView(solo.getView(TextView.class, 3));


        solo.enterText((EditText) solo.getView(R.id.comment), text);

        solo.clickOnButton("Done");
        //solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        //solo.clickOnView(solo.getView(android.R.id.button2));
        //solo.clickOnButton(1);
        solo.clickOnButton("Yes take me there!");
/*
      //  solo.goBack();
        solo.clickOnView(solo.getView(R.id.action_profile));

        //   solo.clickOnMenuItem("Profile");


        solo.goBack();

        MainController mc = MainApplication.getMainController();

        Mood mood = mc.getMe().getMoods().getMood(0);

        assertEquals(text,mood.getMessage());

        solo.goBack();*/
    }
}
