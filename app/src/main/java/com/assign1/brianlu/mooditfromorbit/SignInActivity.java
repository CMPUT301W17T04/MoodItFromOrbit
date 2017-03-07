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
 * Created by brianlu on 2017-02-24.
 *
 * This activity allows users to sign in
 */

public class SignInActivity extends AppCompatActivity implements MView<MainModel> {
    //private UserList users;
    //private String FILENAME;
    //    private ArrayAdapter<User> adapter;
    private EditText userName;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        Button logInButton = (Button) findViewById(R.id.logIn);
        TextView toSignUp = (TextView) findViewById(R.id.toSignUp);
        userName = (EditText) findViewById(R.id.signInInput);

        //FILENAME = getIntent().getExtras().getString("filename");
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainModel mm = MainApplication.getMainModel();

                setResult(RESULT_OK);

                String input = userName.getText().toString();

                User user = new User(input);

                Boolean exists = mm.checkForUser(user);

                Log.d("boolean Value", exists.toString());

                if(exists){
                    Toast.makeText(getBaseContext(),"Invalid User name, Please sign up!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(SignInActivity.this, DashBoard.class);
                    startActivity(intent);
                }
                /*for(int i = 0; i< uc.getUsers().getCount();i++){
                    Log.i("the use name is: ", users.getUser(i).getUserName());

                    if(input.equals(users.getUser(i).getUserName())){
                        Intent intent = new Intent(SignInActivity.this, DashBoard.class);
                        //intent.putExtra("username", input);
                        //intent.putExtra("filename",FILENAME);
                        startActivity(intent);
                        notExist = false;
                    }
                }
                if(notExist){
                    Toast.makeText(getBaseContext(),"Invalid User name, Please sign up!",Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                //intent.putExtra("filename", FILENAME);
                startActivity(intent);
            }
        });

        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);
    }
    @Override
    protected void onStart(){
        super.onStart();
        //loadFromFile();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
    }

    public void update(MainModel mc){
        //mc.getUsers().add(user);
    }

    /*private void loadFromFile() {
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
    }*/


}
