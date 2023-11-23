package com.naruto.didi2.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by DELL on 2021/12/1.
 */

public class ThreadPool {
    private static final ThreadPool ourInstance = new ThreadPool();
    private int KEEP_ALIVE_TIME = 1;
    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
    private ExecutorService executorService = null;

    public static ThreadPool getInstance() {
        return ourInstance;
    }

    private ThreadPool() {
        try {
            int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
            executorService = new ThreadPoolExecutor(3,
                    5, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    taskQueue);
            LogUtils.e("初始化线程池完成：" + NUMBER_OF_CORES);
        } catch (Exception e) {
            LogUtils.e("初始化线程池异常");
            LogUtils.exception(e);
        }
    }

    public void startWork(WorkThread workThread) {
        try {
            executorService.execute(workThread);
        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }

}
