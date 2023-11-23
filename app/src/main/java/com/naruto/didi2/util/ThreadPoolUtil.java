package com.naruto.didi2.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/4/6/0006
 */

public class ThreadPoolUtil {

    private final ExecutorService executorService;

    private ThreadPoolUtil() {
        executorService = Executors.newCachedThreadPool();
    }

    public static ThreadPoolUtil getInstance() {
        return Inner.instance;
    }

    private static class Inner {
        private static ThreadPoolUtil instance = new ThreadPoolUtil();
    }

    public void start(WorkThread workThread) {
        if (executorService != null) {
            executorService.execute(workThread);
        }
    }


}
