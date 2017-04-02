/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * this activity class displays the current users mood history
 */
public class ProfileActivity extends CustomAppCompatActivity implements MView<MainModel>{

    private ListView moodListView;
    private MoodListAdapter adapter;
    private String searchMood;
    private String searchText;
    private Toolbar myToolbarLow;

    private ArrayList<Mood> selfMoods;
    private SwipeRefreshLayout refreshLayout;
    int chronOder;
    int filterRange;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        moodListView = (ListView) findViewById(R.id.profileListView);
        onStart();
        //used https://developer.android.com/training/appbar/setting-up.html#utility
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        /*myToolbarLow = (Toolbar) findViewById(R.id.my_toolbarLow);
        myToolbarLow.inflateMenu(R.menu.profile_menu_low);*/

        setupEvenlyDistributedToolbar();
        MainController mc = MainApplication.getMainController();

        //back button
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        ab.setTitle("Profile");


        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);

        myToolbarLow.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_dashboard:
                        Intent intent = new Intent(ProfileActivity.this,DashBoard.class);
                        startActivity(intent);
                        return true;

                    case R.id.action_all:
                        // temporary because the main following function is not implemented
                        Intent intent3 = new Intent(ProfileActivity.this, FollowSomeoneActivity.class);
                        startActivity(intent3);
                        return true;

                    case R.id.action_requests:
                        // temporary because the main following function is not implemented
                        Intent intent4 = new Intent(ProfileActivity.this, AcceptFollowerActivity.class);
                        startActivity(intent4);
                        return true;

                    default:
                        return false;
                }
            }
        });

        moodListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent3 = new Intent(ProfileActivity.this, EditMood.class);
                intent3.putExtra("moodId",position);
                startActivity(intent3);
            }
        });



    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        updateList();
        MainController mc = MainApplication.getMainController();
        mc.generateRequested();
        selfMoods = mc.getMe().getMoods().getMoods();
        adapter = new MoodListAdapter(this, selfMoods);
        moodListView.setAdapter(adapter);
        checkOnlineStatus();

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
                Intent intent1 = new Intent(ProfileActivity.this, AddMood.class);
                startActivity(intent1);
                MainController mc = MainApplication.getMainController();
                Log.i("how many mood?",Integer.toString(mc.getMe().getMoods().getCount()));
                updateList();
                Log.i("after add mood","come back");

                return true;

            case R.id.action_map:

                Intent intent2 = new Intent(ProfileActivity.this, MapActivity.class);
                startActivity(intent2);
                return true;

            case R.id.action_dashboard:
                Intent intent = new Intent(ProfileActivity.this,DashBoard.class);
                startActivity(intent);
                return true;


            case R.id.action_filter:
                showFilterDialog();
                return true;

            case R.id.action_all_users:
                // temporary because the main following function is not implemented
                Intent intent3 = new Intent(ProfileActivity.this, FollowSomeoneActivity.class);
                startActivity(intent3);
                return true;

            case R.id.action_accept_requests:
                // temporary because the main following function is not implemented
                Intent intent4 = new Intent(ProfileActivity.this, AcceptFollowerActivity.class);
                startActivity(intent4);
                return true;

            case R.id.action_logout:
                Intent intent5 = new Intent(ProfileActivity.this, MoodMainActivity.class);
                startActivity(intent5);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void showFilterDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View view=inflater.inflate(R.layout.filter_view, null);
        final EditText stext = (EditText) view.findViewById(R.id.searchText);
        if(searchText != null){stext.setText(searchText);}
        final EditText smood = (EditText) view.findViewById(R.id.searchMood);
        if(searchMood != null){smood.setText(searchMood);}
        builder.setView(view)
                .setPositiveButton(R.string.filter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        searchText = stext.getText().toString();
                        searchMood = smood.getText().toString();

                        CheckBox s_sort = (CheckBox) view.findViewById(R.id.recentWeek);
//                        String searchTime = s_timefilter.getSelectedItem().toString();
//                        String sort = s_sort.getSelectedItem().toString();
                        getAllSelfMoods();
//                        ArrayList<Mood> newSelfMoods = new ArrayList<>();
                        if(!searchMood.equals("")){
//                            newSelfMoods.addAll(filterByMood(selfMoods, searchMood));
                            selfMoods = filterByMood(selfMoods, searchMood);
                        }
                        if(!searchText.equals("")){
//                            newSelfMoods.addAll(filterByText(selfMoods,searchText));
                            selfMoods = filterByText(selfMoods,searchText);
                        }






                        adapter = new MoodListAdapter(ProfileActivity.this, selfMoods);
                        moodListView.setAdapter(adapter);
                        checkOnlineStatus();





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


    private void getAllSelfMoods(){
        Log.i("getcalled","called once");

        MainController mc = MainApplication.getMainController();
        mc.generateRequested();
        selfMoods = mc.getMe().getMoods().getMoods();
    }

    private ArrayList<Mood> filterByMood(ArrayList<Mood> moods, String emotion){
        MoodList sortedMoods = new MoodList();
        MainController mc = MainApplication.getMainController();
        sortedMoods.merge(new MoodList(moods));
        if(mc.getEmotion(emotion) != null){
            sortedMoods.sortByEmotion(mc.getEmotion(emotion));
            return sortedMoods.getMoods();
        }else {
            return selfMoods;
        }
    }


    private ArrayList<Mood> filterByText(ArrayList<Mood> moods, String text){
        MoodList sortedMoods = new MoodList();
        sortedMoods.merge(new MoodList(moods));
        sortedMoods.sortByWord(text);
        return sortedMoods.getMoods();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);

        MenuItem filterItem = menu.findItem(R.id.action_filter);
        MenuView menuView =
                (MenuView) MenuItemCompat.getActionView(filterItem);
        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }


    public void update(MainModel m ){
        //TODO code to redisplay the data
        updateList();
    }

    public void updateList(){
        MainController mc = MainApplication.getMainController();
        mc.generateRequested();
        adapter = new MoodListAdapter(this, mc.getMe().getMoods().getMoods());
        moodListView.setAdapter(adapter);
        checkOnlineStatus();

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
        myToolbarLow.inflateMenu(R.menu.profile_menu_low);

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
