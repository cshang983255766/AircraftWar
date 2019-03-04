package com.cuishang.aircraftwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * @author Himi
 *
 */
public class Player {
	private int playerHp = 3;
	private Bitmap bmpPlayerHp;

	public int x, y;
	private Bitmap bmpPlayer;

	private int speed = 5;

	private boolean isUp, isDown, isLeft, isRight;

	private int noCollisionCount = 0;

	private int noCollisionTime = 60;

	private boolean isCollision;

	private int PlayerCurrX ;
	private int PlayerCurrY ;


	public Player(Bitmap bmpPlayer, Bitmap bmpPlayerHp) {
		this.bmpPlayer = bmpPlayer;
		this.bmpPlayerHp = bmpPlayerHp;
		PlayerCurrX = MySurfaceView.screenW/2;
		PlayerCurrY = MySurfaceView.screenH - bmpPlayer.getHeight()/2;
		x = MySurfaceView.screenW / 2 - bmpPlayer.getWidth() / 2;
		y = MySurfaceView.screenH - bmpPlayer.getHeight();
	}

	public void GamingOnTouch(MotionEvent event) {
		if ( event.getAction() == MotionEvent.ACTION_MOVE ) {
			PlayerCurrX = (int) event.getX();
			PlayerCurrY = (int) event.getY();
		}
	}

	public void draw(Canvas canvas, Paint paint) {

		if (isCollision) {

			if (noCollisionCount % 2 == 0) {
				canvas.drawBitmap(bmpPlayer, PlayerCurrX - bmpPlayer.getWidth()/2,
						PlayerCurrY - bmpPlayer.getHeight()/2, paint);

			}
		} else {
			canvas.drawBitmap(bmpPlayer, PlayerCurrX - bmpPlayer.getWidth()/2,
					PlayerCurrY - bmpPlayer.getHeight()/2, paint);

		}

		for (int i = 0; i < playerHp; i++) {
			canvas.drawBitmap(bmpPlayerHp, i * bmpPlayerHp.getWidth(), MySurfaceView.screenH - bmpPlayerHp.getHeight(), paint);
		}
	}


	public void onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = true;
		}
	}


	public void onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = false;
		}
	}


	public void logic() {

		if (isLeft) {
			x -= speed;
		}
		if (isRight) {
			x += speed;
		}
		if (isUp) {
			y -= speed;
		}
		if (isDown) {
			y += speed;
		}

		if (x + bmpPlayer.getWidth() >= MySurfaceView.screenW) {
			x = MySurfaceView.screenW - bmpPlayer.getWidth();
		} else if (x <= 0) {
			x = 0;
		}

		if (y + bmpPlayer.getHeight() >= MySurfaceView.screenH) {
			y = MySurfaceView.screenH - bmpPlayer.getHeight();
		} else if (y <= 0) {
			y = 0;
		}

		if (isCollision) {

			noCollisionCount++;
			if (noCollisionCount >= noCollisionTime) {

				isCollision = false;
				noCollisionCount = 0;
			}
		}
	}


	public void setPlayerHp(int hp) {
		this.playerHp = hp;
	}


	public int getPlayerHp() {
		return playerHp;
	}


	public boolean isCollsionWith(Enemy en) {

		if (isCollision == false) {
			int x2 = en.x;
			int y2 = en.y;
			int w2 = en.frameW;
			int h2 = en.frameH;
			if (x >= x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
				return false;
			}

			isCollision = true;
			return true;

		} else {
			return false;
		}
	}

	public boolean isCollsionWith(Bullet bullet) {

		if (isCollision == false) {
			int x2 = bullet.bulletX;
			int y2 = bullet.bulletY;
			int w2 = bullet.bmpBullet.getWidth();
			int h2 = bullet.bmpBullet.getHeight();
			if (x >= x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
				return false;
			}

			isCollision = true;
			return true;

		} else {
			return false;
		}
	}
}
