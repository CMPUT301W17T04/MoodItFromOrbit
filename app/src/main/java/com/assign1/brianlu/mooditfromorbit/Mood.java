package com.assign1.brianlu.mooditfromorbit;

import android.webkit.GeolocationPermissions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by brianlu on 2017-02-23.
 */

public class Mood {
    private ArrayList<String> states;
    private Date date;
//    private GeolocationPermissions geo;
    private String message;


    public Mood(ArrayList<String> states, String message, Date date){
        this.states = states;
        this.date = date;
        this.message = message;
    }

    public ArrayList<String> getMood (){
        return this.states;
    }

    public Date getDate() {
        return this.date;
    }

    public String getMessage(){
        return this.message;
    }

    public void setStates(ArrayList<String> states){
        this.states = states;
    }

    public void setSingleState(String state){
        this.states.add(state);

    }

    public void setMessage(String message){
        this.message = message;
    }


}
