package com.naruto.didi2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.naruto.didi2.util.LogUtils;
//import android.util.TimeUtils;

public class TimeChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "yylog";

    public TimeChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.e("TimeChangeReceiver:" + action);
        Toast.makeText(context,"监听到时间修改",Toast.LENGTH_LONG).show();

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
