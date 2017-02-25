package com.assign1.brianlu.mooditfromorbit;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by FENGYI on 2017-02-24.
 */

public class MoodListTest extends ActivityInstrumentationTestCase2 {
    public MoodListTest(){
        super(MoodMainActivity.class);

    }

    public void testgetMood(){
        MoodList moods = new MoodList();
        ArrayList<String> states = new ArrayList<String>();
        states.add("happy");
        states.add("bored");
        Mood mood = new GMood(states, "Test Mood");

        moods.add(mood);
        Mood returnedMood = moods.getMood(0);

        assertEquals(returnedMood.getMessage(), mood.getMessage());
        assertEquals(returnedMood.getDate(), mood.getDate());
    }

    public void testaddMood(){
        MoodList moods = new MoodList();
        ArrayList<String> states = new ArrayList<String>();
        states.add("happy");
        states.add("bored");
        Mood mood = new GMood(states, "Test mood");

        moods.add(mood);

        assertTrue(moods.hasMood(mood));
    }

    public void testDeleteMood(){
        MoodList moods = new MoodList();
        ArrayList<String> states = new ArrayList<String>();
        states.add("happy");
        states.add("bored");
        Mood mood = new GMood(states,"Last mood");

        moods.add(mood);
        moods.delete(mood);
        assertFalse(moods.hasMood(mood));

    }

    public void testStrings(){
        assertEquals("'test' should be 'test'", "test","test");
        assertTrue("'test' should start with 't'","test".startsWith("t"));
    }

    public void testGetMoods(){
        MoodList moods = new MoodList();
        ArrayList<String> states = new ArrayList<String>();
        states.add("happy");
        states.add("interested");
        ArrayList<String> states1 = new ArrayList<String>();
        states.add("happy");
        states.add("depressed");
        ArrayList<String> states2 = new ArrayList<String>();
        states.add("happy");
        states.add("angry");

        Mood mood = new GMood(states,"test1");
        Mood mood1 = new GMood(states1,"test2");
        Mood mood2 = new GMood(states2,"test3");
        moods.add(mood);
        moods.add(mood1);
        moods.add(mood2);

        ArrayList<Mood> checkList = new ArrayList<Mood>();
        checkList = moods.getMoods();
        for(int i = 0; i< checkList.size();i++){
            for(int j = i+1; j < checkList.size();j++){
                if(checkList.get(i).getDate().after(checkList.get(j).getDate()) ){
                    fail();
                }
            }
        }
    }

    public void testGetCount(){
        int count = 0;
        MoodList moods = new MoodList();
        ArrayList<String> states = new ArrayList<String>();
        states.add("happy");
        states.add("interested");
        ArrayList<String> states1 = new ArrayList<String>();
        states.add("happy");
        states.add("depressed");
        ArrayList<String> states2 = new ArrayList<String>();
        states.add("happy");
        states.add("angry");
        ArrayList<String> states3 = new ArrayList<String>();
        states.add("nono");
        states.add("angry");
        Mood mood1 = new GMood(states,"mood1");
        moods.add(mood1);
        count+=1;
        Mood mood2 = new GMood(states1,"mood2");
        moods.add(mood2);
        count+=1;
        Mood mood3 = new GMood(states2, "mood3");
        moods.add(mood3);
        count+=1;
        Mood mood4 = new GMood(states3,"mood4");
        moods.add(mood4);
        count+=1;
        assertEquals(true,moods.getCount() == count);

    }

}
