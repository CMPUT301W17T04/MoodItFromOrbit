package com.assign1.brianlu.mooditfromorbit;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
                mMapView.getOverlays().add(startMarker1);
                startMarker1.setIcon(icon);
                String address = getAddressFromGeo( mood.getLatitude(),mood.getLongitude());
                String message = mood.getUserName() + ", " + mood.getEmotion().getEmotion() + "\n" + address + "\n" +mood.getMessage();
                startMarker1.setTitle(message);

            }

        }




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
                Intent intent1 = new Intent(DashBoardMap.this, AddMood.class);
                startActivity(intent1);
                return true;

            case R.id.action_profile:

                Intent intent = new Intent(DashBoardMap.this, DashBoard.class);
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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return theAddress;
    }

    public void update(MainModel mc){
        // TODO code to redisplay the data
    }
}
