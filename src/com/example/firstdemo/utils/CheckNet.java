/**
 * 
 */
package com.example.firstdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author jinglong.jjl
 *
 */
public class CheckNet {
    /**
     * 
     * @param activity
     * @return boolean return true if the application can access the internet
     */
    public static boolean hasInternet(Context context) {
        // ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {

                // ��ȡ�������ӹ���Ķ���
                NetworkInfo info = connectivity.getActiveNetworkInfo();

                if (info != null && info.isConnected()) {
                    // �жϵ�ǰ�����Ƿ��Ѿ�����
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
