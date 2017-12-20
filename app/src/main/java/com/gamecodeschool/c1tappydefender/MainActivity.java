package com.gamecodeschool.c1tappydefender;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements View.OnClickListener {

    //this is the entry point for our game
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //here we set our UI layout as the view
        setContentView(R.layout.activity_main);

        //get a reference to the button in our layout
        final Button buttonPlay = (Button)findViewById(R.id.buttonPlay);

        //Listen for clicks
        buttonPlay.setOnClickListener(this);

        //prepare to load fastest time
        SharedPreferences prefs;
        SharedPreferences.Editor editor;
        prefs = getSharedPreferences("HiScores", MODE_PRIVATE);

        //load fastest time
        long fastestTime = prefs.getLong("fastestTime", 1000000);

        //get a reference to the TextView in our layout
        final TextView textFastestTime = (TextView)findViewById(R.id.textHiScore);

        //put high score in textView
        textFastestTime.setText("Fastest Time:" + fastestTime);
    }

    @Override
    public void onClick(View v) {
        //must be the play button.
        //create a new Intent object
        Intent i = new Intent(this, GameActivity.class);
        //Start our gameActivity class via the Intent
        startActivity(i);
        //Now shut this activity down
        finish();
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
