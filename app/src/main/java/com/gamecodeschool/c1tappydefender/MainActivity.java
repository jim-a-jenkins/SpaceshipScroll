package com.gamecodeschool.c1tappydefender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
}
