package com.timothypuglia.scumbagpug;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class HousesDouble extends GameObject{
    private int score;
    private int speed;
    private Random rand = new Random();
    private Animation animation = new Animation();
    private Bitmap spritesheet;

    public HousesDouble(Bitmap res, int x, int y, int w, int h, int s, int numFrames){
        super.x = x;
        super.y = y;
        width=w;
        height=h;
        score = s;

        // speed = 10+(int) (rand.nextDouble()*score/30);
        speed = 10;
        if(speed>40) speed = 40;

        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for (int i=0; i<image.length; i++){
            image[i] = Bitmap.createBitmap(spritesheet,0, i*height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100);
        animation.setDelay(100-speed);
    }
    public void update(){
        x-=speed;
        animation.update();
    }
    public void draw(Canvas canvas){
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        } catch (Exception e){}
    }

//    @Override
//    public int getWidth(){
////        Offset slightly for more realistic collision detection
//        return width-10;
//    }
}
