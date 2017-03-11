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
 * Created by brianlu on 2017-02-23.
 */

public class User{
    private String userName;
    private MoodList moods;
    private ArrayList<String> following;
    private ArrayList<String> followers;
    private String id;

    public User(String userName){
        this.userName = userName;
        this.moods = new MoodList();
        this.following = new ArrayList<>();
        this.followers = new ArrayList<>();
    }

    public String getUserName(){
        return this.userName;
    }

    public String getGsonMoods(){
        //returns moods as gson string
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Mood.class, new MoodSerializer());
        gsonBuilder.registerTypeAdapter(Emotion.class, new EmotionSerializer());

        Gson gson = gsonBuilder.create();

        String json = gson.toJson(moods);

        json = json.replace("\\", "");
        json = json.replace("}\"", "}");
        json = json.replace("\"{", "{");
        return json;
    }

    public MoodList getMoods(){
        return this.moods;
    }


    public void setMoods(MoodList moods){
        this.moods = moods;
    }

    public void addMood(Mood mood){
        if(moods == null){
            moods = new MoodList();
        }
        this.moods.add(mood);
    }

    public void setUserName(String userName){
        this.userName = userName;
    }


    public void addFollowing(User user){
        if(following == null){
            following = new ArrayList<>();
        }
        following.add(user.getId());

    }

    public void addFollower(User user){
        if(followers == null){
            followers = new ArrayList<>();
        }
        followers.add(user.getId());
    }


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

    public ArrayList<String> getFollowing() {
        return following;
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public String getGsonFollowers(){
        Gson gson = new Gson();

        return gson.toJson(followers);
    }

    public String getGsonFollowing(){
        Gson gson = new Gson();

        return gson.toJson(following);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
