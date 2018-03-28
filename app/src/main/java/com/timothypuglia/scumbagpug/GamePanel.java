package com.timothypuglia.scumbagpug;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int MOVESPEED = -5;
    private long housesStartTime;
    private long housesElapsed;
    private MainThread thread;
    private Background bg;
    private Player player;
    private ArrayList<Houses> houses;
    private Random rand = new Random();
    private Context mContext;

    public GamePanel(Context context){

        super(context);
        this.mContext = getContext();

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
        int counter = 0;
        while (retry && counter<1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.nohouse));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.rollendepug),60, 55, 3);
        //we can safely start the game loop
        houses = new ArrayList<Houses>();
        housesStartTime = System.nanoTime();
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            System.out.println("Im being pressed");
            if (!player.getPlaying()){
                player.setPlaying(true);
            }
            else {
                player.setJumping(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            System.out.println("Let go");
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update(){
        if (player.getPlaying()) {
            bg.update();
            player.update();
            System.out.println(player.getScore());
//            Add houses on timer
            long housesElapsed = (System.nanoTime()-housesStartTime)/1000000;
            if (housesElapsed>(2000-player.getScore()/4)){
//                //first house down the middle
                System.out.println("Daar komt een huisje!");
                if(houses.size()==0){
                    houses.add(new Houses(BitmapFactory.decodeResource(getResources(),R.drawable.huisje),WIDTH+10,HEIGHT-((HEIGHT/8)+270),240,135, player.getScore(),1));
                }
//                else {
//                    houses.add(new Houses(BitmapFactory.decodeResource(getResources(),R.drawable.huisje),WIDTH+10,(int)((rand.nextDouble()*HEIGHT)),240,135, player.getScore(),1));
//                }
                housesStartTime = System.nanoTime();
            }
//            Loop through every house for collision
            for (int i=0; i<houses.size();i++){
                houses.get(i).update();
                if (collision(houses.get(i),player)){
                    houses.remove(i);
                    player.setPlaying(false);

//                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
//                    alertDialog.setTitle("Alert");
//                    alertDialog.setMessage("Alert message to be shown");
//                    Aler.setContentView(R.layout.fragment_layout);
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();


                    Intent intent = new Intent(mContext,gameoverscreen.class);

                    intent.putExtra("playerScore",player.getScore());
                    mContext.startActivity(intent);
                    break;
                }
//                Remove missle when far off the screen
                if(houses.get(i).getX()<-100){
                    houses.remove(i);
                    break;
                }
            }
        }

    }



    public boolean collision(GameObject a, GameObject b){
        if (Rect.intersects(a.getRectangle(),b.getRectangle())){
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        final float scaleFactorX = getWidth()/(WIDTH*1.0f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.0f);

        if (canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);

            for (Houses h: houses){
                h.draw(canvas);
            }


            canvas.restoreToCount(savedState);
        }
    }

}

