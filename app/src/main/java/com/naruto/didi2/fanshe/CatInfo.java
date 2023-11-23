package com.naruto.didi2.fanshe;

import com.naruto.didi2.util.LogUtils;

import java.lang.reflect.Method;

/**
 * 猫 class
 *
 * @author yangyong
 * @date 2021/3/22/0022
 */

public class CatInfo {
    public CatInfo() {
    }

    /**
     * 反射带参数的方法
     */
    public void test() {
        try {
//            Class<?> aClass = Class.forName("com.naruto.didi2.fanshe.Dog");
            Class<?> aClass = Dog.class;
            Method getaa = aClass.getMethod("getaa");
            Object invoke = getaa.invoke(null);

            Method getName = aClass.getMethod("getName");
            Object invoke1 = getName.invoke(invoke);



            LogUtils.e("fanhui:"+invoke1);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

}