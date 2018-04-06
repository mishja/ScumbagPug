package com.timothypuglia.scumbagpug;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class Game extends Activity{
    private Player player;
    private Button mainMenuButton;
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


    }

    public void tryAgain(){
        Intent intent = new Intent(this,gameoverscreen.class);
        startActivity(intent);
    }


    public void pauseButtonClicked(View view){
        GamePanel.player.setPlaying(false);
     //  GamePanel.thread.setPaused(true);
   //     mainMenuButton.setVisibility(view.VISIBLE);
    }


}
