package com.naruto.didi2.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.naruto.didi2.util.LogUtils;


public class MyIntentService extends IntentService {
    private static final String WRITEFILE = "writefile";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtils.e("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtils.e("onHandleIntent");
        String action = intent.getAction();
        LogUtils.e("action:" + action);
        LogUtils.e("开始执行子线程:" + Thread.currentThread().getId());
        try {
            Thread.sleep(9 * 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.e("子线程执行结束:" + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy");
    }
}
