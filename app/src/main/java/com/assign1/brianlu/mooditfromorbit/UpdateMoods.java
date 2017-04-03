package com.assign1.brianlu.mooditfromorbit;

import android.icu.util.TimeUnit;
import android.util.Log;

/**
 * part of comman pattern, sends moods to the server
 * Created by Gregory on 2017-03-31.
 */

public class UpdateMoods implements UpdateServer {
    private User user;

    public UpdateMoods(User user){
        this.user = user;
    }

    public void execute(){
        ElasticSearchController.UpdateUsersMoodTask updateUsersMoodTask = new ElasticSearchController.UpdateUsersMoodTask();
        updateUsersMoodTask.execute(user);
        try{
            updateUsersMoodTask.get();
        }
        catch (Exception e){
            Log.d("failed", "Failed to upload moods");
        }

        Log.d("update mood user", user.getUserName());
    }
}
