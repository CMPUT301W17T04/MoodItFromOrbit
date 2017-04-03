package com.assign1.brianlu.mooditfromorbit;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * tests the moodList
 * Created by FENGYI on 2017-02-24.
 */

public class MoodListTest extends ActivityInstrumentationTestCase2 {
    public MoodListTest(){
        super(MoodMainActivity.class);

    }

    public void testGetMood(){
        MoodList moods = new MoodList();
        Emotion happy = new Emotion("Happy", "#06B31D", "\u263A");

        User user = new User("Greg");
        Mood mood = new Mood(happy, user);

        moods.add(mood);

        Mood returnedMood = moods.getMood(0);

        assertEquals(returnedMood.getEmotion(), mood.getEmotion());
        assertEquals(returnedMood.getDate(), mood.getDate());
    }

    public void testAddMood(){
        MoodList moods = new MoodList();
        Emotion happy = new Emotion("Happy", "#06B31D", "\u263A");

        User user = new User("Greg");
        Mood mood = new Mood(happy, user);

        moods.add(mood);

        assertTrue(moods.hasMood(mood));
    }

    public void testDeleteMood(){
        MoodList moods = new MoodList();
        Emotion happy = new Emotion("Happy", "#06B31D", "\u263A");

        User user = new User("Greg");
        Mood mood = new Mood(happy, user);

        moods.add(mood);
        moods.delete(mood);

        assertFalse(moods.hasMood(mood));

    }

    public void testGetMoods(){
        MoodList moods = new MoodList();

        //haven't found different emoji code yet
        Emotion happy = new Emotion("Happy", "#06B31D", "☺");
        Emotion sad = new Emotion("Sad", "#1864D6", "☺");
        Emotion angry = new Emotion("Angry", "#D61C1C", "☺");

        User user = new User("Greg");
        Mood mood = new Mood(happy, user);
        Mood mood1 = new Mood(sad, user);
        Mood mood2 = new Mood(angry, user);
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
        Emotion happy = new Emotion("Happy", "#06B31D", "☺");
        Emotion sad = new Emotion("Sad", "#1864D6", "☺");
        Emotion angry = new Emotion("Angry", "#D61C1C", "☺");

        User user = new User("Greg");
        Mood mood1 = new Mood(happy, user);
        moods.add(mood1);
        count+=1;

        Mood mood2 = new Mood(sad, user);
        moods.add(mood2);
        count+=1;

        Mood mood3 = new Mood(angry, user);
        moods.add(mood3);
        count+=1;

        assertEquals(true,moods.getCount() == count);

    }

    public void testSortByWord(){
        MoodList moods = new MoodList();

        //haven't found different emoji code yet
        Emotion happy = new Emotion("Happy", "#06B31D", "☺");
        Emotion sad = new Emotion("Sad", "#1864D6", "☺");
        Emotion angry = new Emotion("Angry", "#D61C1C", "☺");

        User user = new User("Greg");
        Mood mood = new Mood(happy, user);
        mood.setMessage("hello");

        Mood mood1 = new Mood(sad, user);
        mood1.setMessage("hello goodbye");

        Mood mood2 = new Mood(angry, user);
        mood2.setMessage("goodbye");

        moods.add(mood);
        moods.add(mood1);
        moods.add(mood2);

        moods.sortByWord("hello");

        //checks if each mood contains keyword
        for(int i = 0; i < moods.getCount(); i++){
            if (moods.getMood(i).getMessage().contains("hello")){
            }
            else{
                fail();
            }
        }

    }

    public void testSortByEmotion(){
        MoodList moods = new MoodList();

        //haven't found different emoji code yet
        Emotion happy = new Emotion("Happy", "#06B31D", "☺");
        Emotion sad = new Emotion("Sad", "#1864D6", "☺");
        Emotion angry = new Emotion("Angry", "#D61C1C", "☺");

        User user = new User("Greg");
        Mood mood = new Mood(happy, user);

        Mood mood1 = new Mood(sad, user);

        Mood mood2 = new Mood(angry, user);

        Mood mood3 = new Mood(happy, user);

        moods.add(mood);
        moods.add(mood1);
        moods.add(mood2);
        moods.add(mood3);

        moods.sortByEmotion(happy);

        int count = moods.getCount();
        //checks if each mood matches emotion
        for(int i = 0; i < count; i++){
            if (moods.getMood(i).getEmotion() == happy){
            }
            else{
                fail();
            }
        }
    }

    public void testSortByRecentWeek(){
        MoodList moods = new MoodList();

        //haven't found different emoji code yet
        Emotion happy = new Emotion("Happy", "#06B31D", "☺");
        Emotion sad = new Emotion("Sad", "#1864D6", "☺");
        Emotion angry = new Emotion("Angry", "#D61C1C", "☺");

        User user = new User("Greg");
        Mood mood = new Mood(happy, user);

        Mood mood1 = new Mood(sad, user);

        Mood mood2 = new Mood(angry, user);

        Mood mood3 = new Mood(happy, user);

        moods.add(mood);
        moods.add(mood1);
        moods.add(mood2);
        moods.add(mood3);

        moods.sortByRecentWeek();


        // taken from http://stackoverflow.com/questions/494180/java-how-do-i-check-if-a-date-is-within-a-certain-range
        // Feb 26, 2017 6:03pm
        Date d = new Date();
        Date weekEarly = new Date(d.getTime() - 7 * 24 * 3600 * 1000);

        //checks if each mood is in recent week
        for(int i = 0; i < moods.getCount(); i++){
            if (moods.getMood(i).getDate().after(weekEarly)){
            }
            else{
                fail();
            }
        }
    }

    public void testSortByNewest(){
        MoodList moods = new MoodList();

        //haven't found different emoji code yet
        Emotion happy = new Emotion("Happy", "#06B31D", "☺");
        Emotion sad = new Emotion("Sad", "#1864D6", "☺");
        Emotion angry = new Emotion("Angry", "#D61C1C", "☺");

        User user = new User("Greg");
        Mood mood = new Mood(happy, user);
        moods.add(mood);

        Mood mood1 = new Mood(sad, user);
        moods.add(mood1);

        Mood mood2 = new Mood(angry, user);
        moods.add(mood2);

        Mood mood3 = new Mood(happy, user);
        moods.add(mood3);

        moods.sortByRecentWeek();

        //checks if each mood is in reverse chronological order
        for(int i = 0; i < moods.getCount(); i++){
            if(i == 0){
            }
            else{
                if (moods.getMood(i).getDate().before(moods.getMood(i-1).getDate())){
                }
                else{
                    fail();
                }
            }

        }
    }

}
