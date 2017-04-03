package com.assign1.brianlu.mooditfromorbit;

import java.util.ArrayList;

/**
 * contains following and requests
 * Created by Neil on 2017/3/11.
 */

public class FollowList {
    /**
     * Initial followList.
     */
    private ArrayList<String> followingList = new ArrayList<String>();
    private ArrayList<String> followerList = new ArrayList<String>();
    private ArrayList<String> pendingList =  new ArrayList<String>();
    private ArrayList<String> requestList = new ArrayList<>();

    /**
     * add to Following/Follower list
     * @param id
     */
    public void addFollowing (String id){
        followingList.add(id);
    }

    public void addFollower (String id){
         followerList.add(id);
    }

    public void addPending (String id)  {
         pendingList.add(id);
     }

     public void addRequest(String id){
         requestList.add(id);
     }

    public Boolean containsPending(String id){
         if(pendingList.contains(id)){
             return true;
         }
         else{
             return false;
         }
     }

    public Boolean containsFollowing(String id){
        if(followingList.contains(id)){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean containsFollower(String id){
        if(followerList.contains(id)){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean containsRequest(String id){
        if(requestList.contains(id)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * remove from Following.follower list
     * @param id
     */
    public void removeFollowing(String id){
        followingList.remove(id);
    }

    public void removeFollower(String id){
        followerList.remove(id);
    }

    public void removePending (String id) { pendingList.remove(id);}

    public void removeRequest(String id){
        requestList.remove(id);
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
    public ArrayList<String> getPending ()  {return pendingList;}
    public ArrayList<String> getRequest(){
        return requestList;
    }
}