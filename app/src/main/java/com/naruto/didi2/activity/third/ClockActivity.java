package com.naruto.didi2.activity.third;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.func.ClockUpdateReceiver;
import com.naruto.didi2.intf.CallBack;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ClockActivity extends AppCompatActivity {

    private TextView mHourBt;
    private TextView mMinuteBt;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateClock();
        }
    };
    private ClockUpdateReceiver clockUpdateReceiver;

    private void updateClock() {
        try {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);

            String m = "";
            if (min < 10) {
                m = "0" + min;
            } else {
                m = String.valueOf(min);
            }
            String h = String.valueOf(hour);
            mHourBt.setText(h);
            mMinuteBt.setText(m);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        initView();

        AppUtil.getInstance().regCallBack(new CallBack() {
            @Override
            public void doEvent(String str) {
                mHandler.sendEmptyMessage(0);
            }
        });
        clockUpdateReceiver = new ClockUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter("naruto.intent.clockupdate");
        registerReceiver(clockUpdateReceiver, intentFilter);
        startClock();
//        startClockByTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(clockUpdateReceiver);
    }

    private void startClockByTimer() {
        try {
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
//                    mHandler.sendEmptyMessage(0);
                    Intent intent = new Intent("naruto.intent.clockupdate");
                    String packageName = getPackageName();
                    LogUtils.e("发送广播：" + packageName);
//                    intent.setComponent(new ComponentName(packageName, "com.naruto.func.ClockUpdateReceiver"));
                    intent.setComponent(new ComponentName(ClockActivity.this, ClockUpdateReceiver.class));
                    sendBroadcast(intent);
                }
            };
            timer.schedule(timerTask, 0, 10 * 1000);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    private void startClock() {
        try {
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (manager == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent("naruto.intent.clockupdate");
//                intent.setComponent(new ComponentName(getPackageName(), "com.naruto.func.ClockUpdateReceiver"));//应用间广播
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

                manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);
//                manager.cancel(pendingIntent);
            }


           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent("naruto.intent.clockupdate");
//                intent.addFlags(0x01000000);
                intent.setComponent(new ComponentName(getPackageName(), "com.naruto.func.ClockUpdateReceiver"));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//                manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);
                manager.cancel(pendingIntent);
            }*/
        } catch (Throwable e) {
            LogUtils.e(e.toString());
        }
    }

    private void initView() {
        mHourBt = (TextView) findViewById(R.id.bt_hour);
        mMinuteBt = (TextView) findViewById(R.id.bt_minute);
    }
}
