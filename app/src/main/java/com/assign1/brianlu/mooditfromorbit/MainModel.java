/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.util.Log;

/**
 * Created by Gregory on 2017-03-06.
 */

public class MainModel extends MModel<MView> {
    static private UserList users = null;

    MainModel(){
        super();
        pullUsersFromServer();
    }

    private void pullUsersFromServer(){
        ElasticSearchController.GetUsersTask getUsersTask = new ElasticSearchController.GetUsersTask();
        getUsersTask.execute("");

        try {
            this.users = getUsersTask.get();
        } catch (Exception e){
            Log.i("Error", "Failed to get the users from the async object");
        }
        //Log.d("users", users.getUser(0).getUserName());
    }

    public UserList getUsers() {
        return users;
    }

    public void addUser(User user){
        Log.d("testing", user.getUserName());
        users.add(user);
        ElasticSearchController.AddUsersTask addUsersTask = new ElasticSearchController.AddUsersTask();
        addUsersTask.execute(user);
    }

    public boolean checkForUser(User user){
        return users.hasUser(user);
    }
}
