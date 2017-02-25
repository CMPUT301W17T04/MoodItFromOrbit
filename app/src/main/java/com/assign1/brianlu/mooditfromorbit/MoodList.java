package com.assign1.brianlu.mooditfromorbit;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by FENGYI on 2017-02-24.
 */

public class MoodList {
    private ArrayList<Mood> moods = new ArrayList<Mood>();
    public void add(Mood mood){
        boolean add = true;
        for(int i = 0; i< moods.size();i++){
            if(moods.get(i).getMessage().equals(mood.getMessage())){
                add = false;
                throw new IllegalArgumentException("Mood already exists!");
            }
        }
        if(add){
            moods.add(mood);
        }
    }
    public boolean hasMood (Mood mood){
        boolean has = false;
        for(int i = 0; i< moods.size();i++){
            if(moods.get(i).getMessage().equals(mood.getMessage())){
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
}