package cc.entity;

import cc.view.MySurfaceView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Background {
	private Bitmap background;
	private int
			backgroundY=0;
	private RectF dst;

	private Rect rsc;;
	public Background(Bitmap background) {
		this.background = background;
		dst=new RectF();
		rsc=new Rect();
	}

	public void draw(Canvas canvas ,Paint paint){

		//canvas.scale(MySurfaceView.screenW/background.getWidth(),
		//		MySurfaceView.screenH/background.getHeight());

		rsc.set(0,background.getHeight()-MySurfaceView.screenH-backgroundY,background.getWidth(),background.getHeight()-backgroundY);
		dst.set(0, 0, MySurfaceView.screenW,MySurfaceView.screenH);

		canvas.drawBitmap(background,rsc , dst, paint);

	}
	public void logic(){
		if(background.getHeight()-MySurfaceView.screenH-backgroundY<0){
			backgroundY=0;
		}
		backgroundY+=2;
	}
}