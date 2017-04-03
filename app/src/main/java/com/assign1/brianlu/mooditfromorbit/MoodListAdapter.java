/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * this class is a custom adapter to display moods on the views
 * Created by Gregory on 2017-03-10.
 *
 */

public class MoodListAdapter extends ArrayAdapter<Mood>{

    public MoodListAdapter(Context context, ArrayList<Mood> moods){
        super(context,0, moods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Mood mood = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mood_list, parent, false);
        }

        // Lookup view for data population
        TextView userName = (TextView) convertView.findViewById(R.id.moodUserName);
        TextView emoticon = (TextView) convertView.findViewById(R.id.moodEmoticon);
        TextView group = (TextView) convertView.findViewById(R.id.moodGroup);
        TextView emotion = (TextView) convertView.findViewById(R.id.moodEmotion);
        TextView details = (TextView) convertView.findViewById(R.id.moodDetails);
        TextView date = (TextView) convertView.findViewById(R.id.moodDate);
        ImageView image = (ImageView) convertView.findViewById(R.id.moodImageView);
        LinearLayout item = (LinearLayout) convertView.findViewById(R.id.moodListItem);


        // Populate the data into the template view using the data object
        userName.setText(mood.getUserName());
        emoticon.setText(mood.getEmotion().getEmoticon());
        group.setText(mood.getSocialSituation());
        emotion.setText(mood.getEmotion().getEmotion());
        details.setText(mood.getMessage());
        date.setText(mood.getDateForView());
        image.setImageBitmap(mood.getImage());
        if(mood.getImage() == null){
//            Log.i("no image",Integer.toString(position));
            image.setMaxHeight(0);
        }else{
//            Log.i("has image",Integer.toString(position));
            image.setMaxHeight(720);
//            Log.i("image height",Integer.toString(mood.getImage().getHeight()));
        }
//        Log.i("view height",Integer.toString(image.getHeight()));
        item.setBackgroundColor(mood.getEmotion().getColour());




        // Return the completed view to render on screen
        return convertView;
    }

    @Nullable
    @Override
    public Mood getItem(int position) {
        return super.getItem(position);
    }
}

