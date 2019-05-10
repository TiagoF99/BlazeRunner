package com.example.game1.GameFolder;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.app.Activity;

import com.example.game1.LoseScreen;
import com.example.game1.R;
import com.example.game1.WonScreen;

import static android.support.v4.content.ContextCompat.startActivity;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private com.example.game1.GameFolder.MainThread thread;
    private Context context;

    //Sets up the sprite variables to be used later on.
    public CharacterSprite characterSprite;
    public DoorSprite doorSprite;
    public SpikeSprite spikeSprite;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private int char_width = 300;
    private int char_height = 240;

    private int spike_width;
    private int spike_height;


    public GameView(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        thread = new com.example.game1.GameFolder.MainThread(getHolder(), this);
        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        characterSprite = new CharacterSprite(getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.person),
                        char_width, char_height), 100, screenHeight/2);
        doorSprite = new DoorSprite(getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.door),
                char_width + 300, char_height+500),2500-char_width ,characterSprite.original_y - 215);

        spike_width = char_width - 175;
        spike_height = char_height-175;
        spikeSprite = new SpikeSprite(getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spike),
                char_width-150, spike_height),1250 ,characterSprite.original_y + 1 + char_height- spike_height);



        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {

        levelWon();
        checkCollision();
        characterSprite.update();
        doorSprite.update();
        spikeSprite.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int y = characterSprite.original_y + 1 + char_height;
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);


        if (canvas != null) {
            canvas.drawLine(0, y, 2500, y, paint);
            characterSprite.draw(canvas);
            doorSprite.draw(canvas);
            spikeSprite.draw(canvas);
        }
    }

    private void checkCollision() {

        boolean hit = false;
        for (int x = characterSprite.x; x <= characterSprite.x + char_width; x++) {
            if (x>= spikeSprite.x && x <= spikeSprite.x + spike_width) {
                for (int y = characterSprite.y; y <= characterSprite.y + char_height; y++) {
                    if (y >= spikeSprite.y && y <= spikeSprite.y + spike_height) {
                        hit = true;
                        break;
                    }
                }
            } else if (hit) {
                break;
            }
        }

        if (hit) {
            thread.setRunning(false);
            Intent intent = new Intent(this.context, LoseScreen.class);
            intent.putExtra("level", 1);
            GameView.this.context.startActivity(intent);
        }

    }

    private void levelWon() {
        if (characterSprite.x >= doorSprite.x) {
            thread.setRunning(false);
            Intent intent = new Intent(this.context, WonScreen.class);
            intent.putExtra("level", 1);
            GameView.this.context.startActivity(intent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        switch (action) {

            case MotionEvent.ACTION_DOWN:

//                if (0 <= x && x <= screenWidth/2) {
//                    characterSprite.x -= 10;
//                } else {
//                    characterSprite.x+= 10;
//                }
                //Makes Character Jump
                if (characterSprite.y == characterSprite.original_y) {
                    characterSprite.y -= 500;
                    characterSprite.jump_delay_count += 1;
                }

                break;
        }

        return super.onTouchEvent(event);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

 }
