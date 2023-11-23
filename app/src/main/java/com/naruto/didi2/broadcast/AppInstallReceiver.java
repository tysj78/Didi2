package com.naruto.didi2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.naruto.didi2.util.LogUtils;

public class AppInstallReceiver extends BroadcastReceiver {

    private static final String TAG = "yylog";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Log.e(TAG, "onReceive: ACTION_PACKAGE_ADDED:" + packageName);
//            Toast.makeText(context, "安装成功--" + packageName, Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
//            Log.e(TAG, "onReceive: ACTION_PACKAGE_REMOVED");
            String packageName = intent.getData().getSchemeSpecificPart();
            LogUtils.e("onReceive: ACTION_PACKAGE_REMOVED：" + packageName);
//            AppUtil.getInstance().mAppMap.put(packageName, "");
//            Toast.makeText(context, "卸载成功--" + packageName, Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Log.e(TAG, "onReceive: ACTION_PACKAGE_REPLACED:" + packageName);
//            Toast.makeText(context, "更新成功--" + packageName, Toast.LENGTH_LONG).show();
        }
    }
}
