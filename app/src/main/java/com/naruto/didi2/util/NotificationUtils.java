package com.naruto.didi2.util;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.naruto.didi2.MyApp;
import com.naruto.didi2.R;

public class NotificationUtils {

    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = "com.yangyong.didi2";
    public static String ANDROID_CHANNEL_NAME = "DIDI2";

    private static NotificationUtils mInstance = null;

    private NotificationUtils() {
    }

    public static NotificationUtils getInstance() {
        if (mInstance == null) {
            synchronized (NotificationUtils.class) {
                if (mInstance == null) {
                    mInstance = new NotificationUtils();
                }
            }
        }
        return mInstance;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        // create android channel
        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        // Sets whether notifications posted to this channel should display notification lights
        androidChannel.enableLights(true);
        // Sets whether notification posted to this channel should vibrate.
        androidChannel.enableVibration(true);
        // Sets the notification light color for notifications posted to this channel
        androidChannel.setLightColor(Color.GREEN);
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(androidChannel);
    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) MyApp.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification getAndroidChannelNotification(String title, String body) {
        return new Notification.Builder(MyApp.getContext(), ANDROID_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true)
                .build();
    }

    public void sendNotification(int id, String title, String body) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = getAndroidChannelNotification(title, body);
            getManager().notify(id, notification);
        }
    }


    private static final String NOTIFICATION_CHANNEL_NAME = "BackgroundLocation";
    private NotificationManager notificationManager = null;
    boolean isCreateChannel = false;

    @SuppressLint("NewApi")
    private Notification buildNotification() {
        Notification.Builder builder = null;
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            if (null == notificationManager) {
                notificationManager = (NotificationManager) MyApp.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            }
            String channelId = MyApp.getContext().getPackageName();
            if (!isCreateChannel) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId,
                        NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
                notificationChannel.setLightColor(Color.BLUE); //小圆点颜色
                notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                notificationManager.createNotificationChannel(notificationChannel);
                isCreateChannel = true;
            }
            builder = new Notification.Builder(MyApp.getContext(), channelId);
        } else {
            builder = new Notification.Builder(MyApp.getContext());
        }
        builder.setSmallIcon(R.mipmap.naruto)
                .setContentTitle(MyApp.getContext().getPackageName())
                .setContentText("正在后台运行")
                .setWhen(System.currentTimeMillis())
                .setOngoing(true);

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            return builder.getNotification();
        }
        return notification;
    }

    public void open() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = buildNotification();
            notificationManager.notify(2001, notification);
        }
    }
}