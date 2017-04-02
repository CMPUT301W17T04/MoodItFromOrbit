package com.assign1.brianlu.mooditfromorbit;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by brianlu on 2017-03-21.
 */

public class DashBoardMap extends AppCompatActivity implements MView<MainModel> {
    private Toolbar myToolbarLow;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapView mMapView;
        MapController mMapController;
        MainController mc = MainApplication.getMainController();
        // used to get the current location when click on the map
        mc.startLocationListen(this);
        mc.stopLocationListener();
        Location currentLocation = mc.getLocation();


        Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.marker_kml_point, null);
        // add map view,
        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(17);

        if(currentLocation != null){
            Double lat =  currentLocation.getLatitude();
            Double lng = currentLocation.getLongitude();
            Log.d("the latitude is: ", Double.toString(lat));
            Log.d("the longitude is: ",Double.toString(lng));
            GeoPoint gPt = new GeoPoint(lat, lng);
            mMapController.setCenter(gPt);
            Marker startMarker = new Marker(mMapView);
            startMarker.setPosition(gPt);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            String address = "Current Location: " + getAddressFromGeo( lat,lng);
            startMarker.setTitle(address);
            startMarker.setIcon(icon);

            mMapView.getOverlays().add(startMarker);
        }
        MoodList moods = mc.getFollowingMoods();


        // recursively add more marker overlays to the map
        for(int i =0;i< moods.getCount();i++){
            Mood mood = moods.getMood(i);
            if(mood.getLatitude() != null && mood.getLongitude() != null){
                GeoPoint pt = new GeoPoint(mood.getLatitude(), mood.getLongitude());
                Marker startMarker1 = new Marker(mMapView);
                startMarker1.setPosition(pt);
                startMarker1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

                startMarker1.setIcon(icon);
                String address = getAddressFromGeo( mood.getLatitude(),mood.getLongitude());
                String message = mood.getUserName() + ", " + mood.getEmotion().getEmotion() + "\n" + address + "\n" +mood.getMessage();
                startMarker1.setTitle(message);
                mMapView.getOverlays().add(startMarker1);
            }

        }

        //used https://developer.android.com/training/appbar/setting-up.html#utility
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("Dashboard Map");

        setupEvenlyDistributedToolbar();
        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);

        myToolbarLow.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        Intent intent = new Intent(DashBoardMap.this,ProfileActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.action_all:
                        // temporary because the main following function is not implemented
                        Intent intent3 = new Intent(DashBoardMap.this, FollowSomeoneActivity.class);
                        startActivity(intent3);
                        return true;

                    case R.id.action_dashboard:
                        // temporary because the main following function is not implemented
                        Intent intent5 = new Intent(DashBoardMap.this, DashBoard.class);
                        startActivity(intent5);
                        return true;

                    case R.id.action_requests:
                        // temporary because the main following function is not implemented
                        Intent intent4 = new Intent(DashBoardMap.this, AcceptFollowerActivity.class);
                        startActivity(intent4);
                        return true;

                    default:
                        return false;
                }
            }
        });
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
                Intent intent1 = new Intent(DashBoardMap.this, AddMood.class);
                startActivity(intent1);
                return true;

            case R.id.action_map:
                Intent intent2 = new Intent(DashBoardMap.this, DashBoard.class);
                startActivity(intent2);
                return true;

            case R.id.action_logout:
                Intent intent5 = new Intent(DashBoardMap.this, MoodMainActivity.class);
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
        getMenuInflater().inflate(R.menu.dash_board_menu_map, menu);

        MenuItem filterItem = menu.findItem(R.id.action_filter);
        MenuView menuView =
                (MenuView) MenuItemCompat.getActionView(filterItem);

        return super.onCreateOptionsMenu(menu);
    }

    private String getAddressFromGeo(double lat, double lng){
        Geocoder geocoder;
        geocoder = new Geocoder(this, Locale.getDefault());
        String theAddress = "";
        try{
            List<Address> addresses = geocoder.getFromLocation(lat,lng,1);
            theAddress += addresses.get(0).getAddressLine(0) + ", ";
            theAddress += addresses.get(0).getLocality() + ", ";
            theAddress += addresses.get(0).getAdminArea() + ", ";
            theAddress += addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
//            theAddress +=  addresses.get(0).getFeatureName();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return theAddress;
    }

    public void update(MainModel mc){
        // TODO code to redisplay the data
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
