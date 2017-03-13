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

/**
 * Created by Gregory on 2017-03-06.
 *
 * this is our model
 */

public class MainModel extends MModel<MView> {
    static private UserList users = null;
    static private ArrayList<Emotion> emotions;
    static private User me = null;
    static private MoodList followingMoods = null;
    static private Location moodLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;

    MainModel(){
        super();
        pullUsersFromServer();
        fillEmotions();
        this.followingMoods = new MoodList();
    }

    private void pullUsersFromServer(){
        ElasticSearchController.GetUsersTask getUsersTask = new ElasticSearchController.GetUsersTask();
        getUsersTask.execute("");

        try {
            this.users = getUsersTask.get();
        } catch (Exception e){
            Log.i("Error", "Failed to get the users from the async object");
        }

    }

    /**
     * Fills the ArrayList emotions with required emotions
     */
    private void fillEmotions(){
        //TODO: Add rest of the emotions
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

    public static Emotion getEmotion(String emotionName) {
        for(Emotion emotion: emotions){
            if(emotion.getEmotion().equals(emotionName)){
                return emotion;
            }
        }
        return null;
    }

    public UserList getUsers() {
        return users;
    }

    public void addNewMood(Mood mood){
        me.addMood(mood);
        ElasticSearchController.UpdateUsersMoodTask updateUsersMoodTask = new ElasticSearchController.UpdateUsersMoodTask();
        updateUsersMoodTask.execute(me);
    }
    /**
     * puts the moods of all people that the current user follows into followingMoods
     */
    public void generateFollowingMoods(){
        followingMoods.clear();
        for(User user: users.getUsers()){
            if(me.getFollowing().contains(user.getId())){
                followingMoods.merge(user.getMoods());
            }
        }
    }

    public static MoodList getFollowingMoods() {
        return followingMoods;
    }

    public void addFollower(User user){
        me.addFollower(user);

        ElasticSearchController.UpdateUsersFollowListTask updateUsersFollowListTask = new ElasticSearchController.UpdateUsersFollowListTask();
        updateUsersFollowListTask.execute(me);
    }

    public void addFollowing(User user){
        me.addFollowing(user);

        ElasticSearchController.UpdateUsersFollowListTask updateUsersFollowListTask = new ElasticSearchController.UpdateUsersFollowListTask();
        updateUsersFollowListTask.execute(me);
    }

    public void addUser(User user){
        users.add(user);

        ElasticSearchController.AddUsersTask addUsersTask = new ElasticSearchController.AddUsersTask();
        addUsersTask.execute(user);
    }

    public boolean checkForUser(User user){
        return users.hasUser(user);
    }

    public User getUserByName(String userName){
        return users.getUserByName(userName);
    }

    //taken from http://stackoverflow.com/questions/26893796/how-set-emoji-by-unicode-in-android-textview
    //March 6, 2017 11:36pm
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        MainModel.me = me;
    }

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

    public void stopLocationListener(){
        try {
            moodLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.removeUpdates(locationListener);
        }
        catch(SecurityException e){
            Log.d("Location error", e.toString());

        }
    }
    public Location getLocation(){
        return moodLocation;
    }
}
