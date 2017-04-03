package com.assign1.brianlu.mooditfromorbit;

import android.os.Parcelable;
import android.text.BoringLayout;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * this class stores data about each user
 * Created by brianlu on 2017-02-23.
 *
 *
 */

public class User{
    private String userName;
    private MoodList moods;
    private FollowList followList;
    private String id;
    private transient UserList requested;

    public User(String userName){
        this.userName = userName;
        this.moods = new MoodList();
        this.followList = new FollowList();

    }

    public String getUserName(){
        return this.userName;
    }

    /**
     * returns moods as a json string
     * @return moods as json string
     */
    public String getGsonMoods(){
        //returns moods as gson string
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Mood.class, new MoodSerializer());
        gsonBuilder.registerTypeAdapter(Emotion.class, new EmotionSerializer());

        Gson gson = gsonBuilder.create();

        String json = gson.toJson(moods);

        json = json.replace("\\\"", "\"");
        json = json.replace("}\"", "}");
        json = json.replace("\"{", "{");
        return json;
    }

    public MoodList getMoods(){
        moods.sortByNewest();
        return this.moods;
    }

    public Mood getMostRecentMood(){
        moods.sortByNewest();
        return moods.getMood(0);
    }

    public void setMoods(MoodList moods){
        this.moods = moods;
    }

    public void addMood(Mood mood){
        if(moods == null){
            moods = new MoodList();
        }
        this.moods.add(mood);
        moods.sortByNewest();
    }

    public void deleteFollowing(User user){
        followList.removeFollowing(user.getId());
    }

    public void deleteFollower(User user){
        followList.removeFollower(user.getId());
    }

    public void addFollowing(User user){
        followList.addFollowing(user.getId());

    }

    public void addFollower(User user){
        followList.addFollower(user.getId());
    }

    public void addPending(User user){
        followList.addPending(user.getId());
    }
    public void deletePending(User user){
        followList.removePending(user.getId());
    }

    public void addRequest(User user){
        followList.addRequest(user.getId());
    }

    public void deleteRequest(User user){
        followList.removeRequest(user.getId());
    }

    public ArrayList<String> getFollowing() {
        return followList.getFollowing();
    }

    public ArrayList<String> getFollowers() {
        return followList.getFollower();
    }

    public UserList getRequested(){
        if(requested == null){
            requested = new UserList();
        }
        return requested;
    }

    public void removeOneRequested(User user){
        requested.deleteUser(user);
    }
    public void removeRequested(){
        requested.removeAll();
    }

    /**
     * create requested if null
     */
    public void createRequested(){
        if(requested == null){
            requested = new UserList();
        }
    }

    /**
     * add user to requested, if null create it
     * @param user user to add
     */
    public void addRequestedUser(User user){
        if(requested == null){
            requested = new UserList();
        }
        requested.add(user);
        requested.sortByAlphabetical();
    }

    public ArrayList<String> getPendingRequests(){
        return followList.getRequest();
    }

    public ArrayList<String> getPending(){
        return followList.getPending();
    }

    public String getGsonFollowList(){
        Gson gson = new Gson();

        return gson.toJson(followList);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
