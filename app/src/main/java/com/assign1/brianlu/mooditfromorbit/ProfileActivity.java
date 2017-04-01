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

    private ArrayList<Mood> selfMoods;
    int chronOder;
    int filterRange;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        moodListView = (ListView) findViewById(R.id.profileListView);

        //used https://developer.android.com/training/appbar/setting-up.html#utility
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        MainController mc = MainApplication.getMainController();

        //back button
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(mc.getMe().getUserName());


        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);

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
        getAllSelfMoods();
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
                adapter.notifyDataSetChanged();
                checkOnlineStatus();
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





                        Log.i("first mood is",selfMoods.get(0).getEmotion().getEmotion());
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
        adapter.notifyDataSetChanged();
        checkOnlineStatus();
    }
}
