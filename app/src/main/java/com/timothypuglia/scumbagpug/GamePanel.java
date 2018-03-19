package com.timothypuglia.scumbagpug;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    private MainThread thread;
    private Background bg;

    
    public GamePanel(Context context){

        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so i tcan handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.backgroundsmall));
        bg.setVector(-5);

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
    // yannic gaat nu beunen
//    public void surfaceCreated2(SurfaceHolder holder){
//
//
//        //we can safely start the game loop
//        thread.setRunning(true);
//        thread.start();
//
//    }
    // yannic heeft tot hier gebeund
    @Override
    public boolean onTouchEvent(MotionEvent event){
        return super.onTouchEvent(event);
    }

    public void update(){

        bg.update();

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        final float scaleFactorX = getWidth()/WIDTH;
        final float scaleFactorY = getHeight()/HEIGHT;

        if (canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            bg.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }
}
