package com.assign1.brianlu.mooditfromorbit;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * is the invoker in a command pattern structure
 * asks the commands to execute if internet is available
 * Created by Gregory on 2017-03-29.
 */

public class ServerUploader {
    private ArrayList<UpdateServer> communicationsList = new ArrayList<>();

    void addCommunication(UpdateServer updateServer){
        communicationsList.add(updateServer);
    }

    void sendCommunications(){

        ArrayList<UpdateServer> remove = new ArrayList<>();

        for(UpdateServer updateServer : communicationsList){
            updateServer.execute();

            if(MainApplication.getConnectedToServer()){
                remove.add(updateServer);
            }
        }
        communicationsList.removeAll(remove);
        remove.clear();
    }
}
