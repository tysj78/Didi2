package com.naruto.didi2.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_start;
    private Runnable runnable1;
    private Runnable runnable2;
    private ExecutorService executorService;
    private Runnable runnable3;
    private ExecutorService executorService1;
    private ScheduledExecutorService scheduledExecutorService;
    private ExecutorService executorService2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        initView();
        initThreadPool();
    }

    private void initThreadPool() {
        runnable1 = new Runnable() {
            @Override
            public void run() {
                long id = Thread.currentThread().getId();
                LogUtils.e("task1 start:" + id);
                SystemClock.sleep(2000);
                LogUtils.e("thread1 end:" + id);
            }
        };
        runnable2 = new Runnable() {
            @Override
            public void run() {
            }
        };

        runnable3 = new Runnable() {
            @Override
            public void run() {
                long id = Thread.currentThread().getId();
                LogUtils.e("task3 start:" + id);
                SystemClock.sleep(5000);
                LogUtils.e("thread3 end:" + id);
            }
        };

        //固定线程数线程池,
//        executorService = Executors.newFixedThreadPool(2);

        //缓存线程池，长度为Integer最大值
//        executorService1 = Executors.newCachedThreadPool();
//        executorService1.execute(runnable);

        //周期执行的线程池
//        scheduledExecutorService = Executors.newScheduledThreadPool(3);
//        scheduledExecutorService.scheduleAtFixedRate(runnable2, 0, 5000, TimeUnit.MICROSECONDS);

        //唯一线程的线程池
        executorService2 = Executors.newSingleThreadExecutor();
    }

    private void initView() {
        bt_start = (Button) findViewById(R.id.bt_start);

        bt_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                executeThread();
                break;
        }
    }

    private void executeThread() {
//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                executorService1.execute(runnable1);
//            }
//        };
//        timer.schedule(timerTask,0,1000*50);
        for (int i = 0; i < 5; i++) {
            executorService2.execute(runnable1);
        }
    }


}
