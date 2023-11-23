package com.naruto.didi2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.naruto.didi2.util.LogUtils;

public class LocalReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.e("接收到本地广播");
    }
}
