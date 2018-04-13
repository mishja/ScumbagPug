package com.timothypuglia.scumbagpug;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback,SensorEventListener{

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int MOVESPEED = -5;
    public static MainThread thread;
    public  static Player player;
    private long housesStartTime;
    private long housesElapsed;
    private Game game;
    private Background bg;
    private ArrayList<Houses> houses;
    private ArrayList<HousesDouble> housesDouble;
    private ArrayList<HousesTriple> housesTriple;
    private Random rand = new Random();
    private Context mContext;
    private boolean collisionSmallCheck;
    private boolean collisionBigCheck;
    private boolean collisionBiggerCheck;

    //--------- LEVELS -----------//
    private Integer[] houselist;
    private Integer[] houselistHard = {1,2,2,3,3,2,0,0,0,1,2,2,0,0,0,0,1,1,3,2,1,0,2,2,0,0,0,1,2,0,0,0,0,0,1,2,1,0,0,1,1,1,2,1,0,0,0,0,1,2,2,1,0,0,0,0,1,2,0,0,0,0,0,0,1,2,2,0,2,2,0,2,2,2,2,2,2,2,2,2,1,1,2,0,0,0,0,2,1};
    private Integer[] houselistEasy ={1,2,3,0,0,0,0,1,2,0,0,0,0,0,1,2,2,0,0,1,1,1,2,1,0,0,0,0,1,2,2,1,0,0,0,0,1,2,0,0,0,0,0,0,1,2,2,0,2,2,0,2,2,2,2,2,2,2,2,2,1,1,2,0,0,0,0,2,1};
    private Integer[] houselistMedium ={1,2,3,3,1,1,0,1,1,0,0,0,1,2,2,0,0,0,0,0,1,2,2,2,0,0,1,1,1,2,1,0,0,0,0,1,2,2,1,0,0,0,0,1,2,0,0,0,0,0,0,1,2,2,0,2,2,0,2,2,2,2,2,2,2,2,2,1,1,2,0,0,0,0,2,1};
    private int levelDifficulty;
    private int ground;
    private int l=0;
    public  int playerScore;
    private SensorManager sensorManager;
    Sensor accelerometer;
    private Paint paint;

    public GamePanel(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        this.mContext = getContext();

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so i tcan handle events
        setFocusable(true);
    }


    public GamePanel(Context context){
        this(context,null);
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


        //Game
     //   Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/concertone-regular.ttf");
        game = new Game();
//        paint = new Paint();
//        paint.setTextSize(120);
//        paint.setColor(Color.RED);

        // Game Objects
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.nohouse));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.puglooped),142,100, 8);
       // pauseButton = new pauseButton(BitmapFactory.decodeResource(getResources(), R.drawable.pauze),240,240);

        // Make lists of houses (for collision check)
        houses = new ArrayList<Houses>();
        housesDouble = new ArrayList<HousesDouble>();
        housesTriple = new ArrayList<HousesTriple>();
        housesStartTime = System.nanoTime();

        //sensor ready
        sensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        //Some variables
        ground = GamePanel.HEIGHT /4*3-player.getHeight();
        collisionSmallCheck = false;
        collisionBigCheck = false;

        levelDifficulty= Game.levelDifficulty;

        //set difficulty
        System.out.println("Level difficulty: "+levelDifficulty);
        switch (levelDifficulty){
            case 1: houselist = houselistMedium;
                break;
            case 0: houselist = houselistEasy;
                break;
            case 2: houselist = houselistHard;
                break;
        }

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        Game.mainMenuButton.setVisibility(View.GONE);
        Game.changeDifficultyButton.setVisibility(View.GONE);
        thread.setPaused(false);
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            System.out.println("Im being pressed");
            Game.playButton.setVisibility(View.INVISIBLE);
   //        Game.pausebutton.setVisibility(View.VISIBLE);

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

    public void checkCollisionSmall(){
//          Loop through every house (single height) for collision
        for (int i=0; i<houses.size();i++){
            houses.get(i).update();
            if (houses.get(i).getX()<player.getX()+player.width) {
                if (collision(houses.get(i), player)) {
                    collisionSmallCheck = true;
                    if (player.getY() > ground - 114) {
                        System.out.println("You got hit");
                        System.out.println("hoogte speler:  " + (player.getY()));
                        System.out.println("hoogte klein huisje:  " + (ground-114));
                        houses.remove(i);
                        player.setPlaying(false);
                        Intent intent = new Intent(mContext, gameoverscreen.class);

                        intent.putExtra("playerScore", playerScore);
                        mContext.startActivity(intent);
                        break;
                    } else if (player.getY() <= ground - 114) {
                        System.out.println("You are on a smallbuilding");
                        player.setGround(player.getY());
                    }
                } else {
                    collisionSmallCheck = false;
                }
            }
//                Remove house when far off the screen
            if(houses.get(i).getX()<-240){
                houses.remove(i);
                break;
            }
        }
    }

    public void checkCollisionBig(){
        //          Loop through every house (Double height) for collision
        for (int j=0; j<housesDouble.size();j++){
            housesDouble.get(j).update();
            if (housesDouble.get(j).getX()<player.getX()+player.width) {
                if (collision(housesDouble.get(j), player)) {
                    collisionBigCheck = true;
                    if ((player.getY()+player.getHeight()) > housesDouble.get(j).getY()+19) {
                        System.out.println("You got hit");
                        System.out.println("hoogte speler:  " + (player.getY()+player.getHeight()));
                        System.out.println("hoogte groot huisje:  " + (housesDouble.get(j).getY()+19));
                        housesDouble.remove(j);
                        player.setPlaying(false);
                        Intent intent = new Intent(mContext, gameoverscreen.class);

                        intent.putExtra("playerScore", playerScore);
                        mContext.startActivity(intent);
                        break;
                    } else if ((player.getY()+player.getHeight()) <= housesDouble.get(j).getY()+19) {
                        System.out.println("You are on a bigbuilding");
                        player.setGround(player.getY());
                    }

                } else {
                    collisionBigCheck = false;
                }
            }
//                Remove house when far off the screen
            if(housesDouble.get(j).getX()<-240){
                housesDouble.remove(j);

                break;
            }
        }
    }
    public void checkCollisionBigger(){
        //          Loop through every house (Double height) for collision
        for (int k=0; k<housesTriple.size();k++){
            housesTriple.get(k).update();
            if (housesTriple.get(k).getX()<player.getX()+player.width) {
                if (collision(housesTriple.get(k), player)) {
                    collisionBiggerCheck = true;
                    if ((player.getY()+player.getHeight()) > housesTriple.get(k).getY()+23) {
                        System.out.println("You got hit");
                        System.out.println("hoogte speler:  " + (player.getY()+player.getHeight()));
                        System.out.println("hoogte groot huisje:  " + (housesTriple.get(k).getY()+23));
                        housesTriple.remove(k);
                        player.setPlaying(false);
                        Intent intent = new Intent(mContext, gameoverscreen.class);

                        intent.putExtra("playerScore", playerScore);
                        mContext.startActivity(intent);
                        break;
                    } else if ((player.getY()+player.getHeight()) <= housesTriple.get(k).getY()+23) {
                        System.out.println("You are on a bigbuilding");
                        player.setGround(player.getY());
                    }

                } else {
                    collisionBiggerCheck = false;
                }
            }
//                Remove house when far off the screen
            if(housesTriple.get(k).getX()<-240){
                housesTriple.remove(k);

                break;
            }
        }
    }
    public void update(){
        if (player.getPlaying()) {
            bg.update();
            player.update();
//            Add houses on timer
            long housesElapsed = (System.nanoTime() - housesStartTime) / 1000000;
            // Check if previous house has entered completely
                if (housesElapsed>390){
                    switch (houselist[l]){
                        case 0: housesStartTime = System.nanoTime();
                                break;
                        case 1: houses.add(new Houses(BitmapFactory.decodeResource(getResources(), R.drawable.huisje), WIDTH + 10, HEIGHT - ((HEIGHT / 8) + 265), 240, 135, player.getScore(), 1));
                                housesStartTime = System.nanoTime();
                                break;
                        case 2: housesDouble.add(new HousesDouble(BitmapFactory.decodeResource(getResources(), R.drawable.dubbelhuisje), WIDTH + 10, HEIGHT - ((HEIGHT / 8) + 400), 240, 270, player.getScore(), 1));
                                housesStartTime = System.nanoTime();
                                break;
                        case 3: housesTriple.add(new HousesTriple(BitmapFactory.decodeResource(getResources(), R.drawable.huisjestriple), WIDTH + 10, HEIGHT - ((HEIGHT / 8) + 535), 240, 405, player.getScore(), 1));
                                housesStartTime = System.nanoTime();
                                break;
                    }
                    l++;
                    if (l>=houselist.length){
                        l=0;
                    }
                }

            switch (levelDifficulty){
                case 0: playerScore = player.getScore()*2;
                    break;
                case 1: playerScore = player.getScore()*3;
                    break;
                case 2: playerScore = player.getScore()*4;
                    break;
            }
            checkCollisionSmall();
            checkCollisionBig();
            checkCollisionBigger();

            if (!collisionSmallCheck && !collisionBigCheck && !collisionBiggerCheck){
                player.setGround(GamePanel.HEIGHT /4*3-player.getHeight());
                if (player.getY()<player.ground){
                    player.setFalling(true);}
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
            canvas.drawText(""+playerScore,50,130,Game.paint);
            canvas.drawText("HIGHSCORE: "+HelperSharedPreferences.getSharedPreferencesInt(mContext,"High_score",0),50,220,Game.paint2);
            for (Houses h: houses){
                h.draw(canvas);
            }
            for (HousesDouble h: housesDouble){
                h.draw(canvas);
            }
            for (HousesTriple h: housesTriple){
                h.draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[2]>10){
            player.setJumping(true);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}

