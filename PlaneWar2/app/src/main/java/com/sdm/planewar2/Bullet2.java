package com.sdm.planewar2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class Bullet2 extends GameObject {
	private Bitmap bullet;
	private boolean isAlive1;
	private boolean isAlive2;
	private boolean isExplosion1;
	private boolean isExplosion2;
	private float object_x1;
	private float object_x2;
	private float object_y1;
	private float object_y2;

	public Bullet2(Resources res) {
		super(res);
		 initBitmap();
		 this.harm=1;
		 Random ran=new Random();
		 this.speed=ran.nextInt(8)+80;
	}

	@Override
	public void initScreen(float screen_width, float screen_height) {
		super.initScreen(screen_width, screen_height);
	}

	@Override
	public void initial(int i, float m, float n, int j) {
		isAlive=true;
		isAlive1=true;
		isAlive2=true;
		
		object_x=m-object_width/2;
		object_y=n-object_height*4;
		
		object_x1=m-object_width*5/2;
		object_y1=n-object_height*4;
		
		object_x2=m+object_width*3/2;
		object_y2=n-object_height*4;
	}

	@Override
	public void initBitmap() {
		bullet=BitmapFactory.decodeResource(res, R.drawable.bullet2);
		object_width=bullet.getWidth();
		object_height=bullet.getHeight();
				
	}

	@Override
	public void myDraw(Canvas canvas) {
		if(isAlive){
			if(!isExplosion){
				canvas.drawBitmap(bullet, object_x, object_y, paint);
			}else{
				isAlive=false;
				isExplosion=false;
			}
		}
		
		if(isAlive1){
			if(!isExplosion1){
				canvas.drawBitmap(bullet, object_x1, object_y1, paint);
			}else{
				isAlive1=false;
				isExplosion1=false;
			}
		}
		
		if(isAlive2){
			if(!isExplosion2){
				canvas.drawBitmap(bullet, object_x2, object_y2, paint);
				
			}else{
				isAlive2=false;
				isExplosion2=false;
			}
		}
		
		move();
	}

	@Override
	public void move() {
		if(object_y>0){
			object_y-=speed;
		}else{
			isAlive=false;
		}
		
		if(object_y1>0){
			object_x1-=5;
			object_y1-=speed;
		}else{
			isAlive1=false;
		}
		
		if(object_y2>0){
			object_x2+=5;
			object_y2-=speed;
		}else{
			isAlive2=false;
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
			isExplosion = true;
			return true;
		}
		if (Math.abs((object_x2 + object_width / 2)
				- (obj.object_x + obj.object_width / 2)) < (object_width + obj
						.object_width) / 2
						&& Math.abs((object_y2 + object_height / 2)
								- (obj.object_y + obj.object_height / 2)) < (object_height + obj
										.object_height) / 2) {
			isExplosion = true;
			return true;
		}
		return false;
	}

}
