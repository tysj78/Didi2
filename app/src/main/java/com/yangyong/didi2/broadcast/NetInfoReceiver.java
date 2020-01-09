package com.yangyong.didi2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetInfoReceiver extends BroadcastReceiver {
    private String TAG = "yy";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            ConnectivityManager cmg = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            Log.e(TAG, "数据连接发生变化或者wifi连接发生变化...");
            NetworkInfo.State state_wifi = null;
            NetworkInfo.State state_gprs = null;

            try {
                state_wifi = cmg.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState(); // 获取网络连接状态
            } catch (Exception e) {
                Log.e(TAG, "没有WIFI模块");
            }
            try {
                state_gprs = cmg.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState(); // 获取网络连接状态
            } catch (Exception e) {
                Log.e(TAG, "没有GPRS模块");
            }
            if (null != state_wifi && NetworkInfo.State.CONNECTED == state_wifi) { // 判断是否正在使用WIFI网络
                Log.e(TAG, "using wifi...");
//                context.startService(new Intent(context,ChatService.class));
            } else if (null != state_gprs && NetworkInfo.State.CONNECTED == state_gprs) { // 判断是否正在使用GPRS网络
                Log.e(TAG, "using 3G...");
//                context.startService(new Intent(context,ChatService.class));
            } else {
                Log.e(TAG, "数据断开,停止ChatService...");

//                context.stopService(new Intent(context,ChatService.class));
            }
            // 当数据网络处于开启时

//            if (cmg.getActiveNetworkInfo() != null) {
//                Log.e(TAG, "数据连接...");
//            }
        }

        if (intent.getAction().equals(
                "android.net.conn.BACKGROUND_DATA_SETTING_CHANGED")) {
            // 暂不处理
        }
        if (intent.getAction().equals("android.net.nsd.STATE_CHANGED")) {
            // 暂不处理
        }
        if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
            // 暂不处理
        }
    }
}
