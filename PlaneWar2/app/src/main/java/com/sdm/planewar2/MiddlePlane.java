package com.sdm.planewar2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class MiddlePlane extends GameObject {
	private Bitmap middlePlane;

	public MiddlePlane(Resources res) {
		super(res);
		initBitmap();
		this.score=1500;
		
	}

	@Override
	public void initScreen(float screen_width, float screen_height) {
		super.initScreen(screen_width, screen_height);
	}

	@Override
	public void initial(int i, float m, float n, int j) {
		super.initial(i, m, n, j);
		bloodVloume = 15;
		currentBlood = bloodVloume;
		Random ran = new Random();
		object_x = ran.nextInt((int) (screen_width - object_width));
		object_y=-object_height*(i*8+1);
		this.speed = ran.nextInt(2) + 3 * j;
	}

	@Override
	public void initBitmap() {
		middlePlane = BitmapFactory.decodeResource(res, R.drawable.middle1);
		object_width = middlePlane.getWidth();
		object_height = middlePlane.getHeight() / 4;
	}

	@Override
	public void myDraw(Canvas canvas) {
		if (isAlive) {
			if (!isExplosion) {
				canvas.save();
				canvas.clipRect(object_x, object_y, object_x + object_width,
						object_y + object_height);
				canvas.drawBitmap(middlePlane, object_x, object_y, paint);
				canvas.restore();
				move();
			} else {
				float y = currentFrome * object_height;
				canvas.save();
				canvas.clipRect(object_x, object_y, object_x + object_width,
						object_y + object_height);
				canvas.drawBitmap(middlePlane, object_x, object_y-y, paint);
				canvas.restore();
				currentFrome++;
				if(currentFrome>=4){
					currentFrome=0;
					isAlive=false;
					isExplosion=false;
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
		if (!middlePlane.isRecycled()) {
			middlePlane.recycle();
		}
	}

	@Override
	public void attacked(int harm) {
		currentBlood-=harm;
		if(currentBlood<=0){
			isExplosion=true;
		}
	}

	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}

}
