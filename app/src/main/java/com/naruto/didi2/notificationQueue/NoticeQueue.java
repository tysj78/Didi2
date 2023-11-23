package com.naruto.didi2.notificationQueue;

import android.util.Log;

import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.zlog.LogBean;
import com.naruto.didi2.zlog.LogType;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Singleton class.</p>
 */

public class NoticeQueue {

    /**
     * 存储日志的队列
     */
    private LinkedBlockingQueue<LogBean> mLogQueue = new LinkedBlockingQueue<>();
    private NoticeDispatcher mNoticeDispatcher;

    private static class Inner {
        private static NoticeQueue mInstance = new NoticeQueue();
    }

    public static NoticeQueue getInstance() {
        return Inner.mInstance;
    }


    private NoticeQueue() {
        LogUtils.e("消费线程初始化");
        mNoticeDispatcher = new NoticeDispatcher(mLogQueue);
        mNoticeDispatcher.start();
//        mNoticeDispatcher.start();
    }

    public void start() {
        if (mNoticeDispatcher == null) {
            mLogQueue.clear();
            mNoticeDispatcher = new NoticeDispatcher(mLogQueue);
            mNoticeDispatcher.start();
        }
    }

    /**
     * 向队列添加数据
     *
     * @param logBean
     */
    public void add(final LogBean logBean) {
        try {
            if (mNoticeDispatcher == null) {
                return;
            }

            boolean alive = mNoticeDispatcher.isAlive();
            if (!alive) {
                LogUtils.e("消费线程已死亡，停止添加数据");
                return;
            }

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

    /**
     * 停止消费线程，释放内存资源
     */
    public void stop() {
        mNoticeDispatcher.stopThread();
        try {
            LogBean logBean = new LogBean("", LogType.STOP);
            mLogQueue.offer(logBean);
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
        mNoticeDispatcher = null;
    }
}
