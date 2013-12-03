/**
 * 
 */

package com.example.firstdemo.order;

import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.ad;
import com.example.firstdemo.R;

/**
 * @author jinglong.jjl
 */
public class OrderCoach extends Activity {
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    private static final String TAG = "OrderCoach";
    private Button mDateButton;
    private Button mTimeButton;
    private Button mOrderButton;
    private Time mTime;
    private String mTimezone = Time.TIMEZONE_UTC;

    private String mPlace;
    private EditText mPlacEditText;
    public LocationClient mLocationClient = null;
    public MyLocationListenner myListener = new MyLocationListenner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_coach);
        mDateButton = (Button) findViewById(R.id.datePicker);
        mTimeButton = (Button) findViewById(R.id.timePicker);
        mOrderButton = (Button) findViewById(R.id.orderBtn);
        mPlacEditText = (EditText) findViewById(R.id.place_edit);

        mTime = new Time();
        mTime.set(System.currentTimeMillis());
        setDate(mDateButton, mTime.toMillis(true));
        setTime(mTimeButton, mTime.toMillis(true));

        mLocationClient = new LocationClient(this);
        mLocationClient.setAK("DA39ebf529ed0e2333f252d69173ea17");// for
                                                                  // FirstDemo
        mLocationClient.registerLocationListener(myListener);
        setLocationOption();
        mLocationClient.start();
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        } else {
            Log.d(TAG, "mLocation is null");
        }
        mDateButton.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View arg0) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        mTimeButton.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        mPlacEditText.setText("Hangzhou Yuhang");
        mPlace = mPlacEditText.getText().toString();
        mPlacEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                mPlace = mPlacEditText.getText().toString();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
        mOrderButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "mOrderButton onClick");
                if (mPlacEditText.getText() != null) {
                    Toast.makeText(OrderCoach.this, "预定成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderCoach.this, "预定失败，请重填信息", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mTime.year, mTime.month, mTime.monthDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, mTimeSetListener, mTime.hour, mTime.minute, true);
            default:
                return super.onCreateDialog(id);
        }

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mTime.year = year;
            mTime.month = monthOfYear;
            mTime.monthDay = dayOfMonth;
            Toast.makeText(OrderCoach.this, "year is " + mTime.year, Toast.LENGTH_SHORT).show();
            setDate(mDateButton, mTime.toMillis(true));
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mTime.hour = hourOfDay;
            mTime.minute = minute;
            Toast.makeText(OrderCoach.this, "hour is " + mTime.hour, Toast.LENGTH_SHORT).show();
            setTime(mTimeButton, mTime.toMillis(true));
        }
    };

    private void setDate(TextView view, long millis) {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH
                | DateUtils.FORMAT_ABBREV_WEEKDAY;

        String dateString;
        synchronized (TimeZone.class) {
            TimeZone.setDefault(TimeZone.getTimeZone(mTimezone));
            dateString = DateUtils.formatDateTime(this, millis, flags);
            // setting the default back to null restores the correct behavior
            TimeZone.setDefault(null);
        }
        view.setText(dateString);
    }

    private void setTime(TextView view, long millis) {
        int flags = DateUtils.FORMAT_SHOW_TIME;
        if (DateFormat.is24HourFormat(this)) {
            flags |= DateUtils.FORMAT_24HOUR;
        }
        millis = millis + 8 * 3600 * 1000;// beijing time

        String timeString;
        synchronized (TimeZone.class) {
            TimeZone.setDefault(TimeZone.getTimeZone(mTimezone));
            timeString = DateUtils.formatDateTime(this, millis, flags);
            TimeZone.setDefault(null);
        }
        view.setText(timeString);
    }

    /**
     * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.d(TAG, "onReceiveLocation start+++++++");
            if (location == null)
                return;
            StringBuffer sb = new StringBuffer(256);
            String addrStr = null;
            int result = location.getLocType();
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                /**
                 * 格式化显示地址信息
                 */
                // sb.append("\n省：");
                // sb.append(location.getProvince());
                // sb.append("\n市：");
                // sb.append(location.getCity());
                // sb.append("\n区/县：");
                // sb.append(location.getDistrict());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                addrStr = location.getAddrStr();
            }
            sb.append("\nsdk version : ");
            sb.append(mLocationClient.getVersion());
            sb.append("\nisCellChangeFlag : ");
            sb.append(location.isCellChangeFlag());
            // logMsg(sb.toString());
            if (addrStr != null) {
                logMsg(addrStr);
                mLocationClient.stop();
                Log.d(TAG, "addr is " + addrStr + "stop location");
            } else {
                Log.d(TAG, "addrStr is null request location again");
            }
            Log.i(TAG, sb.toString());
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
            StringBuffer sb = new StringBuffer(256);
            sb.append("Poi time : ");
            sb.append(poiLocation.getTime());
            sb.append("\nerror code : ");
            sb.append(poiLocation.getLocType());
            sb.append("\nlatitude : ");
            sb.append(poiLocation.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(poiLocation.getLongitude());
            sb.append("\nradius : ");
            sb.append(poiLocation.getRadius());
            if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(poiLocation.getAddrStr());
            }
            if (poiLocation.hasPoi()) {
                sb.append("\nPoi:");
                sb.append(poiLocation.getPoi());
            } else {
                sb.append("noPoi information");
            }
            logMsg(sb.toString());
        }
    }

    /**
     * 显示请求字符串
     * 
     * @param str
     */
    public void logMsg(String str) {
        try {

            if (mPlacEditText != null)
                mPlacEditText.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 设置相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setServiceName("com.baidu.location.service_v2.2");// v2.9
        option.setPoiExtraInfo(true);
        /*
         * if(mIsAddrInfoCheck.isChecked()) { option.setAddrType("all"); }
         */
        option.setAddrType("all");
        /*
         * if(null!=mSpanEdit.getText().toString()) { boolean b =
         * isNumeric(mSpanEdit.getText().toString()); if(b) {
         * option.setScanSpan(Integer.parseInt(mSpanEdit.getText().toString()));
         * //设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位 } }
         */
        option.setScanSpan(3000);
        option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
        /*
         * if(mPriorityCheck.isChecked()) {
         * option.setPriority(LocationClientOption.NetWorkFirst); //设置网络优先 }
         * else { option.setPriority(LocationClientOption.GpsFirst);
         * //不设置，默认是gps优先 }
         */
        option.setPoiNumber(10);
        option.disableCache(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        super.onDestroy();
    }
}
