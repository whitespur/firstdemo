
package com.example.firstdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstdemo.data.User;
import com.example.firstdemo.data.UserService;
import com.example.firstdemo.order.OrderCoach;
import com.example.firstdemo.utils.HttpConnection;
import com.example.firstdemo.utils.NetThread;

public class LoginActivity extends Activity {
    EditText username;
    EditText password;
    Button login, register;
    boolean result;
    private String url;
    private static final String TAG = "LoginActivity";
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            Log.d(TAG,"mHandler msg is "+msg.what);
            switch (msg.what) {
              case 0:
                  Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();
                  Intent intent = new Intent();
                  intent.setClass(getApplicationContext(), OrderCoach.class);
                  startActivity(intent);
                  finish();
                  break;
              case -1:
                  Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                  break;
              default:
                  Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                  break;
          }
        };  
      };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }

    private void findViews() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        login.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String name = username.getText().toString();
                String pass = password.getText().toString();
                Log.i(TAG, "login input"+name + "_" + pass);
                UserService uService = new UserService(LoginActivity.this);
                //User retUser = uService.query(name);
                result = uService.login(name, pass);
                url = HttpConnection.BaseURL+ "/login?username="+name+"&passwd="+pass;
                Log.d(TAG,"url is "+url);
                new NetThread(mHandler, url).start();
                Log.d(TAG,"login resutl is "+result);

            }
        });
        register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


}
