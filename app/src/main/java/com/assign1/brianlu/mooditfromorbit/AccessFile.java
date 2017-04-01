package com.assign1.brianlu.mooditfromorbit;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Gregory on 2017-03-31.
 */

abstract class AccessFile extends AsyncTask<Context, Void, Void>{
    private static final String FILENAME = "file.sav";

    /**
     * used some code from lonelyTwitter
     * loads json data from a file
     */
    protected ArrayList<UpdateMoods> loadFromFile(Context context) {
        ArrayList<UpdateMoods> updateMoods;
        try {

            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Mood.class, new MoodSerializer());
            gsonBuilder.registerTypeAdapter(Mood.class, new MoodDeserializer()).create();
            Gson gson = gsonBuilder.create();

            // Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //2017-01-24 18:19

            Type listType = new TypeToken<ArrayList<UpdateMoods>>(){}.getType();

            updateMoods = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            updateMoods = new ArrayList<>();
        }
        return updateMoods;
    }

    /**
     * used some code from lonelyTwitter
     *
     * saves json data in a file
     */
    protected void saveInFile(Context context, ArrayList<UpdateMoods> updateMoods) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Mood.class, new MoodSerializer());
            gsonBuilder.registerTypeAdapter(Mood.class, new MoodDeserializer()).create();
            Gson gson = gsonBuilder.create();

            String json = gson.toJson(updateMoods);
            json = json.replace("\\\"", "\"");
            json = json.replace("}\"", "}");
            json = json.replace("\"{", "{");

            Log.d("json file", json);
            out.write(json);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO: Handle the Exception properly later
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
