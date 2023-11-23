package com.naruto.didi2.util;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/4/6/0006
 */

public abstract class WorkThread extends Thread {
    @Override
    public void run() {
        super.run();
        runInner();
    }

    public abstract void runInner();
}
