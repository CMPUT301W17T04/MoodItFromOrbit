package com.assign1.brianlu.mooditfromorbit;

import android.os.Parcelable;
import android.text.BoringLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by brianlu on 2017-02-23.
 */

public class User{
//    private Integer userID;
    private String userName;
    private ArrayList<Mood> moodList = new ArrayList<Mood>();
    private ArrayList<User> following = new ArrayList<User>();
    private ArrayList<User> sharing = new ArrayList<User>();

    public User(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }


    public ArrayList<Mood> getMoods(){
        return this.moodList;
    }


    public void setMoods( ArrayList<Mood> moods){
        this.moodList = moods;
    }

    public void addSingleMood(Mood mood){
        this.moodList.add(mood);
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public Boolean setFollowing(User user){
        Boolean add = true;
        for(int i =0; i<this.sharing.size();i++){
            if(user.getUserName().equals(this.following.get(i).getUserName())){
                add =false;

            }
        }
        if(add){
            this.following.add(user);
            return add;
        }else{
            return add;
        }
    }

    public Boolean setSharing(User user){
        Boolean add = true;
        for(int i =0; i<this.sharing.size();i++){
            if(user.getUserName().equals(this.sharing.get(i).getUserName())){
                add =false;

            }
        }
        if(add){
            this.sharing.add(user);
            return add;
        }else{
            return add;
        }
    }

    public ArrayList<User> getFollowing(){
        return this.following;
    }
    public ArrayList<User> getSharing(){
        return this.sharing;
    }
}
