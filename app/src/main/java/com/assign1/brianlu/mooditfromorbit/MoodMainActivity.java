package com.assign1.brianlu.mooditfromorbit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MoodMainActivity extends AppCompatActivity {
    private static final String FILENAME = "mooduser.sav";
//    private EditTex

    private ArrayList<User> users = new ArrayList<User>();
    private ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
