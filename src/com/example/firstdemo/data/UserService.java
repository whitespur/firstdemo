
package com.example.firstdemo.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.firstdemo.data.UserDatabaseHelper.OrderColumns;
import com.example.firstdemo.data.UserDatabaseHelper.Tables;
import com.example.firstdemo.data.UserDatabaseHelper.UserColumns;
import com.example.firstdemo.utils.HttpConnection;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner.Result;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;


public class UserService {
    private UserDatabaseHelper dbHelper;
    private static final String TAG = "UserService";
    private String retString;
    private int code = 0;

    public UserService(Context context) {
        dbHelper = new UserDatabaseHelper(context);
    }

    public boolean login(String username, String password) {//need use handler get result from server
        // query local database
        /*
         * SQLiteDatabase sdb=dbHelper.getReadableDatabase(); String
         * sql="select * from user where username=? and password=?"; Cursor
         * cursor=sdb.rawQuery(sql, new String[]{username,password});
         * if(cursor.moveToFirst()==true){ cursor.close(); sdb.close(); return
         * true; } return false;
         */
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();
        String sql = "select * from "+Tables.ORDER;
        Cursor cursor = sdb.rawQuery(sql, null);
        if(null!=cursor && cursor.moveToFirst()){
            if (!TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(OrderColumns.USER_NAME)))) {
                //update curren user in order_table
                sql = "update "+Tables.ORDER+" set "+OrderColumns.USER_NAME+" = ? "+" where Id = 1";
                Object obj[] = {
                        username
                };
                sdb.execSQL(sql,obj);
            }
        }else{
            sql = "insert into "+Tables.ORDER+"(user_name) values(?)";//save curren user name to order table
            Object obj[] = {
                    username
            };
            sdb.execSQL(sql, obj);
        }
       /* sql = "insert into "+Tables.ORDER+"(user_name) values(?)";//save curren user name to order table
        Object obj[] = {
                username
        };
        sdb.execSQL(sql, obj);*/
        sdb.close();
        
        code = 0;
        Log.d(TAG,"login retString is " + retString + " code is " + code);
        if(code == 0){
            return true;
        }else{
            return false;
        }

    }

    public boolean register(User user) {
        boolean ret = false;
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();
        String sql = "insert into "+Tables.USER+"(username,password,phone,age,sex) values(?,?,?,?,?)";
        Object obj[] = {
                user.getUsername(), user.getPassword(),user.getPhone(), user.getAge(), user.getSex()
        };
        sdb.execSQL(sql, obj);
        sql = "insert into "+Tables.ORDER+"(user_name) values(?)";//save curren user name to order table
        Object obj1[] = {
                user.getUsername()
        };
        sdb.execSQL(sql, obj1);
        sdb.close();

        Log.d(TAG, "register retString is " + retString + " code is " + code);
        if (code == 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean Book(String user,long time,String place){//TODO save order info to database
        code = 0;
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();
        String sql = "insert into "+Tables.ORDER+"(user_name,order_time,order_place) values(?,?,?)";
        Object obj[] = {
                user,time,place
        };
        sdb.execSQL(sql, obj);
        sdb.close();
        if(code == 0){
            return true;
        }else{
            return false;
        }
        
    }

    // insert same as register
    public void insert(User user) {

    }

    // delete
    public void delete(String username) {
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        String sql = "delete from "+Tables.USER+" where username = '" + username + "'";
        sdb.execSQL(sql);
    }

    // update
    public void update(User user) {

    }

    public User query(String username) {
        User retUser = new User();
        Cursor cursor;
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        String sql = "select * from "+Tables.USER+" where username = '" + username + "'";
        cursor = sdb.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            if (!TextUtils.isEmpty(cursor.getString(0))) {
                Log.i(TAG, "pass is " + cursor.getString(2));
                retUser.setUsername(cursor.getString(cursor.getColumnIndex(UserColumns.NAME)));
                retUser.setPassword(cursor.getString(cursor.getColumnIndex(UserColumns.PASSWORD)));
                retUser.setAge(cursor.getInt(cursor.getColumnIndex(UserColumns.AGE)));
                retUser.setSex(cursor.getString(cursor.getColumnIndex(UserColumns.SEX)));
            }

        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return retUser;
    }
    
    public String getCurrentUser(){
        String ret=null;
        Cursor cursor;
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        String sql = "select * from "+Tables.ORDER;
        cursor = sdb.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            if (!TextUtils.isEmpty(cursor.getString(0))) {
                ret = cursor.getString(cursor.getColumnIndex(OrderColumns.USER_NAME));
            }
        }
        Log.d(TAG,"getCurrentUser ret is "+ret);
        return ret;
    }

}
