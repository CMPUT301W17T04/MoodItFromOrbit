package com.assign1.brianlu.mooditfromorbit;

import android.webkit.GeolocationPermissions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by brianlu on 2017-02-23.
 */

public class Mood {
    private ArrayList<String> moods;
    private Date date;
//    private GeolocationPermissions geo;
    private String message;


    public Mood(ArrayList<String> moods, String message, Date date){
        this.moods = moods;
        this.date = date;
        this.message = message;
    }

    public ArrayList<String> getMood (){
        return this.moods;
    }

    public Date getDate() {
        return this.date;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMoods(ArrayList<String> moods){
        this.moods = moods;
    }

    public void setSingleMood(String mood){
        this.moods.add(mood);

    }

    public void setMessage(String message){
        this.message = message;
    }


}
