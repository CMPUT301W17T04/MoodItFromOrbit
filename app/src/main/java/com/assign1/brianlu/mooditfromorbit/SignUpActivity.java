package com.assign1.brianlu.mooditfromorbit;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * this activity allows users to sign up
 * Created by brianlu on 2017-02-23.
 *
 *
 */

public class SignUpActivity extends CustomAppCompatActivity implements MView<MainModel> {

    private final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private final static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3;

    private EditText userName;
    private EditText confirm;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            }
        }

        Button logInButton = (Button) findViewById(R.id.logIn);
        userName = (EditText) findViewById(R.id.userInput);
        confirm = (EditText) findViewById(R.id.inputConfirm);
        context = this.getApplicationContext();
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);

                updateFromServer();
                checkOnlineStatus();
                MainController mc = MainApplication.getMainController();

                String input = userName.getText().toString();
                String inputConfirm = confirm.getText().toString();

                if(!input.equals("") & input.equals(inputConfirm)){

                    User user = new User(input);

                    Boolean exists = mc.checkForUser(user);


                    if(!exists){
                        updateUsers(user);
                        mc.communicateToServer(context);
                        Intent intent = new Intent(SignUpActivity.this, DashBoard.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Username already taken!",Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getBaseContext(),"Username doesn't match!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        checkOnlineStatus();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
    }

    public void update(MainModel mm){
        updateFromServer();
    }

    public void updateUsers(User user){
        MainController mc = MainApplication.getMainController();
        mc.addUser(user);
    }

    private void updateFromServer(){
        MainController mc = MainApplication.getMainController();
        mc.pullUsers(context);
    }

}
