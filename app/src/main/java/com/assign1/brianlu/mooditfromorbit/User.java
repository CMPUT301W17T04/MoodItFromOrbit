package com.assign1.brianlu.mooditfromorbit;

import android.os.Parcelable;
import android.text.BoringLayout;
import android.util.Log;
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

    public void setFollowing(User user){
        Boolean add = true;
        for(int i =0; i< following.size();i++){
            if(user.getUserName().equals(following.get(i).getUserName())){
                add = false;
            }
        }
        if(add){
            following.add(user);
        } else{
            throw new IllegalArgumentException("You already followed this user!");
        }
    }

    public void setSharing(User user){
        Boolean add = true;
        for(int i =0; i<this.sharing.size();i++){
            if(user.getUserName().equals(this.sharing.get(i).getUserName())){
                add =false;
            }
        }
        if(add){
            this.sharing.add(user);
        }else{
            throw new IllegalArgumentException("You already shared this user!");
        }
    }

    public int getFollowingCount(){
        return following.size();
    }

    public int getSharingCount(){
        return sharing.size();
    }

    public Boolean hasFollower(User user){
        boolean has = false;
        for(int i = 0; i< following.size();i++){
            if(this.following.get(i).getUserName().equals(user.getUserName())){
                has = true;

            }
        }
        return has;
    }

    public Boolean hasSharer(User user){
        boolean has = false;
        for(int i = 0; i< sharing.size();i++){
            if(this.sharing.get(i).getUserName().equals(user.getUserName())){
                has = true;
            }
        }
        return has;
    }

    public void deleteSharing(User user){
        boolean has = false;
        for(int i =0; i<this.sharing.size();i++){
            if(user.getUserName().equals(this.sharing.get(i).getUserName())){
                has = true;
            }
        }
        if(has){
            sharing.remove(user);
        }
    }

    public void deleteFollowing(User user){
        boolean has = false;
        for(int i =0; i<this.following.size();i++){
            if(user.getUserName().equals(this.following.get(i).getUserName())){
                has = true;
            }
        }
        if(has){
            following.remove(user);
        }
    }

    public User getAFollower(int index){
        return this.following.get(index);
    }

    public User getASharer(int index){
        return this.following.get(index);
    }

    public ArrayList<User> getFollowing(){
        return this.following;
    }
    public ArrayList<User> getSharing(){
        return this.sharing;
    }
}
