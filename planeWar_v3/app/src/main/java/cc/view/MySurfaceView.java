package cc.view;

import java.util.Iterator;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import cc.activities.OverActivity;
import cc.entity.Background;
import cc.entity.Bullet;
import cc.entity.Enemy;
import cc.entity.Player;

import com.example.planewar_v2.R;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {
    public static int score = 0;
    Activity gameActivity;
    public static final int GAMING = 1;
    public static final int GAME_WIN = 2;
    public static final int GAME_LOST = 3;
    public static final int GAME_PAUSE = -1;

    public static int gameState = GAMING;
    public static int screenW;
    public static int screenH;
    public static int currentP = 0;
    private Resources res = this.getResources();

    //声明Bimtmap资源对象
    private Bitmap player[];
    private Bitmap background;
    private Bitmap gameLost;
    private Bitmap bullet;
    private Bitmap enemy;

    //声明游戏元素类的实例
    private Canvas canvas;
    private Background mBackground;
    private Player mPlayer;
    private Bullet mBullet;
    private Vector<Bullet> vcBullet;
    private int countBullet = 0;

    private Vector<Enemy> vcEnemy;

    private SurfaceHolder sfh;
    private Paint paint;
    private boolean flag = true;
    Thread t;
    Bitmap fishBmp[] = new Bitmap[10];

    private int count;

    private TextPaint p;

    private Iterator<Enemy> eIt;

    private Iterator<Bullet> bIt;

    private int lvl = 1;

    public MySurfaceView(Context context) {
        super(context);
        gameActivity = (Activity) context;
        initGame();
    }

    //实例对象的初始化
    private void initGame() {
        player = new Bitmap[5];
        for (int i = 0; i < 5; i++) {
            player[i] = BitmapFactory.decodeResource(this.getResources(), R.drawable.angry_birds_00 + i);
        }
        background = BitmapFactory.decodeResource(this.getResources(), R.drawable.background);
        bullet = BitmapFactory.decodeResource(this.getResources(), R.drawable.bullet);
        enemy = BitmapFactory.decodeResource(this.getResources(), R.drawable.enemy);
        //实例SurfaceHolder对象
        sfh = this.getHolder();
        //为SurfaceView添加状态监听
        sfh.addCallback(this);
        //实例一个画笔
        paint = new Paint();
        paint.setColor(Color.BLACK);
        vcBullet = new Vector<Bullet>();
        vcEnemy = new Vector<Enemy>();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenH = this.getHeight();
        screenW = this.getWidth();
        mBackground = new Background(background);
        mPlayer = new Player(player);
        mBullet = new Bullet(bullet, mPlayer.getPlayerX(), mPlayer.getPlayerY());

        p = new TextPaint();
        p.setTextSize(30);
        p.setColor(Color.GREEN);
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    private void myDraw() {
        //绘图根据游戏状态不同，绘制不同
        canvas = sfh.lockCanvas();
        switch (gameState) {
            case GAMING:
                if (currentP < 4) {
                    currentP++;
                } else {
                    currentP = 0;
                }
                mBackground.draw(canvas, paint);
                mPlayer.draw(canvas, paint);
                mBullet.draw(canvas, paint);
                for (Bullet b : vcBullet) {
                    if (b.isDead) {
                        vcBullet.remove(b);
                    } else {
                        b.draw(canvas, paint);
                    }
                }
                for (Enemy e : vcEnemy) {
                    e.draw(canvas, paint);
                }

                canvas.drawText("获得的分数：" + score, 30, 30, p);

                break;
            case GAME_LOST:
            case GAME_WIN:
                break;
            case GAME_PAUSE:
                break;

        }
        //解锁
        //canvas.drawText("", 100,100,200,200,paint);
        //	canvas.restore();

        sfh.unlockCanvasAndPost(canvas);

    }

    private void logic() {
        //根据不同绘制不同逻辑
        switch (gameState) {
            case GAMING:
                //小鸟动画效果
                mBackground.logic();
                mBullet.logic();
                for (Enemy e : vcEnemy) {
                    e.logic();
                }
                mPlayer.logic();
                //子弹逻辑
                countBullet++;
                if (countBullet > 10) {
                    //并不是绘制一次就加一次子弹，因为这样的话会由于刷新时间非常短引起用户看来几乎就是激光而不是子弹
                    // 若刷新间隔太长，用户会觉得卡顿所以设置刷新几次打出一颗子弹
                    countBullet = 0;
                    vcBullet.add(new Bullet(bullet, mPlayer.getPlayerX() + mPlayer.getBmpW() / 2
                            , mPlayer.getPlayerY()));
                }
                //遍历子弹 ，并检测子弹与敌人的碰撞
                bIt = vcBullet.iterator();
                while (bIt.hasNext()) {

                    Bullet b = bIt.next();
                    b.logic();
                    for (Enemy e : vcEnemy) {
                        if (b.isTouch(e)) {
                            e.setDead();
                            bIt.remove();
                            break;
                        }
                    }
                }
                eIt = vcEnemy.iterator();
                while (eIt.hasNext()) {
                    Enemy e = eIt.next();
                    if (e.isDead()) {
                        eIt.remove();
                        score++;
                        if (score % 30 == 0) lvl++;
                    } else if (e.getEnemyY() > MySurfaceView.screenH) {
                        eIt.remove();
                    } else if (e.click(mPlayer)) {
                        mPlayer.setDead();
                        if (mPlayer.isDead) {
                            flag = false;
                            Intent intent = new Intent(gameActivity, OverActivity.class);
                            intent.putExtra("score", "" + score);
                            gameActivity.startActivity(intent);
                            gameActivity.finish();

                        }
                    }
                }
                //敌机逻辑
                count++;
                if (count > 20) {
                    count = 0;
                    if (vcEnemy.size() < lvl + 2) {
                        vcEnemy.add(new Enemy(enemy));
                    }
                }
                break;
            case GAME_LOST:
                break;
            case GAME_PAUSE:
                break;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    /*	if(vcEnemy!=null) vcEnemy=null;
		if(vcBullet!=null) vcBullet=null;
		if(mPlayer!=null)mPlayer =null;
		if(mBackground!=null)mBackground=null;	
		*/
    }

    @Override
    public void run() {
        while (flag) {

            long t1 = System.currentTimeMillis();
            myDraw();
            logic();
            long t2 = System.currentTimeMillis();
            if (t2 - t1 < 50) {
                try {
                    Thread.sleep(50 - (t2 - t1));
                } catch (InterruptedException e) {
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (gameState) {
            case GAMING:
                break;
            case GAME_LOST:
                break;
            case GAME_PAUSE:
                break;
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (gameState) {
            case GAMING:
                break;
            case GAME_LOST:
                break;
            case GAME_PAUSE:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (gameState) {
            case GAMING:
                mPlayer.onTouchEvent(event);
                break;
            case GAME_LOST:
                break;
            case GAME_PAUSE:
                break;
        }
        return true;
    }

}