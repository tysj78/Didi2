package com.naruto.didi2.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.broadcast.TimeChangeReceiver;
import com.naruto.didi2.util.LogUtils;


/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/2/26/0026
 */

public class ForegroundService extends Service {
    private static final String ID = "didifg";
    private static final CharSequence NAME = "滴滴前台服务";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startForeground();
        }
    };
    private TimeChangeReceiver timeChangeReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("onBind()");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("前台服务创建");
//        startForeground();
        timeChangeReceiver = new TimeChangeReceiver();
        IntentFilter intent = new IntentFilter();
//        intent.addAction(Intent.ACTION_TIME_TICK);
        intent.addAction(Intent.ACTION_TIME_CHANGED);
        intent.addAction(Intent.ACTION_DATE_CHANGED);
        registerReceiver(timeChangeReceiver, intent);
    }

    private void startForeground() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationChannel channel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);


//                Intent nfIntent = new Intent(this, T1Activity.class);
                Notification notification = new Notification.Builder(this, ID)
                        .setContentTitle("滴滴前台服务")
//                        .setContentText("应用需保持运行")
                        .setSmallIcon(R.mipmap.mm)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.mm))
                        .build();

                startForeground(207, notification);// 开始前台服务
//                notification.contentView=remoteViews;

            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("onStartCommand()"+Thread.currentThread().getId()+"dd");
        startForeground();
        Toast.makeText(this,"前台服务已启动",Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
//        super.onDestroy();
        LogUtils.e("onDestroy()");
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知

        try {
            if (timeChangeReceiver != null) {
                unregisterReceiver(timeChangeReceiver);
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }
}
