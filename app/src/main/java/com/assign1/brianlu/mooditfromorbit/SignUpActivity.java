package com.assign1.brianlu.mooditfromorbit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by brianlu on 2017-02-23.
 *
 * this activity allows users to sign up
 */

public class SignUpActivity extends AppCompatActivity implements MView<MainModel> {
    private EditText userName;
    private EditText confirm;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Button logInButton = (Button) findViewById(R.id.logIn);
        userName = (EditText) findViewById(R.id.userInput);
        confirm = (EditText) findViewById(R.id.inputConfirm);

        //FILENAME = getIntent().getExtras().getString("filename");
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);

                MainController mc = MainApplication.getMainController();

                String input = userName.getText().toString();
                String inputConfirm = confirm.getText().toString();

                if(!input.equals("") & input.equals(inputConfirm)){

                    User user = new User(input);
                    //Log.i("match string","two input matches!!");
                    Boolean exists = mc.checkForUser(user);
                    if(!exists){
                        updateUsers(user);
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


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
    }

    public void update(MainModel mc){}

    public void updateUsers(User user){
        MainController mc = MainApplication.getMainController();
        mc.addUser(user);
    }

}
