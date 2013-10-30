package com.example.firstdemo;

import com.example.firstdemo.data.User;
import com.example.firstdemo.data.UserDatabaseHelper;
import com.example.firstdemo.data.UserService;

import android.test.AndroidTestCase;
import android.util.Log;


public class UserTest extends AndroidTestCase {
	public void datatest() throws Throwable{
		UserDatabaseHelper dbhepler=new UserDatabaseHelper(this.getContext());
		dbhepler.getReadableDatabase();
	}
	//×¢²á
	public void registerTest() throws Throwable{	
		UserService uService=new UserService(this.getContext());
		User user=new User();
		user.setUsername("renhaili");
		user.setPassword("123");
		user.setAge(20);
		user.setSex("Å®");
		uService.register(user);
	}
	public void loginTest() throws Throwable{
		UserService uService=new UserService(this.getContext());
		String username="renhaili";
		String password="123";
		boolean flag=uService.login(username, password);
		if(flag){
			Log.i("TAG","µÇÂ¼³É¹¦");
		}else{
			Log.i("TAG","µÇÂ¼Ê§°Ü");
		}
	}
	
}
