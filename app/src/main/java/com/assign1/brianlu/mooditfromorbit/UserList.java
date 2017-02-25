package com.assign1.brianlu.mooditfromorbit;

import java.util.ArrayList;

/**
 * Created by brianlu on 2017-02-24.
 */

public class UserList {
    private ArrayList<User> users = new ArrayList<User>();
    public void add(User user){
        boolean add = true;
        for(int i = 0; i< users.size();i++){
            if(users.get(i).getUserName().equals(user.getUserName())){
                add = false;
                throw new IllegalArgumentException("User already exists!");
            }
        }
        if(add){
            users.add(user);
        }
    }

    public boolean hasUser (User user){
        boolean has = false;
        for(int i = 0; i< users.size();i++){
            if(users.get(i).getUserName().equals(user.getUserName())){
                has = true;
            }
        }
        return has;
    }

    public void deleteUser(User user){
        users.remove(user);
    }

    public User getUser(int index){
        return users.get(index);

    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public int getCount(){
        return this.users.size();
    }

}
