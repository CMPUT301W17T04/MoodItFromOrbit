package com.assign1.brianlu.mooditfromorbit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Gregory on 2017-03-31.
 */

public abstract class CustomAppCompatActivity extends AppCompatActivity {

    protected void checkOnlineStatus(){
        Log.d("checkstatus", MainApplication.getConnectedToServer().toString());
        TextView errorBox = (TextView) findViewById(R.id.error);
        if(!MainApplication.getConnectedToServer()){
            errorBox.setVisibility(View.VISIBLE);
        }
        else{
            errorBox.setVisibility(View.GONE);
        }
    }




}
