package com.sdm.planewar2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class GameGoods extends GameObject {
	private Bitmap missile;
	private Bitmap bullet;
	private boolean isAlive1;
	private boolean isExplosion1;

	private float object_x1;
	private float object_y1;

	private int i;
	private int id1;
	private int id2;

	public GameGoods(Resources res) {
		super(res);
		isAlive = true;
		isAlive1 = true;
		initBitmap();
	}

	@Override
	public void initScreen(float screen_width, float screen_height) {
		super.initScreen(screen_width, screen_height);
		Random ran = new Random();
		i = ran.nextInt(2);
		this.speed = ran.nextInt(2) + 1;
		object_x = ran.nextInt((int) (screen_width - object_width));
		object_y = -object_height;
		object_x1 = ran.nextInt((int) (screen_width - object_width));
		object_y1 = -object_height;
	}

	@Override
	public void initBitmap() {
		missile = BitmapFactory.decodeResource(res, R.drawable.missile_goods);
		bullet = BitmapFactory.decodeResource(res, R.drawable.bullet_goods);
		object_width = bullet.getWidth();
		object_height = bullet.getHeight();

	}

	public void myDraw(Canvas canvas, int i) {
		switch (i) {
		case 0:
			if (isAlive) {
				if (!isExplosion) {
					canvas.drawBitmap(missile, object_x, object_y, paint);
					move();
				} else {
					canvas.drawBitmap(missile, 5, screen_height - object_height
							- 5, paint);
				}
			}
			break;
		case 1:
			if (isAlive1) {
				if (!isExplosion1) {
					canvas.drawBitmap(bullet, object_x1, object_y1, paint);
					move1();
				} else {
					isAlive1=false;
					isExplosion1=false;
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void move() {
		if (object_y <= screen_height) {
			if (object_x == screen_width - object_width) {
				i = 1;
			} else if (object_x == 0) {
				i = 0;
			}
			switch (i) {
			case 0:
				object_x += speed;
				if (object_x > screen_width - object_width) {
					object_x = screen_width - object_width;
				}
				object_y += speed;
				break;
			case 1:
				object_x -= speed;
				if (object_x < 0) {
					object_x = 0;
				}
				object_y += speed;
				break;
			default:
				break;
			}

		} else {
			isAlive = false;
		}
	}
	public void move1() {
		if (object_y1 <= screen_height) {
			if (object_x1 == screen_width - object_width) {
				i = 1;
			} else if (object_x1 == 0) {
				i = 0;
			}
			switch (i) {
			case 0:
				object_x1 += speed;
				if (object_x1 > screen_width - object_width) {
					object_x1 = screen_width - object_width;
				}
				object_y1 += speed;
				break;
			case 1:
				object_x1 -= speed;
				if (object_x1 < 0) {
					object_x1 = 0;
				}
				object_y1 += speed;
				break;
			default:
				break;
			}
			
		} else {
			isAlive1 = false;
		}
	}

	public boolean getAlive1() {
		return isAlive1;
	}

	public void setId_1() {
		id1 = 1;
	}

	public int getId_1() {
		return id1;
	}

	public void setId_2() {
		id2 = 2;
	}

	public int getId_2() {
		return id2;
	}

	@Override
	public void release() {
		if (!missile.isRecycled()) {
			missile.recycle();
		}
		if (!bullet.isRecycled()) {
			bullet.recycle();
		}
	}

	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}
	
	public boolean isCollide1(GameObject obj) {
		if (Math.abs((object_x1 + object_width / 2)
				- (obj.object_x + obj.object_width / 2)) < (object_width + obj
				.object_width) / 2
				&& Math.abs((object_y1 + object_height / 2)
						- (obj.object_y + obj.object_height / 2)) < (object_height + obj
						.object_height) / 2) {
			isExplosion1 = true;
			return true;
		}
		return false;
	}

}
