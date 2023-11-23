package com.naruto.didi2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.naruto.didi2.util.NotificationUtil;

/**
 * Created by hongming.wang on 2017/9/13.
 * 前台定位service
 */

public class LocationForcegroundService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Android O上才显示通知栏
        if (Build.VERSION.SDK_INT >= 26) {
            showNotify();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //显示通知栏
    public void showNotify() {
        //调用这个方法把服务设置成前台服务
        startForeground(NotificationUtil.NOTIFY_ID, NotificationUtil.buildNotification(getApplicationContext()));
    }

//    private final IBinder mBinder = new LocalBinder();

//    public class LocalBinder extends Binder {
//        LocationForcegroundService getService() {
//            return LocationForcegroundService.this;
//        }
//    }
}
