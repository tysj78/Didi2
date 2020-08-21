package com.yangyong.didi2.notificationQueue;

import android.util.Log;

import com.yangyong.didi2.util.AppUtil;
import com.yangyong.didi2.util.LogUtils;
import com.yangyong.didi2.zlog.LogBean;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Singleton class.</p>
 */

public class NoticeQueue {

    private static final String TAG = "NoticeQueue";

    /**
     * 存储日志的队列
     */
    private LinkedBlockingQueue<LogBean> mLogQueue = new LinkedBlockingQueue<>();

    private static class Inner {
        private static NoticeQueue mInstance = new NoticeQueue();
    }

    public static NoticeQueue getInstance() {
        return Inner.mInstance;
    }

    private NoticeQueue() {
        LogUtils.e("任务线程初始化");
        NoticeDispatcher mNoticeDispatcher = new NoticeDispatcher(mLogQueue);
        mNoticeDispatcher.start();
    }

//    public void start() {
//        mNoticeDispatcher.start();
//    }

    public void add(final LogBean logBean) {
        try {
            boolean offer = mLogQueue.offer(logBean);
            if (!offer) {
                LogUtils.e("入队offer失败: ");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mLogQueue.put(logBean);
                        } catch (InterruptedException e) {
                            Log.e("yy", "入队put异常：" + e.toString());
                        }
                    }
                }).start();
            } else {
                LogUtils.e("入队offer成功: ");
            }
        } catch (Exception e) {
            LogUtils.e("入队异常：" + e.toString());
        }
    }
}
