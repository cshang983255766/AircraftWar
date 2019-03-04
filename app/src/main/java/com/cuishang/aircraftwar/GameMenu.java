package com.cuishang.aircraftwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameMenu {

    private Bitmap bmpMenu;
    private Bitmap bmpButton, bmpButtonPress;
    private int btnX, btnY;
    private Boolean isPress;

    public GameMenu(Bitmap bmpMenu, Bitmap bmpButton, Bitmap bmpButtonPress) {
        this.bmpMenu = bmpMenu;
        this.bmpButton = bmpButton;
        this.bmpButtonPress = bmpButtonPress;
        btnX = MySurfaceView.screenW / 2 - bmpButton.getWidth() / 2;
        btnY = MySurfaceView.screenH - bmpButton.getHeight();
        System.out.println("cuishang1 btnX = " + btnX + " btnY = " + btnY);
        isPress = false;
    }
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bmpMenu, 0, 0, paint);
        if (isPress) {
            canvas.drawBitmap(bmpButtonPress, btnX, btnY, paint);
        } else {
            canvas.drawBitmap(bmpButton, btnX, btnY, paint);
        }
    }
    public void onTouchEvent(MotionEvent event) {
        int pointX = (int) event.getX();
        int pointY = (int) event.getY();
        System.out.println("cuishang pointX = "+ pointX + "pointY" + pointY);
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (pointX > btnX && pointX < btnX + bmpButton.getWidth()) {
                if (pointY > btnY && pointY < btnY + bmpButton.getHeight()) {
                    isPress = true;
                } else {
                    isPress = false;
                }
            } else {
                isPress = false;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (pointX > btnX && pointX < btnX + bmpButton.getWidth()) {
                if (pointY > btnY && pointY < btnY + bmpButton.getHeight()) {
                    isPress = false;
                    MySurfaceView.gameState = MySurfaceView.GAMEING;
                }
            }
        }
    }

}
