package com.assign1.brianlu.mooditfromorbit;

import java.util.ArrayList;

/**
 * Created by FENGYI on 2017-02-24.
 */

public class ImportantMood extends Mood implements Moodable {
    public ImportantMood(ArrayList<String> states, String message) {
        super(states,message);
    }


    public boolean Important() {
        return true;
    }

    public String getMessage() {
        return "IMPORTANT!! " + this.message;
    }
}
