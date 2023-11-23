package com.naruto.didi2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.naruto.didi2.util.LogUtils;

/**
 * Created by DELL on 2023/4/6.
 */

public class TestReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String jd = intent.getStringExtra("jd");
        LogUtils.e("TestReceiver接收到广播："+jd);
    }
}
