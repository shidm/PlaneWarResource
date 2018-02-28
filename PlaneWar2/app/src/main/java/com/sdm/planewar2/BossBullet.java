package com.sdm.planewar2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class BossBullet extends GameObject {
	private Bitmap bullet;

	public BossBullet(Resources res) {
		super(res);
		initBitmap();
	}

	@Override
	public void initScreen(float screen_width, float screen_height) {
		super.initScreen(screen_width, screen_height);
	}

	@Override
	public void initial(int i, float m, float n, int j) {
		isAlive = true;
		Random ran = new Random();
		this.speed = ran.nextInt(3) + 3;
		object_x = m - object_width / 2;
		object_y = n;
	}

	@Override
	public void initBitmap() {
		bullet = BitmapFactory.decodeResource(res, R.drawable.bigbullet);
		object_width = bullet.getWidth();
		object_height = bullet.getHeight();
	}

	@Override
	public void myDraw(Canvas canvas) {
		if (isAlive) {
			if (!isExplosion) {
				canvas.drawBitmap(bullet, object_x, object_y, paint);
				move();
			} else {
				isAlive = false;
				isExplosion = false;
			}
		}
	}

	@Override
	public void move() {
		if (object_y < screen_height) {
			object_y += speed;
		} else {
			isAlive = false;
		}
	}

	@Override
	public void release() {
		if (!bullet.isRecycled()) {
			bullet.recycle();
		}
	}

	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}

}
