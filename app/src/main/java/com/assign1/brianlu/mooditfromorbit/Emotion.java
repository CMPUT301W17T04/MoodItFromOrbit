/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.graphics.Color;

/**
 * Created by Gregory on 2017-02-26.
 */

public class Emotion {
    private String emotion;
    private int colour;
    private String emoticon;

    public Emotion(String emotion, String colourString, String emoticon){
        this.emotion = emotion;
        this.emoticon = emoticon;
        // taken from http://stackoverflow.com/questions/5248583/how-to-get-a-color-from-hexadecimal-color-string
        // Feb 26, 2017 3:48pm

        this.colour = Color.parseColor(colourString);
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public String getEmoticon() {
        return emoticon;
    }

    public void setEmoticon(String emoticon) {
        this.emoticon = emoticon;
    }
}
