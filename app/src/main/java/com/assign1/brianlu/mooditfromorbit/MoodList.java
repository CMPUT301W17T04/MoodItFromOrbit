package com.assign1.brianlu.mooditfromorbit;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * this class stores moods in an ArrayList
 * Created by FENGYI on 2017-02-24.
 *
 */

public class MoodList{
    private ArrayList<Mood> moods = null;

    public MoodList(){
        moods = new ArrayList<>();
    }

    public void add(Mood mood){
        moods.add(mood);
    }

    /**
     * check if contains mood
     * @param mood mood to check for
     * @return true or false
     */
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

    public void merge(MoodList foundMoods){
        moods.addAll(foundMoods.getMoods());
    }

    public int getCount(){
        return this.moods.size();
    }

    public void clear(){
        moods.clear();
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


    /**
     * sort by emotion
     * @param emotion emotion to sort by
     */
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


    /**
     * sort by most recent week
     */
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


    /**
     * sorts moods by reverse chronological order
     */
    public void sortByNewest(){
        Comparator<Mood> compareByDate = new Comparator<Mood>() {
            @Override
            public int compare(Mood o1, Mood o2) {
                Date d1 = o1.getDate();
                Date d2 = o2.getDate();

                return d2.compareTo(d1);
            }
        };

        Collections.sort(this.moods, compareByDate);
    }
}