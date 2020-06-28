package com.yangyong.didi2.service;

import android.app.IntentService;
import android.content.Intent;

import com.yangyong.didi2.util.LogUtils;

public class MyIntentService extends IntentService {
    private static final String WRITEFILE = "writefile";

    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (WRITEFILE.equals(action)) {
                LogUtils.e("开始执行子线程:"+Thread.currentThread().getId());
                try {
                    Thread.sleep(9*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LogUtils.e("子线程执行结束:"+Thread.currentThread().getId());
            }
        }
    }

}
