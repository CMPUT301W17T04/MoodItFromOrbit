package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by brianlu on 2017-02-24.
 */

public class DashBoard extends Activity implements MView<MainModel>{
    //private ArrayList<User> users;
    //private String FILENAME;
    //private ArrayList<Emotion> emotions;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);
        TextView message = (TextView) findViewById(R.id.dashMessage);


        //FILENAME = getIntent().getExtras().getString("filename");
        //String userName = getIntent().getExtras().getString("username");
        //String messageCon = "Congratulation, you signed in as " + userName + "☺" +"\n" + "☺" + "\n" + "☺";
        message.setText("☺");


    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }


    public void update(MainModel mc){}
    /**
     * Fills the ArrayList emotions with required emotions
     */
    /*private void fillEmotions(){
        //TODO: Add rest of the emotions
        Emotion happy = new Emotion("Happy", "#06B31D", "☺");
        Emotion sad = new Emotion("Sad", "#1864D6", "☺");
        Emotion angry = new Emotion("Angry", "#D61C1C", "☺");

        emotions.add(happy);
        emotions.add(sad);
        emotions.add(angry);
    }*/



}
