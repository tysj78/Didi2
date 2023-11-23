package com.naruto.didi2.zlog;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Singleton class.</p>
 */

class LogQueue {

    private static final String TAG = "NoticeQueue";

    /**
     * 存储日志的队列
     */
    private LinkedBlockingQueue<LogBean> mLogQueue = new LinkedBlockingQueue<>();
    private LogDispatcher mLogDispatcher;

    LogQueue(String logDir) {
        mLogDispatcher = new LogDispatcher(mLogQueue, logDir);
    }

    void start() {
        mLogDispatcher.start();
    }

    void add(final LogBean logBean) {
        try {
            boolean offer = mLogQueue.offer(logBean);
            if (!offer) {
                Log.e("yy", "入队offer失败: " );
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mLogQueue.put(logBean);
                        } catch (InterruptedException e) {
                            Log.e("yy", "入队put异常："+e.toString());
                        }
                    }
                }).start();
            }else {
                Log.e("yy", "入队offer成功: " );
            }
        } catch (Exception e) {
            Log.e("yy", "入队异常："+e.toString());
        }
    }
}
