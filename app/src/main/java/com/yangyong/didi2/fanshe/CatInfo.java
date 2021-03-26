package com.yangyong.didi2.fanshe;

import com.yangyong.didi2.util.LogUtils;

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
            Class<?> aClass = Class.forName("com.yangyong.didi2.fanshe.Dog");
            Object object = aClass.newInstance();
            //定义值类型
            Method method = aClass.getMethod("getName",Integer.class);
            //输入实参
            Object invoke = method.invoke(object,30);
            LogUtils.e("fanhui:"+invoke);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }
}