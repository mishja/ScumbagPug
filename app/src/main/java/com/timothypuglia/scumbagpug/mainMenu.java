package com.timothypuglia.scumbagpug;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//private Map<String, Integer> playerMap;
//
//        private Map<String, Integer> getPlayers() {
//        HashMap<String, Integer> fakePlayers = new HashMap<>();
//        fakePlayers.put("Alice", 1);
//        fakePlayers.put("Bob", 2);
//        fakePlayers.put("Jon", 3);
//        return fakePlayers;
//        }

public class mainMenu extends Activity {

    public static AutoCompleteTextView player1NameATV;
    public TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       TypeFaceUtil.overrideFont(getApplicationContext(),"serif","fonts/concertone-regular.ttf");
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
        setContentView(R.layout.activity_menu);

        player1NameATV = (AutoCompleteTextView)findViewById(R.id.player1NameATV);
        // these are the trees
        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/concertone-regular.ttf");

        title = (TextView)findViewById(R.id.title);
        title.setTypeface(tf);
        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);
        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, -1.0f); //MISCHA AFBLIJVEN
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(8000L); //DIT GETAL BEPAALT DE SNELHEID VAN DE BOMEN. HOGER IS TRAGER

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX + width);
            }
        });
        animator.start();

        // these are the clouds
        final ImageView backgroundOne2 = (ImageView) findViewById(R.id.background_one2);
        final ImageView backgroundTwo2 = (ImageView) findViewById(R.id.background_two2);
        final ValueAnimator animator2 = ValueAnimator.ofFloat(0.0f, -1.0f);//MISCHA AFBLIJVEN
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.setDuration(20000L); //DIT GETAL BEPAALT DE SNELHEID VAN DE WOLKEN. HOGER IS TRAGER

        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne2.getWidth();
                final float translationX = width * progress;
                backgroundOne2.setTranslationX(translationX);
                backgroundTwo2.setTranslationX(translationX + width);
            }
        });
        animator2.start();
    }

    public void playButtonClicked(View view){
        finish();
        Intent intent = new Intent(this, LevelDifficulty.class);
        startActivity(intent);
    }




    public void scoreButtonClicked(View view){
        player1NameATV = findViewById(R.id.player1NameATV);
        Intent intent2 = new Intent(this, ScoreBoard.class);
       intent2.putExtra("playerName",player1NameATV.getText().toString());
        startActivity(intent2);
    }

    public String getPlayerName(){
        return player1NameATV.getText().toString();
    }

    }