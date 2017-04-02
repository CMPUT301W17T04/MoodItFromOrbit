package com.assign1.brianlu.mooditfromorbit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.TimeUnit;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class AcceptFollowerActivity extends CustomAppCompatActivity implements MView<MainModel>{

    private ListView usersListView;
    private UsersAdapter adapter;
    private SwipeRefreshLayout refreshLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_follower);

        usersListView = (ListView) findViewById(R.id.userListView);

        //used https://developer.android.com/training/appbar/setting-up.html#utility
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //back button
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        ab.setTitle("Requests");
        checkOnlineStatus();


        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainController mc = MainApplication.getMainController();
                User clickedUser = mc.getMe().getRequested().getUser(position);
                mc.addFollower(clickedUser);
                updateList();
            }
        });

        // taken from https://developer.android.com/training/swipe/respond-refresh-request.html
        //March 10, 2017 4:45pm
        /**
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.

                        updateList();
                        refreshLayout.setRefreshing(false);
                    }
                }
        );
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        MainController mc = MainApplication.getMainController();
        adapter = new UsersAdapter(this, mc.getMe().getRequested().getUsers());
        usersListView.setAdapter(adapter);
        checkOnlineStatus();
        mc.generateRequested();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
        Log.d("this is dead", "destroyed");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_mood:
                //switch to add mood activity
                Intent intent1 = new Intent(AcceptFollowerActivity.this, AddMood.class);
                startActivity(intent1);
                adapter.notifyDataSetChanged();
                checkOnlineStatus();
                return true;

            case R.id.action_dashboard:
                Intent intent = new Intent(AcceptFollowerActivity.this,DashBoard.class);
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
        getMenuInflater().inflate(R.menu.accept_menu, menu);

        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }


    public void update(MainModel m ){
        //TODO code to redisplay the data
        adapter.notifyDataSetChanged();
        checkOnlineStatus();
    }

    /**
     * updates data in adapter
     */
    public void updateList(){
        MainController mc = MainApplication.getMainController();
        mc.pullUsers();
        adapter.notifyDataSetChanged();
        checkOnlineStatus();
        mc.generateRequested();

    }
}