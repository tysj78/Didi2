package com.naruto.didi2.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yangyong on 2019/12/10/0010.
 */

public class SpUtils {
    public static String RUNCOUNT = "runcount";
    public static String SAVETIME = "savetime";
    public static String DIDISP = "didi_sp";
    public static String LOGIN = "login";
    public static String UPLOADSTATUS = "uploadstatus";

    public static int getValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        return sp.getInt(key, 0);
    }

    public static void saveValue(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        sp.edit().putInt(key, value).commit();
    }

    public static boolean getBooleanValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        return sp.getBoolean(key, false);
    }

    public static void saveBooleanValue(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        sp.edit().putBoolean(key, value).commit();
    }

    public static long getLongValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        return sp.getLong(key, 0);
    }

    public static void saveLongValue(Context context, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        sp.edit().putLong(key, value).commit();
    }

    public static String getStringValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        return sp.getString(key, "");
    }

    public static void saveStringValue(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("didi_sp", 0);
        sp.edit().putString(key, value).commit();
    }

    public static void clear(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("didi_sp", 0).edit();
        editor.clear();
        editor.commit();
    }
}
