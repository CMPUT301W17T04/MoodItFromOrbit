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

public class MainModel extends MModel<MView> {
    static private UserList users = null;
    static private ArrayList<Emotion> emotions;

    MainModel(){
        super();
        pullUsersFromServer();
        fillEmotions();
    }

    private void pullUsersFromServer(){
        ElasticSearchController.GetUsersTask getUsersTask = new ElasticSearchController.GetUsersTask();
        getUsersTask.execute("");

        try {
            this.users = getUsersTask.get();
        } catch (Exception e){
            Log.i("Error", "Failed to get the users from the async object");
        }
        //Log.d("users", users.getUser(0).getUserName());
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

    public void addUser(User user){
        Log.d("testing", user.getUserName());
        user.newMood();
        users.add(user);
        ElasticSearchController.AddUsersTask addUsersTask = new ElasticSearchController.AddUsersTask();
        addUsersTask.execute(user);
    }

    public boolean checkForUser(User user){
        return users.hasUser(user);
    }

    //taken from http://stackoverflow.com/questions/26893796/how-set-emoji-by-unicode-in-android-textview
    //March 6, 2017 11:36pm
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}
