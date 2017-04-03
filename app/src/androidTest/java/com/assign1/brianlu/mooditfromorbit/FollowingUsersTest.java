package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.robotium.solo.Solo;

import junit.framework.TestSuite;

import org.junit.Test;

/**
 * tests adding a follower, accepting a follower and removing a follower
 * user1 must not be following or have sent a request to user2 before starting test
 * Created by gdbaker on 2017-03-13.
 */

public class FollowingUsersTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private String user1 = "Greg";
    private String user2 = "Geoff";

    public FollowingUsersTest() {
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
     * runs all tests in order
     * @return t
     */
    public static TestSuite suite() {
        TestSuite t = new TestSuite();
        t.addTestSuite(FollowTest.class);
        t.addTestSuite(AcceptFollowTest.class);
        t.addTestSuite(FollowingOnDashboardTest.class);
        t.addTestSuite(UnFollowTest.class);


        return t;
    }



}
