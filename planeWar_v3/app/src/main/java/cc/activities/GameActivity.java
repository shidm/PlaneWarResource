package cc.activities;

import cc.db.DBContext;

import cc.view.MySurfaceView;

import com.example.planewar_v2.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MySurfaceView myView=new MySurfaceView(this);
		setContentView(myView);
		getWindow().
				setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);


	}

	private boolean isExist;
	@Override
	public void onBackPressed() {
		if(!isExist){
			isExist=true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					isExist=false;
				}
			}, 5000);
			Toast.makeText(this, "再按一次返回键回主菜单", 0).show();
		}else{
			Intent intent=new Intent(this,MenuActivity.class);
			startActivity(intent);
			finish();
		}
	}

}