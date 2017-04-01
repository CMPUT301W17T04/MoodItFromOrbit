/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.app.Application;
import android.content.Context;

/**
 * this is where we store all of our local data
 *
 * Created by Gregory on 2017-03-06.
 *
 *
 */


public class MainApplication extends Application {
    transient private static Boolean connectedToServer = true;
    transient private static MainModel model = null;
    transient private static MainController controller = null;
    transient private static Boolean done = false;

    public static Boolean getDone() {
        return done;
    }

    public static void setDone(Boolean done) {
        MainApplication.done = done;
    }

    public static Boolean getConnectedToServer() {
        return connectedToServer;
    }

    public static void setConnectedToServer(Boolean connectedToServer) {
        MainApplication.connectedToServer = connectedToServer;
    }

    public static MainModel getMainModel(){
        if(model == null){
            model = new MainModel();
        }
        return model;
    }

    public static MainController getMainController(){
        if(controller == null){
            controller = new MainController(getMainModel());
        }
        return controller;
    }
}
