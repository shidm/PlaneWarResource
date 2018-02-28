package cc.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cc.db.DBContext;

import com.example.planewar_v2.R;

public class OverActivity extends Activity {
	private TextView tv;
	private SQLiteDatabase db;
	private DBContext dbc;
	private EditText et;
	private String s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_over);
		getWindow().
				setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
		tv=(TextView)findViewById(R.id.textView1);
		et=(EditText)findViewById(R.id.editText1);
		Intent gettedIntent=getIntent();
		s=gettedIntent.getStringExtra("score");
		tv.setText("得分:"+s);
		//打开数据库
		db=openOrCreateDatabase("score_list.db",
				Context.MODE_PRIVATE, null);
		dbc=new DBContext(this);
	}
	public void onClick(View view){
		ContentValues values=new ContentValues();
		values.put("name", et.getText().toString().trim());
		values.put("score",s.trim());
		dbc.insert(values);
		Intent intent=new Intent(this,MenuActivity.class);
		startActivity(intent);
		finish();
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