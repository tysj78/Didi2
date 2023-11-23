package com.naruto.didi2.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.didi2.service.MyService;

import com.naruto.didi2.intf.TimeChangListener;
import com.naruto.didi2.util.SpUtils;

import java.util.Timer;
import java.util.TimerTask;

public class TimeChangeActivity extends AppCompatActivity implements TimeChangListener, View.OnClickListener {

    private static final String TAG = "yy";
    private TextView time_hint;
    private StringBuffer stringBuffer;
    private Timer timer;
    private TimerTask timerTask;
    private int i = 1;
    private Button query_count;
    private TextView time_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_change);
        initView();
//        registerBroadcast();

//        TimeUtils.getInstance().setTimeChangListener(this);
//        stringBuffer = new StringBuffer();
        Intent start=new Intent (this,MyService.class);
        if(Build.VERSION.SDK_INT>=26){
            startForegroundService (start);
        }else{
            startService (start);
        }
//        startTimer();
    }

    @Override
    public void onTimeChange(String s) {
        stringBuffer.append("\n"+s);
        time_hint.setText(stringBuffer);
//        if (timer != null) {
//            stopTimer();
//            startTimer();
//        }
    }

    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
//                Log.e(TAG, "run: " + i);
                SpUtils.saveValue(TimeChangeActivity.this, SpUtils.RUNCOUNT, i);
                i++;
            }
        };
        timer.schedule(timerTask, 0, 36000000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void initView() {
        time_hint = (TextView) findViewById(R.id.time_hint);
        query_count = (Button) findViewById(R.id.query_count);
        query_count.setOnClickListener(this);
        time_count = (TextView) findViewById(R.id.time_count);
        time_count.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query_count:
//                int value = SpUtils.getValue(TimeChangeActivity.this, SpUtils.RUNCOUNT);
//                time_count.setText(value + "");
                break;
        }
    }
//    private void registerBroadcast() {
//        Log.e(TAG, "注册时间监听广播: " );
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_TIME_CHANGED);
//        filter.addAction(Intent.ACTION_DATE_CHANGED);
//        TimeChangeReceiver timeChangedReceiver = new TimeChangeReceiver();
//        this.registerReceiver(timeChangedReceiver, filter);
//    }

//    interface TimeChangListener{
//        void onTimeChange();
//    }
//    public TimeChangListener timeChangListener;
//
//    public void setTimeChangListener(TimeChangListener listener){
//        if (listener!=null) {
//        timeChangListener=listener;
//        timeChangListener.onTimeChange();
//        }
//    }
}
