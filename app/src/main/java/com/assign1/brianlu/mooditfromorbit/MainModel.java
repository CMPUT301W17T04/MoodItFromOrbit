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
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * this is our model
 * Created by Gregory on 2017-03-06.
 *
 *
 */

public class MainModel extends MModel<MView> {
    static private UserList users = null;
    static private ArrayList<Emotion> emotions;
    static private User me = null;
    static private MoodList followingMoods = null;
    static private Location moodLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;
    static private UserList allExceptMe = null;

    MainModel(){
        super();
        pullUsersFromServer();
        fillEmotions();
        followingMoods = new MoodList();
    }

    /**
     * grabs all users from the server
     */
    public void pullUsersFromServer(){
        Log.d("pulled", "Pulled from server");
        ElasticSearchController.GetUsersTask getUsersTask = new ElasticSearchController.GetUsersTask();
        getUsersTask.execute("");

        try {
            users = getUsersTask.get();


        } catch (Exception e){
            Log.i("Error", "Failed to get the users from the async object");
            Log.i("error", e.toString());
        }

    }

    /**
     * Fills the ArrayList emotions with required emotions
     */
    private void fillEmotions(){
        emotions = new ArrayList<>();

        Emotion happy = new Emotion("Happy", "#06B31D", getEmojiByUnicode(0x263A));
        Emotion sad = new Emotion("Sad", "#1864D6", getEmojiByUnicode(0x1F62D));
        Emotion angry = new Emotion("Angry", "#D61C1C", getEmojiByUnicode(0x1F621));
        Emotion fear = new Emotion("Fear", "#FFFF00", getEmojiByUnicode(0x1F631));
        Emotion disgust = new Emotion("Disgust", "#773A0E", getEmojiByUnicode(0x1F612));
        Emotion confusion = new Emotion("Confusion", "#6A0888", getEmojiByUnicode(0x1F914));
        Emotion shame = new Emotion("Shame", "#FF0080", getEmojiByUnicode(0x1F62E));
        Emotion surprise = new Emotion("Surprise", "#FF8000", getEmojiByUnicode(0x1F632));

        emotions.add(happy);
        emotions.add(sad);
        emotions.add(angry);
        emotions.add(fear);
        emotions.add(disgust);
        emotions.add(confusion);
        emotions.add(shame);
        emotions.add(surprise);
    }

    /**
     * returns emotion that has the same name as emotionName
     * @param emotionName name of emotion
     * @return emotion with name
     */
    public static Emotion getEmotion(String emotionName) {
        for(Emotion emotion: emotions){
            if(emotion.getEmotion().equals(emotionName)){
                return emotion;
            }
        }
        return null;
    }

    /**
     * returns all users
     * @return all users
     */
    public UserList getUsers() {
        return users;
    }

    /**
     * creates a userList of all users that are not the current user
     */
    public void setAllExceptMeUsers(){
        if(allExceptMe == null){
            allExceptMe = new UserList();
            allExceptMe.merge(users);
        }
        else{
            allExceptMe.removeAll();
            allExceptMe.merge(users);
        }
        if(me != null){
            User tempUser = allExceptMe.getUserByName(me.getUserName());

            allExceptMe.deleteUser(tempUser);
        }

        allExceptMe.sortByAlphabetical();

    }

    /**
     * return all users that aren't the current user
     * @return users that aren't me
     */
    public UserList getAllExceptMeUsers(){
        return allExceptMe;
    }

    /**
     * adds new mood to current users mood history
     * @param mood new mood
     */
    public void addNewMood(Mood mood, Context context){
        me.addMood(mood);
        updateMoodList(context);

    }

    /**
     * updates moodList on the server
     * @param context current activity context
     */
    public void updateMoodList(Context context){
        ServerUploader serverUploader = new ServerUploader();
        UpdateMoods updateMoods = new UpdateMoods(me);
        serverUploader.addMoodsCommunication(updateMoods, context);
        serverUploader.sendCommunication(context);
    }

    /**
     * attempts to upload to server
     * @param context current activity context
     */
    public void communicateToServer(Context context){
        ServerUploader serverUploader = new ServerUploader();
        serverUploader.sendCommunication(context);
    }
    /**
     * puts the moods of all people that the current user follows into followingMoods
     */
    public void generateFollowingMoods(){
        pullUsersFromServer();
        followingMoods.clear();
        for(User user: users.getUsers()){
            if(me.getFollowing().contains(user.getId())){
                Mood mood = user.getMostRecentMood();
                if(mood != null) {
                    followingMoods.add(mood);
                }
            }
        }
        followingMoods.sortByNewest();
    }

    /**
     * returns followingMoods
     * @return list of moods of users that current user is following
     */
    public static MoodList getFollowingMoods() {
        return followingMoods;
    }

    /**
     * adds user to follower
     * @param user user that is following current user
     */
    public void addFollower(User user){
        me.addFollower(user);

        ElasticSearchController.UpdateUsersFollowListTask updateUsersFollowListTask = new ElasticSearchController.UpdateUsersFollowListTask();
        updateUsersFollowListTask.execute(me);

        try {
            updateUsersFollowListTask.get();
        }
        catch(Exception e){
            Log.d("Time failed", e.toString());
        }

        removePending(user);
        addFollowing(user);
    }

    /**
     * adds user to following
     * @param user user that current user is following
     */
    private void addFollowing(User user){
        user.addFollowing(me);

        ElasticSearchController.UpdateUsersFollowListTask updateUsersFollowListTask = new ElasticSearchController.UpdateUsersFollowListTask();
        updateUsersFollowListTask.execute(user);

        try {
            updateUsersFollowListTask.get();
        }
        catch(Exception e){
            Log.d("Time failed", e.toString());
        }
    }

    /**
     * checks if user is already pending or following
     * @param user user that current user is trying to follow
     */
    public String checkPending(User user){
        if(!me.getFollowing().contains(user.getId()) && !me.getPending().contains(user.getId())){
            //addPending(user);
            return "send follow request to " + user.getUserName();
        }
        else if(me.getFollowing().contains(user.getId())){
            //removeFollowing(user);

            return "stop following " + user.getUserName();
        }

        return null;
    }

    /**
     * adds a user to pending
     * @param user user to add to pending
     */
    public void addPending(User user){
        me.addPending(user);

        ElasticSearchController.UpdateUsersFollowListTask updateUsersFollowListTask = new ElasticSearchController.UpdateUsersFollowListTask();
        updateUsersFollowListTask.execute(me);

        try {
            updateUsersFollowListTask.get();
        }
        catch(Exception e){
            Log.d("Time failed", e.toString());
        }

        addRequest(user);
    }

    /**
     * remove a user from following
     * @param user user to stop following
     */
    public void removeFollowing(User user){
        if(me.getFollowing().contains(user.getId())){
            me.deleteFollowing(user);

            ElasticSearchController.UpdateUsersFollowListTask updateUsersFollowListTask = new ElasticSearchController.UpdateUsersFollowListTask();
            updateUsersFollowListTask.execute(me);

            removeFollower(user);
        }
    }

    /**
     * removes a user from followers
     * @param user user to remove
     */
    private void removeFollower(User user){
        user.deleteFollower(me);

        ElasticSearchController.UpdateUsersFollowListTask updateUsersFollowListTask = new ElasticSearchController.UpdateUsersFollowListTask();
        updateUsersFollowListTask.execute(user);
    }

    /**
     * removes a user from pending
     * @param user user to remove
     */
    private void removePending(User user){
        user.deletePending(me);

        ElasticSearchController.UpdateUsersFollowListTask updateUsersFollowListTask = new ElasticSearchController.UpdateUsersFollowListTask();
        updateUsersFollowListTask.execute(me);

        removeRequest(user);
    }

    /**
     * generates a UserList of all users that are requesting to follow current user
     */
    public void generateRequested(){
        try{
            if(me.getRequested() != null){
                me.removeRequested();
            }
            else{
                me.createRequested();
            }
        }
        catch (Exception e){
            Log.d("server issue", "the server quit accepting new users until it is cleared of all users");
            throw e;
        }


        for(String id : me.getPendingRequests()){
            User user = users.getUserById(id);
            me.addRequestedUser(user);
        }
    }

    /**
     * adds me to user I'm trying to follow's requestList
     * @param user user I'm trying to follow
     */
    private void addRequest(User user){
        user.addRequest(me);

        ElasticSearchController.UpdateUsersFollowListTask updateUsersFollowListTask = new ElasticSearchController.UpdateUsersFollowListTask();
        updateUsersFollowListTask.execute(user);

        try {
            updateUsersFollowListTask.get();
        }
        catch(Exception e){
            Log.d("Time failed", e.toString());
        }
    }

    /**
     * removes a user from requests
     * @param user user to remove
     */
    private void removeRequest(User user){
        me.deleteRequest(user);
        me.removeOneRequested(user);

        ElasticSearchController.UpdateUsersFollowListTask updateUsersFollowListTask = new ElasticSearchController.UpdateUsersFollowListTask();
        updateUsersFollowListTask.execute(user);
        try {
            updateUsersFollowListTask.get();
        }
        catch(Exception e){
            Log.d("Time failed", e.toString());
        }
    }

    /**
     * adds a user and syncs to server
     * @param user user to add
     */
    public void addUser(User user){
        users.add(user);

        ElasticSearchController.AddUsersTask addUsersTask = new ElasticSearchController.AddUsersTask();
        addUsersTask.execute(user);
        try {
            addUsersTask.get();
        }
        catch(Exception e){
            Log.d("Time failed", e.toString());
        }
    }

    /**
     * checks if user already exists
     * @param user user to check for
     * @return true or false
     */
    public boolean checkForUser(User user){
        return users.hasUser(user);
    }

    /**
     * returns user with username
     * @param userName username of requested user
     * @return user with username
     */
    public User getUserByName(String userName){
        return users.getUserByName(userName);
    }

    /**
     * converts unicode to string emoji
     * taken from http://stackoverflow.com/questions/26893796/how-set-emoji-by-unicode-in-android-textview
     * March 6, 2017 11:36pm
     *
     * @param unicode unicode value of an emoji
     * @return string value of emoji
     */
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }


    /**
     * returns current user
     * @return current user
     */

    public User getMe() {
        return me;
    }

    /**
     * sets current user
     * @param me current user
     */
    public void setMe(User me) {
        MainModel.me = me;

    }

    /**
     * activate gps and look for current location
     * @param context context of activity
     */
    public void startLocationListen(Context context){
        // referenced https://developer.android.com/guide/topics/location/strategies.html
        // March 12, 2017 10:00pm

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                moodLocation = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        catch (SecurityException e){
            Log.d("location", e.toString());
        }
    }

    /**
     * stop gps from looking for current location
     */
    public void stopLocationListener(){
        try {
            moodLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.removeUpdates(locationListener);
        }
        catch(SecurityException e){
            Log.d("Location error", e.toString());

        }
    }

    /**
     * return current location
     * @return current location
     */
    public Location getLocation(){
        return moodLocation;
    }


}
