package com.sdm.planewar2;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GameObject {
	protected int currentFrome;
	protected int score;
	protected int speed;
	protected int harm;
	protected int bloodVloume;
	protected int currentBlood;

	protected float object_x;
	protected float object_y;
	protected float object_width;
	protected float object_height;
	protected float screen_width;
	protected float screen_height;

	protected boolean isAlive;
	protected boolean isExplosion;
	protected Paint paint;
	protected Resources res;

	public GameObject(Resources res) {
		this.res = res;
		paint = new Paint();
	}

	public void initScreen(float screen_width, float screen_height) {
		this.screen_width = screen_width;
		this.screen_height = screen_height;
	}

	public void initial(int i, float m, float n, int j) {
		isAlive = true;
		object_y = -object_height * (i * 2 + 1);
	}

	public void initBitmap() {
	}

	public void myDraw(Canvas canvas) {
	}

	public void move() {
	}

	public void release() {
	}

	public void attacked(int harm) {
	}

	public boolean isCollide(GameObject obj) {
		if (Math.abs((object_x + object_width / 2)
				- (obj.object_x + obj.object_width / 2)) < (object_width + obj
				.object_width) / 2
				&& Math.abs((object_y + object_height / 2)
						- (obj.object_y + obj.object_height / 2)) < (object_height + obj
						.object_height) / 2) {
			isExplosion = true;
			return true;
		}
		return false;
	}

}
