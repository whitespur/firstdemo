package com.example.firstdemo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class UserProvider extends ContentProvider {

	public static final String AUTHORITY = "cn.csdn";

	private static final int BLACK = 0;
	UserDatabaseHelper mOpenHelper;
	SQLiteDatabase mDatabase;
	private static final String[] TABLE_NAMES = { UserDatabaseHelper.Tables.USER, };

	private static final UriMatcher sUriMatcher;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		sUriMatcher.addURI(AUTHORITY, "black", BLACK);

	}

	public boolean onCreate() {
		mOpenHelper = new UserDatabaseHelper(this.getContext());
		mDatabase = mOpenHelper.getWritableDatabase();
		return true;
	}

	// private SQLiteDatabase mDatabase;
	public synchronized SQLiteDatabase getDatabase(Context context) {
		
		try {
			mOpenHelper = new UserDatabaseHelper(context);
			mDatabase = mOpenHelper.getWritableDatabase();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mDatabase;
	}

	public String getType(Uri uri) {
		return null;
	}

	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor=null;
		return cursor;
	}

	public Uri insert(Uri uri, ContentValues initialValues) {
		mDatabase = mOpenHelper.getWritableDatabase();
		return uri;
	}

	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int a=0;
		return a;
	}

	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int a =0;
		return a;
	}

}
