package com.assign1.brianlu.mooditfromorbit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.assign1.brianlu.mooditfromorbit.AddMood.REQUEST_CODE;

/**
 * Edits a mood that is clicked on
 * Created by FENGYI on 2017-03-12.
 */

public class EditMood extends AppCompatActivity implements MView<MainModel> {
    ImageView IMG;
    Bitmap imageBitmap;
    Mood mood;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood);

        final MainController mc = MainApplication.getMainController();

        int moodId = getIntent().getExtras().getInt("moodId");
        mood = mc.getMe().getMoods().getMood(moodId);
        context = this.getApplicationContext();

        // reference : "http://stackoverflow.com/questions/5683728/convert-java-util-date-to-string"
        // April 2nd, 2017
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String reportDate = df.format(mood.getDate());

        String moodInfo = "Created on: " + reportDate;
        if (mood.getLatitude() != null && mood.getLongitude() != null){
            moodInfo += "\nAt loacation: " + getAddressFromGeo(mood.getLatitude(),mood.getLongitude());
        }

        TextView info = (TextView) findViewById(R.id.moodInfo);
        info.setText(moodInfo);

        String moodEmotion = mood.getEmotion().getEmotion();
        String moodGS = mood.getSocialSituation();

        Spinner s_emotions = (Spinner) findViewById(R.id.Eemotions);
        s_emotions.setSelection(getSelectedItem(s_emotions,moodEmotion));


        Spinner g_status = (Spinner) findViewById(R.id.Egroupstatus);
        g_status.setSelection(getSelectedItem(g_status,moodGS));

        IMG = (ImageView) findViewById(R.id.Epic);
        if (mood.getImage() != null) {
            imageBitmap = mood.getImage();
            IMG.setImageBitmap(imageBitmap);
        }


        EditText get_comment = (EditText) findViewById(R.id.Ecomment);
        if (mood.getMessage()!= null){get_comment.setText(mood.getMessage());}




        //camera implementation
        Button photoEdit = (Button) findViewById(R.id.editPhoto);
        photoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager())!= null)
                {
                    startActivityForResult(i, REQUEST_CODE);
                }
            }
        });

        Button photoDelete = (Button) findViewById(R.id.deletePhoto);
        photoDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imageBitmap = null;
                IMG.setImageResource(R.mipmap.ic_launcher);

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


                mood.setEmotion(mc.getEmotion(t_emotions));
                mood.setSocialSituation(groupstring);
                mood.setMessage(commentstring);
                mood.setImage(imageBitmap);

                mc.updateMoodList(context);

                finish();
            }
        });


        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mc.getMe().getMoods().delete(mood);
                mc.updateMoodList(context);
                finish();
            }

        });


        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);
    }

    /**
     *
     * @param s
     * @param checkS
     * @return
     */
    private int getSelectedItem(Spinner s,String checkS){
        int index = 0;

        for (int i=0;i<s.getCount();i++){
            if (s.getItemAtPosition(i).equals(checkS)){
                index = i;
            }
        }
        return index;
    }

    // setting photo to imageview
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                IMG.setImageBitmap(imageBitmap);

            }
        }
    }
    // reference: "http://stackoverflow.com/questions/9409195/how-to-get-complete-address-from-latitude-and-longitude"
    // 20 March, 2017
    /**
     * @param lat
     * @param lng
     * @return
     */
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
//            String postalCode = addresses.get(0).getPostalCode();
//            theAddress +=  addresses.get(0).getFeatureName();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return theAddress;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
    }
    public void update(MainModel mc){}

}


