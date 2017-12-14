package com.gamecodeschool.c1tappydefender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by jimjenkins on 12/14/17.
 */

public class TDView extends SurfaceView implements Runnable{
    volatile boolean playing;
    Thread gameThread = null;

    //Game Objects
    private PlayerShip player;

    //For Drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public TDView(Context context) {
        super(context);
        //initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        //initialize our player ship
        player = new PlayerShip(context);
    }

    @Override
    public void run() {
        while(playing){
            update();
            draw();
            control();
        }
    }

    private void update(){
        //update the player
        player.update();
    }

    private void draw(){
        if (ourHolder.getSurface().isValid()) {
            //lock area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            //Rub out the last frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            //draw player
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);

            //unlock and draw scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try{
            gameThread.sleep(17);
        } catch (InterruptedException e) {

        }
    }

    //clean up thread if player pauses or quits
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {

        }
    }

    //make a new thread and start it
    //execution moves to our R
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


}


























