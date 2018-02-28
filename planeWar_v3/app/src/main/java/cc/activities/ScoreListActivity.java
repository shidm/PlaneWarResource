package cc.activities;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import cc.db.DBContext;

import com.example.planewar_v2.R;

public class ScoreListActivity extends  Activity {
	private ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_list);

		getWindow().
				setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);

		openOrCreateDatabase("score_list.db", Context.MODE_PRIVATE, null);
		DBContext dbc=new DBContext(this);
		Cursor c=dbc.select();
		lv=(ListView)findViewById(R.id.listView1);
		SimpleCursorAdapter adapter=
				new SimpleCursorAdapter(this,
						R.layout.score_list_item,
						c,
						new String[]{"name","score"},
						new int[]{R.id.list_item_name,R.id.list_item_score},
						CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		lv.setAdapter(adapter);
	}
}