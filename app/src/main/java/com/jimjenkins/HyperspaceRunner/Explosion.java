package com.jimjenkins.HyperspaceRunner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by jimjenkins on 12/22/17.
 */

public class Explosion {
    private Bitmap explosion1;
    private Bitmap explosion2;
    private Bitmap explosion3;
    private Bitmap explosion4;
    private Bitmap explosion5;
    private Bitmap explosion6;
    private Bitmap explosion7;
    private Bitmap explosion8;
    private Bitmap explosion9;
    private Bitmap explosion10;
    private int exploCounter;
    private int enemyCounter;

    public Explosion(Context context){
        explosion1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion1);
        explosion2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion2);
        explosion3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion3);
        explosion4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion4);
        explosion5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion5);
        explosion6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion6);
        explosion7 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion7);
        explosion8 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion8);
        explosion9 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion9);
        explosion10 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion10);
        exploCounter = 50;
        enemyCounter = 50;
    }

    //destroy the player
    public void destroyShip(PlayerShip player, Canvas canvas, Paint paint){
        if (getExploCounter() >= 45) {
            canvas.drawBitmap(getExplosion1(), player.getCenterX() - getCenterX(getExplosion1()),
                    player.getCenterY() - getCenterY(getExplosion1()), paint);
            subtractExploCounter();
        }
        if (getExploCounter() < 45 && getExploCounter() >= 40) {
            canvas.drawBitmap(getExplosion2(), player.getCenterX() - getCenterX(getExplosion2()),
                    player.getCenterY() - getCenterY(getExplosion2()), paint);
            subtractExploCounter();
        }
        if (getExploCounter() < 40 && getExploCounter() >= 35) {
            canvas.drawBitmap(getExplosion3(), player.getCenterX() - getCenterX(getExplosion3()),
                    player.getCenterY() - getCenterY(getExplosion3()), paint);
            subtractExploCounter();
        }
        if (getExploCounter() < 35 && getExploCounter() >= 30) {
            canvas.drawBitmap(getExplosion4(), player.getCenterX() - getCenterX(getExplosion4()),
                    player.getCenterY() - getCenterY(getExplosion4()), paint);
            subtractExploCounter();
        }
        if (getExploCounter() < 30 && getExploCounter() >= 25) {
            canvas.drawBitmap(getExplosion5(), player.getCenterX() - getCenterX(getExplosion5()),
                    player.getCenterY() - getCenterY(getExplosion5()), paint);
            subtractExploCounter();
        }
        if (getExploCounter() < 25 && getExploCounter() >= 20) {
            canvas.drawBitmap(getExplosion6(), player.getCenterX() - getCenterX(getExplosion6()),
                    player.getCenterY() - getCenterY(getExplosion6()), paint);
            subtractExploCounter();
        }
        if (getExploCounter() < 20 && getExploCounter() >= 15) {
            canvas.drawBitmap(getExplosion7(), player.getCenterX() - getCenterX(getExplosion7()),
                    player.getCenterY() - getCenterY(getExplosion7()), paint);
            subtractExploCounter();
        }
        if (getExploCounter() < 15 && getExploCounter() >= 10) {
            canvas.drawBitmap(getExplosion8(), player.getCenterX() - getCenterX(getExplosion8()),
                    player.getCenterY() - getCenterY(getExplosion8()), paint);
            subtractExploCounter();
        }
        if (getExploCounter() < 10 && getExploCounter() >= 5) {
            canvas.drawBitmap(getExplosion9(), player.getCenterX() - getCenterX(getExplosion9()),
                    player.getCenterY() - getCenterY(getExplosion9()), paint);
            subtractExploCounter();
        }
        if (getExploCounter() < 5 && getExploCounter() > 0) {
            canvas.drawBitmap(getExplosion10(), player.getCenterX() - getCenterX(getExplosion10()),
                    player.getCenterY() - getCenterY(getExplosion10()), paint);
            subtractExploCounter();
        }
    }


    //destroy enemies
    public void destroyEnemy(int enemyX, int enemyY, Canvas canvas, Paint paint){
        if (getEnemyCounter() >= 45) {
            canvas.drawBitmap(getExplosion1(), enemyX, enemyY, paint);
            subtractEnemyCounter();
        }
        if (getEnemyCounter() < 45 && getEnemyCounter() >= 40) {
            canvas.drawBitmap(getExplosion2(), enemyX, enemyY, paint);
            subtractEnemyCounter();
        }
        if (getEnemyCounter() < 40 && getEnemyCounter() >= 35) {
            canvas.drawBitmap(getExplosion3(), enemyX, enemyY, paint);
            subtractEnemyCounter();
        }
        if (getEnemyCounter() < 35 && getEnemyCounter() >= 30) {
            canvas.drawBitmap(getExplosion4(), enemyX, enemyY, paint);
            subtractEnemyCounter();
        }
        if (getEnemyCounter() < 30 && getEnemyCounter() >= 25) {
            canvas.drawBitmap(getExplosion5(), enemyX, enemyY, paint);
            subtractEnemyCounter();
        }
        if (getEnemyCounter() < 25 && getEnemyCounter() >= 20) {
            canvas.drawBitmap(getExplosion6(), enemyX, enemyY, paint);
            subtractEnemyCounter();
        }
        if (getEnemyCounter() < 20 && getEnemyCounter() >= 15) {
            canvas.drawBitmap(getExplosion7(), enemyX, enemyY, paint);
            subtractEnemyCounter();
        }
        if (getEnemyCounter() < 15 && getEnemyCounter() >= 10) {
            canvas.drawBitmap(getExplosion8(), enemyX, enemyY, paint);
            subtractEnemyCounter();
        }
        if (getEnemyCounter() < 10 && getEnemyCounter() >= 5) {
            canvas.drawBitmap(getExplosion9(), enemyX, enemyY, paint);
            subtractEnemyCounter();
        }
        if (getEnemyCounter() < 5 && getEnemyCounter() > 0) {
            canvas.drawBitmap(getExplosion10(), enemyX, enemyY, paint);
            subtractEnemyCounter();
        }
    }

    public Bitmap getExplosion1() {
        return explosion1;
    }

    public Bitmap getExplosion2() {
        return explosion2;
    }

    public Bitmap getExplosion3() {
        return explosion3;
    }

    public Bitmap getExplosion4() {
        return explosion4;
    }

    public Bitmap getExplosion5() {
        return explosion5;
    }

    public Bitmap getExplosion6() {
        return explosion6;
    }

    public Bitmap getExplosion7() {
        return explosion7;
    }

    public Bitmap getExplosion8() {
        return explosion8;
    }

    public Bitmap getExplosion9() {
        return explosion9;
    }

    public Bitmap getExplosion10() {
        return explosion10;
    }

    public int getExploCounter() {
        return exploCounter;
    }

    public int getEnemyCounter() {
        return enemyCounter;
    }

    public void setEnemyCounter(int enemyCounter) {
        this.enemyCounter = enemyCounter;
    }

    public void subtractExploCounter() {
        this.exploCounter--;
    }

    public void subtractEnemyCounter() {
        this.enemyCounter--;
    }

    public int getCenterX(Bitmap explosion){
        return explosion.getWidth() / 2;
    }

    public int getCenterY(Bitmap explosion){
        return explosion.getHeight() / 2;
    }
}
