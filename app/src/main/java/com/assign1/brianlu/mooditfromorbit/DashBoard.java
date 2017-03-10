package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_mood:
                //switch to add mood activity
                Intent intent1 = new Intent(DashBoard.this, AddMood.class);
                startActivity(intent1);
                return true;

            case R.id.action_profile:
                MainController mc = MainApplication.getMainController();
                User user = mc.getMe();

                ElasticSearchController.UpdateUsersMoodTask updateUsersMoodTask = new ElasticSearchController.UpdateUsersMoodTask();
                updateUsersMoodTask.execute(user);

                Intent intent = new Intent(DashBoard.this, ProfileActivity.class);
                startActivity(intent);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dash_board_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        MenuItem sortItem = menu.findItem(R.id.action_sort);
        MenuView menuView =
                (MenuView) MenuItemCompat.getActionView(sortItem);

        return super.onCreateOptionsMenu(menu);
    }



    public void update(MainModel mc){
        // TODO code to redisplay the data
    }



}
