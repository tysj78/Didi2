package com.yangyong.didi2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.yangyong.didi2.MyApp;

import java.io.IOException;

public class NetInfoReceiver extends BroadcastReceiver {
    private String TAG = "yy";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cmg = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
            Log.e(TAG, "using 移动网络...");
//                context.startService(new Intent(context,ChatService.class));
        } else {
            Log.e(TAG, "数据断开,停止ChatService...");
//            Toast.makeText(MyApp.mContext,"数据连接断开了",Toast.LENGTH_SHORT).show();
            try {
                Runtime.getRuntime().exec("adb shell am start com.yangyong.didi2/.activity.BroadcastActivity");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
