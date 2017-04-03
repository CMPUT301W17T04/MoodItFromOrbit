package com.assign1.brianlu.mooditfromorbit;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gregory on 2017-04-02.
 */

public class ThreeSpaceInputFilter implements InputFilter {

    Pattern mPattern;

    public ThreeSpaceInputFilter() {
        mPattern=Pattern.compile("[A-B-a-b-0-9]{0,20}+((\\.[0-9]{0,20})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        Matcher matcher=mPattern.matcher(dest);
        if(!matcher.matches())
            return "";
        return null;
    }

}