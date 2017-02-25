package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends ActivityInstrumentationTestCase2 {

    public ExampleInstrumentedTest() {
        super(com.assign1.brianlu.mooditfromorbit.MoodMainActivity.class);
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
    public void testgetMood(){
        MoodList tweets = new MoodList();
        Mood mood = new GMood("Test Mood");

        moods.add(mood);
        Mood returnedMood = moods.getMood(0);

        assertEquals(returnedMood.getMessage(), mood.getMessage());
        assertEquals(returnedMood.getDate(), mood.getDate());
    }
}
