package com.assign1.brianlu.mooditfromorbit;

import android.graphics.Bitmap;
import android.location.Location;


import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by brianlu on 2017-02-23.
 *
 * Contains data for mood event
 */

public class Mood {
    private Emotion emotion;
    private Date date;
    private Location geoLoc;
    private String message;
    private Bitmap image;
    private String socialSituation;
    private String userName;


    public Mood(Emotion emotion, User user){
        this.emotion = emotion;
        this.userName = user.getUserName();
        this.date = new Date(System.currentTimeMillis());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public String getGsonEmotion(){
        Gson gson = new Gson();

        return gson.toJson(this.emotion);
    }

    public String getStringDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        return df.format(date);
    }

    public String getDateForView(){
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");

        return df.format(date);
    }

    public String getStringLocation(){
        //TODO implement string
        return null;
    }

    public String getStringImage(){
        //TODO convert image to string
        return null;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    public Location getGeoLoc() {
        return geoLoc;
    }

    public void setGeoLoc(Location geoLoc) {
        this.geoLoc = geoLoc;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getSocialSituation() {
        return socialSituation;
    }

    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage(){
        return this.message;
    }


    public void setMessage(String message){
        this.message = message;
    }

}
