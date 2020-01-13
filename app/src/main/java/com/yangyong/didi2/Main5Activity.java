package com.yangyong.didi2;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yangyong.didi2.util.Constant;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main5Activity extends AppCompatActivity implements View.OnClickListener {

    private NotificationUtils mNotificationUtils;
    private Notification notification;
    private Button main_ss;
    private Timer timer;
    private Button main_send;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        initView();
        timer = new Timer();

       /* //android8.0及以上使用NotificationUtils
        if (Build.VERSION.SDK_INT >= 26) {
            mNotificationUtils = new NotificationUtils(this);
            Notification.Builder builder2 = mNotificationUtils.getAndroidChannelNotification
                    ("适配android 8限制后台定位功能", "正在后台定位");
            notification = builder2.build();
        } else {
            //获取一个Notification构造器
            Notification.Builder builder = new Notification.Builder(Main5Activity.this);
            Intent nfIntent = new Intent(Main5Activity.this, Main5Activity.class);

            builder.setContentIntent(PendingIntent.
                    getActivity(Main5Activity.this, 0, nfIntent, 0)) // 设置PendingIntent
                    .setContentTitle("适配android 8限制后台定位功能") // 设置下拉列表里的标题
                    .setSmallIcon(R.drawable.ic_launcher_foreground) // 设置状态栏内的小图标
                    .setContentText("正在后台定位") // 设置上下文内容
                    .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

            notification = builder.build(); // 获取构建好的Notification
        }
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音*/
    }

    /**
     * 判断服务是否开启
     *
     * @return
     */
    public boolean isServiceRunning(Context context, String ServiceName) {
        if (TextUtils.isEmpty(ServiceName)) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(1000);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }

    private void initView() {
        main_ss = (Button) findViewById(R.id.main_ss);

        main_ss.setOnClickListener(this);
        main_send = (Button) findViewById(R.id.main_send);
        main_send.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_ss:
                Intent start = new Intent(this, MyService.class);
                if (Build.VERSION.SDK_INT >= 26) {
                    startForegroundService(start);
                } else {
                    startService(start);
                }
//                startService(start);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boolean b = isServiceRunning(Main5Activity.this, "com.yangyong.didi2.MyService");
                        Log.e(Constants.TAG, "服务运行状态：" + b);
                        Log.e(Constants.TAG, "监测线程开启，5分钟后再次检测服务运行状态.....");
                    }
                }, 2000, 1000 * 60 * 3);
                break;
            case R.id.main_send:
                MyService.getHandler().sendEmptyMessage(Constant.RECONFIG);
                break;
        }
    }
}
