package com.assign1.brianlu.mooditfromorbit;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.ArrayList;


/**
 * Created by Neil on 2017/3/12.
 */

public class FollowAdapter extends ArrayAdapter<String> {
    private ArrayList<String>  userName;
    private Context context;
    public FollowAdapter(ArrayList<String> userName, Context context) {
        super(context, R.layout.activity_follow, userName);
        this.userName = userName;
        this.context = context;
    }
}
