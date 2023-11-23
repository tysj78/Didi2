package com.naruto.didi2.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.didi2.broadcast.DidiAlarmReceiver;
import com.naruto.didi2.service.Bind1Service;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.AutoTouch;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.PermissionUtils;

import io.reactivex.functions.Consumer;

public class SimulateGestureActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoTouch mAutoTouch;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mAutoTouch.autoClickPos(1, 200, 700, 0, 0);
                    break;
                case 2:
                    mAutoTouch.autoClickPos(1, 200, 500, 0, 0);
                    break;
                case 3:
                    mAutoTouch.autoClickPos(2, 200, 1300, 200, 500);
                    break;
                case 4:
                    mAutoTouch.autoClickPo(2, 200, 900, 200, 850);
                    mAutoTouch.autoClickPo(2, 200, 850, 200, 800);
                    mAutoTouch.autoClickPo(2, 200, 800, 200, 750);
                    break;
                case 5:
                    bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                    break;
            }
        }
    };
    private Button bt_alarm;
    private ServiceConnection mServiceConnection;
    private Bind1Service.MyBinder myBinder;
    private Bind1Service mServer;
    private Intent intent;
    private Button bt_copy_file;
    private String[] per = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Button bt_asynch;
    private ProgressBar pb;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulate_gesture);
        initView();
//        mAutoTouch = new AutoTouch();

//        mHandler.sendEmptyMessageDelayed(1, 5000);
//        mHandler.sendEmptyMessageDelayed(3, 6000);
//        mHandler.sendEmptyMessageDelayed(4, 3000);
//        Battery.addWhite(this);
    }

    private void initView() {
        bt_alarm = (Button) findViewById(R.id.bt_alarm);

        bt_alarm.setOnClickListener(this);
        bt_copy_file = (Button) findViewById(R.id.bt_copy_file);
        bt_copy_file.setOnClickListener(this);
        bt_asynch = (Button) findViewById(R.id.bt_asynch);
        bt_asynch.setOnClickListener(this);
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_alarm:
//                openAlarm();
                PermissionUtils.requestPermissions(this, per, new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            LogUtils.e("开启了存储权限.");
                        }
                    }
                });
                break;
            case R.id.bt_copy_file:
                break;
            case R.id.bt_asynch:
                createAsynch();
                break;
        }
    }

    private void createAsynch() {
        MyAsyncTask task=  new MyAsyncTask();
//        .execute("ACTION");
    }

    private void initBind1Service() {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (Bind1Service.MyBinder) service;
                mServer = myBinder.getServer();
                mServer.autoClickPo(2, 200, 900, 200, 600);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mServer = null;
            }
        };
        intent = new Intent(this, Bind1Service.class);
    }

    private void openAlarm() {
        try {
            //开始时间
            long firstime = SystemClock.elapsedRealtime();
            Intent intent = new Intent(SimulateGestureActivity.this, DidiAlarmReceiver.class);
            intent.setAction("com.didi.yangyong.alarm");
            PendingIntent sender =
                    PendingIntent.getBroadcast(SimulateGestureActivity.this, 0, intent, 0);

            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.setRepeating(AlarmManager.ELAPSED_REALTIME, firstime + 5000, 1000 * 60, sender);
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }
    private class MyAsyncTask extends AsyncTask<String, Integer, Integer> {
        private StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                for (int i = 1; i <= 100; i++) {
                    Thread.sleep(500);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 1;
            }
            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Integer progress = values[0];
            stringBuffer.setLength(0);
            stringBuffer.append("下载进度：").append(progress).append("%");
            tv.setText(stringBuffer);
            pb.setProgress(progress);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 0) {
                AppUtil.getInstance().toast("文件保存完成");
            } else {
                AppUtil.getInstance().toast("文件保存失败");
            }
        }
    }
}
