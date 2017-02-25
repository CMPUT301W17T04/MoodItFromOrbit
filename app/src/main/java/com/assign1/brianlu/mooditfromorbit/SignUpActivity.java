package com.assign1.brianlu.mooditfromorbit;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by brianlu on 2017-02-23.
 */

public class SignUpActivity extends AppCompatActivity {
    private ArrayList<User> users;
    private String FILENAME;
//    private ArrayAdapter<User> adapter;
    private EditText userName;
    private EditText confirm;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        Button signInButton = (Button) findViewById(R.id.signIn);
        userName = (EditText) findViewById(R.id.userInput);
        confirm = (EditText) findViewById(R.id.inputConfirm);
        FILENAME = getIntent().getExtras().getString("filename");
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                String input = userName.getText().toString();
                String inputConfirm = confirm.getText().toString();
                if(input.equals(inputConfirm)){
//                    Log.i("match string","two input matches!!");
                    User newUser = new User(input);
                    users.add(newUser);

                } else{
                    Toast.makeText(getBaseContext(),"User name doesn't match!",Toast.LENGTH_SHORT).show();
                }
                saveInFile();

            }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

    }


    private void loadFromFile() {
        try {
            Log.i("file name is: ",FILENAME);
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html Jan-21-2016
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            users = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            users = new ArrayList<User>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(users, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }




}
