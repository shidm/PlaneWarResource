package cc.entity;

import java.util.Random;

import cc.view.MySurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class Enemy {
	private boolean isDead = false;
	private Bitmap enemy;
	private int enemyX;
	private int enemyY;
	private int bmpH;
	private int bmpW;
	private boolean right=true;

	public boolean isAlive=true;

	public Enemy(Bitmap enemy){
		this.enemy=enemy;
		bmpH=enemy.getHeight();
		bmpW=enemy.getWidth();

		int r=new Random().nextInt(MySurfaceView.screenW-bmpW);
		enemyX=r;
		enemyY=0;
	}

	public void draw(Canvas canvas,Paint paint){

		canvas.drawBitmap(enemy, enemyX,enemyY, paint);


	}
	public void logic(){
		//敌人左右摇摆
		//	if(right)
		//	{if(enemyX>MySurfaceView.screenW-bmpW){right=false;}enemyX+=10;}
		//else if(!right){
		//	if(enemyX<0){right=true;}enemyX-=10;
		//	}
		enemyY+=10;
	}
	public Bitmap getEnemy() {
		return enemy;
	}
	public int getEnemyX() {
		return enemyX;
	}
	public int getEnemyY() {
		return enemyY;
	}
	public int getBmpH() {
		return bmpH;
	}
	public int getBmpW() {
		return bmpW;
	}
	public boolean isDead(){
		return isDead;
	}
	public void setDead(){
		isDead=true;
	}

	public boolean click(Player p) {
		return  !(p.getPlayerY()>enemyY+bmpH||p.getPlayerY()+p.getBmpH()<enemyY
				||p.getPlayerX()>enemyX+bmpW||p.getPlayerX()+p.getBmpW()<enemyX);
	}
}