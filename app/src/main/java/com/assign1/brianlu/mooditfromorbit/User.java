package com.assign1.brianlu.mooditfromorbit;

import java.util.ArrayList;

/**
 * Created by brianlu on 2017-02-23.
 */

public class User {
    protected Integer userID;
    private String userName;
    private ArrayList moodList;

    public void CreateUser(String userName, Integer userID){
        this.userName = userName;
        this.userID = userID;
//        this.userID = getUniqueUserID();
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }


}
