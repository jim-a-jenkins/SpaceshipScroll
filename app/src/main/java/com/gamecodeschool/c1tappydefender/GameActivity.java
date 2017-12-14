package com.gamecodeschool.c1tappydefender;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
    private TDView gameView;

    //this is where the play button takes us
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create an instance of TDView
        //pass in "this" which is the context of our app
        gameView = new TDView(this);

        //make gameView the view for the activity
        setContentView(gameView);
    }

    // if the activity is paused make sure to pause our thread
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //if the activity is resumed make sure the resume our thread
    @Override
    protected  void onResume() {
        super.onResume();
        gameView.resume();
    }
}
