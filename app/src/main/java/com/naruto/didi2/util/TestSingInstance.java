package com.naruto.didi2.util;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/9/11/0011
 */

public class TestSingInstance {
    private static final TestSingInstance ourInstance = new TestSingInstance();
    private int count;

    public static TestSingInstance getInstance() {
        return ourInstance;
    }

    private TestSingInstance() {
        LogUtils.e("TestSingInstance实例被创建");
    }

    public int getCount() {
        return count;
    }

    public int setCount() {
        return count = ++count;
    }

}
