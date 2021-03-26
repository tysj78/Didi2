package com.yangyong.didi2.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mobilewise.didi2.R;
import com.yangyong.didi2.broadcast.DidiAlarmReceiver;
import com.yangyong.didi2.constant.Constants;

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
                create();
                break;
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
