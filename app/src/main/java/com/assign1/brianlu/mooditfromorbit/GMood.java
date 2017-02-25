package com.assign1.brianlu.mooditfromorbit;

/**
 * Created by FENGYI on 2017-02-24.
 */

public class GMood extends Mood implements Moodable {
    public GMood(String message) {
        super(message);
    }

    @Override
    public boolean isImportant() {
        return false;
    }

    public String getMessage() {
        return this.message;
    }
}
