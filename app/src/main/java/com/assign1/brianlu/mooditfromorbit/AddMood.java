/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AddMood extends AppCompatActivity implements MView<MainModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);

        MainController mc = MainApplication.getMainController();

        mc.startLocationListen(this);

/*        Spinner s = (Spinner) findViewById(R.id.emotions);

        s.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // do something with selected item.
                // incomplete
                //Toast.makeText(AddMood.this, parent.getselected)
            }
        });*/



        // when done button is pressed
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO get info from input
                //this creates a test mood and adds it
                MainController mc = MainApplication.getMainController();
                Mood mood = new Mood(mc.getEmotion("Happy"), mc.getMe());
                mood.setSocialSituation("With One Other");
                mood.setMessage("A Test Message");


                // Remove the listener you previously added
                mc.stopLocationListener();
                Location moodLocation = mc.getLocation();

                mood.setLocation(moodLocation);

                Log.d("location", moodLocation.toString());
                mc.addNewMood(mood);
                finish();
            }
        });







        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
    }
    public void update(MainModel mc){}
}
