package com.naruto.didi2.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//import com.yangyong.striking_arithmetic.DiDiUtil;


import com.naruto.didi2.R;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private Button main_start;
    private Timer timer;
    final static int RESTARTTIMER = 7;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESTARTTIMER: {
                    if (timer != null) {
                        try {
                            timer.cancel();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
//                    timer = null;
                    timer = new Timer();
                    timer.schedule(new firstTask(), 0, 3000);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        initView();
//        register.html();
    }

    private void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        this.registerReceiver(new TimeChangedReceiver(), filter);
    }

    private void initView() {
        main_start = (Button) findViewById(R.id.main_start);

        main_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_start:
                timer = new Timer();
                timer.schedule(new firstTask(), 0, 3000);
                break;
        }
    }

    public class TimeChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

//            Log.d(TAG, "---onReceive() start!---");

            String action = intent.getAction();
            Log.e(TAG, "onReceive: "+action );
            if (Intent.ACTION_DATE_CHANGED.equals(action)) {
                Log.e(TAG, "---DATE_CHANGED!---");
                handler.sendEmptyMessage(RESTARTTIMER);
            }

            if (Intent.ACTION_TIME_CHANGED.equals(action)) {
                handler.sendEmptyMessage(RESTARTTIMER);
                Log.e(TAG, "---TIME_CHANGED!---");

            }

//            Log.d(TAG, "---onReceive() end!---");
        }

    }

//    final static class MsgType {
//        final static int RESTARTTIMER = 7;
//    }

//    final Handler mHandlerMsg = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what) {
//                case MsgType.RESTARTTIMER: {
//                    if (timer != null) {
//                        try {
//                            timer.cancel();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
////                    timer = null;
//                    timer = new Timer();
//                    timer.schedule(new firstTask(), 0, 3000);
//                }
//                break;
//            }
//            return true;
//        }
//    });

    class firstTask extends TimerTask {
        @Override
        public void run() {
            // TODO do something
            Log.e(TAG, "timer正在运行run: ");
        }
    }
}
