package com.sdm.planewar2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class MyPlane extends GameObject {
	private Bitmap myPlane[]=new Bitmap[2];
	private Bitmap myPlaneExplosion[]=new Bitmap[4]; 
	private float middle_x;
	private float middle_y;
	
	public MyPlane(Resources res) {
		super(res);
		isAlive=true;
		initBitmap();
	}
	
	@Override
	public void initScreen(float screen_width, float screen_height) {
		super.initScreen(screen_width, screen_height);
		object_x=screen_width/2-object_width/2;
		object_y=screen_height-object_height*2;
		
		middle_x=object_x+object_width/2;
		middle_y=object_y;
	}

	@Override
	public void initBitmap() {
		for(int i=0;i<myPlane.length;i++){
		myPlane[i]=BitmapFactory.decodeResource(res, R.drawable.hero1+i);
		}
		
		for(int j=0;j<myPlaneExplosion.length;j++){
			myPlaneExplosion[j]=BitmapFactory.decodeResource(res, R.drawable.hero_blowup_n1+j);
		}
		
		object_width=myPlane[1].getWidth();
		object_height=myPlane[1].getHeight();
		
	}

	@Override
	public void myDraw(Canvas canvas) {
		if(isAlive){
			if(!isExplosion){
				canvas.drawBitmap(myPlane[currentFrome], object_x, object_y, paint);
				currentFrome++;
				if(currentFrome>=myPlane.length){
					currentFrome=0;
				}
			}else{
				canvas.drawBitmap(myPlaneExplosion[currentFrome], object_x, object_y, paint);
				currentFrome++;
				if(currentFrome>=myPlaneExplosion.length){
					currentFrome=0;
					isAlive = false;
				}
			}
		}
	}
	
	public void initObject_xy(float x,float y){
		this.middle_x=x;
		this.object_x=x-object_width/2;
		this.middle_y=y-object_height;
		this.object_y=y-object_height;
	}
		
	public float getMiddle_x(){
		return middle_x;
		
	}
	
	public float getMiddle_y(){
		return middle_y;
	}

	@Override
	public void release() {
		for(int i=0;i<myPlane.length;i++){
			if(!myPlane[i].isRecycled()){
				myPlane[i].recycle();
			}
		}
		
		for(int j=0;j<myPlaneExplosion.length;j++){
			if(!myPlaneExplosion[j].isRecycled()){
				myPlaneExplosion[j].recycle();
			}
		}
	}
	
}
