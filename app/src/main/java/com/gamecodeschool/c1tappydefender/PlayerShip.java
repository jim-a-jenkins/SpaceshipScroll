package com.gamecodeschool.c1tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by jimjenkins on 12/14/17.
 */

public class PlayerShip {
    private Bitmap bitmap;
    private int x, y;
    private int speed = 0;

    public PlayerShip(Context context) {
        x = 50;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
    }

    public void update() {
        x++;
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
}
