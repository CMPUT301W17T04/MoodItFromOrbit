package com.assign1.brianlu.mooditfromorbit;

import android.graphics.Bitmap;
import android.location.Location;



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


    public Mood(Emotion emotion){
        this.emotion = emotion;
        this.date = new Date(System.currentTimeMillis());
    }

    public Emotion getEmotion() {
        return emotion;
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

    public String getMessage(){
        return this.message;
    }


    public void setMessage(String message){
        this.message = message;
    }



}
