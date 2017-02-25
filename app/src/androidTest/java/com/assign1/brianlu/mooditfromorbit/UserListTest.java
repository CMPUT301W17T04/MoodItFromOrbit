package com.assign1.brianlu.mooditfromorbit;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by brianlu on 2017-02-24.
 */

public class UserListTest extends ActivityInstrumentationTestCase2 {
    public UserListTest(){
        super(MoodMainActivity.class);

    }
    // test if has a user when added
    public void testHasUser(){
        UserList users = new UserList();
        User user = new User("blu1");

        users.add(user);

        assertTrue(users.hasUser(user));
    }

    // test get the full user list
    public void testGetUsers(){
        UserList users =new UserList();
        User user1 = new User("blu1");
        User user2 = new User("fmao1");
        User user3 = new User("cqtran");
        User user4 = new User("jcao5");
        User user5 = new User("gdbaker");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        ArrayList<User> users2 = new ArrayList<User>();
        users2 = users.getUsers();
        assertEquals(true, users.getUser(0) == users2.get(0));
        assertEquals(true, users.getUser(1) == users2.get(1));
        assertEquals(true, users.getUser(2) == users2.get(2));
        assertEquals(true, users.getUser(3) == users2.get(3));
        assertEquals(true, users.getUser(4) == users2.get(4));
    }
    // test add user to user list
    public void testGetUser(){
        UserList users = new UserList();
        User user = new User("blu1");
        users.add(user);
        User returnedUser = users.getUser(0);

        assertEquals(returnedUser.getUserName(),user.getUserName());
    }
    // test delete users
    public void testDeleteUser(){
        UserList users = new UserList();
        User user = new User("blu1");
        users.add(user);
        users.deleteUser(user);
        assertFalse(users.hasUser(user));
    }
    // test duplicate user not allowed to be added
    public void testDuplicateUsers(){
        UserList users = new UserList();
        User user1 = new User("blu1");
        User user2 = new User("fmao1");
        User user3 = new User("blu1");
        users.add(user1);
        users.add(user2);
        try{
            users.add(user3);
            fail();
        }catch(Exception e){
            assertTrue(users.hasUser(user3));
        }
    }
    // test user list not allow to have duplicate users
    public void testHasDuplicate(){
        UserList users = new UserList();
        User user1 = new User("blu1");
        User user2 = new User("blu1");
        users.add(user1);
        assertTrue(users.hasUser(user2));
    }
    // test user count in a user list
    public void testCountUsers(){
        UserList users = new UserList();
        User user1 = new User("blu1");
        User user2 = new User("fmao1");
        User user3 = new User("cqtran");
        User user4 = new User("jcao5");
        User user5 = new User("gdbaker");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        Integer count = 5;
        assertEquals(true, users.getCount() == count);
    }
    // test add followers
    public void testUserFollowing(){
        User user1 = new User("blu1");
        User user2 = new User("fmao1");
        User user3 = new User("cqtran");
        user1.setFollowing(user2);
        assertTrue(user1.hasFollower(user2));
        assertFalse(user1.hasFollower(user3));
    }
    // test add sharers
    public void testUserSharing(){
        User user1 = new User("blu1");
        User user2 = new User("fmao1");
        User user3 = new User("cqtran");
        user1.setSharing(user2);
        assertTrue(user1.hasSharer(user2));
        assertFalse(user1.hasSharer(user3));
    }
    // check duplicate follower not allowed
    public void testDuplicateFollower(){
        User user = new User("fmal1");
        User user1 = new User("blu1");
        User user2 = new User("blu1");
        user.setFollowing(user1);
        try{
            user.setFollowing(user2);
            fail();
        }catch (Exception e){
            assertTrue(user.hasFollower(user2));
        }
    }
    // check duplicate sharer not allowed
    public void testDuplicateSharer(){
        User user = new User("fmal1");
        User user1 = new User("blu1");
        User user2 = new User("blu1");
        user.setSharing(user1);
        try{
            user.setSharing(user2);
            fail();
        }catch (Exception e){
            assertTrue(user.hasSharer(user2));
        }
    }
    // check if sharing count returns correct shared users
    public void testSharingCount(){
        User user1 = new User("blu1");
        User user2 = new User("fmao1");
        User user3 = new User("cqtran");
        User user4 = new User("jcao5");
        User user5 = new User("gdbaker");
        user1.setSharing(user2);
        user1.setSharing(user3);
        user1.setSharing(user4);
        user1.setSharing(user5);
        assertEquals(user1.getSharingCount(),4);
    }
    // check if the following count returns correct followed users
    public void testFollowingCount(){
        User user1 = new User("blu1");
        User user2 = new User("fmao1");
        User user3 = new User("cqtran");
        User user4 = new User("jcao5");
        User user5 = new User("gdbaker");
        user1.setFollowing(user2);
        user1.setFollowing(user3);
        user1.setFollowing(user4);
        user1.setFollowing(user5);
        assertEquals(user1.getFollowingCount(),4);
    }
    // test if a user can remove a following
    public void testDeleteFollowing(){
        User user1 = new User("blu1");
        User user2 = new User("fmao1");
        user1.setFollowing(user2);
        assertTrue(user1.hasFollower(user2));
        user1.deleteFollowing(user2);
        assertFalse(user1.hasFollower(user2));
    }
    // test if a user can remove a sharing
    public void testDeleteSharing(){
        User user1 = new User("blu1");
        User user2 = new User("fmao1");
        user1.setSharing(user2);
        assertTrue(user1.hasSharer(user2));
        user1.deleteSharing(user2);
        assertFalse(user1.hasSharer(user2));
    }

}
