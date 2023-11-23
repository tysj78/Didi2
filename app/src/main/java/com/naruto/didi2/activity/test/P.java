package com.naruto.didi2.activity.test;

import com.naruto.didi2.util.LogUtils;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/6/9/0009
 */

public class P {
    public int sum = 0;

    public static synchronized void aMethod() {
        // do something
        LogUtils.e("线程启动：" + Thread.currentThread().getId());
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 1);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 2);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 3);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 4);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 5);
        try {
            Thread.sleep(3000);
            LogUtils.e("线程结束：" + Thread.currentThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void delete() {
        // do something
        LogUtils.e("删除数据开始：" + Thread.currentThread().getId());
        try {
            Thread.sleep(3000);
            LogUtils.e("删除数据结束：" + Thread.currentThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void query() {
        // do something
        LogUtils.e("查询数据开始：" + Thread.currentThread().getId());
        try {
            Thread.sleep(3000);
            LogUtils.e("查询数据结束：" + Thread.currentThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void method2(Q q) {
        //非同步代码
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 1);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 2);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 3);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 4);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 5);

        //同步代码块，对象锁
        synchronized (this) {
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 1);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 2);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 3);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 4);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 5);
        }

        //同步代码块，类锁
        synchronized (P.class) {
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 1);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 2);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 3);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 4);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 5);
        }


        //同步代码块，对象锁
        synchronized (q) {
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 1);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 2);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 3);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 4);
            LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 5);
            try {
                Thread.sleep(3000);
                LogUtils.e(Thread.currentThread().getId() + "==synchronized结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public void method3(int add) {
        LogUtils.e(Thread.currentThread().getId() + "==开始存钱");
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 1);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 2);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 3);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 4);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 5);
        sum += add;

        LogUtils.e(Thread.currentThread().getId() + "==开始休眠");
//        SystemClock.sleep(5000);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 6);
        LogUtils.e(Thread.currentThread().getId() + "==结束,当前余额：" + sum);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 7);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 8);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 9);
        LogUtils.e(Thread.currentThread().getId() + "==synchronized" + 10);
        sum = 0;
    }
}
