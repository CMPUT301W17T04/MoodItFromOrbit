package com.assign1.brianlu.mooditfromorbit;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * tests the userList
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



}
