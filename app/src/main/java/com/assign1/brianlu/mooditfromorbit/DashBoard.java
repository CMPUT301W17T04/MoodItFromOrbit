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
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

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
    private Toolbar myToolbarLow;

    private ArrayList<Mood> selfMoods;
    private boolean checked = false;
    private Spinner spinner;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);

        moodListView = (ListView) findViewById(R.id.dashboardListView);

        //used https://developer.android.com/training/appbar/setting-up.html#utility
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        ab.setTitle("Dashboard");

        setupEvenlyDistributedToolbar();
        checkForRequests();

        myToolbarLow.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        Intent intent = new Intent(DashBoard.this,ProfileActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.action_all:
                        // temporary because the main following function is not implemented
                        Intent intent3 = new Intent(DashBoard.this, FollowSomeoneActivity.class);
                        startActivity(intent3);
                        return true;

                    case R.id.action_requests:
                        // temporary because the main following function is not implemented
                        Intent intent4 = new Intent(DashBoard.this, AcceptFollowerActivity.class);
                        startActivity(intent4);
                        return true;

                    default:
                        return false;
                }
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

            case R.id.action_filter:
                showFilterDialog();
                return true;

            case R.id.action_logout:
                Intent intent5 = new Intent(DashBoard.this, MoodMainActivity.class);
                startActivity(intent5);
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
        mc.generateRequested(context);
        checkForRequests();
        adapter.notifyDataSetChanged();
        checkOnlineStatus();

    }

    // reference: "http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android"
    // 24 March, 2017
    private void showFilterDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = DashBoard.this.getLayoutInflater();
        View view=inflater.inflate(R.layout.filter_view, null);

        final EditText stext = (EditText) view.findViewById(R.id.searchText);
        if(searchText != null){
            stext.setText(searchText);
        }
        spinner = (Spinner) view.findViewById(R.id.emotions);
        /*if(searchMood != null){
            smood.setText(searchMood);
        }*/
        final CheckBox s_sort = (CheckBox) view.findViewById(R.id.recentWeek);
        s_sort.setChecked(checked);
        builder.setView(view)
                .setPositiveButton(R.string.filter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        searchText = stext.getText().toString();
                        searchMood = spinner.getSelectedItem().toString();

                        getAllSelfMoods();
                        if(s_sort.isChecked()){
                            checked = true;
                            selfMoods = filterByWeek(selfMoods);
                        }else{
                            checked = false;
                        }
                        if(!searchMood.equals("Search Mood")){
                            selfMoods = filterByMood(selfMoods, searchMood);
                        }
                        if(!searchText.equals("")){
                            selfMoods = filterByText(selfMoods,searchText);
                        }
                        adapter = new MoodListAdapter(DashBoard.this, selfMoods);
                        moodListView.setAdapter(adapter);
                        checkOnlineStatus();


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        onStart();
                        dialog.cancel();
                    }
                });
        builder.show();
    }


    private void getAllSelfMoods(){
        Log.i("getcalled","called once");
        MainController mc = MainApplication.getMainController();
//        mc.generateRequested();
        selfMoods = mc.getFollowingMoods().getMoods();
    }

    private ArrayList<Mood> filterByMood(ArrayList<Mood> moods, String emotion){
        MoodList sortedMoods = new MoodList();
        MainController mc = MainApplication.getMainController();
        sortedMoods.merge(new MoodList(moods));
        if(mc.getEmotion(emotion) != null){
            sortedMoods.sortByEmotion(mc.getEmotion(emotion));
            if(sortedMoods.getCount() == 0){
                Toast.makeText(getBaseContext(), "There are now such moods!",
                        Toast.LENGTH_LONG).show();
            }
            return sortedMoods.getMoods();
        }else {
            Toast.makeText(getBaseContext(), "Invalid mood type!",
                    Toast.LENGTH_LONG).show();
            return new ArrayList<Mood>();
        }
    }

    private ArrayList<Mood> filterByWeek(ArrayList<Mood> moods){
        MoodList sortedMoods = new MoodList();
        sortedMoods.merge(new MoodList(moods));
        sortedMoods.sortByRecentWeek();
        return sortedMoods.getMoods();
    }


    private ArrayList<Mood> filterByText(ArrayList<Mood> moods, String text){
        MoodList sortedMoods = new MoodList();
        sortedMoods.merge(new MoodList(moods));
        sortedMoods.sortByWord(text);
        return sortedMoods.getMoods();
    }




    /**
     * taken from http://stackoverflow.com/questions/26489079/evenly-spaced-menu-items-on-toolbar
     * April 2, 2017 1:29 AM
     *
     * We use this in order to evenly distribute the buttons
     *
     * This method will take however many items you have in your
     * menu/menu_main.xml and distribute them across your devices screen
     * evenly using a Toolbar. Enjoy!!
     */
    public void setupEvenlyDistributedToolbar(){
        // Use Display metrics to get Screen Dimensions
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        // Toolbar
        myToolbarLow = (Toolbar) findViewById(R.id.my_toolbarLow);
        // Inflate your menu
        myToolbarLow.inflateMenu(R.menu.dash_board_menu_low);

        // Add 10 spacing on either side of the toolbar
        myToolbarLow.setContentInsetsAbsolute(10, 10);

        // Get the ChildCount of your Toolbar, this should only be 1
        int childCount = myToolbarLow.getChildCount();
        // Get the Screen Width in pixels
        int screenWidth = metrics.widthPixels;

        // Create the Toolbar Params based on the screenWidth
        Toolbar.LayoutParams toolbarParams = new Toolbar.LayoutParams(screenWidth, ActionBar.LayoutParams.WRAP_CONTENT);

        // Loop through the child Items
        for(int i = 0; i < childCount; i++){
            // Get the item at the current index
            View childView = myToolbarLow.getChildAt(i);
            // If its a ViewGroup
            if(childView instanceof ViewGroup){
                // Set its layout params
                childView.setLayoutParams(toolbarParams);
                // Get the child count of this view group, and compute the item widths based on this count & screen size
                int innerChildCount = ((ViewGroup) childView).getChildCount();
                int itemWidth  = (screenWidth / innerChildCount);
                // Create layout params for the ActionMenuView
                ActionMenuView.LayoutParams params = new ActionMenuView.LayoutParams(itemWidth, ActionBar.LayoutParams.WRAP_CONTENT);
                // Loop through the children
                for(int j = 0; j < innerChildCount; j++){
                    View grandChild = ((ViewGroup) childView).getChildAt(j);
                    if(grandChild instanceof ActionMenuItemView){
                        // set the layout parameters on each View
                        grandChild.setLayoutParams(params);
                    }
                }
            }
        }
    }
}
