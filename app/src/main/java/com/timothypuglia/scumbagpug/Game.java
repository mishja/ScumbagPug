package com.timothypuglia.scumbagpug;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;


public class Game extends Activity{
    private Player player;
    public static Button mainMenuButton;
    private ImageButton pausebutton;
    private Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


                //SET CONTEXT
        setContentView(R.layout.activity_game);

        mainMenuButton = (Button)findViewById(R.id.mainMenu);
        pausebutton = (ImageButton)findViewById(R.id.pausebutton);

        mainMenuButton.setVisibility(View.GONE);
    }

    public void tryAgain(){
        Intent intent = new Intent(this,gameoverscreen.class);
        startActivity(intent);
    }


    public void pauseButtonClicked(View view){
        GamePanel.player.setPlaying(false);
        System.out.println("visibility is: "+ mainMenuButton.getVisibility());
        System.out.println("visibility is: "+ pausebutton.getVisibility());
        mainMenuButton.setVisibility(View.VISIBLE);
        System.out.println("visibility is: "+ mainMenuButton.getVisibility());
     //  GamePanel.thread.setPaused(true);
   //     mainMenuButton.setVisibility(view.VISIBLE);
    }

    public void menuButtonClicked(View view){
        Intent intent = new Intent(this,mainMenu.class);
        startActivity(intent);
    }
}
