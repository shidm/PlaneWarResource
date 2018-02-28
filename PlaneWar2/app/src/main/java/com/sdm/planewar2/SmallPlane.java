package com.sdm.planewar2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class SmallPlane extends GameObject {
	private Bitmap smallPlane;

	public SmallPlane(Resources res) {
		super(res);
		initBitmap();
		this.score = 100;

	}

	@Override
	public void initScreen(float screen_width, float screen_height) {
		super.initScreen(screen_width, screen_height);
	}

	@Override
	public void initial(int i, float m, float n, int j) {
		super.initial(i, m, n, j);
		bloodVloume = 1;
		currentBlood = bloodVloume;
		Random ran = new Random();
		object_x = ran.nextInt((int) (screen_width - object_width));
		this.speed = ran.nextInt(6) + 3* j;
	}

	@Override
	public void initBitmap() {
		smallPlane = BitmapFactory.decodeResource(res, R.drawable.small1);
		object_width = smallPlane.getWidth();
		object_height = smallPlane.getHeight() / 3;
	}

	@Override
	public void myDraw(Canvas canvas) {
		if (isAlive) {
			if (!isExplosion) {
				canvas.save();
				canvas.clipRect(object_x, object_y, object_x + object_width,
						object_y + object_height);
				canvas.drawBitmap(smallPlane, object_x, object_y, paint);
				canvas.restore();
				move();
			} else {
				float y = currentFrome * object_height;
				canvas.save();
				canvas.clipRect(object_x, object_y, object_x + object_width,
						object_y + object_height);
				canvas.drawBitmap(smallPlane, object_x, object_y - y, paint);
				canvas.restore();
				currentFrome++;
				if (currentFrome >= 3) {
					currentFrome=0;
					isAlive = false;
					isExplosion = false;
				}

			}
		}
	}

	@Override
	public void move() {
		object_y += speed;
		if (object_y > screen_height) {
			isAlive = false;
		}
	}

	@Override
	public void release() {
		if (!smallPlane.isRecycled()) {
			smallPlane.recycle();
		}
	}

	@Override
	public void attacked(int harm) {
		currentBlood -= harm;
		if (currentBlood <= 0) {
			isExplosion = true;
		}
	}

	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}

}
