/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.app.Application;

/**
 * Created by Gregory on 2017-03-06.
 */

public class MoodApplipcation extends Application {

    transient private static MoodList moods;

    public static MoodList getMoodList(){
        if(moods == null){
            moods = new MoodList();
        }
        return moods;
    }

    transient private static UserList users;

    public static UserList getUserList(){
        if(users == null){
            users = new UserList();
        }
        return users;
    }
}
