package com.naruto.didi2.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;


import com.naruto.didi2.R;
import com.naruto.didi2.activity.TimeChangeActivity;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.SpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private static final String ID = "DIDI2SERVICE";
    private static final String NAME = "didi2记时服务";
    private Timer timer;
    private TimerTask task;
    private int runCount = 0;
    private int thread = 360;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("TimerService onCreate: ");

        if (Build.VERSION.SDK_INT >= 26) {
            setForeground();
        } else {
            setFg();
        }

        startTime();
//        createTHread();
    }

    /**
     * 创建线程任务
     */
    private void createTHread() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            LogUtils.e(Thread.currentThread().getId() + ":" + thread);
                            thread++;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();
    }

    private void startTime() {
        stopTime();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runCount++;
                //更新sp时间
                SpUtils.saveStringValue(MyService.this, "TIME", getCurrentDate() + " 运行次数：" + runCount);
                LogUtils.e("运行次数：" + runCount);
            }
        };
        timer.schedule(task, 0, 5000);
    }

    private void stopTime() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;

        }
    }


    private void setFg() {
        //获取一个Notification构造器
        Notification.Builder builder = new Notification.Builder(this);
        Intent nfIntent = new Intent(this, TimeChangeActivity.class);

        builder.setContentIntent(PendingIntent.
                getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
                .setContentTitle("android 5.0限制后台定位功能") // 设置下拉列表里的标题
                .setSmallIcon(R.drawable.ic_launcher) // 设置状态栏内的小图标
                .setContentText("这是一个前台服务") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

        Notification notification = builder.build(); // 获取构建好的Notification
        startForeground(107, notification);
    }

    @TargetApi(26)
    private void setForeground() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(this, ID)
                .setContentTitle("didi2记时服务")
                .setContentText("应用需保持运行")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .build();
        startForeground(107, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("onStartCommand: ");
        Toast.makeText(this,"onStartCommand",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    //获取当前日期
    private String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        stopTime();
        stopForeground(true);
        LogUtils.e("TimerService onDestroy: ");
    }
}
