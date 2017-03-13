package com.assign1.brianlu.mooditfromorbit;

import java.util.ArrayList;

/**
 * Created by Neil on 2017/3/11.
 */

public class FollowList {
    /**
     * Initial followList.
     */
    private ArrayList<String> followingList = new ArrayList<String>();
    private ArrayList<String> followerList = new ArrayList<String>();

    /**
     * add to Following/Follower list
     * @param userName
     */
    public void addFollowing (String userName){
        followingList.add(userName);
    }

     public void addFollower (String userName){
         followerList.add(userName);
    }

    /**
     * remove from Following.follower list
     * @param userName
     */
    public void removeFollowing(String userName){
        followingList.remove(userName);
    }

    public void removeFollower(String userName){
        followerList.remove(userName);
    }

    /**
     * return following/follower list
     * @return
     */
    public ArrayList<String> getFollowing (){
        return followingList;
    }
    public ArrayList<String> getFollower (){
        return followerList;
    }
}