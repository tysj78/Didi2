package com.naruto.didi2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.naruto.didi2.util.LogUtils;

public class PermissionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.e("跨进程广播getComponent:"+intent.getComponent());

        LogUtils.e("getAction:" + intent.getAction());
        LogUtils.e("getData:"+intent.getData());
        LogUtils.e("getCategories:"+intent.getCategories());
        LogUtils.e("getType:"+intent.getType());
        LogUtils.e("getScheme:"+intent.getScheme());
    }
}
