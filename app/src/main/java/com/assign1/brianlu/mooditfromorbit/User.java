package com.assign1.brianlu.mooditfromorbit;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by brianlu on 2017-02-23.
 */

public class User{
//    private Integer userID;
    private String userName;
    private ArrayList<Mood> moodList = new ArrayList<Mood>();

    public User(String userName){
        this.userName = userName;
//        this.userID = userID;
    }

    public String getUserName(){
        return this.userName;
    }


    public ArrayList<Mood> getMoods(){
        return this.moodList;
    }

//    public Integer getUserID(){
//        return this.userID;
//    }
    public void setMoods( ArrayList<Mood> moods){
        this.moodList = moods;
    }

    public void addSingleMood(Mood mood){
        this.moodList.add(mood);
    }

    public void setUserName(String userName){
        this.userName = userName;
    }


}
