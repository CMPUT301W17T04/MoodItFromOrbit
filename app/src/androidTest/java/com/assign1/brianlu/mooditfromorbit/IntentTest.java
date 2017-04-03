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

public class IntentTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public IntentTest() {
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

    // Intent test *****************

    // dashboard-activity to profile-activity
    public void testdashBoardToProfile(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
    }

    // dashboard-map-activity to profile-activity
    public void testDmapToProfile(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_map));
        solo.assertCurrentActivity("Wrong activity", DashBoardMap.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
    }

    // profile-map-activity to profile-activity
    public void testPmapToProfile(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.action_map));
        solo.assertCurrentActivity("Wrong activity", ProfileMap.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);

    }

    // follow-someone-activity to profile-activity
    public void testAlltoProfile(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_all));
        solo.assertCurrentActivity("Wrong activity", FollowSomeoneActivity.class);
    }

    // accept-request-activity to profile-activity
    public void testReqtoProfile(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_requests));
        solo.assertCurrentActivity("Wrong activity", AcceptFollowerActivity.class);
    }

    // accept-request-activity to dashboard-activity
    public void testReqtoDashboard(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_requests));
        solo.assertCurrentActivity("Wrong activity", AcceptFollowerActivity.class);
        solo.clickOnView(solo.getView(R.id.action_dashboard));
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
    }

    // accept-request-activity to follow-someone-activity
    public void testReqtoAll(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_requests));
        solo.assertCurrentActivity("Wrong activity", AcceptFollowerActivity.class);
        solo.clickOnView(solo.getView(R.id.action_all));
        solo.assertCurrentActivity("Wrong activity", FollowSomeoneActivity.class);
    }

    // follow-someone-activity to accept-request-activity
    public void testAlltoReq(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_all));
        solo.assertCurrentActivity("Wrong activity", FollowSomeoneActivity.class);
        solo.clickOnView(solo.getView(R.id.action_requests));
        solo.assertCurrentActivity("Wrong activity", AcceptFollowerActivity.class);
    }

    // follow-someone-activity to dashboard-activity
    public void testAlltoDashboard(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_all));
        solo.assertCurrentActivity("Wrong activity", FollowSomeoneActivity.class);
        solo.clickOnView(solo.getView(R.id.action_dashboard));
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
    }

    // dashboard activity to dashboard-map-activity
    public void testDashboardToMap(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_map));
        solo.assertCurrentActivity("Wrong activity", DashBoardMap.class);
    }

    // dashboard activity to follow-someone-activity
    public void testDashboardToAll(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_all));
        solo.assertCurrentActivity("Wrong activity", FollowSomeoneActivity.class);
    }

    // dashboard activity to accept-request-activity
    public void testDashboardToReq(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_requests));
        solo.assertCurrentActivity("Wrong activity", AcceptFollowerActivity.class);
    }

    // dashboard-map-activity to dashboard-activity
    public void testDmaptoDashbaord(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_map));
        solo.assertCurrentActivity("Wrong activity", DashBoardMap.class);
        solo.clickOnView(solo.getView(R.id.action_dashboard));
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
    }

    // dashboard map activity to profile activity
    public void testDmaptoProfile(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_map));
        solo.assertCurrentActivity("Wrong activity", DashBoardMap.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
    }

    // dashboard map activity to accept-request-activity
    public void testDmaptoReq(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_map));
        solo.assertCurrentActivity("Wrong activity", DashBoardMap.class);
        solo.clickOnView(solo.getView(R.id.action_requests));
        solo.assertCurrentActivity("Wrong activity", AcceptFollowerActivity.class);
    }

    // dashboard map activity to follow-some-one activity
    public void testDmaptoAll(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_map));
        solo.assertCurrentActivity("Wrong activity", DashBoardMap.class);
        solo.clickOnView(solo.getView(R.id.action_all));
        solo.assertCurrentActivity("Wrong activity", FollowSomeoneActivity.class);
    }

    public void testProfiletoDashbaord(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView((R.id.action_dashboard)));
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
    }

    public void testProfiletoAll(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView((R.id.action_all)));
        solo.assertCurrentActivity("Wrong activity", FollowSomeoneActivity.class);
    }



    public void testProfiletoReq(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView((R.id.action_requests)));
        solo.assertCurrentActivity("Wrong activity", AcceptFollowerActivity.class);
    }


    public void testProfiletoMap(){
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


}
