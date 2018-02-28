package com.sdm.planewar2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {
	private long exitTime;
	private StartView startView;
	private MyView myView;
	private EndView endView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		startView = new StartView(this);
		setContentView(startView);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
			}
		}
	}

	public void toMyView() {
		myView = new MyView(this);
		setContentView(myView);
		startView = null;
	}

	public void toEndView(int sumScore) {
		endView = new EndView(this, sumScore);
		setContentView(endView);
		myView = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (System.currentTimeMillis() - exitTime > 2000) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
		}

		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE,
					AudioManager.FX_FOCUS_NAVIGATION_UP);
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER,
					AudioManager.FX_FOCUS_NAVIGATION_UP);
		}
		return true;
	}

}
