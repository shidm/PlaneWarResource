package com.sdm.planewar2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class EndView extends BasicView {
	private Bitmap logo;
	private Bitmap button;

	private float logo_x;
	private float logo_y;
	private float button_x;
	private float button_y;

	private SoundPlay sound;
	private MainActivity mainActivity;
	private int sumScore;
	private String score;
	private FileManager file;
	private boolean flag;

	public EndView(Context context, int sumScore) {
		super(context);
		this.mainActivity = (MainActivity) context;
		sound=new SoundPlay(mainActivity);
		sound.initSound();
		this.sumScore = sumScore;
		score = "0";

		this.file = new FileManager();
		if (file.isUse()) {
			file.initFile();
			if (file.read().length() <= 0) {
				score = "0";
			} else {
				score = file.read();
			}
			if (sumScore > Integer.parseInt(score)) {
				file.write(String.valueOf(sumScore));
				flag = true;
			}
		} else {
			Toast.makeText(context, "分数保存失败", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void initBitmap() {
		logo = BitmapFactory.decodeResource(getResources(),
				R.drawable.shoot_copyright);
		button = BitmapFactory.decodeResource(getResources(),
				R.drawable.game_reagain);

		logo_x = screen_width / 2 - logo.getWidth() / 2;
		logo_y = screen_height / 5;
		button_x = screen_width / 2 - button.getWidth() / 2;
		button_y = screen_height - 3 * button.getHeight();
	}

	@Override
	public void release() {
		if (!logo.isRecycled()) {
			logo.recycle();
		}
		if (!button.isRecycled()) {
			button.recycle();
		}
	}

	@Override
	public void myDraw() {
		try {
			canvas = sur.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);

				canvas.drawBitmap(logo, logo_x, logo_y, paint);
				canvas.drawBitmap(button, button_x, button_y, paint);

				paint.setTextSize(30);
				paint.setAntiAlias(true);
				paint.setColor(Color.BLACK);
				float highestLong = paint.measureText("最高分：" + score);
				canvas.drawText("最高分：" + score, screen_width / 2 - highestLong
						/ 2, button_y - 100, paint);
				if (flag) {
					paint.setColor(Color.RED);
					float bestLong = paint.measureText("新纪录！ "
							+ String.valueOf(sumScore));
					canvas.drawText("新纪录！ " + String.valueOf(sumScore),
							screen_width / 2 - bestLong / 2, button_y - 30,
							paint);
				}else{
					float currentLong = paint.measureText("总分："
							+ String.valueOf(sumScore));
					canvas.drawText("总分：" + String.valueOf(sumScore), screen_width
							/ 2 - currentLong / 2, button_y - 30, paint);
				}
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

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		if (x > button_x && x < button_x + button.getWidth() && y > button_y
				&& y < button_y + button.getHeight()) {
			sound.play(1, 0);
			mainActivity.toMyView();
		}
		return false;
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

}