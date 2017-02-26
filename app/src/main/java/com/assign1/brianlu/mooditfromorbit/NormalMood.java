package com.assign1.brianlu.mooditfromorbit;

import java.util.ArrayList;

/**
 * Created by FENGYI on 2017-02-24.
 */

public class NormalMood extends Mood implements Moodable {
    public NormalMood(ArrayList<String> states, String message) {
        super(states, message);
    }



    public boolean isImportant() {
        return false;
    }

    public String getMessage() {
        return this.message;
    }
}
