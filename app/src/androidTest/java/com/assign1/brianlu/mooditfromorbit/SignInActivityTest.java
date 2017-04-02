package com.assign1.brianlu.mooditfromorbit;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by Noire on 2017/4/2.
 */

public class SignInActivityTest extends ActivityInstrumentationTestCase2<SignInActivity> {
    private Solo solo;
    private String testName;


    public SignInActivityTest(){ super(SignInActivity.class);}
}
