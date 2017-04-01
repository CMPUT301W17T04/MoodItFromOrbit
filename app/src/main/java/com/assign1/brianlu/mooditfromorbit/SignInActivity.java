package com.assign1.brianlu.mooditfromorbit;

import android.content.Intent;
import android.os.Bundle;
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

    private EditText userName;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        Button logInButton = (Button) findViewById(R.id.logIn);
        TextView toSignUp = (TextView) findViewById(R.id.toSignUp);
        userName = (EditText) findViewById(R.id.signInInput);

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
        mc.pullUsers();
    }



}
