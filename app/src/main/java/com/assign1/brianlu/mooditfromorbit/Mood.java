package com.assign1.brianlu.mooditfromorbit;

import android.graphics.Bitmap;
import android.location.Location;



import java.util.ArrayList;
import java.util.Date;

/**
 * Created by brianlu on 2017-02-23.
 */

public class Mood {
    private ArrayList<String> states;
    private Date date;
    private Location geoLoc;
    protected String message;
    private Bitmap image;


    public Mood(ArrayList<String> states, String message){
        this.states = states;
        this.date = new Date(System.currentTimeMillis());
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

    public void addState(String state){
        boolean add = true;
        for(int i = 0; i< states.size(); i++){
            if (state.equals(states.get(i))){
                add = false;
            }
        }
        if(add){
            this.states.add(state);
        }
    }


}
