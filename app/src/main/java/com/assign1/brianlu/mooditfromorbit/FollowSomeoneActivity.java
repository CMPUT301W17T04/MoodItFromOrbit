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
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.lang3.StringUtils;

/**
 * this activity shows all users and allows them to follow a user by clicking on them
 */
public class FollowSomeoneActivity extends CustomAppCompatActivity implements MView<MainModel>{

    private ListView usersListView;
    private UsersAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private Toolbar myToolbarLow;
    private User clickedUser;
    private String check;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_someone);

        usersListView = (ListView) findViewById(R.id.userListView);

        //used https://developer.android.com/training/appbar/setting-up.html#utility
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //back button
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Users");

        setupEvenlyDistributedToolbar();
        checkOnlineStatus();


        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);

        myToolbarLow.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_dashboard:
                        Intent intent = new Intent(FollowSomeoneActivity.this,DashBoard.class);
                        startActivity(intent);
                        return true;

                    case R.id.action_profile:
                        // temporary because the main following function is not implemented
                        Intent intent3 = new Intent(FollowSomeoneActivity.this, ProfileActivity.class);
                        startActivity(intent3);
                        return true;

                    case R.id.action_requests:
                        // temporary because the main following function is not implemented
                        Intent intent4 = new Intent(FollowSomeoneActivity.this, AcceptFollowerActivity.class);
                        startActivity(intent4);
                        return true;

                    default:
                        return false;
                }
            }
        });
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainController mc = MainApplication.getMainController();
                clickedUser = mc.getAllExceptMeUsers().getUser(position);
                check = mc.checkPending(clickedUser);

                if(check != null){
                    verifyFollow();
                }

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
        mc.generateRequested(context);
        adapter = new UsersAdapter(this, mc.getAllExceptMeUsers().getUsers());
        usersListView.setAdapter(adapter);
        checkOnlineStatus();
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
                Intent intent1 = new Intent(FollowSomeoneActivity.this, AddMood.class);
                startActivity(intent1);
                adapter.notifyDataSetChanged();
                checkOnlineStatus();
                return true;

            case R.id.action_logout:
                Intent intent5 = new Intent(FollowSomeoneActivity.this, MoodMainActivity.class);
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
        getMenuInflater().inflate(R.menu.follow_menu, menu);

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
        mc.generateRequested(context);
        adapter = new UsersAdapter(this, mc.getAllExceptMeUsers().getUsers());
        usersListView.setAdapter(adapter);
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
        myToolbarLow.inflateMenu(R.menu.follow_menu_low);

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

    protected void verifyFollow(){

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(FollowSomeoneActivity.this);
        builder.setMessage("Would you like to " + check + "?")
                .setTitle("Users");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainController mc = MainApplication.getMainController();
                Log.d("test", StringUtils.substring(check, 0, 4));
                if(StringUtils.substring(check, 0, 4).equals("send")){
                    mc.addPending(clickedUser);
                    updateList();
                }
                else if (StringUtils.substring(check, 0, 4).equals("stop")){
                    mc.removeFollowing(clickedUser);
                    updateList();
                }

                check = null;
                clickedUser = null;
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                clickedUser = null;
                check = null;
            }
        });
        // Set other dialog properties


        // Create the AlertDialog
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


    }
}
