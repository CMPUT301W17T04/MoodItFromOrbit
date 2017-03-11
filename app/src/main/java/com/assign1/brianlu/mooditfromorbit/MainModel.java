/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Gregory on 2017-03-06.
 */

/**
 * this is our model
 */

public class MainModel extends MModel<MView> {
    static private UserList users = null;
    static private ArrayList<Emotion> emotions;
    static private User me = null;
    static private MoodList followingMoods = null;

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

    public static ArrayList<Emotion> getEmotions() {
        return emotions;
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

        ElasticSearchController.UpdateUsersFollowersTask updateUsersFollowersTask = new ElasticSearchController.UpdateUsersFollowersTask();
        updateUsersFollowersTask.execute(me);
    }

    public void addFollowing(User user){
        me.addFollowing(user);

        ElasticSearchController.UpdateUsersFollowingTask updateUsersFollowingTask = new ElasticSearchController.UpdateUsersFollowingTask();
        updateUsersFollowingTask.execute(user);
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
}
