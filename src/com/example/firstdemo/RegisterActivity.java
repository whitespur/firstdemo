package com.example.firstdemo;

import com.example.firstdemo.data.User;
import com.example.firstdemo.data.UserService;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	EditText username;
	EditText password;
	EditText age;
	RadioGroup sex;	
	Button register;
	private static final String TAG = "RegisterActivity";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		findViews();
		register.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String name=username.getText().toString().trim();
				String pass=password.getText().toString().trim();
				String agestr=age.getText().toString().trim();
				String sexstr=((RadioButton)RegisterActivity.this.findViewById(sex.getCheckedRadioButtonId())).getText().toString();
				Log.i(TAG,name+"_"+pass+"_"+agestr+"_"+sexstr);
				UserService uService=new UserService(RegisterActivity.this);
				User user=new User();
				user = uService.query(name);
				Log.i(TAG,"query name is "+user.getUsername());
				if(TextUtils.isEmpty(user.getUsername())){
					user.setUsername(name);
					user.setPassword(pass);
					user.setAge(Integer.parseInt(agestr));
					user.setSex(sexstr);
					uService.register(user);
					Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(RegisterActivity.this, "Regitster failed", Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}
	private void findViews() {
		username=(EditText) findViewById(R.id.usernameRegister);
		password=(EditText) findViewById(R.id.passwordRegister);
		age=(EditText) findViewById(R.id.ageRegister);
		sex=(RadioGroup) findViewById(R.id.sexRegister);
		register=(Button) findViewById(R.id.Register);
	}

}
