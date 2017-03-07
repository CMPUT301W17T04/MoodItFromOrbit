/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

/**
 * Created by Gregory on 2017-03-06.
 */

public class MainController extends MModel<MView> {
    static private UserList users = new UserList();

    public static UserList getUsers() {
        return users;
    }

    public boolean checkForUser(User user){
        return users.hasUser(user);
    }
}
