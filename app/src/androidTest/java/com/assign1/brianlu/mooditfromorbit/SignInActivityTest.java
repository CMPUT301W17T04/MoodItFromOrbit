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
    private String testName = "TestUser" ;
    private EditText userNameView;
    private View SigninButtonView;
    private View SignupButtonView;

    public SignInActivityTest(){
        super(SignInActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        userNameView = (EditText) solo.getView(R.id.signInInput);
        SigninButtonView = solo.getView(R.id.logIn);
        SignupButtonView = solo.getView(R.id.toSignUp);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

     //test whether the "Creat new login!" button is work or not.

    public void testSignUpButton(){

        solo.clickOnView(SignupButtonView);
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
    }

    //test whether the signin button is work or not
    public void testSignInButton(){

        String AutoInput = testName;

        solo.enterText(userNameView,AutoInput);
        assertTrue(solo.waitForText(AutoInput));
        solo.clickOnView(SigninButtonView);
        solo.assertCurrentActivity("Wrong Activity", SignInActivity.class);



    }

    //test when there is no username input, whether the log in still work or not.
    public void testUserInputOrNot() {

        Activity activity = (SignInActivity) solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", SignInActivity.class);

        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", SignInActivity.class);

        assertTrue(solo.waitForText("Wrong userName input"));

    }

    //test when the input userName is exist in server.
    public void testExistUserName(){
        Activity activity = (SignInActivity) solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", SignInActivity.class);

        String existUser = "neil";
        solo.enterText(userNameView,existUser);
        solo.clickOnView(SigninButtonView);
        solo.assertCurrentActivity("Wrong Activity", SignInActivity.class);



    }

    


}
