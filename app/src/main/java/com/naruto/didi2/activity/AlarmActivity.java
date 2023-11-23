package com.naruto.didi2.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.broadcast.DidiAlarmReceiver;
import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.util.LogUtils;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private Button create_alarm;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        context=this;
        initView();
    }

    private void initView() {
        create_alarm = (Button) findViewById(R.id.create_alarm);

        create_alarm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_alarm:
//                create();
                createTestqq();
                break;
        }
    }

    private void createTestqq() {

        try {
            LogUtils.e("时钟服务关闭");
            AlarmManager  mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, DidiAlarmReceiver.class);
            //在Android-O上使用隐式广播
//            intent.addFlags(0x01000000);
            intent.setAction("com.didi.yangyong.alarm");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.cancel(pendingIntent);
        } catch (Throwable e) {
            LogUtils.exception(e);
        }


        try {
            LogUtils.e("时钟服务启动");
            AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, DidiAlarmReceiver.class);
            //在Android-O上使用隐式广播
//            intent.addFlags(0x01000000);
            intent.setAction("com.didi.yangyong.alarm");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);
        } catch (Throwable e) {
            LogUtils.exception(e);
        }
    }

    private void create() {
        Log.e(Constants.TAG, "设定闹钟");
        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DidiAlarmReceiver.class); // 指定跳转的Intent

        intent.setAction("com.didi.yangyong.alarm");
        PendingIntent  mAlarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);    // 指定PendingIntent
        // 设置闹钟
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),1000, mAlarmIntent);
    }
}
