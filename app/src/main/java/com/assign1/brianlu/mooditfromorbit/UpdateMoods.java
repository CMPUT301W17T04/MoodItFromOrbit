package com.assign1.brianlu.mooditfromorbit;

import android.util.Log;

/**
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
        Log.d("update mood user", user.getUserName());
    }
}
