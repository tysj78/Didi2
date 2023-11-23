package com.naruto.didi2.util;


import android.content.Intent;

import com.naruto.didi2.MyApp;
import com.naruto.didi2.activity.third.MnActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by DELL on 2022/1/5.
 */

public class TimerUtil {
    private static final TimerUtil instance = new TimerUtil();
    private Timer mTimer;
    private TimerTask mTimerTask;


    private TimerUtil() {
    }

    public void stopTimer() {
        try {
            if (mTimerTask != null) {
                mTimerTask.cancel();
                mTimerTask = null;
            }
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
            LogUtils.e("定时器已关闭");
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    public synchronized void startTimer() {
        stopTimer();
        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    LogUtils.e("定时器运行中：" + Thread.currentThread().getId());
                    lock();
                }
            };
            mTimer.schedule(mTimerTask, 0, 10 * 1000);
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    public static TimerUtil getInstance() {
        return instance;
    }

    private void lock() {
        LogUtils.e("checkRunningApps: " + "开始对非白名单应用锁屏");
        try {
            Intent intent = new Intent();
            intent.setClass(MyApp.getContext(), MnActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApp.getContext().startActivity(intent);
        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }

}
