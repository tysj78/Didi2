package com.yangyong.didi2.util;

import android.util.Log;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/6/19/0019
 */

public class LogUtils {
    private static final String TAG = "yylog";

    public static void e(String content) {
        Log.e(TAG, content);
//        com.tencent.mars.xlog.Log.e(TAG, content);
    }
}
