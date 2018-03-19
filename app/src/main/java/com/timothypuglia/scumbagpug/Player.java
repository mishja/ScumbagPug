package com.timothypuglia.scumbagpug;


import android.graphics.Bitmap;
import android.view.animation.Animation;

public class Player extends GameObject {
    private Bitmap spritesheet;
    private int score;
    private double dya;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation;
    private long startTime;

    public Player(Bitmap res, int w, int h, int numFrames){
        x = 100;
        y = GamePanel.HEIGHT/2;
        dy = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];

//        HIER NOG WAT ZOOI VOOR DE LOOP MET ROTORS ZEGMAAR
    }

    public void setUp(boolean b){
        up = b;
    }
    public void update(){
        long elapsed = (System.nanoTime())
    }
}
