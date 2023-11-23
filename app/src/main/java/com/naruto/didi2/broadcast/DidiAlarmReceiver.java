package com.naruto.didi2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.naruto.didi2.constant.Constants;

/**
 * Created by yangyong on 2019/12/10/0010.
 */

public class DidiAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case "com.didi.yangyong.alarm":
                Log.e(Constants.TAG, "接收到arm广播: " );
                break;
        }
    }
}
