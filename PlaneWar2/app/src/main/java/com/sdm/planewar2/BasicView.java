package com.sdm.planewar2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class BasicView extends SurfaceView implements Callback, Runnable {
	protected SurfaceHolder sur;
	protected Canvas canvas;
	protected Paint paint;
	
	protected Thread t;
	protected boolean threadFlag;
	
	protected float screen_width;
	protected float screen_height;
	
	protected int currentFrome;

	public BasicView(Context context) {
		super(context);
		sur=this.getHolder();
		sur.addCallback(this);
		paint=new Paint();
	}
	
	public void initBitmap(){}
	public void release(){}
	public void myDraw(){}
	

	@Override
	public void run() {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

}
