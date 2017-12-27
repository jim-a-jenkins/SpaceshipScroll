package com.gamecodeschool.c1tappydefender;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jimjenkins on 12/14/17.
 */

public class TDView extends SurfaceView implements Runnable{
    private SoundPool soundPool;
        int start = -1;
        int bump = -1;
        int destroyed = -1;
        int win = -1;

    volatile boolean playing;
    Thread gameThread = null;

    private int screenX;
    private int screenY;

    //Game Objects
    private PlayerShip player;
    public EnemyShip enemy1;
    public EnemyShip enemy2;
    public EnemyShip enemy3;
    public EnemyShip enemy4;
    public EnemyShip enemy5;

    public Explosion explosion;

    //make space dust
    public ArrayList<SpaceDust> dustList = new ArrayList<SpaceDust>();

    //For Drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    //for enemy explosions
    private boolean enemyExp1 = false;
    private boolean enemyExp2 = false;
    private boolean enemyExp3 = false;
    private boolean enemyExp4 = false;
    private boolean enemyExp5 = false;
    private int deathCoorX1;
    private int deathCoorY1;
    private int deathCoorX2;
    private int deathCoorY2;
    private int deathCoorX3;
    private int deathCoorY3;
    private int deathCoorX4;
    private int deathCoorY4;
    private int deathCoorX5;
    private int deathCoorY5;


    private float distanceRemaining;
    private long timeTaken;
    private long timeStarted;
    private long fastestTime;

    private Context context;

    //temp change to public to see...
    private boolean gameEnded;

    //persistence
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    public TDView(Context context, int x, int y) {
        super(context);
        this.context = context;

        //soundpool is deprecated
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        try{
            //create objects of the 2 required classes
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            //create our three fx in memory ready for use
            descriptor = assetManager.openFd("start.ogg");
            start = soundPool.load(descriptor,0);

            descriptor = assetManager.openFd("win.ogg");
            win = soundPool.load(descriptor,0);

            descriptor = assetManager.openFd("bump.ogg");
            bump = soundPool.load(descriptor,0);

            descriptor = assetManager.openFd("destroyed.ogg");
            destroyed = soundPool.load(descriptor,0);
        }catch(IOException e){
            //print error msg
            Log.e("error", "failed to load sound files");
        }

        //initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        //get a reference file called HiScores, or create one
        prefs = context.getSharedPreferences("HiScores", context.MODE_PRIVATE);

        //Init the editor ready
        editor = prefs.edit();

        //load fastest time from entry in the file
        //else, load default high score
        fastestTime = prefs.getLong("fastestTime", 1000000);

        startGame();
    }

    @Override
    public void run() {
        while(playing){
            update();
            draw();
            control();
        }
    }

    //canvas.drawBitmap(getExplosion9(), player.getCenterX() - getCenterX(getExplosion9()), player.getCenterY() - getCenterY(getExplosion9()), paint);

    private void update(){
        boolean hitDetected = false;
        //collision detection
        if(Rect.intersects(player.getHitBox(), enemy1.getHitBox()) && !gameEnded){
            hitDetected = true;
            enemyExp1 = true;
            deathCoorX1 = enemy1.getCenterX() - explosion.getCenterX(explosion.getExplosion1());
            deathCoorY1 = enemy1.getCenterY() - explosion.getCenterY(explosion.getExplosion1());
            enemy1.setX(-100);
        }
        if(Rect.intersects(player.getHitBox(), enemy2.getHitBox()) && !gameEnded){
            hitDetected = true;
            enemyExp2 = true;
            deathCoorX2 = enemy2.getCenterX() - explosion.getCenterX(explosion.getExplosion2());
            deathCoorY2 = enemy2.getCenterY() - explosion.getCenterY(explosion.getExplosion2());
            enemy2.setX(-100);
        }
        if(Rect.intersects(player.getHitBox(), enemy3.getHitBox()) && !gameEnded){
            hitDetected = true;
            enemyExp3 = true;
            deathCoorX3 = enemy3.getCenterX() - explosion.getCenterX(explosion.getExplosion3());
            deathCoorY3 = enemy3.getCenterY() - explosion.getCenterY(explosion.getExplosion3());
            enemy3.setX(-100);
        }
        if(screenX > 1000){
            if(Rect.intersects(player.getHitBox(), enemy4.getHitBox()) && !gameEnded){
                hitDetected = true;
                enemyExp4 = true;
                deathCoorX4 = enemy4.getCenterX() - explosion.getCenterX(explosion.getExplosion4());
                deathCoorY4 = enemy4.getCenterY() - explosion.getCenterY(explosion.getExplosion4());
                enemy4.setX(-100);
            }
        }
        if(screenX > 1200){
            if(Rect.intersects(player.getHitBox(), enemy5.getHitBox()) && !gameEnded){
                hitDetected = true;
                enemyExp5 = true;
                deathCoorX5 = enemy5.getCenterX() - explosion.getCenterX(explosion.getExplosion5());
                deathCoorY5 = enemy5.getCenterY() - explosion.getCenterY(explosion.getExplosion5());
                enemy5.setX(-100);
            }
        }

        if(hitDetected){
            soundPool.play(bump,1,1,0,0,1);
            player.reduceShieldStrength();
            if(player.getShieldStrength() < 0){
                soundPool.play(destroyed,1,1,0,0,1);
                gameEnded = true;
            }
        }

        //update the player
        player.update();
        //update the enemies
        enemy1.update(player.getSpeed());
        enemy2.update(player.getSpeed());
        enemy3.update(player.getSpeed());
        if(screenX > 1000){
            enemy4.update(player.getSpeed());
        }
        if(screenX > 1200){
            enemy5.update(player.getSpeed());
        }

        for (SpaceDust sd : dustList){
            sd.update(player.getSpeed());
        }

        if(!gameEnded){
            //subtract distance to home planet based on current speed
            distanceRemaining -= player.getSpeed();
            //how long has the player been flying?
            timeTaken = System.currentTimeMillis() - timeStarted;
        }

        //Completed the game!
        if(distanceRemaining < 0){
            soundPool.play(win,1,1,0,0,1);
            //check for new fastest time
            if(timeTaken < fastestTime){
                //save high score
                editor.putLong("fastestTime", timeTaken);
                editor.commit();
                fastestTime = timeTaken;
            }

            //avoid negative numbers
            distanceRemaining = 0;

            //Now end the game
            gameEnded = true;
        }

    }

    private void draw(){
        if (ourHolder.getSurface().isValid()) {
            //lock area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            //Rub out the last frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            //FOR DEBUGGING
            /*
            paint.setColor(Color.argb(255,255,255,255));
            //draw hitboxes
            canvas.drawRect(player.getHitBox().left, player.getHitBox().top, player.getHitBox().right,
                    player.getHitBox().bottom, paint);
            canvas.drawRect(enemy1.getHitBox().left, enemy1.getHitBox().top, enemy1.getHitBox().right,
                    enemy1.getHitBox().bottom, paint);
            canvas.drawRect(enemy2.getHitBox().left, enemy2.getHitBox().top, enemy2.getHitBox().right,
                    enemy2.getHitBox().bottom, paint);
            canvas.drawRect(enemy3.getHitBox().left, enemy3.getHitBox().top, enemy3.getHitBox().right,
                    enemy3.getHitBox().bottom, paint);
                    */

            //draw specs of dust
            paint.setColor(Color.argb(255, 255,255,255));


            //draw the dust from arraylist
            for (SpaceDust sd : dustList) {
                if(player.getSpeed() <= 4) {
                    canvas.drawPoint(sd.getX(), sd.getY(), paint);
                }
                else if(player.getSpeed() > 4 && player.getSpeed() <= 8){
                    canvas.drawLine(sd.getX(), sd.getY(), sd.getX()+5, sd.getY(), paint);
                }
                else if(player.getSpeed() > 8 && player.getSpeed() <= 12){
                    canvas.drawLine(sd.getX(), sd.getY(), sd.getX()+10, sd.getY(), paint);
                }
                else if(player.getSpeed() > 12 && player.getSpeed() <= 16){
                    canvas.drawLine(sd.getX(), sd.getY(), sd.getX()+15, sd.getY(), paint);
                }
                else{
                    canvas.drawLine(sd.getX(), sd.getY(), sd.getX()+20, sd.getY(), paint);
                }
            }

            //draw player if he's alive
            if(explosion.getExploCounter() > 25) {
                canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            }
            if(player.isBoosting() && explosion.getExploCounter() > 45){
                //draw flames to center-left of ship image
                Bitmap fire1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire1);
                Bitmap fire2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire2);
                Bitmap fire3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire3);

                Random generator = new Random();
                int whichBoost = generator.nextInt(3);
                switch(whichBoost){
                    case 0:
                        canvas.drawBitmap(fire1, player.getX()-fire1.getWidth(), player.getY()+fire1.getHeight()/2, paint);
                        break;
                    case 1:
                        canvas.drawBitmap(fire2, player.getX()-fire1.getWidth(), player.getY()+fire1.getHeight()/2, paint);
                        break;
                    case 2:
                        canvas.drawBitmap(fire3, player.getX()-fire1.getWidth(), player.getY()+fire1.getHeight()/2, paint);
                        break;
                }
            }

            //draw enemies
            canvas.drawBitmap(enemy1.getBitmap(), enemy1.getX(), enemy1.getY(), paint);
            canvas.drawBitmap(enemy2.getBitmap(), enemy2.getX(), enemy2.getY(), paint);
            canvas.drawBitmap(enemy3.getBitmap(), enemy3.getX(), enemy3.getY(), paint);
            if(screenX > 1000){
                canvas.drawBitmap(enemy4.getBitmap(), enemy4.getX(), enemy4.getY(), paint);
            }
            if(screenX > 1200){
                canvas.drawBitmap(enemy5.getBitmap(), enemy5.getX(), enemy5.getY(), paint);
            }

            //blow up enemies if hitting our ship
            if(enemyExp1){
                explosion.destroyEnemy(deathCoorX1, deathCoorY1, canvas, paint);
            }
            if(enemyExp2){
                explosion.destroyEnemy(deathCoorX2, deathCoorY2, canvas, paint);
            }
            if(enemyExp3){
                explosion.destroyEnemy(deathCoorX3, deathCoorY3, canvas, paint);
            }
            if(screenX > 1000){
                if(enemyExp4){
                    explosion.destroyEnemy(deathCoorX4, deathCoorY4, canvas, paint);
                }
            }
            if(screenX > 1200){
                if(enemyExp5){
                    explosion.destroyEnemy(deathCoorX5, deathCoorY5, canvas, paint);
                }
            }
            if(explosion.getEnemyCounter() == 0){
                enemyExp1 = false;
                enemyExp2 = false;
                enemyExp3 = false;
                enemyExp4 = false;
                enemyExp5 = false;
                explosion.setEnemyCounter(50);
            }

            if(!gameEnded) {
                //draw the HUD
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(25);
                //canvas.drawText("Fastest:" + fastestTime + "s", 10, 20, paint);
                canvas.drawText("Fastest:"+formatTime(fastestTime)+"s",10,20,paint);
                //canvas.drawText("Time:" + timeTaken + "s", screenX / 2, 20, paint);
                canvas.drawText("Time:" + formatTime(timeTaken)+"s", screenX/2, 20, paint);
                canvas.drawText("Distance:" + distanceRemaining / 1000 + "KM", screenX / 3, screenY - 20, paint);

                canvas.drawText("Shield:" + player.getShieldStrength(), 10, screenY - 20, paint);
                canvas.drawText("Speed:" + player.getSpeed() * 60 + "MPS", (screenX / 3) * 2, screenY - 20, paint);
            } else{
                //show pause screen and disable boosting
                player.stopBoosting();

                //blow up the ship if you didn't make it
                if(distanceRemaining > 0) {
                    explosion.destroyShip(player, canvas, paint);
                }

                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over", screenX/2, 100, paint);
                paint.setTextSize(25);
                //canvas.drawText("Fastest:" + fastestTime + "s", screenX/2, 160, paint);
                canvas.drawText("Fastest:"+formatTime(fastestTime)+"s", screenX/2, 160, paint);

                //canvas.drawText("Time:" + timeTaken + "s", screenX/2, 200, paint);
                canvas.drawText("Time:"+formatTime(timeTaken)+"s", screenX/2, 200, paint);

                canvas.drawText("Distance remaining:" + distanceRemaining/1000 + " KM", screenX/2, 240, paint);

                paint.setTextSize(80);
                canvas.drawText("Tap to replay!", screenX/2, 350, paint);
            }



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
                    //if we are on the pause screen, start a new game
                    if(gameEnded){
                        startGame();
                    }
                    break;
            }
            return true;
    }

    private void startGame(){
        soundPool.play(start, 1,1,0,0,1);
        //initialize game objects
        player = new PlayerShip(context, screenX, screenY);
        enemy1 = new EnemyShip(context, screenX, screenY);
        enemy2 = new EnemyShip(context, screenX, screenY);
        enemy3 = new EnemyShip(context, screenX, screenY);
        //initialize explosions
        explosion = new Explosion(context);



        if(screenX > 1000) {
            enemy4 = new EnemyShip(context, screenX, screenY);
        }
        if(screenX >1200){
            enemy5 = new EnemyShip(context, screenX, screenY);
        }

        int numSpecs = 40;
        for (int i = 0; i < numSpecs; i++){
            SpaceDust spec = new SpaceDust(screenX, screenY);
            dustList.add(spec);
        }
        //Reset time and distance
        distanceRemaining = 10000;
        timeTaken = 0;

        //get start time
        timeStarted = System.currentTimeMillis();

        gameEnded = false;
    }
    private String formatTime(long time){
        long seconds = (time) / 1000;
        long thousandths = (time) - (seconds * 1000);
        String strThousandths = "" + thousandths;
        if (thousandths < 100){strThousandths = "0" + thousandths;}
        if (thousandths < 10){strThousandths = "0" + strThousandths;}
        String stringTime = "" + seconds + "." + strThousandths;
        return stringTime;
    }
}





























