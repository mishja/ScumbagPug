package com.timothypuglia.scumbagpug;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.w3c.dom.Text;

public class gameoverscreen extends Activity {

    TextView tv_score;
    private int score = 0;
    int highscore = 0;
    int highscore2 = 0;
    //  int lastScore;

    int best1, best2, best3;

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
        setContentView(R.layout.activity_gameoverscreen);

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);
        TextView highScoreLabel2 = (TextView) findViewById(R.id.highScoreLabel2);
        TextView highScoreLabel3 = (TextView) findViewById(R.id.highScoreLabel3);

        int score = getIntent().getIntExtra("playerScore", 0); //playerScore komt uit Player
        scoreLabel.setText(score + "");
        System.out.println(score);
        SharedPreferences settings = getSharedPreferences("Game_DATA", Context.MODE_PRIVATE);

//        SharedPreferences.Editor editor = settings.edit();
//        editor.putInt("High_score ",0);
//        editor.putInt("High_score2 ",0);
//        editor.putInt("High_score3 ",0);
//        editor.commit();

        int highscore = settings.getInt("High_score",0);
        int highscore2 = settings.getInt("High_score2", 0);
        int highscore3 = settings.getInt("High_score3", 0);

//        SharedPreferences.Editor editor = settings.edit();
//        SharedPreferences.Editor.clear();
//        editor.commit();



        if (score > highscore) {
            int temp = highscore;
            highscore = score;
            highscore2 = temp;
            int temp2 = settings.getInt("High_score2", 0);
            highscore3=temp2;
            highScoreLabel.setText("Highscore 1: >score " + score);
            highScoreLabel2.setText("Highscore 2: " + highscore2);
            highScoreLabel3.setText("Highscore 3: " + highscore3);
            scoreLabel.setText("last score " + score);

            //save that stuff
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("High_score", score);
            editor.putInt("High_score2",temp);
            editor.putInt("High_score3",temp2);
            editor.commit();
                }
        else if (score > highscore2) {
            int temp = highscore2;
            highscore2 = score;
            highscore3 = temp;
            highScoreLabel.setText("Highscore 1: " + highscore);
            highScoreLabel2.setText("Highscore 2: >score " + score);
            highScoreLabel3.setText("Highscore 3: " + highscore3);
            scoreLabel.setText("last score " + score);

            //save that stuff
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("High_score2 ",score);
            editor.putInt("High_score3 ",highscore3);
            editor.commit();
        }
        else if (score > highscore3) {
         //   int temp = highscore3;
            highscore3 = score;
            highScoreLabel.setText("Highscore 1: " + highscore);
            highScoreLabel2.setText("Highscore 2: " + highscore2);
            highScoreLabel3.setText("Highscore 3: >score " + score);
            scoreLabel.setText("last score " + score);

            //save that stuff
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("High_score3",score);
            editor.commit();
        }
        else { //deze gaat nog mis
            SharedPreferences.Editor editor = settings.edit();
            int hallo =0;
            editor.putInt("High_score ",hallo);
            editor.putInt("High_score2 ",hallo);
            editor.putInt("High_score3 ",hallo);
            editor.commit();
            highscore = settings.getInt("High_score",0);
            highscore2 = settings.getInt("High_score2", 0);
            highscore3 = settings.getInt("High_score3", 0);



            highScoreLabel.setText("Highscore 1 >: " + highscore);
            highScoreLabel2.setText("Highscore 2 >: " + highscore2);
            highScoreLabel3.setText("Highscore 3 >: " + highscore3);
        }


    }

    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), Game.class));
    }
}
