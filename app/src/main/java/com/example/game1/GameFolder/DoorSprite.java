package com.example.game1.GameFolder;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class DoorSprite {

    private Bitmap image;
    public int x, y;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int original_y;
    private int jump_delay_count = 5;
    private int x_velocity = 25;

    public DoorSprite(Bitmap bmp, int x, int y) {

        image = bmp;
        this.x = x;
        this.y = y;
        original_y = y;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }


    public void update() {

        x -= x_velocity;

    }

}
