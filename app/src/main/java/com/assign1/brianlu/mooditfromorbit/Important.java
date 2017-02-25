package com.assign1.brianlu.mooditfromorbit;

/**
 * Created by FENGYI on 2017-02-24.
 */

public class Important extends Mood implements Moodable {
    public Important(String message) {
        super(message);
    }

    @Override
    public boolean Important() {
        return true;
    }

    public String getMessage() {
        return "IMPORTANT!! " + this.message;
    }
}
