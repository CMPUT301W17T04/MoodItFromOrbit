package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by brianlu on 2017-02-24.
 */


public class DashBoard extends AppCompatActivity implements MView<MainModel>{



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);

        //used https://developer.android.com/training/appbar/setting-up.html#utility
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
    }


    public void update(MainModel mc){}




}
