package com.assign1.brianlu.mooditfromorbit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewGroupCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by brianlu on 2017-03-29.
 */

public class FilterFragment extends android.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filter_view, container,
                false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
