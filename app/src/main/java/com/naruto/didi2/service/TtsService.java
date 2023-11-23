package com.naruto.didi2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;

import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.ThreadPoolUtil;
import com.naruto.didi2.util.WorkThread;

/**
 * tts值监测服务 class
 *
 * @author yangyong
 * @date 2021/6/16/0016
 */

public class TtsService extends Service {
    private TTSListener mTTSListener;
    private boolean start = true;

    public class MyBinder extends Binder {
        public TtsService getTtsService() {
            return TtsService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public void setTTSListener(TTSListener ttsListener) {
        mTTSListener = ttsListener;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        register();

        LogUtils.e("TtsService onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegister();
        LogUtils.e("TtsService onDestroy");
    }

    public void unRegister() {
        start = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("TtsService onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    private void register() {
        ThreadPoolUtil.getInstance().start(new WorkThread() {
            @Override
            public void runInner() {
                while (start) {
                    double v = Math.random() * 100;
                    if (mTTSListener != null) {
                        mTTSListener.change(v);
                    }
                    SystemClock.sleep(5000);
                }
            }
        });
    }


}
