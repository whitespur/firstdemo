
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
    Button login, register;
    boolean result;
    private static final String TAG = "LoginActivity";

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
                Log.d(TAG,"login resutl is "+result);
                if (result) {
                    //if (pass.equals(retUser.getPassword())) {//local database
                    Log.i(TAG, "pass is" + pass);
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, OrderCoach.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_LONG).show();
                } else {
                    Log.i(TAG, "Login failed" + "pass is " + pass);
                    Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_LONG).show();
                }

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
