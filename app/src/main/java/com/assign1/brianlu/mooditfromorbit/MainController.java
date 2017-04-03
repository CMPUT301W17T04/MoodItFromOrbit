/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

/**
 * this is our controller, it interacts with the model
 * Created by Gregory on 2017-03-06.
 *
 *
 */

public class MainController implements MController {
    MainModel mm = null;

    public MainController(MainModel mm){
        this.mm = mm;
    }

    public UserList getUsers() {
        return mm.getUsers();
    }

    public UserList getAllExceptMeUsers(){

        mm.setAllExceptMeUsers();
        return mm.getAllExceptMeUsers();
    }

    /**
     * pulls users from server
     * @param context current application context
     */
    public void pullUsers(Context context){
        mm.communicateToServer(context);
        mm.pullUsersFromServer();
    }
    /**
     * calls addUser() and setMe()
     * @param user
     */
    public void addUser(User user){
        mm.addUser(user);
        mm.setMe(user);
    }

    /**
     * adds a new follower
     * @param user user to follow
     */
    public void addFollower(User user){
        mm.addFollower(user);
    }

    /**
     * stops following a user
     * @param user user to stop following
     */
    public void removeFollowing(User user){
        mm.removeFollowing(user);
    }

    /**
     * adds a user to pending follower
     * @param user user to add
     */
    public void addPending(User user){
        mm.addPending(user);
    }

    /**
     * checks if already following user
     * @param user user to check
     * @return string on what to do
     */
    public String checkPending(User user){
        return mm.checkPending(user);
    }

    /**
     * sends a new mood to the server
     * @param context current application context
     */
    public void communicateToServer(Context context){
        mm.communicateToServer(context);
    }

    /**
     * updates the moods
     * @param context current application context
     */
    public void updateMoodList(Context context){
        mm.updateMoodList(context);
    }

    /**
     * check if username valid
     * @param userName username to check
     * @return true or false
     */
    public boolean checkSignIn(String userName){
        User me = mm.getUserByName(userName);

        if(me != null){
            mm.setMe(me);
            mm.generateRequested();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * generate a list of users that have requested to follow the current user
     * @param context
     */
    public void generateRequested(Context context){
        pullUsers(context);
        if(MainApplication.getConnectedToServer()){
            mm.setMe(mm.getUsers().getUserByName(mm.getMe().getUserName()));
            mm.generateRequested();
        }

    }

    /**
     * check if user is there
     * @param user user to check
     * @return true or false
     */
    public boolean checkForUser(User user){
        return mm.checkForUser(user);
    }

    /**
     * returns current user
     * @return current user
     */
    public User getMe(){
        return mm.getMe();
    }

    public Emotion getEmotion(String emotionName){
        return mm.getEmotion(emotionName);
    }

    /**
     * generate following moods and return them
     * @return following moods
     */
    public MoodList getFollowingMoods() {
        mm.generateFollowingMoods();
        return mm.getFollowingMoods();
    }

    /**
     * calls generateFollowingMoods
     */
    public void generateFollowingMoods(){
        mm.generateFollowingMoods();
    }

    public void addNewMood(Mood mood, Context context){
        mm.addNewMood(mood, context);
    }

    public void startLocationListen(Context context){
        mm.startLocationListen(context);
    }

    public void stopLocationListener(){
        mm.stopLocationListener();
    }

    public Location getLocation(){
        return mm.getLocation();
    }

}
