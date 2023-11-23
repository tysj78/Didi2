package com.naruto.didi2.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {

    private Timer timer;
    private TimerTask timerTask;

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        openTimer();
        return super.onStartCommand(intent, flags, startId);
    }

    private void openTimer() {
        Log.e("yy", "openTimer: " );
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
//                Log.e("yy", "run: " + MyApp.mActivity);
            }
        };
        timer.schedule(timerTask, 0, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timerTask != null) {
            timerTask.cancel();
        }

    }


}
