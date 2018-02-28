package cc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**在此类中封装对SQLite数据库的操作*/
public class DBContext {
	private DBHelper dbHelper;
	public  DBContext(Context context){
		dbHelper=new DBHelper(context,
				"score_list.db", null,1);//1表示版本，null (CursorFactory)
	}


	public long insert(ContentValues values){
		//获得数据库
		SQLiteDatabase db=
				dbHelper.getWritableDatabase();
		//执行数据的插入操作
		long id=db.insert("score_list",
				null, values);
		//释放资源
		db.close();
		return id;
	}

	/*	public void delete(int id){
            SQLiteDatabase db=
                    dbHelper.getWritableDatabase();
            String sql="delete from score_list where id=?";
            db.execSQL(sql, new String[]{String.valueOf(id)});
        }
    **/
	public Cursor select(){
		SQLiteDatabase db=
				dbHelper.getWritableDatabase();
		String sql="select * from score_list order by score desc";

		return	db.rawQuery(sql, new String[]{});
	}

	//操作SQLite数据库的工具类
	private class DBHelper extends SQLiteOpenHelper{
		//父类中没有无参构造函数
		public DBHelper(Context context, String name, CursorFactory factory,
						int version) {
			super(context, name, factory, version);
		}
		/**此方法在数据库创建时自动调用
		 * 只调用1次，具体在此方法中做什么
		 * 取决于具体业务*/




		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql="create table score_list(_id int primary_key auto increment," +
					"score int,"
					+"name varchar(100))";
			db.execSQL(sql);

		}
		/**此方法在数据库升级时自动调用
		 * 一般指的是数据库的版本发生变化，
		 * 具体在此方法中做什么
		 * 取决于具体业务*/
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i("TAG", "onUpgrade");
			/*String sql="drop table notetab";
			db.execSQL(sql);*/
		}

	}
}