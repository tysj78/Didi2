package com.naruto.didi2.fanshe;

import com.naruto.didi2.util.LogUtils;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/3/22/0022
 */

public class Dog {
    public Dog() {
    }

    public String getName(Integer a) {
        LogUtils.e("getname");
        return (a+5)+"";
    }

    public String getName() {
        LogUtils.e("getname11");
        return "mr";
    }

    public static Dog getaa() {
        LogUtils.e("getaa");
        return new Dog();
    }

    @Override
    public String toString() {
        return "Dog{======}";
    }
}