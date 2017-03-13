package com.assign1.brianlu.mooditfromorbit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;
import android.util.Log;


import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
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
    private Double latitude;
    private Double longitude;
    private String message;
    private Bitmap image;
    private String socialSituation;
    private String userName;
    private String encoded;



    public Mood(Emotion emotion, User user){

        this.emotion = emotion;
        this.userName = user.getUserName();
        this.date = new Date(System.currentTimeMillis());
        this.latitude = null;
        this.longitude = null;
        this.message = null;
    }


    public String getUserName() {
        return userName;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    /**
     * returns emotion object as a json string
     * @return emotion object as json string
     */
    public String getGsonEmotion(){
        Gson gson = new Gson();
        return gson.toJson(this.emotion);

    }

    /**
     * return date formatted for upload to server
     * @return date in string format
     */
    public String getStringDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        return df.format(date);
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * return date formatted for displaying on the screen
     * @return date in string format
     */
    public String getDateForView(){
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");

        return df.format(date);
    }

    /**
     * converts Bitmap image to string
     * using base64 bytes
     *
     * taken from http://stackoverflow.com/questions/4837110/how-to-convert-a-base64-string-into-a-bitmap-image-to-show-it-in-a-imageview
     */
     private void convertImageToByte(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * converts encoded image string to bitmap
     */
    private void convertByteToImage(){
        byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
        image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    /**
     * sets image to the value of the encoded string
     * @param encoded encoded image string
     */
    public void setImageFromEncoded(String encoded){
        this.encoded = encoded;
        if(encoded.equals("")){
            image = null;
        }
        else{
            convertByteToImage();
        }
    }

    public String getEncoded() {
        if(image == null){
            encoded = "";
        }
        else{
            convertImageToByte();
        }
        return encoded;
    }

    public void setEmotion(Emotion emotion) {

        this.emotion = emotion;
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

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    /**
     * set the latitude and longitude
     * @param location current location
     */
    public void setLocation(Location location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();

    }
}
