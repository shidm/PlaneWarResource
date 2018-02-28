package com.sdm.planewar2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class Bullet1 extends GameObject {
	private Bitmap bullet;
	private Bitmap bulletCheck;
	private boolean isAlive1;
	private boolean isExplosion1;
	private float object_x1;
	private float object_y1;

	public Bullet1(Resources res) {
		super(res);
		initBitmap();
		this.harm=1;
		Random ran=new Random();
		speed=ran.nextInt(5)+100;
	}

	@Override
	public void initScreen(float screen_width, float screen_height) {
		super.initScreen(screen_width, screen_height);
		
	}

	@Override
	public void initial(int i, float m, float n, int j) {
		super.initial(i, m, n, j);
		isAlive1=true;
		object_x=m-object_width/2;
		object_y=n-4*object_height;
		object_x1=m-object_width/2;
		object_y1=n-5*object_height-25;
		
	}

	@Override
	public void initBitmap() {
		bullet=BitmapFactory.decodeResource(res, R.drawable.bullet2);
		bulletCheck=BitmapFactory.decodeResource(res, R.drawable.bullet4bao);
		object_width=bullet.getWidth();
		object_height=bullet.getHeight();
	}

	@Override
	public void myDraw(Canvas canvas) {
		if(isAlive){
			if(!isExplosion){
			canvas.drawBitmap(bullet, object_x, object_y, paint);
			move();
			}else{
				canvas.drawBitmap(bulletCheck, object_x, object_y, paint);
					isExplosion=false;
					isAlive=false;
			}
		}
		if(isAlive1){
			if(!isExplosion1){
				canvas.drawBitmap(bullet, object_x1, object_y1, paint);
				move1();
			}else{
				isExplosion1=false;
				isAlive1=false;
			}
		}
	}

	@Override
	public void move() {
		if(object_y>0){
			object_y-=speed;
		}else{
			isAlive=false;
		}
		
	}
	
	public void move1(){
		if(object_y1>0){
			object_y1-=speed;
		}else{
			isAlive1=false;
		}
	}

	@Override
	public void release() {
		if(!bullet.isRecycled()){
			bullet.recycle();
		}
	}

	@Override
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
