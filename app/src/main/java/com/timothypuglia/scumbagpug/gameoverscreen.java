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

  //      TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
 //       TextView highScoreLabel =(TextView) findViewById(R.id.highScoreLabel);



        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_score.setText("SCORE: " + score);

        SharedPreferences preferences = getSharedPreferences("PREFS",0);



         score =getIntent().getIntExtra("playerScore",0);
  //      scoreLabel.setText(score+"");
        System.out.println(score);
        SharedPreferences settings = getSharedPreferences("Game_DATA", Context.MODE_PRIVATE);

        int highScore = settings.getInt("HIGH_SCORE",0);
  //      score = preferences.getInt("lastScore",0);
        best1 = preferences.getInt("best1",0);
        best2 = preferences.getInt("best2",0);
        best3 = preferences.getInt("best3",0);

        if(score > best3) {
            best3 = score;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Best3", best3);
            editor.apply();
        }
        if(score > best2) {
            int temp = best2;
            best2 = score;
            best3 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Best3", best3);
            editor.putInt("Best2", best2);
            editor.apply();
        }
        if(score>best1) {
            int temp = best1;
            best1 = score;
            best2 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Best3", best3);
            editor.putInt("Best2", best2);
            editor.apply();


  //          highScoreLabel.setText("High Score: "+score);

            //save that stuff
   //   SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.commit();

        }

      tv_score.setText("LAST SCORE: " + score +"\n" +
                "best1: " + best1 + "\n" +
                "best2: " + best2 + "\n" +
                "best3: " + best3 +"\n"+
      "geld" + "\n" +
      "superdoei");

    }

    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(),Game.class));
    }

}
