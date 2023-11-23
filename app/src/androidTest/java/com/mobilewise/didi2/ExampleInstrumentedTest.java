package com.mobilewise.didi2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static boolean flag = false;
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.yangyong.didi2", appContext.getPackageName());
    }

    @Test
    public void qq() throws Exception{
//        ThreadA threadA = new ThreadA();
//        ThreadB threadB = new ThreadB();
//
//        new Thread(threadA, "threadA").start();
//        Thread.sleep(1000);//为了保证threadA比threadB先启动，sleep一下
//        new Thread(threadB, "threadB").start();
        System.out.println("aaaaa");
    }

    class ThreadA extends Thread {
        public void run() {
            while (true) {
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + " : flag is " + flag);
                    break;
                }
            }

        }

    }

    class ThreadB extends Thread {
        public void run() {
            flag = true;
            System.out.println(Thread.currentThread().getName() + " : flag is " + flag);
        }
    }
}
