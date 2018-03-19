package com.timothypuglia.scumbagpug;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject {
    private Bitmap spritesheet;
    private int score;
    private double dya;
    private boolean up;
    private boolean playing;
    private Animation animation= new Animation();
    private long startTime;

    public Player(Bitmap res){
        x = 100;
        y = GamePanel.HEIGHT/2;
        dy = 0;
        score = 0;
        spritesheet = res;
    }

    public void setUp(boolean b){
        up = b;
    }
    public void update(){
        long elapsed = (System.nanoTime()-startTime/1000000);
        if (elapsed>100){
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if (up){
            dy = (int)(dya-=1.1);
        }
        else {
            dy = (int)(dya+=1.1);
        }
        if (dy>14)dy=14;
        if (dy<14)dy=-14;
        y+=dy*2;
        dy=0;

    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing=b;}
    public void resetDYA(){dya=0;}
    public void resetScore(){score=0;}
}
