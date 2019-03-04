package com.cuishang.aircraftwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Background {
    private int y1;
    private int y2;

    private Bitmap bitmap;

    public Background(Bitmap bitmap){
        this.bitmap = bitmap;
        y1 = 0;
        y2 = y1 - bitmap.getHeight();
    }

    public void draw(Canvas canvas){

        logic();
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap,0,y1,paint);
        canvas.drawBitmap(bitmap,0,y2,paint);

    }

    public void logic(){

        y1+=5;
        y2+=5;
        if(y1 >= bitmap.getHeight()){
            y1 = y2-bitmap.getHeight(); //移动在第二张上面
        }
        if(y2 >= bitmap.getHeight()){
            y2 = y1-bitmap.getHeight();
        }
    }

}
