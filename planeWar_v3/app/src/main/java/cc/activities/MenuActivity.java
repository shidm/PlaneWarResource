package cc.activities;


import com.example.planewar_v2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().
				setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}


	public void onClick(View view){
		switch (view.getId()) {
			case R.id.menu_start:
				Intent intent=new Intent(this,GameActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.menu_sortlist:
				Intent intent3=new Intent(this,ScoreListActivity.class);
				startActivity(intent3);
				break;
			case R.id.menu_help:
				Intent intent2=new Intent(this,HelpActivity.class);
				startActivity(intent2);
				break;
			case R.id.menu_quit:
				finish();
				break;
		}
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
			Toast.makeText(this, "再按一次返回键退出", 0).show();
		}else{
			finish();
		}
	}

}