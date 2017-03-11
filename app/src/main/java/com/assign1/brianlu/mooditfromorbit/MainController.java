/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Gregory on 2017-03-06.
 */

public class MainController implements MController {
    MainModel mm = null;

    public MainController(MainModel mm){
        this.mm = mm;
    }

    public UserList getUsers() {
        return mm.getUsers();
    }

    public void addUser(User user){
        mm.addUser(user);
        mm.setMe(user);
    }
    public void addFollower(User user){
        mm.addFollower(user);
    }

    public void addFollowing(User user){
        mm.addFollowing(user);
    }

    public boolean checkSignIn(String userName){
        User me = mm.getUserByName(userName);

        if(me != null){
            mm.setMe(me);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkForUser(User user){
        return mm.checkForUser(user);
    }

    public User getMe(){
        return mm.getMe();
    }

    public ArrayList<Emotion> getEmotions(){
        return mm.getEmotions();
    }

    public MoodList getFollowingMoods() {
        mm.generateFollowingMoods();
        return mm.getFollowingMoods();
    }

    public void generateFollowingMoods(){
        mm.generateFollowingMoods();
    }

}
