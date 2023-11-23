package com.naruto.didi2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.naruto.didi2.MyApp;
import com.naruto.didi2.util.LogUtils;

/**
 * Created by DELL on 2021/8/13.
 */

public class WIFIApReceiver extends BroadcastReceiver {
    private static final String TAG ="yylog";
    private boolean mRegistered;
    private static int isWiFiApState = 14;

    public static boolean isWiFiApOpened_O() {
        return (isWiFiApState == 12 || isWiFiApState == 13);
    }

    public void setListening(boolean listening) {
        if (listening && !mRegistered) {
            Log.d(TAG, "Registering receiver");
            final IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
            MyApp.getContext().registerReceiver(this, filter);
            mRegistered = true;
        } else if (!listening && mRegistered) {
            Log.d(TAG, "Unregistering receiver");
            MyApp.getContext().unregisterReceiver(this);
            mRegistered = false;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()!=null&&intent.getAction().equals("android.net.wifi.WIFI_AP_STATE_CHANGED")){
            LogUtils.e("接收到热点状态变化广播");
        }
        isWiFiApState = intent.getIntExtra("wifi_state", 14);
        String result = null;

        switch (isWiFiApState) {
            case 11:
                result = "DISABLED";
                break;
            case 10:
                result =  "DISABLING";
                break;
            case 13:
                result =  "ENABLED";
                break;
            case 12:
                result =  "ENABLING";
                break;
            case 14:
                result =  "FAILED";
                break;
        }

        Log.d(TAG, "WiFi state : " + result);
    }


}
