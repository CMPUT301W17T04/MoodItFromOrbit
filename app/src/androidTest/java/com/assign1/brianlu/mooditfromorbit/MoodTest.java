package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * tests use cases relating to adding moods
 * Created by brianlu on 2017-03-13.
 */

public class MoodTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public MoodTest() {
        super(com.assign1.brianlu.mooditfromorbit.MoodMainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.assign1.brianlu.mooditfromorbit", appContext.getPackageName());
    }

    // Intent test *****************

    // dashboard activity to profile activity
    public void testdashBoardToProfile(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
    }

    // dashboar map activity to profile activity
    public void testDmapToProfile(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_map));
        solo.assertCurrentActivity("Wrong activity", DashBoardMap.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
    }

    // profile map activity to profile activity
    public void testPmapToProfile(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.action_map));
        solo.assertCurrentActivity("Wrong activity", ProfileMap.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);

    }

    // follow someone activity to profile activity
    public void testAlltoProfile(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_all));
        solo.assertCurrentActivity("Wrong activity", FollowSomeoneActivity.class);
    }

    // accept request activity to profile activity
    public void testReqtoProfile(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_requests));
        solo.assertCurrentActivity("Wrong activity", AcceptFollowerActivity.class);
    }



    // UI test *****************

    /** usecase: Moods US 01.01.01
     * As a participant, I want to add a mood event to my mood history, each event with the current date and time,
     * a required emotional state, optional trigger, and optional social situation.
     **/
    public void testCreateMood() {
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.action_add_mood));
        solo.assertCurrentActivity("Wrong activity", AddMood.class);
        String text = "this is test";
        solo.enterText((EditText) solo.getView(R.id.comment), text);
        solo.clickOnButton("Done");
        solo.waitForText("this is test");
        solo.clickOnButton("No");
        MainController mc = MainApplication.getMainController();
        final MoodList profileMoodList = mc.getMe().getMoods();
        Mood mood = profileMoodList.getMood(0);
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        assertEquals("this is test", mood.getMessage());
        assertTrue(solo.waitForText("Angry"));
        assertTrue(solo.waitForText("Alone"));
    }


    /** usecase: Moods US 01.02.01,
     * I want the emotional states to include at least: anger, confusion, disgust, fear, happiness, sadness, shame, and surprise.
    **/
     public void testEmotionSpinner(){

        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.action_add_mood));
        solo.assertCurrentActivity("Wrong activity", AddMood.class);
        solo.clickOnView(solo.getView(R.id.emotions));
        solo.clickOnView(solo.getView(TextView.class,1));
        assertTrue(solo.waitForText("Angry"));

        solo.clickOnView(solo.getView(R.id.emotions));
        solo.clickOnView(solo.getView(TextView.class,2));
        assertTrue(solo.waitForText("Confusion"));

        solo.clickOnView(solo.getView(R.id.emotions));
        solo.clickOnView(solo.getView(TextView.class,3));
        assertTrue(solo.waitForText("Disgust"));

        solo.clickOnView(solo.getView(R.id.emotions));
        solo.clickOnView(solo.getView(TextView.class,4));
        assertTrue(solo.waitForText("Fear"));

        solo.clickOnView(solo.getView(R.id.emotions));
        solo.clickOnView(solo.getView(TextView.class,5));
        assertTrue(solo.waitForText("Happy"));

        solo.clickOnView(solo.getView(R.id.emotions));
        solo.clickOnView(solo.getView(TextView.class,6));
        assertTrue(solo.waitForText("Sad"));

        solo.clickOnView(solo.getView(R.id.emotions));
        assertTrue(solo.waitForText("Shame"));
        assertTrue(solo.waitForText("Surprise"));

    }


    /** usecase: Moods US 01.03.01,
     * I want consistent emoticons and colors to depict and distinguish the emotional states in any view.
    **/
     public void testEmotionColor(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        MainController mc = MainApplication.getMainController();
        final MoodList profileMoodList = mc.getMe().getMoods();
        for(int i=0;i<profileMoodList.getCount();i++){
            Mood mood = profileMoodList.getMood(i);
            if(mood.getEmotion().getEmotion().equals("Angry")){
                assertEquals(Color.parseColor("#D61C1C"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Happy")){
                assertEquals(Color.parseColor("#06B31D"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Sad")){
                assertEquals(Color.parseColor("#1864D6"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Fear")){
                assertEquals(Color.parseColor("#FFFF00"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Disgust")){
                assertEquals(Color.parseColor("#773A0E"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Confusion")){
                assertEquals(Color.parseColor("#6A0888"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Shame")){
                assertEquals(Color.parseColor("#FF0080"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Surprise")){
                assertEquals(Color.parseColor("#FF0000"),mood.getEmotion().getColour());
            } else{
                assertTrue(false);
            }
        }

        solo.clickOnView(solo.getView(R.id.action_dashboard));
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        final MoodList followingMoodList = mc.getFollowingMoods();
        for(int i=0;i<followingMoodList.getCount();i++){
            Mood mood = followingMoodList.getMood(i);
            if(mood.getEmotion().getEmotion().equals("Angry")){
                assertEquals(Color.parseColor("#D61C1C"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Happy")){
                assertEquals(Color.parseColor("#06B31D"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Sad")){
                assertEquals(Color.parseColor("#1864D6"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Fear")){
                assertEquals(Color.parseColor("#FFFF00"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Disgust")){
                assertEquals(Color.parseColor("#773A0E"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Confusion")){
                assertEquals(Color.parseColor("#6A0888"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Shame")){
                assertEquals(Color.parseColor("#FF0080"),mood.getEmotion().getColour());
            } else if(mood.getEmotion().getEmotion().equals("Surprise")){
                assertEquals(Color.parseColor("#FF0000"),mood.getEmotion().getColour());
            } else{
                assertTrue(false);
            }
        }
    }

    /** US 01.04.01 As a participant,
     * I want to view a given mood event and all its available details.
     **/
    public void testMoodDetails(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);


        MainController mc = MainApplication.getMainController();
        final MoodList profileMoodList = mc.getMe().getMoods();
        Mood mood = profileMoodList.getMood(0);

        solo.waitForText(mc.getMe().getUserName());
        solo.waitForText(mood.getMessage());
        solo.waitForText(mood.getDateForView());
        solo.waitForText(mood.getEmotion().getEmotion());
        solo.waitForText(mood.getSocialSituation());

    }


    /** US 01.05.01 As a participant,
     * I want to edit the details of a given mood event of mine.
     *
     */
    public void testEditMood(){
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);

        MainController mc = MainApplication.getMainController();
        final MoodList profileMoodList = mc.getMe().getMoods();
        Mood mood = profileMoodList.getMood(0);
        solo.waitForText(mood.getMessage());
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong activity", EditMood.class);
        assertTrue(solo.waitForText(mood.getMessage()));
        assertTrue(solo.waitForText(mood.getEmotion().getEmotion()));
        assertTrue(solo.waitForText(mood.getSocialSituation()));

        solo.clearEditText((EditText) solo.getView(R.id.Ecomment));
        solo.enterText((EditText) solo.getView(R.id.Ecomment),"new message here");

        solo.clickOnView(solo.getView(R.id.Eemotions));
        solo.clickOnView(solo.getView(TextView.class,3));

        assertTrue(solo.waitForText(mood.getEmotion().getEmotion()));
        solo.clickOnButton("Update");
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);

        solo.scrollToTop();
        assertTrue(solo.waitForText("new message here"));
        assertTrue(solo.waitForText("Confusion"));

    }

    /** US 01.06.01 As a participant, As a participant,
     * I want to delete a given mood event of mine.
     *
     */
    public void testDeleteMood() {
        solo.assertCurrentActivity("Wrong activity", MoodMainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signInInput), "blu1");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong activity", DashBoard.class);
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);

        MainController mc = MainApplication.getMainController();
        final MoodList profileMoodList = mc.getMe().getMoods();
        Mood mood = profileMoodList.getMood(0);
        solo.waitForText(mood.getMessage());
        solo.clickInList(0);

        solo.scrollToBottom();
        Button deleteButton = (Button) solo.getView(R.id.delete);
        solo.clickOnView(deleteButton);
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);


    }
}
