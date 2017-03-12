package com.assign1.brianlu.mooditfromorbit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by FENGYI on 2017-03-12.
 */

public class EditMood extends AppCompatActivity implements MView<MainModel> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood);

        Button button = (Button) findViewById(R.id.Update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
    }
    public void update(MainModel mc){}
}


