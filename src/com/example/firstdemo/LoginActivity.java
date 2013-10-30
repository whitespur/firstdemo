package com.example.firstdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstdemo.data.User;
import com.example.firstdemo.data.UserService;
import com.example.firstdemo.order.OrderCoach;



public class LoginActivity extends Activity {
	EditText username;
	EditText password;
	Button login,register;
	private static final String TAG="LoginActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.main);
		findViews();
	}
	private void findViews() {
		username=(EditText) findViewById(R.id.username);
		password=(EditText) findViewById(R.id.password);
		login=(Button) findViewById(R.id.login);
		register=(Button) findViewById(R.id.register);
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String name=username.getText().toString();
				String pass=password.getText().toString();
				Log.i(TAG,name+"_"+pass);
				UserService uService=new UserService(LoginActivity.this);
				/*boolean flag=uService.login(name, pass);
				if(flag){
					Log.i("TAG","��¼�ɹ�");
					Toast.makeText(LoginActivity.this, "��¼�ɹ�", Toast.LENGTH_LONG).show();
				}else{
					Log.i("TAG","��¼ʧ��");
					Toast.makeText(LoginActivity.this, "��¼ʧ��", Toast.LENGTH_LONG).show();
				}*/
				User retUser = uService.query(name);
				if(pass.equals(retUser.getPassword())){
					Log.i(TAG,"pass is"+pass);
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, OrderCoach.class);
					startActivity(intent);
					Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_LONG).show();
				}else{
					Log.i(TAG,"Login"+"pass is "+pass+"user pass is "+retUser.getPassword());
					Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_LONG).show();
				}
				
				
			}
		});
		register.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

}
