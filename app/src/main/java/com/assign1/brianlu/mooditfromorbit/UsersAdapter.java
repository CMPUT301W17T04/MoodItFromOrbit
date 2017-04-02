package com.assign1.brianlu.mooditfromorbit;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gregory on 2017-04-01.
 */

public class UsersAdapter extends ArrayAdapter<User> {

    public UsersAdapter(Context context, ArrayList<User> users){
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_list, parent, false);
        }

        // Lookup view for data population
        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        TextView type = (TextView) convertView.findViewById(R.id.type) ;
        LinearLayout item = (LinearLayout) convertView.findViewById(R.id.userListItem);



        MainController mc = MainApplication.getMainController();
        User me = mc.getMe();
        // Populate the data into the template view using the data object

        userName.setText(user.getUserName());
        int colour;
        String text;

        if(me.getFollowing().contains(user.getId())){
            colour = Color.parseColor("#3ADF00");
            text = "Following";
        }
        else if(me.getPending().contains(user.getId())){
            colour = Color.parseColor("#FFBF00");
            text = "Sent Request";
        }
        else{
            colour = Color.parseColor("#FE2E2E");
            text = "Not Following";
        }
        item.setBackgroundColor(colour);
        type.setText(text);


        // Return the completed view to render on screen
        return convertView;
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return super.getItem(position);
    }
}