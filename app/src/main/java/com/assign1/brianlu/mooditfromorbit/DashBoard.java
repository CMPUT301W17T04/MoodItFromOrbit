package com.assign1.brianlu.mooditfromorbit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by brianlu on 2017-02-24.
 */

public class DashBoard extends Activity implements MView<MainModel>{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);
        TextView message = (TextView) findViewById(R.id.dashMessage);
        MainController mc = MainApplication.getMainController();

        message.setText(mc.getEmotions().get(0).getEmoticon() + mc.getEmotions().get(1).getEmoticon()
                + mc.getEmotions().get(2).getEmoticon() + mc.getEmotions().get(3).getEmoticon()
                + mc.getEmotions().get(4).getEmoticon() + mc.getEmotions().get(5).getEmoticon()
                + mc.getEmotions().get(6).getEmoticon() + mc.getEmotions().get(7).getEmoticon());

        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);

    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
    }


    public void update(MainModel mc){}



}
