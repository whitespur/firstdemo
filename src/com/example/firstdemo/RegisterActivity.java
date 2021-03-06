
package com.example.firstdemo;

import com.example.firstdemo.data.User;
import com.example.firstdemo.data.UserService;
import com.example.firstdemo.order.OrderCoach;
import com.example.firstdemo.utils.CheckNet;
import com.example.firstdemo.utils.HttpConnection;
import com.example.firstdemo.utils.NetThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    EditText phone;
    RadioGroup sex;
    Button register;
    boolean result;
    private String url;
    private static final String TAG = "RegisterActivity";
    
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            Log.d(TAG,"mHandler msg is "+msg.what);
            switch (msg.what) {
              case 0:
                  Toast.makeText(getApplicationContext(), "Register success", Toast.LENGTH_LONG).show();
                  Intent intent = new Intent();
                  intent.setClass(getApplicationContext(), OrderCoach.class);
                  startActivity(intent);
                  finish();
                  break;
              case -1:
                  Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_LONG).show();
                  break;
              default:
                  Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_LONG).show();
                  break;
          }
        };  
      };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        findViews();
        register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String name = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String agestr = age.getText().toString().trim();
                String phonestr=phone.getText().toString().trim();
                String sexstr = ((RadioButton) RegisterActivity.this.findViewById(sex.getCheckedRadioButtonId())).getText().toString();
                Log.d(TAG, name + "_" + pass + "_" + agestr + "_" + sexstr);
                UserService uService = new UserService(RegisterActivity.this);
                User user = new User();
                user = uService.query(name);
                Log.d(TAG, "query name is " + user.getUsername());
                if (TextUtils.isEmpty(user.getUsername())) {// no this name
                                                            // before
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(agestr) || TextUtils.isEmpty(sexstr)) {
                        Toast.makeText(RegisterActivity.this, "Register failed,please fill in your information completely", Toast.LENGTH_LONG).show();
                    } else {
                        user.setUsername(name);
                        user.setPassword(pass);
                        user.setAge(Integer.parseInt(agestr));
                        user.setSex(sexstr);
                        user.setPhone(phonestr);
                        // network judge
                        // no network tip
                        if (!CheckNet.hasInternet(getApplicationContext())) {
                            // TODO use dialog instead
                            Toast.makeText(RegisterActivity.this, "no network,please connect your network first", Toast.LENGTH_LONG).show();
                        } else {
                            result = uService.register(user);
                            uService.login(name, pass);
                            url = HttpConnection.BaseURL + "/init?username=" +name + "&passwd=" + pass+ "&phone="+phonestr+"&age=" +agestr+ "&sex="
                                    + sexstr;
                            Log.d(TAG,"url is "+url);
                            new NetThread(mHandler,url).start();

                        }

                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Regitster failed", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void findViews() {
        username = (EditText) findViewById(R.id.usernameRegister);
        password = (EditText) findViewById(R.id.passwordRegister);
        age = (EditText) findViewById(R.id.ageRegister);
        phone = (EditText) findViewById(R.id.phoneRegister);
        sex = (RadioGroup) findViewById(R.id.sexRegister);
        register = (Button) findViewById(R.id.Register);
    }

}
