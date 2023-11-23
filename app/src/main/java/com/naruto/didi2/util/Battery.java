package com.naruto.didi2.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;



public class Battery {


    public static void addWhite(Activity activity){
        PowerManager packageManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        //应用是否在 白名单中
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!packageManager.isIgnoringBatteryOptimizations(activity.getPackageName())){
                //方法1、启动一个  ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS Intent
                Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                activity.startActivity(intent);
                //方法2、触发系统对话框
//                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:"+activity.getPackageName()));
//                activity.startActivity(intent);
//            }
        }
    }

    /**
     * 是否正在充电
     * @return
     */
    public static boolean isPlugged(Context context){
        //发送个包含充电状态的广播，并且是一个持续的广播
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = context.registerReceiver(null,filter);
        //获取充电状态
        int isPlugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean acPlugged = isPlugged == BatteryManager.BATTERY_PLUGGED_AC;
        boolean usbPlugged = isPlugged == BatteryManager.BATTERY_PLUGGED_USB;
        boolean wifiPlugged = isPlugged == BatteryManager.BATTERY_PLUGGED_WIRELESS;
        return acPlugged || usbPlugged || wifiPlugged;
    }


    /**
     * 是否正在使用wifi
     * @param context
     * @return
     */
    public static boolean isWifi(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //获得当前活动的网络信息
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (null != activeNetworkInfo && activeNetworkInfo.isConnected() &&
                activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            return true;
        }
        return false;
    }
}
