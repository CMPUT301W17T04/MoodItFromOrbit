/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;

/**
 * Created by Gregory on 2017-03-06.
 */

public class ElasticSearchController {
    private static JestDroidClient client;

    /**
     * adds moods to elastic search
     */
    public static class AddMoodsTask extends AsyncTask<Mood, Void, Void> {

        @Override
        protected Void doInBackground(Mood... moods) {
            verifySettings();

            for (Mood mood : moods) {
                Index index = new Index.Builder(mood).index("cmput301w17t4").type("mood").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        mood.setId(result.getId());
                    }
                    else {
                        Log.i("Error", "Elastics was not able to to add the mood");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the moods");
                }

            }
            return null;
        }
    }

    /**
     * gets moods from elastic search
     */
    public static class GetMoodsTask extends AsyncTask<String, Void, MoodList> {
        @Override
        protected MoodList doInBackground(String... search_parameters) {
            verifySettings();

            MoodList moods = new MoodList();

            String query = "";

            if(search_parameters[0] != "") {
                query = "{\"query\" : {\"term\" : { \"username\" : \"" + search_parameters[0] + "\" }}}";
            }

            Log.d("search parameter", search_parameters[0]);
            // TODO Build the query
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t4")
                    .addType("mood")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    MoodList foundMoods = new MoodList(result.getSourceAsObjectList(Mood.class));
                    moods.merge(foundMoods);
                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return moods;
        }
    }

    /**
     * adds users to elastic search
     */
    public static class AddUsersTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {
                Log.d("testsing", user.getUserName());
                Index index = new Index.Builder(user).index("cmput301w17t4").type("user").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.d("usersetid", result.getId());
                        user.setId(result.getId());
                        Log.d("usersetid", user.getId());
                    }
                    else {
                        Log.i("Error", "Elastics was not able to to add the user");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the users");
                }

            }
            return null;
        }
    }

    /**
     * gets users from elastic search
     */
    public static class GetUsersTask extends AsyncTask<String, Void, UserList> {
        @Override
        protected UserList doInBackground(String... search_parameters) {
            verifySettings();

            String query = "";
            UserList users = new UserList();
            if(search_parameters[0] != "") {
                query = "{\"query\" : {\"term\" : { \"username\" : \"" + search_parameters[0] + "\" }}}";
            }

            Log.d("search parameters", search_parameters[0]);
            // TODO Build the query
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t4")
                    .addType("user")
                    .build();

            Log.d("search", search.toString());

            try {
                // TODO get the results of the query
                Log.d("testing", "before search");
                SearchResult result = client.execute(search);
                Log.d("testing", "after search");
                if (result.isSucceeded()){
                    Log.d("testing", "here");
                    Log.d("testing", "error here");
                    UserList foundUsers = new UserList(result.getSourceAsObjectList(User.class));
                    //UserList foundUsers = new UserList(result.getHits(User.class));

                    List<SearchResult.Hit<Map,Void>> hits = client.execute(search).getHits(Map.class);

                    int i = 0;
                    for(SearchResult.Hit hit : hits){

                        Map source = (Map)hit.source;
                        String id = (String)source.get(JestResult.ES_METADATA_ID);
                        foundUsers.getUser(i).setId(id);
                        i++;
                        Log.d("testing", id);

                    }

                    //UserList foundUsers = new UserList(hits);

                    Log.d("testing", "maybe");
                    users.merge(foundUsers);



                    //Log.d("testing", foundUsers.getUser(0).getId());
                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Log.d("Error", e.toString());
            }

            return users;
        }
    }


    public static class UpdateUsersTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {
                Log.d("testsing", user.getUserName());

                String query = "";


                query = "{\"doc\" : " + user.getGsonMoods() + "}";

                Log.d("userid", user.getId());
                Log.d("gson string", query);
                Update update = new Update.Builder(query)
                        .index("cmput301w17t4")
                        .type("user")
                        .id(user.getId())
                        .build();

                Log.d("search", update.toString());

                try {
                    // TODO get the results of the query
                    Log.d("testing", "before search");
                    client.execute(update);
                    Log.d("testing", "after search");
                    /*if (result.isSucceeded()){
                        Log.d("testing", "here");
                        UserList foundUsers = new UserList(result.getSourceAsObjectList(User.class));
                        users.merge(foundUsers);
                    }
                    else {
                        Log.i("Error", "The search query failed to find any tweets that matched");
                    }*/
                }
                catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                    Log.d("Error", e.toString());
                }

            }
            return null;
        }
    }



    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
