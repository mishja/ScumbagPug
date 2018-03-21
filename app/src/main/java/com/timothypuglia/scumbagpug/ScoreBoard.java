package com.timothypuglia.scumbagpug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.w3c.dom.Text;

//----------------------------------------------
//
//   THIS IS THE LEADERBOARD ACTIVITY
//   WITH THE LAYOUT activity_score.xml
//
//----------------------------------------------


public class ScoreBoard extends Activity {

    private String playerName;
    private TextView textView2;

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
        setContentView(R.layout.activity_score);
        playerName = getIntent().getStringExtra("playerName");

        textView2=findViewById(R.id.text_view_id_jeej);

        textView2.setText(playerName);


    }

    //Button back to main menu
    public void scoreButtonClicked(View view) {
        Intent intent2 = new Intent(this, mainMenu.class);
        startActivity(intent2);

        }
   // public void enternameButton(View view){
    // }

    }
