
package com.example.firstdemo.data;

import android.R.integer;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";

    private static final int DATABASE_VERSION = 1;

    public static final String ADD_TIME_KEY = "add_time";

    public interface Tables {
        public static final String USER = "user";
        public static final String ORDER = "order";
    }

    public interface UserColumns {
        public static final String NAME = "username";
        public static final String PASSWORD = "password";
        public static final String AGE = "age";
        public static final String SEX = "sex";
        public static final String EMAIL = "email";
    }
    
    public interface OrderColumns{
        public static final String ORDER_NUM = "order_num";
        public static final String USER_NAME = "user_name";
        public static final String ORDER_TIME = "order_time";
        public static final String ORDER_PALCE = "order_place";
        public static final String PRICE = "price";
        public static final String ORDER_STATUS = "order_status";
    }

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        Log.d("UserDatabaseHelper", "onCreate+++++++++++");
        // create user table
        db.execSQL("CREATE TABLE " + Tables.USER + " ( 'Id' INTEGER PRIMARY KEY AUTOINCREMENT, " + UserColumns.NAME + " TEXT," + UserColumns.PASSWORD
                + " TEXT," + UserColumns.AGE + " INTEGER," + UserColumns.SEX + " TEXT," + UserColumns.EMAIL + " TEXT" + ");");
        /*
         * String sql=
         * "create table user(id integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2))"
         * ; db.execSQL(sql);
         */
        // TODO create order table
        db.execSQL("CREATE TABLE "+ Tables.ORDER+" ( 'Id' INTEGER PRIMARY KEY AUTOINCREMENT, " + OrderColumns.ORDER_NUM+" INTEGER,"
                +OrderColumns.USER_NAME+" TEXT,"+OrderColumns.ORDER_TIME+" FLOAT,"+OrderColumns.ORDER_PALCE+" TEXT,"
                +OrderColumns.PRICE+" FLOAT,"+OrderColumns.ORDER_STATUS+" INTEGER"+");");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
