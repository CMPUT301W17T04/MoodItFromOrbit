/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import java.util.ArrayList;

/**
 * Created by Gregory on 2017-03-06.
 */

public class MainController implements MController {
    MainModel mm = null;

    public MainController(MainModel mm){
        this.mm = mm;
    }

    public UserList getUsers() {
        return mm.getUsers();
    }

    public void addUser(User user){
        mm.addUser(user);
    }

    public boolean checkForUser(User user){
        return mm.checkForUser(user);
    }

    public ArrayList<Emotion> getEmotions(){
        return mm.getEmotions();
    }
}
