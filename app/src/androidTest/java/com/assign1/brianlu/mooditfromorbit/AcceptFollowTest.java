package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * must be run by FollowingUsersTest
 * Created by Gregory on 2017-04-03.
 */

public class AcceptFollowTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private String user1 = "Greg";
    private String user2 = "Geoff";

    public AcceptFollowTest() {
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
     * tests accepting a follower
     */
    public void testAcceptFollower(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), user2);
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnText("Take me to requests!");
        solo.assertCurrentActivity("Wrong activity", AcceptFollowerActivity.class);
        solo.clickOnText(user1);
        solo.sleep(1000);
        MainController mc = MainApplication.getMainController();
        User first = mc.getUsers().getUserByName(user1);
        User second = mc.getUsers().getUserByName(user2);
        assertTrue(first.getFollowing().contains(second.getId()));
    }
}
