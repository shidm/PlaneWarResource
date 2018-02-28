package cc.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import cc.view.MySurfaceView;

public class Player {
	private Bitmap player[];
	private int playerX;

	private int playerMX;
	private int playerY;
	private int bmpH;
	private int bmpW;

	public boolean isDead=false;


	private float m;
	private float y;
	private boolean flag=false;

	public Player(Bitmap[] player){
		this.player=player;
		bmpH=player[MySurfaceView.currentP].getHeight();
		bmpW=player[MySurfaceView.currentP].getWidth();
		playerX=MySurfaceView.screenW/2;
		playerMX=playerX+bmpW/2;
		m= playerX;
		y= playerY;
		playerY=MySurfaceView.screenH-bmpH;

	}

	public void draw(Canvas canvas,Paint paint){

		canvas.drawBitmap(player[MySurfaceView.currentP], playerX,playerY, paint);
	}

	public void onTouchEvent(MotionEvent event){
		if(event.getAction()==MotionEvent.ACTION_MOVE){
			flag=true;
			y=(int)event.getY();
			m=(int)event.getX();
		}else if(event.getAction()==MotionEvent.ACTION_DOWN){
			flag=true;
			m=(int)event.getX();
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			flag=false;

		}


		//	playerY=(int) event.getY();
	}
	public void logic(){

		if(playerX>MySurfaceView.screenW-bmpW){
			playerX=MySurfaceView.screenW-bmpW;
		}else if(playerX<0){
			playerX=0;
		}else{
			if(flag){
				if(m<MySurfaceView.screenW/2){
					playerX-=15;}
				else if(m>MySurfaceView.screenW/2){playerX+=15;
				}
			}
		}
		//else{playerX=MySurfaceView.screenW-bmpW;}
	  
	 /* if(playerY>MySurfaceView.screenH-bmpH)playerY=MySurfaceView.screenH-bmpH;
		if(++MySurfaceView.currentP>=5)MySurfaceView.currentP=0;
 */}

	public Bitmap[] getPlayer() {
		return player;
	}

	public int getPlayerX() {
		return playerX;
	}

	public int getPlayerY() {
		return playerY;
	}

	public int getBmpH() {
		return bmpH;
	}

	public int getBmpW() {
		return bmpW;
	}

	public void setDead(){
		isDead=true;
	}

}