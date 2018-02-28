package com.sdm.planewar2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class BigPlane extends GameObject {
	private Bitmap bigPlane;
	private int i;

	public BigPlane(Resources res) {
		super(res);
		initBitmap();
		this.score = 5000;

	}

	@Override
	public void initScreen(float screen_width, float screen_height) {
		super.initScreen(screen_width, screen_height);
	}

	@Override
	public void initial(int i, float m, float n, int j) {
		super.initial(i, m, n, j);
		bloodVloume = 100;
		currentBlood = bloodVloume;
		Random ran = new Random();
		this.speed = ran.nextInt(3) + 5;
		object_x = screen_width / 2 - object_width / 2;
	}

	@Override
	public void initBitmap() {
		bigPlane = BitmapFactory.decodeResource(res, R.drawable.big1);
		object_width = bigPlane.getWidth();
		object_height = bigPlane.getHeight() / 5;
	}

	@Override
	public void myDraw(Canvas canvas) {
		if (isAlive) {
			if (!isExplosion) {
				canvas.save();
				canvas.clipRect(object_x, object_y, object_x + object_width,
						object_y + object_height);
				canvas.drawBitmap(bigPlane, object_x, object_y, paint);
				canvas.restore();
				move();
			} else {
				float y = currentFrome * object_height;
				canvas.save();
				canvas.clipRect(object_x, object_y, object_x + object_width,
						object_y + object_height);
				canvas.drawBitmap(bigPlane, object_x, object_y - y, paint);
				canvas.restore();
				currentFrome++;
				if (currentFrome >= 5) {
					currentFrome = 0;
					isAlive = false;
					isExplosion = false;
				}
			}
		}
	}

	@Override
	public void move() {
		if ((object_y + speed) < (screen_height / 2 - object_height / 2)) {
			object_y += speed;
		} else {
			object_y = screen_height / 2 - object_height / 2;
		}
		
		if (object_x == screen_width - object_width) {
			i = 1;
		} 
		else if (object_x == 0) {
			i = 0;
		}
		switch (i) {
		case 0:
			if (object_y == screen_height / 2 - object_height / 2) {
				object_x += speed;
				if (object_x >= screen_width - object_width) {
					object_x = screen_width - object_width;
				}
			}
			break;

		case 1:
			if (object_y == screen_height / 2 - object_height / 2) {
				object_x -= speed;
				if (object_x <= 0) {
					object_x = 0;
				}
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void release() {
		if (!bigPlane.isRecycled()) {
			bigPlane.recycle();
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
	
	public float get_x(){
		return object_x+object_width/2;
	}
	
	public float get_y(){
		return object_y+object_height;
	}

}
