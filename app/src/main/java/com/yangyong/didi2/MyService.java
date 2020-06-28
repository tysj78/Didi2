package com.yangyong.didi2;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.yangyong.didi2.activity.TimeChangeActivity;
import com.yangyong.didi2.util.Constant;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private String TAG = "yy";
    private static final String ID = "channel_1";
    private static final String NAME = "前台服务";
    private static MyService mInstance;
    private int io = 0;
    private int io2 = 0;
    private int io3 = 0;
    private Handler mIMHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.RECONFIG:
//                    Log.e(TAG, "handleMessage收到: ");
//                    Toast.makeText(mInstance, "我是myservice,收到message", Toast.LENGTH_SHORT).show();
                    startTime();
                    break;
                case Constant.UPDATE:
//                    String obj = (String) msg.obj;
//                    Toast.makeText(mInstance, obj, Toast.LENGTH_SHORT).show();
//                    TimeUtils.getInstance().onTimeChangListener(s1+"---"+s2+"---"+s3);
                    break;
                case Constant.UPDATE2:
//                    String obj2 = (String) msg.obj;
//                    Toast.makeText(mInstance, obj2, Toast.LENGTH_SHORT).show();
//                    TimeUtils.getInstance().onTimeChangListener(obj2);
                    break;
                case Constant.UPDATE3:
//                    String obj3 = (String) msg.obj;
//                    Toast.makeText(mInstance, obj3, Toast.LENGTH_SHORT).show();
//                    TimeUtils.getInstance().onTimeChangListener(obj3);
                    break;
            }
        }
    };
    private Timer timer;
    private TimerTask task;

    private Timer timer2;
    private TimerTask task2;

    private Timer timer3;
    private TimerTask task3;
    private String s1;
    private String s2;
    private String s3;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Log.e(TAG, "onCreate: ");
        if (Build.VERSION.SDK_INT >= 26) {
            setForeground();
        } else {
            setFg();
        }

        startTime();
        startTime2();
        startTime3();
    }

    private void startTime() {
        stopTime();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                s1 = "run: " + Thread.currentThread().getId() + "==" + io;
                Log.e(TAG, s1);

//                Message message1 = new Message();
//                message1.what=Constant.UPDATE;
//                message1.obj=s1;
//                mIMHandler.sendMessage(message1);
//                TimeUtils.getInstance().onTimeChangListener(s1);
                io++;
            }
        };
        timer.schedule(task, 0, 5000);
    }

    private void startTime2() {
        stopTime2();
        timer2 = new Timer();
        task2 = new TimerTask() {
            @Override
            public void run() {
                s2 = "run2: " + Thread.currentThread().getId() + "==" + io2;
                Log.e(TAG, s2);
//                Message message1 = new Message();
//                message1.what=Constant.UPDATE2;
//                message1.obj=s1;
//                mIMHandler.sendMessage(message1);
//                TimeUtils.getInstance().onTimeChangListener(s1);
                io2++;
            }
        };
        timer2.schedule(task2, 0, 5000);
    }

    private void startTime3() {
        stopTime3();
        timer3 = new Timer();
        task3 = new TimerTask() {
            @Override
            public void run() {
                s3 = "run3: " + Thread.currentThread().getId() + "==" + io3;
                Log.e(TAG, s3);
//                Message message1 = new Message();
//                message1.what=Constant.UPDATE3;
//                message1.obj=s1;
//                mIMHandler.sendMessage(message1);
//                TimeUtils.getInstance().onTimeChangListener(s1);
                io3++;
            }
        };
        timer3.schedule(task3, 0, 5000);
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

    private void stopTime2() {
        if (timer2 != null) {
            timer2.cancel();
            timer2 = null;
        }
        if (task2 != null) {
            task2.cancel();
            task2 = null;

        }
    }

    private void stopTime3() {
        if (timer3 != null) {
            timer3.cancel();
            timer3 = null;
        }
        if (task3 != null) {
            task3.cancel();
            task3 = null;

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
        startForeground(2, notification);
    }

    @TargetApi(26)
    private void setForeground() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(this, ID)
                .setContentTitle("收到一条重要通知")
                .setContentText("这是重要通知")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    public static Handler getHandler() {
        return mInstance.mIMHandler;
    }
}
