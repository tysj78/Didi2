package com.naruto.didi2.activity.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.didi2.service.TTSListener;
import com.naruto.didi2.service.TtsService;
import com.naruto.didi2.util.LogUtils;

public class TtsActivity extends AppCompatActivity implements TTSListener {

    private TextView tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        initView();

        register();
    }

    private void register() {
        Intent intent = new Intent(this, TtsService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        tts = (TextView) findViewById(R.id.tts);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.e("onServiceConnected");
            TtsService.MyBinder binder = (TtsService.MyBinder) service;
            binder.getTtsService().setTTSListener(TtsActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.e("onServiceDisconnected");
        }
    };

    @Override
    public void change(double t) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tts.setText(t + "");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        LogUtils.e("TtsActivity onDestroy");
    }
}
