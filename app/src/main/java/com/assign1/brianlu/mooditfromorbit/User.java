package com.assign1.brianlu.mooditfromorbit;

import android.os.Parcelable;
import android.text.BoringLayout;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by brianlu on 2017-02-23.
 */

public class User{
//    private Integer userID;
    private String userName;
    private MoodList moods;
    private ArrayList<String> following;
    private ArrayList<String> followers;
    private String id;

    public User(String userName){
        this.userName = userName;
        this.moods = new MoodList();
    }

    public String getUserName(){
        return this.userName;
    }

    public void newMood(){
        //dont use
        Emotion happy = new Emotion("Happy", "#06B31D", "F263A");
        Mood newMood = new Mood(happy);
        moods.add(newMood);
    }

    public String getGsonMoods(){
        //dont use
        newMood();
        Gson gson = new Gson();

        return gson.toJson(moods);
    }

    public MoodList getMoods(){
        return this.moods;
    }


    public void setMoods(MoodList moods){
        this.moods = moods;
    }

    public void addSingleMood(Mood mood){
        this.moods.add(mood);
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    /*public void setFollowing(User user){
        Boolean add = true;
        for(int i =0; i< following.getCount();i++){
            if(user.getUserName().equals(following.getUser(i).getUserName())){
                add = false;
            }
        }
        if(add){
            following.add(user);
        } else{
            throw new IllegalArgumentException("You already followed this user!");
        }
    }*/

    public void addFollowing(User user){
        following.add(user.getId());
    }

    public void addFollower(User user){
        followers.add(user.getId());
    }

    /*public void setSharing(User user){
        Boolean add = true;
        for(int i =0; i<this.sharing.getCount();i++){
            if(user.getUserName().equals(this.sharing.getUser(i).getUserName())){
                add =false;
            }
        }
        if(add){
            this.sharing.add(user);
        }else{
            throw new IllegalArgumentException("You already shared this user!");
        }
    }*/

    public int getFollowingCount(){
        return following.size();
    }

    public int getFollowersCount(){
        return followers.size();
    }

    public Boolean hasFollowing(User user){
        if(this.following.contains(user.getId())){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean hasFollower(User user){
        if(this.followers.contains(user.getId())){
            return true;
        }
        else{
            return false;
        }
    }

    /*public Boolean hasFollower(User user){
        boolean has = false;
        for(int i = 0; i< following.getCount();i++){
            if(this.following.getUser(i).getUserName().equals(user.getUserName())){
                has = true;

            }
        }
        return has;
    }*/

    /*public Boolean hasSharer(User user){
        boolean has = false;
        for(int i = 0; i< sharing.getCount();i++){
            if(this.sharing.getUser(i).getUserName().equals(user.getUserName())){
                has = true;
            }
        }
        return has;
    }*/

    /*public void deleteSharing(User user){
        boolean has = false;
        for(int i =0; i<this.sharing.getCount();i++){
            if(user.getUserName().equals(this.sharing.getUser(i).getUserName())){
                has = true;
            }
        }
        if(has){
            sharing.deleteUser(user);
        }
    }*/

    /*public void deleteFollowing(User user){
        boolean has = false;
        for(int i =0; i<this.following.getCount();i++){
            if(user.getUserName().equals(this.following.getUser(i).getUserName())){
                has = true;
            }
        }
        if(has){
            following.deleteUser(user);
        }
    }*/

    public ArrayList<String> getFollowing() {
        return following;
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
