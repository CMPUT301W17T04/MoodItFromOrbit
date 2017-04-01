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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
 * this class is our elastic search controller.
 * It allows up to upload data to the server
 * and pull data down from the server.
 * 
 * Created by Gregory on 2017-03-06.
 */

public class ElasticSearchController {
    private static JestDroidClient client;

    /**
     * adds users to elastic search
     */
    public static class AddUsersTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {

                Index index = new Index.Builder(user).index("cmput301w17t4").type("user").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    MainApplication.setConnectedToServer(true);
                    if (result.isSucceeded()) {

                        user.setId(result.getId());

                    }
                    else {
                        Log.i("Error", "Elastics was not able to to add the user");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the users");
                    MainApplication.setConnectedToServer(false);
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

            // TODO Build the query
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t4")
                    .addType("user")
                    .build();

            try {
                // TODO get the results of the query

                SearchResult result = client.execute(search);
                MainApplication.setConnectedToServer(true);

                if (result.isSucceeded()){

                    UserList foundUsers = new UserList(result.getSourceAsObjectList(User.class));

                    //taken from http://stackoverflow.com/questions/33352798/elasticsearch-jest-client-how-to-return-document-id-from-hit
                    // March 7, 2017 10:00pm
                    List<SearchResult.Hit<Map,Void>> hits = result.getHits(Map.class);

                    int i = 0;
                    for(SearchResult.Hit hit : hits){

                        Map source = (Map)hit.source;

                        String id = (String)source.get(JestResult.ES_METADATA_ID);
                        foundUsers.getUser(i).setId(id);
                        i++;

                    }


                    users.merge(foundUsers);


                }
                else {
                    Log.i("Error", "The search query failed to find any users that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Log.d("Error", e.toString());
                MainApplication.setConnectedToServer(false);
            }

            return users;
        }
    }


    /**
     * updates moodslist on the server
     */
    public static class UpdateUsersMoodTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {

                String query = "";


                query = "{\"doc\" : { \"type\" : \"nested\", \"moods\" : " + user.getGsonMoods() + "}}";

                Update update = new Update.Builder(query)
                        .index("cmput301w17t4")
                        .type("user")
                        .id(user.getId())
                        .build();

                try {

                    // TODO get the results of the query

                    client.execute(update);
                    MainApplication.setConnectedToServer(true);


                }
                catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to upload to the elasticsearch server!");
                    Log.d("Error", e.toString());
                    MainApplication.setConnectedToServer(false);


                }
                MainApplication.setDone(true);
                Log.d("premain", MainApplication.getDone().toString());
            }

            return null;
        }
    }

    /**
     * updates the users following and followers on the server
     */
    public static class UpdateUsersFollowListTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {

                String query = "";


                query = "{\"doc\" : { \"type\" : \"nested\", \"followList\" : " + user.getGsonFollowList() + "}}";

                Update update = new Update.Builder(query)
                        .index("cmput301w17t4")
                        .type("user")
                        .id(user.getId())
                        .build();

                try {
                    // TODO get the results of the query

                    client.execute(update);
                    MainApplication.setConnectedToServer(true);

                }
                catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                    Log.d("Error", e.toString());
                    MainApplication.setConnectedToServer(false);
                }

            }
            return null;
        }
    }



    public static void verifySettings() {
        if (client == null) {

            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Mood.class, new MoodDeserializer()).create();


            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);

            client = (JestDroidClient) factory.getObject();

            // referenced http://codenav.org/code.html?project=/io/searchbox/jest-droid/0.1.0&path=/Source%20Packages/com.searchly.jestdroid/JestClientFactory.java
            client.setGson(gson);
        }
    }
}
