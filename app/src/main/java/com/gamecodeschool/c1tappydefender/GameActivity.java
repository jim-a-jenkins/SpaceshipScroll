package com.gamecodeschool.c1tappydefender;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;

public class GameActivity extends Activity {
    private TDView gameView;

    //this is where the play button takes us
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get a display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        //Load the resolution into a point object
        Point size = new Point();
        display.getSize(size);

        // Create an instance of TDView
        //pass in "this" which is the context of our app
        gameView = new TDView(this, size.x, size.y);

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

    //if player hits back button, quit app
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return false;
    }
}
