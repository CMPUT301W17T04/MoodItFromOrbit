package com.assign1.brianlu.mooditfromorbit;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brianlu on 2017-02-24.
 *
 * this class contains an ArrayList of users.
 */

public class UserList{
    private ArrayList<User> users = new ArrayList<User>();

    public UserList(){
    }

    /**
     * adds users that
     * @param foundUsers users that are added
     */
    public UserList(List foundUsers){
        users.addAll(foundUsers);
    }

    /**
     * adds user to users if not already there
     * @param user user to be added
     */
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

    /**
     * checks if user is in users
     * @param user user to check for
     * @return true or false
     */
    public boolean hasUser (User user){
        boolean has = false;
        for(int i = 0; i< users.size();i++){
            if(this.users.get(i).getUserName().equals(user.getUserName())){
                has = true;
            }
        }
        return has;
    }

    public void merge(UserList foundUsers){
        users.addAll(foundUsers.getUsers());
    }

    public void deleteUser(User user){
        if(hasUser(user)){
            this.users.remove(user);
        }
    }

    public User getUser(int index){
        return this.users.get(index);
    }

    /**
     * returns user that matches username
     * @param userName username to check for
     * @return user
     */
    public User getUserByName(String userName){
        for(User user : users){

            if(user.getUserName().equals(userName)){
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public int getCount(){
        return this.users.size();
    }

}
