package com.yangyong.didi2.notificationQueue;

import android.util.Log;

import com.yangyong.didi2.util.LogUtils;
import com.yangyong.didi2.zlog.LogBean;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 消费线程，取出队列日志，存文件
 */

public class NoticeDispatcher extends Thread {
    private static final Object LUCK = new Object();
    /**
     * 存储日志的队列
     */
    private LinkedBlockingQueue<LogBean> mLogQueue;


    NoticeDispatcher(LinkedBlockingQueue<LogBean> logQueue) {
        this.mLogQueue = logQueue;
    }

    @Override
    public void run() {

        try {
//            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            while (true) {
                try {
//                    synchronized (LUCK) {
                        LogBean logBean;
                        logBean = mLogQueue.take();
//                        if (logBean == null) {
//                            break;
//                        }
                        //耗时
                        sendNotification(logBean);
//                    }
                } catch (Exception e) {
                    Log.e("run: ", e.toString());
                }
            }
//            LogUtils.e("消费线程结束.");
        } catch (Exception e) {
            Log.e("run: ", e.toString());
        }
    }

    private void sendNotification(LogBean unReadNum) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.e("更新未读数量" + unReadNum.toString());
    }

}
