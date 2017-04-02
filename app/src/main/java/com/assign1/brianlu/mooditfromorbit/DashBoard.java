package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * this is the main view which will show the moods of people that the user is following
 * Created by brianlu on 2017-02-24.
 *
 *
 */


public class DashBoard extends CustomAppCompatActivity implements MView<MainModel>{


    private ListView moodListView;
    private MoodListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private String searchMood;
    private String searchText;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);

        moodListView = (ListView) findViewById(R.id.dashboardListView);

        //used https://developer.android.com/training/appbar/setting-up.html#utility
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        ActionBar ab = getSupportActionBar();

        ab.setTitle("Dashboard");


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


        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        MainController mc = MainApplication.getMainController();
        checkOnlineStatus();

        adapter = new MoodListAdapter(this, mc.getFollowingMoods().getMoods());
        moodListView.setAdapter(adapter);

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
                Intent intent1 = new Intent(DashBoard.this, AddMood.class);
                startActivity(intent1);
                return true;

            case R.id.action_map:
                Intent intent2 = new Intent(DashBoard.this, DashBoardMap.class);
                startActivity(intent2);
                return true;

            case R.id.action_profile:
                Intent intent = new Intent(DashBoard.this, ProfileActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_filter:
                showFilterDialog();
                return true;

            case R.id.menu_refresh:
                refreshLayout.setRefreshing(true);
                updateList();
                refreshLayout.setRefreshing(false);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void showFilterDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = DashBoard.this.getLayoutInflater();
        final View v_iew=inflater.inflate(R.layout.filter_view, null);
        builder.setView(v_iew)
                .setPositiveButton(R.string.filter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText stext = (EditText) v_iew.findViewById(R.id.searchText);
                        EditText smood = (EditText) v_iew.findViewById(R.id.searchMood);
                        searchText = stext.getText().toString();
                        searchMood = smood.getText().toString();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dash_board_menu, menu);


        MenuItem filterItem = menu.findItem(R.id.action_filter);
        MenuView menuView =
                (MenuView) MenuItemCompat.getActionView(filterItem);

        return super.onCreateOptionsMenu(menu);
    }



    public void update(MainModel mm){
        updateList();

    }

    /**
     * updates data in adapter
     */
    public void updateList(){
        MainController mc = MainApplication.getMainController();
        mc.generateFollowingMoods();
        mc.generateRequested();
        adapter.notifyDataSetChanged();
        checkOnlineStatus();

    }
}
