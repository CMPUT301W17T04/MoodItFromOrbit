package com.assign1.brianlu.mooditfromorbit;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * is the invoker in a command pattern structure
 * asks the commands to execute if internet is available
 * Created by Gregory on 2017-03-29.
 */

public class ServerUploader {
    private ArrayList<AsyncTask<User, Void, Void>> communicationsList = new ArrayList<>();
    private static User me = null;

    void addCommunication(AsyncTask<User, Void, Void> communication){
        communicationsList.add(communication);
    }

    void sendCommunications(){
        if(me == null){
            me = MainApplication.getMainController().getMe();
        }

        for(AsyncTask<User, Void, Void> communication : communicationsList){
            communication.execute(me);
        }
        communicationsList.clear();
    }
}
