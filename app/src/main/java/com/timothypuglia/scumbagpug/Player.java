package com.timothypuglia.scumbagpug;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;


public class Player extends GameObject{

    private Bitmap spritesheet;
    private int score;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    public int ground;
    private boolean jumping = false;
    private boolean falling = false;

    private double gravity;
    private double jumpStart;
    private double maxFallingSpeed;

    public Player(Bitmap res, int w, int h, int numFrames) {

        score = 0;
        height = h;

        x = 100;
        y = GamePanel.HEIGHT /4*3-height;

        width = w;
        gravity = 0.70;
        jumpStart =-16.0;
        maxFallingSpeed = 12;

        //GROUND HAS TO BE CHANGED TO THE HEIGHT OF THE BUILDINGS
        ground = y;




        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(300);
        startTime = System.nanoTime();

    }

    public void setGround(int ground){
        this.ground = ground;
    }

    public void setJumping(boolean b) {

        if (!falling) {
            jumping = b;
            System.out.println("I set jumping true ");
        }
    }

    public void setFalling(boolean b){
        if (!jumping){
            falling = b;
        }
    }
    public void setUp(boolean b){up = b;}

    //////////////get screen height
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if (jumping){
            dy=jumpStart;
            falling=true;
            jumping=false;
        }

        if (falling){
            dy+=gravity;
            if (dy>maxFallingSpeed){
                dy=maxFallingSpeed;
            }

        }
        else {
            dy=0;
        }
        y+=dy*2;

        if (y>=ground){
            y=ground;
            falling=false;
        }
//        if(up){
//            dy = (int)(dya-=1.1);
//
//        }
//        else{
//            dy = (int)(dya+=1.1);
//        }
//
//        if(dy>14)dy = 14;
//        if(dy<-14)dy = -14;
//
//        y += dy*2;
//        dy = 0;

//        if (y<0){
//            y=0;
//            dy=0;
//        }
//        if (y>getScreenHeight()){
//            y=getScreenHeight()-height;
//            dy=0;
//        }
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDY(){dy = 0;}
    public void resetScore(){score = 0;}



}


