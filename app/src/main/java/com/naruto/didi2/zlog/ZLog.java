package com.naruto.didi2.zlog;

import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Logging helper class.
 */

public class ZLog {

    private static LogQueue mLogQueue;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.CHINA);
    private static Boolean saveToFile = true;
    private static String mLogDir;
    private static final String TAG = "yy";

    public static synchronized void openSaveToFile() {
        saveToFile = true;
    }

    public static synchronized void closeSaveToFile() {
        saveToFile = false;
    }

    /**
     * 初始化 ZLog，
     * 使用前应该先调用一下此方法
     */
    public static synchronized void Init(String logDir) {
//        initLogZipDate();
        mLogDir = logDir;
        mLogQueue = new LogQueue(logDir);
        mLogQueue.start();
    }

//    private static void initLogZipDate() {
//        String currentDate = getCurrentDate();
//        SharedPreferencesUtils.getIntance().saveLogZipDate(SharedPreferencesUtils.LOGZIPDATE, currentDate);
//    }

    public static void e(String TAG, String text) {
        e(TAG, text, true);
    }

    public static void e(String text) {
        e(TAG, text, true);
    }

    public static void e(String TAG, String text, boolean saveToFile) {
        Log.e(TAG, text);
        if (mLogQueue != null && ZLog.saveToFile && saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, text), LogType.ERROR));
        }
    }

    public static void e(String TAG, String text, Throwable e) {
        e(TAG, text, e, true);
    }

    public static void e(String TAG, String text, Throwable e, boolean saveToFile) {
        Log.e(TAG, e.toString());
        if (mLogQueue != null && ZLog.saveToFile && saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, String.format("%s--->%s", text, e.toString())), LogType.ERROR));
        }
    }

    public static void d(String TAG, String text) {
        d(TAG, text, true);
    }

    public static void d(String text) {
        d(TAG, text, true);
    }

    public static void d(String TAG, String text, boolean saveToFile) {
        Log.d(TAG, text);
        if (mLogQueue != null && ZLog.saveToFile && saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, text), LogType.DEBUG));
        }
    }

    public static void i(String TAG, String text) {
        i(TAG, text, true);
    }

    public static void i(String text) {
        i(TAG, text, true);
    }

    public static void i(String TAG, String text, boolean saveToFile) {
        Log.i(TAG, text);
        if (mLogQueue != null && ZLog.saveToFile && saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, text), LogType.INFO));
        }
    }

    public static void i(String TAG, String text, Throwable e) {
        i(TAG, text, e, true);
    }

    public static void i(String TAG, String text, Throwable e, boolean saveToFile) {
        Log.i(TAG, String.format("%s--->%s", text, e.toString()));
        if (mLogQueue != null && ZLog.saveToFile && saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, String.format("%s--->%s", text, e.toString())), LogType.INFO));
        }
    }

    public static void w(String TAG, String text) {
        w(TAG, text, true);
    }

    public static void w(String text) {
        w(TAG, text, true);
    }

    public static void v(String TAG, String text) {
        v(TAG, text, true);
    }

    public static void v(String text) {
        v(TAG, text, true);
    }

    public static void w(String TAG, String text, boolean saveToFile) {
        Log.w(TAG, text);
        if (mLogQueue != null && ZLog.saveToFile && saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, text), LogType.WARN));
        }
    }

    public static void v(String TAG, String text, boolean saveToFile) {
        Log.v(TAG, text);
        if (mLogQueue != null && ZLog.saveToFile && saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, text), LogType.VERBOSE));
        }
    }
    public static void wtf(String TAG, String text) {
        wtf(TAG, text, true);
    }

    public static void wtf(String TAG, String text, boolean saveToFile) {
        Log.wtf(TAG, text);
        if (mLogQueue != null && ZLog.saveToFile && saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, text), LogType.WTF));
        }
    }

    public static void crash(String TAG, String text) {
        Log.e(TAG, text);
        if (mLogQueue != null && ZLog.saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, text), LogType.CRASH));
        }
    }

    private static String buildMessage(String TAG, String text) {
        try {
            StringBuilder sbLog = new StringBuilder();
            sbLog.append(simpleDateFormat.format(new Date()));
            sbLog.append("/");
            sbLog.append(TAG);
            sbLog.append("--->");
            sbLog.append(text);
            sbLog.append("\n");
            return sbLog.toString();
        } catch (Exception e) {
            Log.e(TAG, "buildMessage: " + e.toString());
            return "";
        }
    }

    private static String getYesterday() {
        //获取前一天的时间
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    //获取当前日期
    private static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
