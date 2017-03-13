package com.assign1.brianlu.mooditfromorbit;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * deserializes the data from a json mood object
 * Created by Gregory on 2017-03-13.
 */

//referenced http://www.javacreed.com/gson-deserialiser-example/
public class MoodDeserializer implements JsonDeserializer<Mood> {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @Override
    public Mood deserialize(JsonElement src, Type typeOfSrc,
                                 JsonDeserializationContext context) throws JsonParseException
    {

        JsonObject jsonObject = src.getAsJsonObject();

        JsonElement jsonEmotion = jsonObject.get("emotion");
        Emotion emotion = context.deserialize(jsonEmotion, Emotion.class);

        JsonElement jsonUserName = jsonObject.get("userName");
        String userName = jsonUserName.getAsString();

        JsonElement jsonDate = jsonObject.get("date");

        Date date;
        try {
            date = df.parse(jsonDate.getAsString());
        }
        catch (Exception e){
            Log.d("Date failed", "failure");
            date = new Date();
        }
        Double latitude;
        JsonElement jsonLatitude = jsonObject.get("latitude");
        try{
            latitude = jsonLatitude.getAsDouble();
        }
        catch(Exception e){
            latitude = null;
        }


        JsonElement jsonLongitude = jsonObject.get("longitude");
        Double longitude;
        try{
            longitude = jsonLongitude.getAsDouble();
        }
        catch (Exception e){
            longitude = null;
        }

        JsonElement jsonMessage = jsonObject.get("message");
        String message;
        try{
            message = jsonMessage.getAsString();
        }
        catch (Exception e){
            message = "";
        }


        JsonElement jsonEncoded = jsonObject.get("encoded");
        String encoded;
        try{
            encoded = jsonEncoded.getAsString();
        }
        catch (Exception e){
            Log.d("enocoded", e.toString());
            encoded = "";
        }

        JsonElement jsonSocial = jsonObject.get("socialSituation");
        String socialSituation = jsonSocial.getAsString();

        User user = new User(userName);
        Mood mood = new Mood(emotion, user);
        mood.setImageFromEncoded(encoded);
        mood.setSocialSituation(socialSituation);
        mood.setDate(date);
        mood.setLatitude(latitude);
        mood.setLongitude(longitude);
        mood.setMessage(message);
        Log.d("It did it", "hello");
        return mood;

    }
}
