package com.assign1.brianlu.mooditfromorbit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * contains functions for all activities
 * Created by Gregory on 2017-03-31.
 */

public abstract class CustomAppCompatActivity extends AppCompatActivity {
    protected Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
    }

    /**
     * checks the online status of the app
     */
    protected void checkOnlineStatus(){
        TextView errorBox = (TextView) findViewById(R.id.error);
        if(!MainApplication.getConnectedToServer()){
            errorBox.setVisibility(View.VISIBLE);
        }
        else{
            errorBox.setVisibility(View.GONE);
        }
    }

    /**
     * check if there are follow requests, throw popup if there are
     */
    protected void checkForRequests(){
        MainController mc = MainApplication.getMainController();
        if(!mc.getMe().getPendingRequests().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(CustomAppCompatActivity.this);
            builder.setMessage("You have new follower requests")
                    .setTitle("Request");

            builder.setPositiveButton("Take me to requests!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(CustomAppCompatActivity.this, AcceptFollowerActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            // Set other dialog properties


            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }




}
