package com.example.game1.GameFolder;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class CharacterSprite {

    private Bitmap image;
    public int x, y;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public int original_y;
    public int velocity_x = 5;
    public int jump_delay_count = 5;

    public CharacterSprite(Bitmap bmp, int x, int y) {

        image = bmp;
        this.x = x;
        this.y = y;
        original_y = y;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update() {

        //x+=velocity_x;
        if (original_y != y && jump_delay_count%5 == 0) {
            y+=25;
        }
        if (jump_delay_count%5 != 0) {
            jump_delay_count += 1;
        }

    }










}
