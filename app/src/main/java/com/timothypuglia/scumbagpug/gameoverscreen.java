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
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.media.MediaPlayer;

import org.w3c.dom.Text;

public class gameoverscreen extends Activity {

    TextView tv_score;
    private int score;
    public int highscore;
    public int highscore2;
    public int highscore3;
    public String playerName;
    //  int lastScore;

    public MediaPlayer mediaPlayer;

 // MediaPlayer mediaPlayer= MediaPlayer.create(gameoverscreen.this,R.raw.dead1);


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

        //Text views
        mediaPlayer= MediaPlayer.create(gameoverscreen.this,R.raw.dead1);
        mediaPlayer.start();

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);
        TextView highScoreLabel2 = (TextView) findViewById(R.id.highScoreLabel2);
        TextView highScoreLabel3 = (TextView) findViewById(R.id.highScoreLabel3);

        score = getIntent().getIntExtra("playerScore", 0); //playerScore komt uit Player
        scoreLabel.setText(score + "");
        System.out.println(score);

        //Get highscores from sharedpreferences
        highscore = HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score",0);
        highscore2 = HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score2",0);
        highscore3 = HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score3",0);

        //get playername
        if(!mainMenu.player1NameATV.getText().toString().matches(""))
        {System.out.println("PLayer is known");
            System.out.println("player:"+mainMenu.player1NameATV.getText());
            playerName = mainMenu.player1NameATV.getText().toString();
            // not null not empty
        }else {
            playerName = "UNKNOWN_PLAYER";
            System.out.println("Unkown player");
            //null or empty
        }

        if (score > highscore) {
            int temp = highscore;
            highscore = score;
            highscore2 = temp;
            int temp2 = HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score2",0);
            String name_highscore2 = HelperSharedPreferences.getSharedPreferencesString(getApplicationContext(),"Name_Highscore","");
            highscore3 = temp2;
            highScoreLabel.setText("Highscore 1: " + score+" "+playerName);
            highScoreLabel2.setText("Highscore 2: " + highscore2);
            highScoreLabel3.setText("Highscore 3: " + highscore3);
            scoreLabel.setText("last score " + score);

            //save that stuff
            HelperSharedPreferences.putSharedPreferencesInt(getApplicationContext(),"High_score",score);
            HelperSharedPreferences.putSharedPreferencesInt(getApplicationContext(),"High_score2",temp);
            HelperSharedPreferences.putSharedPreferencesInt(getApplicationContext(),"High_score3",temp2);
            //name
            HelperSharedPreferences.putSharedPreferencesString(getApplicationContext(),"Name_Highscore3",HelperSharedPreferences.getSharedPreferencesString(getApplicationContext(),"Name_Highscore2",""));
            HelperSharedPreferences.putSharedPreferencesString(getApplicationContext(),"Name_Highscore2",HelperSharedPreferences.getSharedPreferencesString(getApplicationContext(),"Name_Highscore",""));
            HelperSharedPreferences.putSharedPreferencesString(getApplicationContext(),"Name_Highscore",playerName);

        } else if (score > highscore2) {
            int temp = highscore2;
            highscore3 = temp;
            highscore2 = score;

            highScoreLabel.setText("Highscore 1: " + highscore);
            highScoreLabel2.setText("Highscore 2: " + score);
            highScoreLabel3.setText("Highscore 3: " + highscore3);
            scoreLabel.setText("last score " + score);

            //save that stuff
            HelperSharedPreferences.putSharedPreferencesInt(getApplicationContext(),"High_score2",score);
            HelperSharedPreferences.putSharedPreferencesInt(getApplicationContext(),"High_score3",highscore3);
            //name
            HelperSharedPreferences.putSharedPreferencesString(getApplicationContext(),"Name_Highscore3",HelperSharedPreferences.getSharedPreferencesString(getApplicationContext(),"Name_Highscore2",""));
            HelperSharedPreferences.putSharedPreferencesString(getApplicationContext(),"Name_Highscore2",playerName);

        } else if (score > highscore3) {
            //   int temp = highscore3;
            highscore3 = score;
            highScoreLabel.setText("Highscore 1: " + highscore);
            highScoreLabel2.setText("Highscore 2: " + highscore2);
            highScoreLabel3.setText("Highscore 3: " + score);
            scoreLabel.setText("last score " + score);

            //save that stuff
            HelperSharedPreferences.putSharedPreferencesInt(getApplicationContext(),"High_score3",score);
            //name
            HelperSharedPreferences.putSharedPreferencesString(getApplicationContext(),"Name_Highscore3",playerName);

        } else { //deze gaat nog mis

            highscore = HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score",0);
            highscore2 = HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score2",0);
            highscore3 = HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score3",0);

//            highscore = settings.getInt("High_score", 0);
//            highscore2 = settings.getInt("High_score2", 0);
//            highscore3 = settings.getInt("High_score3", 0);

            highScoreLabel.setText("Highscore 1: " + highscore);
            highScoreLabel2.setText("Highscore 2: " + highscore2);
            highScoreLabel3.setText("Highscore 3: " + highscore3);
            scoreLabel.setText("last score " + score);

        }


    }

    public void tryAgain(View view) {
        mediaPlayer.stop();
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("levelDifficultyTryAgain",Game.levelDifficulty);
  //     MediaPlayer mediaPlayer= MediaPlayer.create(gameoverscreen.this,R.raw.dead1);

        startActivity(intent);
    }

    public void mainMenu(View view) {
        mediaPlayer.stop();
        Intent intent = new Intent(this, mainMenu.class);
 //       MediaPlayer mediaPlayer= MediaPlayer.create(gameoverscreen.this,R.raw.dead1);
  //      mediaPlayer.stop();
        startActivity(intent);

    }

}