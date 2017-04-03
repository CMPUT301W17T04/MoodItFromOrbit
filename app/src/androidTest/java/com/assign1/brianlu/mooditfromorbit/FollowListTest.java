package com.assign1.brianlu.mooditfromorbit;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Noire on 2017/4/2.
 */

public class FollowListTest  extends ActivityInstrumentationTestCase2 {
    public FollowListTest() {
        super(FollowSomeoneActivity.class);

    }

    public void testAddToFollowing(){
        FollowList user = new FollowList();
        String username = "Noire";

        user.addFollowing(username);

        assertTrue(user.containsFollowing(username));
    }

    public void testAddToFollower(){
        FollowList user = new FollowList();
        String username = "Noire";

        user.addFollower(username);

        assertTrue(user.containsFollower(username));
    }

    public void testAddToPending(){
        FollowList user = new FollowList();
        String username = "Noire";

        user.addPending(username);

        assertTrue(user.containsPending(username));

    }

    public void testAddToRequest(){
        FollowList user = new FollowList();
        String username = "Noire";

        user.addRequest(username);

        assertTrue(user.containsRequest(username));
    }

    public void testRemoveFromFollowing(){
        FollowList user = new FollowList();
        String username = "Remove_neil";

        user.addFollowing(username);
        user.removeFollowing(username);

        assertFalse(user.containsFollowing(username));
    }

    public void testRemoveFromFollower(){
        FollowList user = new FollowList();
        String username = "Remove_neil";

        user.addFollower(username);
        user.removeFollower(username);

        assertFalse(user.containsFollower(username));
    }

    public void testRemoveFromPending(){
        FollowList user = new FollowList();
        String username = "Remove_neil";

        user.addPending(username);
        user.removePending(username);

        assertFalse(user.containsPending(username));
    }

    public void testRemoveFromRequest(){
        FollowList user = new FollowList();
        String username = "Remove_neil";

        user.addRequest(username);
        user.removeRequest(username);

        assertFalse(user.containsRequest(username));
    }

    public void testGetFollowing() {
        FollowList user = new FollowList();
        String username1 = "Admin";
        String username2 = "Supervisor";
        String username3 = "Master";

        user.addFollowing(username1);
        user.addFollowing(username2);
        user.addFollowing(username3);

        ArrayList<String> returnFollowingList = user.getFollowing();

        assertEquals(username1, returnFollowingList.get(0));
        assertEquals(username2, returnFollowingList.get(1));
        assertEquals(username3, returnFollowingList.get(2));
    }

    public void testGetFollower() {
        FollowList user = new FollowList();
        String username1 = "Admin";
        String username2 = "Supervisor";
        String username3 = "Master";

        user.addFollower(username1);
        user.addFollower(username2);
        user.addFollower(username3);

        ArrayList<String> returnFollowerList = user.getFollower();

        assertEquals(username1, returnFollowerList.get(0));
        assertEquals(username2, returnFollowerList.get(1));
        assertEquals(username3, returnFollowerList.get(2));
    }

    public void testGetPending() {
        FollowList user = new FollowList();
        String username1 = "Admin";
        String username2 = "Supervisor";
        String username3 = "Master";

        user.addPending(username1);
        user.addPending(username2);
        user.addPending(username3);

        ArrayList<String> returnPending = user.getPending();

        assertEquals(username1, returnPending.get(0));
        assertEquals(username2, returnPending.get(1));
        assertEquals(username3, returnPending.get(2));
    }
    public void testGetRequest() {
        FollowList user = new FollowList();
        String username1 = "Admin";
        String username2 = "Supervisor";
        String username3 = "Master";

        user.addRequest(username1);
        user.addRequest(username2);
        user.addRequest(username3);

        ArrayList<String> returnRequest = user.getRequest();

        assertEquals(username1, returnRequest.get(0));
        assertEquals(username2, returnRequest.get(1));
        assertEquals(username3, returnRequest.get(2)) ;
    }


}

