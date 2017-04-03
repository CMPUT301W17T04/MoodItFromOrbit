package com.assign1.brianlu.mooditfromorbit;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * is the invoker in a command pattern structure
 * asks the commands to execute if internet is available
 * Created by Gregory on 2017-03-29.
 */

public class ServerUploader extends AccessFile {
    //TODO: still need to add call to upload in more places
    void addMoodsCommunication(UpdateMoods updateMoods, Context context){

        ArrayList<UpdateMoods> communicationsList = loadFromFile(context);

        communicationsList.add(updateMoods);

        saveInFile(context, communicationsList);

    }


    public void sendCommunication(Context context){
        ArrayList<UpdateMoods> communicationsList = loadFromFile(context);
        ArrayList<UpdateMoods> remove = new ArrayList<>();

        for(UpdateMoods updateMood : communicationsList){
            updateMood.execute();

            if(MainApplication.getConnectedToServer()){
                remove.add(updateMood);
                Log.d("connected", "upload success");
            }
        }
        communicationsList.removeAll(remove);
        remove.clear();
        saveInFile(context, communicationsList);
    }

}
