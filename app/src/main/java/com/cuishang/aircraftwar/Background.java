package com.cuishang.aircraftwar;

import android.graphics.Bitmap;

public class Background {
    private int y1;
    private int y2;

    private Bitmap bitmap;

    public Background(Bitmap bitmap){
        this.bitmap = bitmap;
        y1 = 0;
        y2 = y1 - bitmap.getHeight();
        

    }
}
