package com.cuishang.aircraftwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameMenu {

    private Bitmap bmpMenu;
    private Bitmap bmpButton, bmpButtonPress;
    private Bitmap bmpGameWin;
    private Bitmap bmpGameLost;
    private Bitmap bmpRestart;
    private int btnX, btnY;
    private int rstX, rstY;
    private Boolean isPress;

    public GameMenu(Bitmap bmpMenu, Bitmap bmpButton, Bitmap bmpButtonPress, Bitmap bmpGameWin, Bitmap bmpGameLost, Bitmap bmpRestart) {
        this.bmpMenu = bmpMenu;
        this.bmpButton = bmpButton;
        this.bmpButtonPress = bmpButtonPress;
        this.bmpRestart = bmpRestart;
        this.bmpGameLost = bmpGameLost;
        this.bmpGameWin = bmpGameWin;
        btnX = MySurfaceView.screenW / 2 - bmpButton.getWidth() / 2;
        btnY = MySurfaceView.screenH - bmpButton.getHeight();
        rstX = MySurfaceView.screenW / 2 - bmpRestart.getWidth() / 2;
        rstY = MySurfaceView.screenH - bmpRestart.getHeight();
        System.out.println("cuishang1 btnX = " + btnX + " btnY = " + btnY);
        isPress = false;
    }
    public void draw(Canvas canvas, Paint paint) {
        switch (MySurfaceView.gameState){
            case MySurfaceView.GAME_MENU :
                canvas.drawBitmap(bmpMenu, 0, 0, paint);
                if (isPress) {
                    canvas.drawBitmap(bmpButtonPress, btnX, btnY, paint);
                } else {
                    canvas.drawBitmap(bmpButton, btnX, btnY, paint);
                }
                break;

            case MySurfaceView.GAME_LOST:
                canvas.drawBitmap(bmpGameLost, 0, 0, paint);
                canvas.drawBitmap(bmpRestart, rstX, rstY, paint);
                break;
            case MySurfaceView.GAME_WIN:
                canvas.drawBitmap(bmpGameWin, 0, 0, paint);
                canvas.drawBitmap(bmpRestart, rstX, rstY, paint);
                break;
            default:
                break;
        }

    }
    public void onTouchEvent(MotionEvent event) {
        int pointX = (int) event.getX();
        int pointY = (int) event.getY();
        switch (MySurfaceView.gameState)
        {
            case MySurfaceView.GAME_MENU :
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
                break;

            case MySurfaceView.GAME_WIN:
            case MySurfaceView.GAME_LOST:

                System.out.println("cuishang pointX = "+ pointX + "pointY" + pointY);
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (pointX > rstX && pointX < rstX + bmpRestart.getWidth()) {
                        if (pointY > rstY && pointY < rstY + bmpRestart.getHeight()) {
                            isPress = true;
                        } else {
                            isPress = false;
                        }
                    } else {
                        isPress = false;
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (pointX > rstX && pointX < rstX + bmpRestart.getWidth()) {
                        if (pointY > rstY && pointY < rstY + bmpRestart.getHeight()) {
                            isPress = false;
                            MySurfaceView.gameState = MySurfaceView.GAMEING;

                        }
                    }
                }
                break;
        }

    }

}
