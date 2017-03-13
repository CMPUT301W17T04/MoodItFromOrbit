package com.assign1.brianlu.mooditfromorbit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Neil on 2017/3/12.
 */

/* I had to comment out to allow the code to compile
public class FollowActivity extends AppCompatActivity {
    private ListView followList;

    private FollowList follower;
    private FollowList following;
    private FollowAdapter followerAdapter;
    private FollowAdapter followingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        followList = (ListView) findViewById(R.id.FollowListView);

        Button Follower=(Button)findViewById(R.id.follower1);
        Button Following=(Button)findViewById(R.id.following1);

        Follower.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                followList.setAdapter(followerAdapter);
            }
        });

        Following.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                followList.setAdapter(followingAdapter);
            }

        });
    }
}*/

