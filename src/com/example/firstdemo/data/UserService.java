package com.example.firstdemo.data;

import com.example.firstdemo.data.UserDatabaseHelper.UserColumns;
import com.example.firstdemo.utils.HttpConnection;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

public class UserService {
	private UserDatabaseHelper dbHelper;
	private static final String TAG="UserService";
	public UserService(Context context){
		dbHelper=new UserDatabaseHelper(context);
	}
	
	public boolean login(String username,String password){
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql="select * from user where username=? and password=?";
		Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});		
		if(cursor.moveToFirst()==true){
			cursor.close();
			return true;
		}
		return false;
	}
	public boolean register(User user){
		SQLiteDatabase sdb=dbHelper.getWritableDatabase();
		String sql="insert into user(username,password,age,sex) values(?,?,?,?)";
		Object obj[]={user.getUsername(),user.getPassword(),user.getAge(),user.getSex()};
		sdb.execSQL(sql, obj);	
		//TODO: register info to server 
		HttpConnection mHttpConnection = new HttpConnection();
		mHttpConnection.getURL(HttpConnection.BaseURL+"?register?username="+user.getUsername()+"&password="+user.getPassword());
		return true;
	}
	//insert same as register
	public void insert(User user){
		
	}
	//delete
	public void delete(String  username){
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql = "delete from user where username = '"+username+"'";
		sdb.execSQL(sql);
	}
	//update
	public void update(User user){
		
	}
	public User query(String username){
		User retUser=new User();
		Cursor cursor;
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql ="select * from user where username = '"+username+"'";
		cursor = sdb.rawQuery(sql,null);
		if(cursor.moveToFirst() ){
			if(!TextUtils.isEmpty(cursor.getString(0))){
				Log.i(TAG,"pass is "+cursor.getString(2));
				retUser.setUsername(cursor.getString(cursor.getColumnIndex(UserColumns.NAME)));
				retUser.setPassword(cursor.getString(cursor.getColumnIndex(UserColumns.PASSWORD)));
				retUser.setAge(cursor.getInt(cursor.getColumnIndex(UserColumns.AGE)));
				retUser.setSex(cursor.getString(cursor.getColumnIndex(UserColumns.SEX)));
			}
			
		}
		if(!cursor.isClosed()){
			cursor.close();
		}
		return retUser;
	}
	
}
