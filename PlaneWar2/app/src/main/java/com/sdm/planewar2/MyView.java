package com.sdm.planewar2;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MyView extends BasicView {
	private Bitmap bj;
	private Bitmap button;
	private float scalex;
	private float scaley;

	private GameGoods goods;
	private MyPlane myPlane;
	private BigPlane bigPlane;
	private BossBullet bossBullet;
	private List<GameObject> bullets;
	private List<GameObject> planes;

	private int sumScore;
	private boolean isPlay;

	private int time;
	private int smallCount;
	private int middleCount;
	private int bigCount;

	private MainActivity mainActivity;
	private Handler handler;
	private SoundPlay sound;

	private static final String TAG = "MyView";

	@SuppressLint("HandlerLeak")
	public MyView(Context context) {
		super(context);
		mainActivity = (MainActivity) context;
		sound = new SoundPlay(mainActivity);
		sound.initSound();

		time = 1;
		smallCount = 0;
		middleCount = 0;
		bigCount = 0;
		isPlay = true;

		goods = new GameGoods(getResources());
		myPlane = new MyPlane(getResources());
		bossBullet = new BossBullet(getResources());
		bullets = new ArrayList<GameObject>();
		for (int i = 0; i < 8; i++) {
			Bullet2 bullet = new Bullet2(getResources());
			bullets.add(bullet);
		}

		planes = new ArrayList<GameObject>();
		bigPlane = new BigPlane(getResources());
		planes.add(bigPlane);
		for (int j = 0; j <= 8; j++) {
			SmallPlane smallPlane = new SmallPlane(getResources());
			planes.add(smallPlane);
		}
		for (int j = 0; j < 10; j++) {
			MiddlePlane middlePlane = new MiddlePlane(getResources());
			planes.add(middlePlane);
		}

		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == 1) {
					Log.d(TAG, "handleMessage: ");
					mainActivity.toEndView(sumScore);
				}
			}
		};
	}

	@Override
	public void initBitmap() {
		bj = BitmapFactory
				.decodeResource(getResources(), R.drawable.background);
		button = BitmapFactory.decodeResource(getResources(), R.drawable.play);

		scalex = screen_width / bj.getWidth();
		scaley = screen_height / bj.getHeight();
	}

	@Override
	public void myDraw() {
		try {
			canvas = sur.lockCanvas();
			if (canvas != null) {

				canvas.save();// ---------------------------------》画背景
				canvas.scale(scalex, scaley, 0, 0);
				canvas.drawBitmap(bj, 0, 0, paint);
				canvas.restore();

				for (GameObject obj : bullets) {// ---------------------》画子弹
					if (obj.isAlive) {
						for (GameObject obje : planes) {
							if (obje.isAlive && !obje.isExplosion) {
								if (obj.isCollide(obje)) {
									obje.attacked(obj.harm);
									if (obje.isExplosion) {
										if (obje instanceof SmallPlane) {
											sound.play(4, 0);
										}
										if (obje instanceof MiddlePlane) {
											sound.play(5, 0);
										}
										if (obje instanceof BigPlane) {
											sound.play(6, 0);
										}
										addSumScore(obje.score);
									}
									break;
								}

							}
						}
						obj.myDraw(canvas);
						// sound.play(3, 0);
					}

				}

				if (bossBullet.isAlive && bigPlane.isAlive) {// ----------------------------------》画boss敌机子弹
					if (bossBullet.isCollide(myPlane)) {
						myPlane.isExplosion = true;
					}
					bossBullet.myDraw(canvas);
				}

				for (GameObject obje : planes) {// ------------------------》画飞机
					if (obje.isAlive) {
						obje.myDraw(canvas);
						if (!obje.isExplosion && myPlane.isAlive) {
							if (obje.isCollide(myPlane)) {
								myPlane.isExplosion = true;
								Log.d(TAG, "isExplosion: ");
							}
						}
					}
				}

				if (!myPlane.isAlive) {
					sound.play(7, 0);
					Log.d(TAG, "!myPlane.isAlive");
					threadFlag = false;
				}

				myPlane.myDraw(canvas);// ------------------------》画玩家飞机

				if (goods.getId_1() == 1) {// -------------------------------------》画物品
					if (goods.isAlive) { // -----------------》注意：如果以分数判断时，为此分数时才会画，分数一过则消失
						if (!goods.isExplosion) {
							if (goods.isCollide(myPlane)) {
								sound.play(2, 0);
							}
						}
						goods.myDraw(canvas, 0);
					}
				}
				if (goods.getId_2() == 2) {
					if (goods.getAlive1()) {
						if (goods.isCollide1(myPlane)) {
							bullets.clear();
							for (int i = 0; i < 8; i++) {
								Bullet1 bullet = new Bullet1(getResources());
								bullets.add(bullet);
							}
							sound.play(2, 0);
						}
						goods.myDraw(canvas, 1);
					}
				}

				canvas.save();
				if (isPlay) {// ----------------------------------》画暂停按钮
					canvas.clipRect(screen_width - button.getWidth() - 10, 10,
							screen_width - 10, 10 + button.getHeight() / 2);
					canvas.drawBitmap(button, screen_width - button.getWidth()
							- 10, 10, paint);
				} else {
					canvas.save();
					canvas.clipRect(screen_width - button.getWidth() - 10, 10,
							screen_width - 10, 10 + button.getHeight() / 2);
					canvas.drawBitmap(button, screen_width - button.getWidth()
							- 10, 10 - button.getHeight() / 2, paint);
				}
				canvas.restore();

				paint.setTextSize(30);// --------------------------》画分数
				paint.setAntiAlias(true);
				canvas.drawText("分数：" + String.valueOf(sumScore), 5, 30, paint);

			}
		} catch (Exception e) {
		} finally {
			if (canvas != null) {
				sur.unlockCanvasAndPost(canvas);
			}
		}
	}

	public void initObject() {
		if(sumScore>=1000){// -----------------------------------------》初始化物品
			goods.setId_1();
		}
		if(sumScore>=3000){
			goods.setId_2();
		}
		for (GameObject obj : bullets) {// ---------------------------》初始化子弹
			if (!obj.isAlive) {
				obj.initial(0, myPlane.getMiddle_x(), myPlane.getMiddle_y(), 0);
				break;
			}
		}

		for (GameObject obje : planes) {// ------------------------------》初始化敌机
			if (obje instanceof SmallPlane) {
				if (!obje.isAlive) {
					obje.initial(smallCount, 0, 0, time);
					smallCount++;
					if (smallCount >= 8) {
						smallCount = 0;
					}
					break;

				}
			}

			if (obje instanceof MiddlePlane) {
				if (sumScore >= 5000) {
					if (!obje.isAlive) {
						obje.initial(middleCount, 0, 0, time);
						middleCount++;
						if (middleCount >= 4) {
							middleCount = 0;
						}
						break;
					}
				}
			}

			if (obje instanceof BigPlane) {
				if (sumScore >= 10000) {
					if (!obje.isAlive) {
						obje.initial(bigCount, 0, 0, time);
						bigCount++;
						if (bigCount >= 2) {
							bigCount = 0;
						}
						break;

					}
				}
			}
		}

		if (!bossBullet.isAlive
				&& bigPlane.object_y == screen_height / 2
				- bigPlane.object_height / 2) {// ----------------------------------》初始化boss敌机子弹
			bossBullet.initial(0, bigPlane.get_x(), bigPlane.get_y(), 0);
		}

		if (sumScore >= time * 5000 && time < 8) {
			time++;
		}
	}

	@Override
	public void run() {
		while (threadFlag) {
			long startTime = System.currentTimeMillis();
			initObject();
			myDraw();
			long endTime = System.currentTimeMillis();
			if (!isPlay) {
				try {
					synchronized (t) {
						t.wait();
					}
				} catch (Exception e) {
				}
			}
			try {
				if ((endTime - startTime) < 50) {
					Thread.sleep(50 - (endTime - startTime));
				}
			} catch (Exception e) {
			}
		}
		Log.d(TAG, "threadFlag: "+threadFlag);
		handler.sendEmptyMessage(1);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screen_width = this.getWidth();
		screen_height = this.getHeight();
		initBitmap();

		goods.initScreen(screen_width, screen_height);
		myPlane.initScreen(screen_width, screen_height);
		bossBullet.initScreen(screen_width, screen_height);
		for (GameObject obj : bullets) {
			obj.initScreen(screen_width, screen_height);
		}

		for (GameObject obje : planes) {
			obje.initScreen(screen_width, screen_height);
		}

		if (!threadFlag) {
			threadFlag = true;
			t = new Thread(this);
			t.start();
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& event.getPointerCount() == 1) {

			//左下角的导弹（大招）
			if (x > 5 && x < 5 + goods.object_width
					&& y > screen_height - goods.object_height - 5
					&& y < screen_height - 5) {

				//isExplosion表示已经碰撞过了，isAlive表示goods还存活着（吃到了这个道具）
				if (goods.isExplosion && goods.isAlive) {
					for (GameObject obj : planes) {
						if (obj.isAlive) {
							obj.attacked(20);
							if (obj.isExplosion) {
								sound.play(7, 0);
								addSumScore(obj.score);
								goods.isAlive = false;
							}
						}
					}
				}
			}

			if (x > screen_width - button.getWidth() - 10
					&& x < screen_width - 10 && y > 10
					&& y < 10 + button.getHeight() / 2) {
				if (isPlay) {
					isPlay = false;
				} else {
					isPlay = true;
					synchronized (t) {
						t.notify();
					}
				}
			}
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE
				&& event.getPointerCount() == 1) {
			myPlane.initObject_xy(x, y);
			return true;
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		release();
		Log.d(TAG, "surfaceDestroyed:");
		threadFlag = false;
	}

	@Override
	public void release() {
		if (!bj.isRecycled()) {
			bj.recycle();
		}
		if (!button.isRecycled()) {
			button.recycle();
		}
		goods.release();
		myPlane.release();
		bossBullet.release();

		for (GameObject obj : bullets) {
			obj.release();
		}
		for (GameObject obje : planes) {
			obje.release();
		}
	}

	public void addSumScore(int score) {
		sumScore += score;
	}

}