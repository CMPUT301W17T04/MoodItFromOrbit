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

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Gregory on 2017-03-06.
 */

public class ElasticSearchEmotionController {
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

            String query = "{\"query\" : {\"term\" : { \"emotion\" : \"" + search_parameters[0] + "\" }}}";
            Log.d("sterisd", search_parameters[0]);
            // TODO Build the query
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t4")
                    .addType("mood")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    List<Mood> foundMoods = result.getSourceAsObjectList(Mood.class);
                    moods.addAll(foundMoods);
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
