package com.timothypuglia.scumbagpug;


import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Type;


public class Game extends Activity {
    private Player player;
    public static Button mainMenuButton;
    public static ImageButton pauseButton;
    private Thread thread;
    public static int levelDifficulty = 0;
    public static Button changeDifficultyButton;
    public static ImageView playButton;

    public TextView scoreUpdate;
    public static Paint paint;
    public static Paint paint2;
    public int Highscore;

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

        ////////////////// begin background animation ////////////////////////
//        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
//        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);
//        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, -1.0f); //MISCHA AFBLIJVEN
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.setDuration(8000L); //DIT GETAL BEPAALT DE SNELHEID VAN DE BOMEN. HOGER IS TRAGER
//
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                final float progress = (float) animation.getAnimatedValue();
//                final float width = backgroundOne.getWidth();
//                final float translationX = width * progress;
//                backgroundOne.setTranslationX(translationX);
//                backgroundTwo.setTranslationX(translationX + width);
//            }
//        });
//        animator.start();

        // these are the clouds
//        final ImageView backgroundOne2 = (ImageView) findViewById(R.id.background_one2);
//        final ImageView backgroundTwo2 = (ImageView) findViewById(R.id.background_two2);
//        final ValueAnimator animator2 = ValueAnimator.ofFloat(0.0f, -1.0f);//MISCHA AFBLIJVEN
//        animator2.setRepeatCount(ValueAnimator.INFINITE);
//        animator2.setInterpolator(new LinearInterpolator());
//        animator2.setDuration(20000L); //DIT GETAL BEPAALT DE SNELHEID VAN DE WOLKEN. HOGER IS TRAGER
//
//        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                final float progress = (float) animation.getAnimatedValue();
//                final float width = backgroundOne2.getWidth();
//                final float translationX = width * progress;
//                backgroundOne2.setTranslationX(translationX);
//                backgroundTwo2.setTranslationX(translationX + width);
//            }
//        });
//        animator2.start();

        ////////////////////// end background animation //////////////////////
        paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.parseColor("#FFA500"));
        paint.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/concertone-regular.ttf"));
        paint2 = new Paint();
        paint2.setTextSize(60);
        paint2.setColor(Color.parseColor("#FFA500"));
        paint2.setTypeface((Typeface.createFromAsset(getAssets(),"fonts/concertone-regular.ttf")));

        SharedPreferences settings = getSharedPreferences("Game_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Highscore = settings.getInt("High_score",0);

        levelDifficulty = getIntent().getIntExtra("levelDifficulty", 3);
        System.out.println("level difficulty in game java: "+levelDifficulty);
        if (levelDifficulty == 3) {
            levelDifficulty = getIntent().getIntExtra("levelDifficultyTryAgain", 0);
        }
        mainMenuButton = (Button)findViewById(R.id.mainMenu);
        changeDifficultyButton = (Button)findViewById(R.id.changeDifficulty);
        playButton = (ImageView)findViewById(R.id.playbutton);
        pauseButton = (ImageButton)findViewById(R.id.pausebutton);


        mainMenuButton.setVisibility(View.GONE);
        changeDifficultyButton.setVisibility(View.GONE);

    }

    public void tryAgain() {
        Intent intent = new Intent(this, gameoverscreen.class);
        startActivity(intent);
    }


    public void pauseButtonClicked(View view) {
        GamePanel.player.setPlaying(false);
        mainMenuButton.setVisibility(View.VISIBLE);
        changeDifficultyButton.setVisibility(View.VISIBLE);
        //  GamePanel.thread.setPaused(true);
        //     mainMenuButton.setVisibility(view.VISIBLE);
    }

    public void menuButtonClicked(View view) {
        Intent intent = new Intent(this, mainMenu.class);
        startActivity(intent);
    }

    public void difficultyButtonClicked(View view) {
        Intent intent2 = new Intent(this, LevelDifficulty.class);
        startActivity(intent2);
    }

    public int getLevelDifficulty() {
        System.out.println("your function returns: "+levelDifficulty);
        return levelDifficulty;
    }
    public int getHighscore(){
        return Highscore;
    }
}
