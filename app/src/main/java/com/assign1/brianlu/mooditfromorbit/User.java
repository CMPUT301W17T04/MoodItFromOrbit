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
    private MoodList moods;
    private UserList following;
    private UserList sharing;
    private String id;

    public User(String userName){
        this.userName = userName;
        moods = new MoodList();
        following = new UserList();
        sharing = new UserList();
    }

    public String getUserName(){
        return this.userName;
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

    public void setFollowing(User user){
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
    }

    public void setSharing(User user){
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
    }

    public int getFollowingCount(){
        return following.getCount();
    }

    public int getSharingCount(){
        return sharing.getCount();
    }

    public Boolean hasFollower(User user){
        boolean has = false;
        for(int i = 0; i< following.getCount();i++){
            if(this.following.getUser(i).getUserName().equals(user.getUserName())){
                has = true;

            }
        }
        return has;
    }

    public Boolean hasSharer(User user){
        boolean has = false;
        for(int i = 0; i< sharing.getCount();i++){
            if(this.sharing.getUser(i).getUserName().equals(user.getUserName())){
                has = true;
            }
        }
        return has;
    }

    public void deleteSharing(User user){
        boolean has = false;
        for(int i =0; i<this.sharing.getCount();i++){
            if(user.getUserName().equals(this.sharing.getUser(i).getUserName())){
                has = true;
            }
        }
        if(has){
            sharing.deleteUser(user);
        }
    }

    public void deleteFollowing(User user){
        boolean has = false;
        for(int i =0; i<this.following.getCount();i++){
            if(user.getUserName().equals(this.following.getUser(i).getUserName())){
                has = true;
            }
        }
        if(has){
            following.deleteUser(user);
        }
    }

    public User getAFollower(int index){
        return this.following.getUser(index);
    }

    public User getASharer(int index){
        return this.following.getUser(index);
    }

    public UserList getFollowing(){
        return this.following;
    }
    public UserList getSharing(){
        return this.sharing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
