package com.naruto.didi2.util;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

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

    public static void log(String msg) {
        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            Log.e(TAG, msg);
        } else {
            Log.e(TAG, "参数长度：" + length);
            while (msg.length() > segmentSize) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.e(TAG, logContent);
            }
//            Log.e(tag,"-------------------"+ msg);// 打印剩余日志
            Log.e(TAG, msg);
        }
    }

    public static void exception(Throwable e) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String exceptioninfo = writer.toString();
        Log.e(TAG,exceptioninfo);

    }
}
