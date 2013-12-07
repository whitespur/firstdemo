package com.example.firstdemo.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class NetThread extends Thread{
    private Handler handleNet;
    private String url;
    private static final String TAG = "NetThread";
    public NetThread(Handler handler,String url) {
        super();
        this.handleNet = handler;
        this.url=url;
    }

    public void run() {
        int code =  -1;
        String retString;
        HttpConnection mHttpConnection = new HttpConnection();
        retString = mHttpConnection.getURL(url);
        try {
            JSONObject result = new JSONObject(retString);
            code = result.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"code is "+code);
        if (handleNet != null) {
            Message message = handleNet.obtainMessage();
            message.what = code;
            handleNet.sendMessage(message);
         }
    }
};
