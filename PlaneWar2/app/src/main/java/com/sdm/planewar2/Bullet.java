package com.sdm.planewar2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class Bullet extends GameObject {
	private Bitmap bullet;
	private Bitmap bulletCheck;

	public Bullet(Resources res) {
		super(res);
		initBitmap();
		this.harm=1;
		Random ran=new Random();
		speed=ran.nextInt(8)+100;
	}

	@Override
	public void initScreen(float screen_width, float screen_height) {
		super.initScreen(screen_width, screen_height);
		
	}

	@Override
	public void initial(int i, float m, float n, int j) {
		super.initial(i, m, n, j);
		object_x=m-object_width/2;
		object_y=n-4*object_height;
		
	}

	@Override
	public void initBitmap() {
		bullet=BitmapFactory.decodeResource(res, R.drawable.bullet1);
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
//				currentFrome++;
//				if(currentFrome>=4){
					isExplosion=false;
					isAlive=false;
//				}
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

	@Override
	public void release() {
		if(!bullet.isRecycled()){
			bullet.recycle();
		}
	}

	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}
	
	

}
