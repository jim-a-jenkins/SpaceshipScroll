package com.gamecodeschool.c1tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by jimjenkins on 12/14/17.
 */

public class PlayerShip {
    private boolean boosting;
    private Bitmap bitmap;
    private int x, y;
    private int speed = 0;

    private final int GRAVITY = -12;

    //stop ship leaving screen
    private int maxY;
    private int minY;

    //Limit the bounds of the ship's speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    public PlayerShip(Context context, int screenX, int screenY) {
        boosting = false;
        x = 50;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);

        maxY = screenY - bitmap.getHeight();
        minY = 0;
    }

    public void update() {
        //are we boosting?
        if (boosting) {
            //speed up
            speed += 2;
        } else {
            //slow down!
            speed -=5;
        }

        //constrain top speed
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }

        // Never stop completely
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        // move the ship up or down
        y -= speed + GRAVITY;

        //don't let ship stray off screen
        if (y < minY){
            y = minY;
        }

        if (y > maxY) {
            y = maxY;
        }
    }

    //Getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setBoosting() {
        boosting = true;
    }

    public void stopBoosting() {
        boosting = false;
    }

}
















