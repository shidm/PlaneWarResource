package com.sdm.planewar2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class StartView extends BasicView {
	private Bitmap logo;
	private Bitmap fly;
	private Bitmap button;

	private float logo_x;
	private float logo_y;
	private float fly_x;
	private float fly_y;
	private float button_x;
	private float button_y;

	private MainActivity mainActivity;
	private SoundPlay sound;

	private String name = "sdm";

	public StartView(Context context) {
		super(context);
		this.mainActivity = (MainActivity) context;
		sound=new SoundPlay(mainActivity);
		sound.initSound();
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& event.getPointerCount() == 1) {
			float x = event.getX();
			float y = event.getY();
			if (x > button_x && x < button_x + button.getWidth()
					&& y > button_y && y < button_y + button.getHeight()) {
				sound.play(1, 0);
				mainActivity.toMyView();
				
			}
		}
		return false;
	}

	@Override
	public void initBitmap() {
		logo = BitmapFactory.decodeResource(getResources(),
				R.drawable.shoot_copyright);
		fly = BitmapFactory.decodeResource(getResources(), R.drawable.fly);
		button = BitmapFactory.decodeResource(getResources(),
				R.drawable.game_start);

		logo_x = screen_width / 2 - logo.getWidth() / 2;
		logo_y = screen_height / 5;
		fly_x = screen_width / 2 - fly.getWidth() / 2;
		fly_y = logo_y + logo.getHeight() + fly.getWidth() / 3;
		button_x = screen_width / 2 - button.getWidth() / 2;
		button_y = screen_height - 3 * button.getHeight();

	}

	@Override
	public void myDraw() {
		try {
			canvas = sur.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);

				canvas.drawBitmap(logo, logo_x, logo_y, paint);
				canvas.drawBitmap(button, button_x, button_y, paint);

				canvas.save();
				canvas.clipRect(fly_x, fly_y, fly_x + fly.getWidth(), fly_y
						+ fly.getHeight() / 3);
				canvas.drawBitmap(fly, fly_x,
						fly_y - currentFrome * fly.getHeight() / 3, paint);
				currentFrome++;
				if (currentFrome >= 3) {
					currentFrome = 0;
				}
				canvas.restore();

				paint.setTextSize(30);
				paint.setAntiAlias(true);
				float textLong = paint.measureText(name, 0, name.length());
				canvas.drawText(name, screen_width / 2 - textLong / 2, 60,
						paint);
			}
		} catch (Exception e) {
		} finally {
			if (canvas != null) {
				sur.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public void run() {
		while (threadFlag) {
			long startTime = System.currentTimeMillis();
			myDraw();
			long endTime = System.currentTimeMillis();
			try {
				if ((endTime - startTime) < 500) {
					Thread.sleep(500 - (endTime - startTime));
				}
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screen_width = this.getWidth();
		screen_height = this.getHeight();
		initBitmap();

		if (!threadFlag) {
			threadFlag = true;
			t = new Thread(this);
			t.start();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		release();
		threadFlag = false;

	}

	@Override
	public void release() {
		if (!logo.isRecycled()) {
			logo.recycle();
		}
		if (!fly.isRecycled()) {
			fly.recycle();
		}
		if (!button.isRecycled()) {
			button.recycle();
		}
	}

}
