package com.yangyong.didi2.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yangyong on 2019/12/10/0010.
 */

public class SpUtils {
    public static String RUNCOUNT = "runcount";
    public static String UPLOADSTATUS = "uploadstatus";
    public static String DIDISP = "didi_sp";

    public static int getValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        return sp.getInt(key, 0);
    }

    public static void saveValue(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        sp.edit().putInt(key, value).commit();
    }

    public static String getStringValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        return sp.getString(key, "");
    }

    public static void saveStringValue(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        sp.edit().putString(key, value).commit();
    }
}
