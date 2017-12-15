package com.gamecodeschool.c1tappydefender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by jimjenkins on 12/14/17.
 */

public class TDView extends SurfaceView implements Runnable{
    volatile boolean playing;
    Thread gameThread = null;

    //Game Objects
    private PlayerShip player;
    public EnemyShip enemy1;
    public EnemyShip enemy2;
    public EnemyShip enemy3;

    //make space dust
    public ArrayList<SpaceDust> dustList = new ArrayList<SpaceDust>();

    //For Drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public TDView(Context context, int x, int y) {
        super(context);
        //initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        //initialize our player ship
        player = new PlayerShip(context, x, y);
        enemy1 = new EnemyShip(context, x, y);
        enemy2 = new EnemyShip(context, x, y);
        enemy3 = new EnemyShip(context, x, y);

        int numSpecs = 40;
        for (int i = 0; i < numSpecs; i++) {
            SpaceDust spec = new SpaceDust(x, y);
            dustList.add(spec);
        }

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
        //update the enemies
        enemy1.update(player.getSpeed());
        enemy2.update(player.getSpeed());
        enemy3.update(player.getSpeed());

        for (SpaceDust sd : dustList){
            sd.update(player.getSpeed());
        }
    }

    private void draw(){
        if (ourHolder.getSurface().isValid()) {
            //lock area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            //Rub out the last frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            //draw specs of dust
            paint.setColor(Color.argb(255, 255,255,255));

            //draw the dust from arraylist
            for (SpaceDust sd : dustList) {
                canvas.drawPoint(sd.getX(), sd.getY(), paint);
            }

            //draw player
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);

            //draw enemies
            canvas.drawBitmap(enemy1.getBitmap(), enemy1.getX(), enemy1.getY(), paint);
            canvas.drawBitmap(enemy2.getBitmap(), enemy2.getX(), enemy2.getY(), paint);
            canvas.drawBitmap(enemy3.getBitmap(), enemy3.getX(), enemy3.getY(), paint);


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

    //surface view allows us to handle the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                //has the player lifted finger?
                case MotionEvent.ACTION_UP:
                    player.stopBoosting();
                    break;
                //has the player touched the screen
                case MotionEvent.ACTION_DOWN:
                    player.setBoosting();
                    break;
            }
            return true;
    }
}





























