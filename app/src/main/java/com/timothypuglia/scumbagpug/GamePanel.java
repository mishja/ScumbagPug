package com.timothypuglia.scumbagpug;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int MOVESPEED = -5;
    public static MainThread thread;
    public static Player player;
    private long housesStartTime;
    private long housesElapsed;
    private Background bg;
    private ArrayList<Houses> houses;
    private ArrayList<HousesDouble> housesDouble;
    private Random rand = new Random();
    private Context mContext;
    private View pauseButton;

    private int ground;



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

//    public GamePanel(Context context, AttributeSet attributeSet){
//        this(context); // roep de constructor die geen AttributeSet ontvangt aan
//    }
//
//    public GamePanel(Context context){
//
//        super(context);
//        this.mContext = getContext();
//
//        //add the callback to the surfaceholder to intercept events
//        getHolder().addCallback(this);
//
//        thread = new MainThread(getHolder(), this);
//
//        //make gamePanel focusable so i tcan handle events
//        setFocusable(true);
//    }


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
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playerblock),70,70, 1);
       // pauseButton = new pauseButton(BitmapFactory.decodeResource(getResources(), R.drawable.pauze),240,240);
        //we can safely start the game loop
        houses = new ArrayList<Houses>();
        housesDouble = new ArrayList<HousesDouble>();
        housesStartTime = System.nanoTime();
        thread.setRunning(true);
        thread.start();
        ground = GamePanel.HEIGHT /4*3-player.getHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        thread.setPaused(false);
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

    public void checkCollisionSmall(){
//          Loop through every house (single height) for collision
        for (int i=0; i<houses.size();i++){
            houses.get(i).update();
            System.out.println("hoogte speler:  "+ player.getY());
            System.out.println("hoogte huisje:  " +(ground-120));
            if (collision(houses.get(i),player)){
                if (player.getY()>ground-120){
                    System.out.println("You got hit");

                    houses.remove(i);
                    player.setPlaying(false);
                    Intent intent = new Intent(mContext,gameoverscreen.class);

                    intent.putExtra("playerScore",player.getScore());
                    mContext.startActivity(intent);
                    break;
                }else if (player.getY()<=ground-120){
                    System.out.println("You are on a building");
                    player.setGround(player.getY());
                }


//                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
//                    alertDialog.setTitle("Alert");
//                    alertDialog.setMessage("Alert message to be shown");
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();

            } else{
                player.setGround(GamePanel.HEIGHT /4*3-player.getHeight());
                if (player.getY()<player.ground){
                    player.setFalling(true);}

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
            System.out.println("hoogte speler:  "+ player.getY());
            System.out.println("hoogte huisje:  " +(ground-240));
            if (collision(housesDouble.get(j),player)){
                if (player.getY()>ground-240){
                    System.out.println("You got hit");

                    housesDouble.remove(j);
                    player.setPlaying(false);
                    Intent intent = new Intent(mContext,gameoverscreen.class);

                    intent.putExtra("playerScore",player.getScore());
                    mContext.startActivity(intent);
                    break;
                }else if (player.getY()<=ground-240){
                    System.out.println("You are on a building");
                    player.setGround(player.getY());
                }

            } else{
                player.setGround(GamePanel.HEIGHT /4*3-player.getHeight());
                if (player.getY()<player.ground){
                    player.setFalling(true);}

            }
//                Remove house when far off the screen
            if(housesDouble.get(j).getX()<-240){
                housesDouble.remove(j);

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

            if (housesElapsed > 3000 && houses.size()==0) {
//                //first house down the middle
                System.out.println("Daar komt een huisje!");

                houses.add(new Houses(BitmapFactory.decodeResource(getResources(), R.drawable.huisje), WIDTH + 10, HEIGHT - ((HEIGHT / 8) + 265), 240, 135, player.getScore(), 1));
//                } else{
//                    houses.add(new Houses(BitmapFactory.decodeResource(getResources(),R.drawable.dubbelhuisje),WIDTH+10,HEIGHT-((HEIGHT/8)+265),240,270,player.getScore(),1));

//                else {
//                    houses.add(new Houses(BitmapFactory.decodeResource(getResources(),R.drawable.huisje),WIDTH+10,(int)((rand.nextDouble()*HEIGHT)),240,135, player.getScore(),1));
//                }
                housesDouble.add(new HousesDouble(BitmapFactory.decodeResource(getResources(), R.drawable.dubbelhuisje), WIDTH + 350, HEIGHT - ((HEIGHT / 8) + 400), 240, 270, player.getScore(), 1));

                housesStartTime = System.nanoTime();
            }

            checkCollisionSmall();
  //          checkCollisionBig();
        }
    }


    public void pauseButtonClicked(View view){
        thread.setPaused(true);
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
            for (HousesDouble h: housesDouble){
                h.draw(canvas);
            }


            canvas.restoreToCount(savedState);
        }
    }

}

