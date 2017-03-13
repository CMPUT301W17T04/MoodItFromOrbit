package com.assign1.brianlu.mooditfromorbit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import org.apache.commons.lang3.ObjectUtils;

import static com.assign1.brianlu.mooditfromorbit.AddMood.REQUEST_CODE;

/**
 * Edits a mood that is clicked on
 * Created by FENGYI on 2017-03-12.
 */

public class EditMood extends AppCompatActivity implements MView<MainModel> {
    ImageView IMG;
    Bitmap imageBitmap;
    Mood mood;
    int moodId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood);


        final MainController mc = MainApplication.getMainController();

        moodId = getIntent().getExtras().getInt("moodId");
        mood = mc.getMe().getMoods().getMood(moodId);

        EditText get_comment = (EditText) findViewById(R.id.Ecomment);
        if (mood.getMessage()!= null){get_comment.setText(mood.getMessage());}


//        Spinner s_emotions = (Spinner) findViewById(R.id.emotions);
//        s_emotions.setSel

        mc.startLocationListen(this);

        //camera implementation
        Button cam = (Button) findViewById(R.id.Ecamera);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager())!= null)
                {
                    startActivityForResult(i, REQUEST_CODE);
                }
            }
        });



        // when done button is pressed
        Button updateButton = (Button) findViewById(R.id.update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO get info from input
                //this creates a test mood and adds it

                // emotions, groupstatus, and comment from spinner and edittext
                Spinner s_emotions = (Spinner) findViewById(R.id.Eemotions);
                String t_emotions = s_emotions.getSelectedItem().toString();

                Spinner g_status = (Spinner) findViewById(R.id.Egroupstatus);
                String groupstring = g_status.getSelectedItem().toString();

                EditText get_comment = (EditText) findViewById(R.id.Ecomment);
                String commentstring = get_comment.getText().toString();

                MainController mc = MainApplication.getMainController();
//                Mood mood = new Mood(mc.getEmotion(t_emotions), mc.getMe());
                mood.setSocialSituation(groupstring);
                mood.setMessage(commentstring);
                mood.setImage(imageBitmap);





                // Remove the listener you previously added
                mc.stopLocationListener();

                Location moodLocation = mc.getLocation();

                //only if share location is toggled
                //  mood.setLocation(moodLocation);




                Switch locationswitch = (Switch) findViewById(R.id.Elocations);
                locationswitch.setTextOn("On"); // displayed text of the Switch whenever it is in checked or on state
                locationswitch.setTextOff("Off"); // displayed text of the Switch whenever it is in unchecked i.e. off state
                // if true or false
                Boolean switchState = locationswitch.isChecked();
                if (switchState){
                    // if on locations enabled
                    mood.setLocation(moodLocation);

                } else {
                    // if off locations disabled
                }
                Log.d("location", moodLocation.toString());
//                mc.addNewMood(mood);
                mc.updateMoodList();

                finish();
            }
        });


        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mc.getMe().getMoods().delete(mood);
                mc.updateMoodList();
                finish();
            }

        });


        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
    }
    public void update(MainModel mc){}
}


