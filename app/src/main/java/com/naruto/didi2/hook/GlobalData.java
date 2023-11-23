package com.naruto.didi2.hook;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class GlobalData {
    public static String mBaseApkPath = "";
    public static String mLibsDir = "";//payload_lib/
    public static String mLibPath = "";//patch apk-->origin.apk
    public static String packageName = "";
    public static String appName = "";
    public static Context mContext;
    public static Application mApplication = null;
    public static String mDataDataDir = "";
    public static boolean mHookWebView = false;//webview hook can't in application
    public static android.content.pm.PackageInfo mSignInfo = null;
    public static Activity m_activity = null;
    public static int m_emm_startactivity = 0;// 0, 1 true, -1 false
    public static boolean m_istopactivity = false;
    public static String mDataTmp = "";
    public static int mMeasuredWidth;
    public static int mMeasuredHeight;
    public static Integer mPatternLockCountdown = 600;
}
