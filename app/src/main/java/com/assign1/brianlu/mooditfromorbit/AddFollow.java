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
import android.widget.EditText;
import android.widget.ListView;

/**
 *
 * add the user name to the following list while click the follow button on profile
 */

public class AddFollow extends AppCompatActivity {
    Button Follow_Botton;
    private User userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Follow_Botton = (Button) findViewById(R.id.FollowCheck);

        Follow_Botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FollowName = userName.getUserName();
                addfollowname(FollowName);
            }
        });


    }
    private void addfollowname(String AccountName){


    }

    };
