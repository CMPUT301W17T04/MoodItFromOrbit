/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Gregory on 2017-03-09.
 *
 * this is a custom json serializer to convert a mood object into
 * a string of json form
 */

//taken from http://stackoverflow.com/questions/6856937/gson-custom-serializer-in-specific-case
//March 9, 11:00 pm
public class MoodSerializer implements JsonSerializer<Mood> {

    @Override
    public JsonElement serialize(Mood src, Type typeOfSrc,
                                 JsonSerializationContext context)
    {
        final JsonObject obj = new JsonObject();
        obj.addProperty("emotion", src.getGsonEmotion());
        obj.addProperty("userName", src.getUserName());
        obj.addProperty("date", src.getStringDate());
        obj.addProperty("latitude", src.getLatitude());
        obj.addProperty("longitude", src.getLongitude());
        obj.addProperty("message", src.getMessage());
        obj.addProperty("image", src.getStringImage());
        obj.addProperty("socialSituation", src.getSocialSituation());

        return obj;
    }
}
