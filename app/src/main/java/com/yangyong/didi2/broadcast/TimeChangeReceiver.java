package com.yangyong.didi2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
//import android.util.TimeUtils;

public class TimeChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "yy";

    public TimeChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_DATE_CHANGED.equals(action)) {
            Log.e(TAG, "---监测到日期改变,重启Timer---");
//            uploadCallLogTimer.start();
//            uploadSmsLogTimer.start();
        }

        if (Intent.ACTION_TIME_CHANGED.equals(action)) {
            Log.e(TAG, "---监测到时间改变,重启Timer---");
//            uploadCallLogTimer.start();
//            uploadSmsLogTimer.start();
//            Toast.makeText(context,"监测到时间改变",Toast.LENGTH_SHORT).show();
        }
    }
}
