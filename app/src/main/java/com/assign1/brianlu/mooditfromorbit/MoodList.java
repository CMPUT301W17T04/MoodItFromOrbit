package com.assign1.brianlu.mooditfromorbit;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by FENGYI on 2017-02-24.
 */

public class MoodList {
    private ArrayList<Mood> moods = new ArrayList<Mood>();

    public void add(Mood mood){
        moods.add(mood);
    }

    public boolean hasMood (Mood mood){
        boolean has = false;
        for(int i = 0; i< moods.size();i++){
            if(moods.get(i).getEmotion().equals(mood.getEmotion())){
                has = true;
            }
        }
        return has;
    }

    public void delete(Mood mood){
        moods.remove(mood);
    }

    public Mood getMood(int index){
        return moods.get(index);

    }

    public ArrayList<Mood> getMoods(){
        return moods;
    }

    public int getCount(){
        return this.moods.size();
    }

    /**
     * removes all moods that do not contain the keyword
     * @param keyword word to search for
     */
    public void sortByWord(CharSequence keyword){
        for(int i = 0; i < this.getCount(); i++){
            if (this.getMood(i).getMessage().contains(keyword)){
            }
            else{
                this.delete(this.getMood(i));
            }
        }
    }

    public void sortByEmotion(Emotion emotion){
        for(int i = 0; i < this.getCount(); i++){
            if (this.getMood(i).getEmotion() == emotion){
            }
            else{
                this.delete(this.getMood(i));
                i--;
            }
        }
    }

    public void sortByRecentWeek(){
        // taken from http://stackoverflow.com/questions/494180/java-how-do-i-check-if-a-date-is-within-a-certain-range
        // Feb 26, 2017 6:03pm
        Date d = new Date();
        Date weekEarly = new Date(d.getTime() - 7 * 24 * 3600 * 1000);

        //checks if each mood is in recent week
        for(int i = 0; i < this.getCount(); i++){
            if (this.getMood(i).getDate().after(weekEarly)){
            }
            else{
                this.delete(this.getMood(i));
            }
        }
    }

    public void sortByNewest(){
        //TODO add sorting algorithm
    }
}