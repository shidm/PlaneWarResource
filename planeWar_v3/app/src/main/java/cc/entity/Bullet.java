package cc.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
	private Bitmap bullet;
	private int bulletX;
	private int bulletY;
	private int bmpH;
	private int bmpW;
	//子弹是否超出屏幕，优化处理
	public boolean isDead=false;

	//构造方法，
	public Bullet(Bitmap bullet,int bulletX,int bulletY){
		this.bullet=bullet;
		bmpH=bullet.getHeight();
		bmpW=bullet.getWidth();
		this.bulletX=bulletX;
		this.bulletY=bulletY;
	}

	public void draw(Canvas canvas,Paint paint){

		canvas.drawBitmap(bullet,bulletX,bulletY, paint);

	}
	public void logic(){
		bulletY-=20;
		if(bulletY==-20){
			isDead=true;
		}
	}
	public int getBulletX() {
		return bulletX;
	}

	public int getBulletY() {
		return bulletY;
	}

	public int getBmpH() {
		return bmpH;
	}

	public int getBmpW() {
		return bmpW;
	}

	public boolean isTouch(Enemy e){
		return  	bulletX>e.getEnemyX()
				&& bulletX<e.getEnemyX()+e.getBmpW()
				&&	bulletY>e.getEnemyY()
				&&  bulletY<e.getEnemyY()+e.getBmpH();
	}
}