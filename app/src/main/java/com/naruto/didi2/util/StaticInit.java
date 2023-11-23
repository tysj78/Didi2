package com.naruto.didi2.util;

import android.util.Log;

/**
 * Created by yangyong on 2019/11/12/0012.
 */

public class StaticInit {
    private static final String TAG = "yy";
    public static int id=55;

    public static void getA(){
        Log.e(TAG, "getA初始化: " );
    }
    public static void getB(){
        Log.e(TAG, "getB初始化: " );
    }
    public static void getC(){
        Log.e(TAG, "getC初始化: " );
    }
}
