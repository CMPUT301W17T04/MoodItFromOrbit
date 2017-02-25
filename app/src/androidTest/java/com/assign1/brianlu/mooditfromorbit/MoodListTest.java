package com.assign1.brianlu.mooditfromorbit;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by brianlu on 2017-02-24.
 */

public class MoodListTest extends ActivityInstrumentationTestCase2 {
    public MoodListTest(){
        super(MoodMainActivity.class);

    }

    public void testgetMood(){
        MoodList moods = new MoodList();
        Mood mood = new GMood("Test Mood");

        moods.add(mood);
        Mood returnedMood = moods.getMood(0);

        assertEquals(returnedMood.getMessage(), mood.getMessage());
        assertEquals(returnedMood.getDate(), mood.getDate());
    }
}
