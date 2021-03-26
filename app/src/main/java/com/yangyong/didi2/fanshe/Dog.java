package com.yangyong.didi2.fanshe;

import com.yangyong.didi2.util.LogUtils;

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
        LogUtils.e("gatname");
        return (a+5)+"";
    }

    @Override
    public String toString() {
        return "Dog{======}";
    }
}