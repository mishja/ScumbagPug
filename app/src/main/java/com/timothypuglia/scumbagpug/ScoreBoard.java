package com.timothypuglia.scumbagpug;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextClock;
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
    private TextView title;
    private TextView highscore1;
    private TextView highscoreName1;
    private TextView highscore2;
    private TextView highscoreName2;
    private TextView highscore3;
    private TextView highscoreName3;
    private TextView highscore4;
    private TextView highscoreName4;
    private TextView rankingNumber;
    private TextView rankingName;
    private TextView rankingScore;

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


        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/concertone-regular.ttf");
        title=findViewById(R.id.text_view_id_jeej);
        title.setTypeface(tf);
        rankingNumber=(TextView)findViewById(R.id.ranking);
        rankingName=(TextView)findViewById(R.id.rankingName);
        rankingScore=(TextView)findViewById(R.id.rankingScore);
        highscore2=(TextView)findViewById(R.id.rankingScore2);
        highscore3=(TextView)findViewById(R.id.rankingScore3);
        highscore4=(TextView)findViewById(R.id.rankingScore4);

        highscore1=(TextView)findViewById(R.id.rankingScore1);
        highscore2=(TextView)findViewById(R.id.rankingScore2);
        highscore3=(TextView)findViewById(R.id.rankingScore3);
        highscore4=(TextView)findViewById(R.id.rankingScore4);
        highscoreName1=(TextView)findViewById(R.id.rankingName1);
        highscoreName2=(TextView)findViewById(R.id.rankingName2);
        highscoreName3=(TextView)findViewById(R.id.rankingName3);
        highscoreName4=(TextView)findViewById(R.id.rankingName4);
        System.out.println("highscore: "+ HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score",0));
        System.out.println("Highscore 2: "+HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score2",0));

        highscore1.setText(""+HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score",0));
        highscore2.setText(""+HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score2",0));
        highscore3.setText(""+HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score3",0));
        highscore4.setText(""+HelperSharedPreferences.getSharedPreferencesInt(getApplicationContext(),"High_score4",0));
        highscoreName1.setText(HelperSharedPreferences.getSharedPreferencesString(getApplicationContext(),"Name_Highscore",""));
        highscoreName2.setText(HelperSharedPreferences.getSharedPreferencesString(getApplicationContext(),"Name_Highscore2",""));
        highscoreName3.setText(HelperSharedPreferences.getSharedPreferencesString(getApplicationContext(),"Name_Highscore3",""));
        highscoreName4.setText(HelperSharedPreferences.getSharedPreferencesString(getApplicationContext(),"Name_Highscore4",""));

        //set styles
        rankingName.setTypeface(tf);
        rankingNumber.setTypeface(tf);
        rankingScore.setTypeface(tf);
        highscoreName1.setTypeface(tf);
        highscoreName2.setTypeface(tf);
        highscoreName3.setTypeface(tf);
        highscoreName4.setTypeface(tf);
        highscore1.setTypeface(tf);
        highscore2.setTypeface(tf);
        highscore3.setTypeface(tf);
        highscore4.setTypeface(tf);
    }

    //Button back to main menu
    public void scoreButtonClicked(View view) {
        mainMenu.mediaPlayer.pause();
        Intent intent2 = new Intent(this, mainMenu.class);
        startActivity(intent2);

        }
   // public void enternameButton(View view){
    // }

    public void resetButtonClicked(View view){
        HelperSharedPreferences.putSharedPreferencesInt(getApplicationContext(),"High_score",0);
        HelperSharedPreferences.putSharedPreferencesInt(getApplicationContext(),"High_score2",0);
        HelperSharedPreferences.putSharedPreferencesInt(getApplicationContext(),"High_score3",0);
        HelperSharedPreferences.putSharedPreferencesString(getApplicationContext(),"Name_Highscore","");
        HelperSharedPreferences.putSharedPreferencesString(getApplicationContext(),"Name_Highscore2","");
        HelperSharedPreferences.putSharedPreferencesString(getApplicationContext(),"Name_Highscore3","");
    }


    }
