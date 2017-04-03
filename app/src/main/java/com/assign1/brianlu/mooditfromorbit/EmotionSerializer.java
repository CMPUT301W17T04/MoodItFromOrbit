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
 * this is a custom json serializer for the emotion class
 */
//taken from http://stackoverflow.com/questions/6856937/gson-custom-serializer-in-specific-case
    //March 9, 11:00 pm
public class EmotionSerializer implements JsonSerializer<Emotion> {

    @Override
    public JsonElement serialize(Emotion src, Type typeOfSrc,
                                 JsonSerializationContext context)
    {
        final JsonObject obj = new JsonObject();
        obj.addProperty("emotion", src.getEmotion());
        obj.addProperty("colour", src.getColour());
        obj.addProperty("emoticon", src.getEmoticon());

        return obj;
    }
}
