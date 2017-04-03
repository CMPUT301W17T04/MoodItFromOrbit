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
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity allows users to sign in
 * Created by brianlu on 2017-02-24.
 *
 *
 */

public class SignInActivity extends CustomAppCompatActivity implements MView<MainModel> {
    private final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private final static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3;
    
    private EditText userName;
    private Context context;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

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
        TextView toSignUp = (TextView) findViewById(R.id.toSignUp);
        userName = (EditText) findViewById(R.id.signInInput);

        context = this.getApplicationContext();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainController mc = MainApplication.getMainController();
                setResult(RESULT_OK);

                updateFromServer();

                checkOnlineStatus();
                String input = userName.getText().toString();

                Boolean exists = mc.checkSignIn(input);

                if(!exists){
                    Toast.makeText(getBaseContext(),"Invalid User name, Please sign up!",Toast.LENGTH_SHORT).show();
                }
                else{
                    mc.communicateToServer(context);
                    Intent intent = new Intent(SignInActivity.this, DashBoard.class);
                    startActivity(intent);
                }

            }
        });

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);

                startActivity(intent);
            }
        });

        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);
    }
    @Override
    protected void onStart(){
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

    private void updateFromServer(){
        MainController mc = MainApplication.getMainController();
        mc.pullUsers(context);
    }



}
