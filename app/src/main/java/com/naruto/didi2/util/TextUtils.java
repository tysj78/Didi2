package com.naruto.didi2.util;

import com.naruto.didi2.MyApp;
import com.naruto.didi2.R;

/**
 * Created by DELL on 2021/8/16.
 */

public class TextUtils {
    //    private static final TextUtils ourInstance = new TextUtils();
//
//    public static TextUtils getInstance() {
//        return ourInstance;
//    }
//
//    private TextUtils() {
//    }
    public static String mySetText(int i) {
        try {
            return String.format(MyApp.getContext().getString(R.string.txtText1), i);
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
        return "";
    }

    public static String mySetText(float f) {
        try {
            return String.format(MyApp.getContext().getString(R.string.txtText2), f);
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
        return "";
    }

    public static String mySetText(String s) {
        try {
            return String.format(MyApp.getContext().getString(R.string.txtText3), s);
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
        return "";
    }

    public static String mySetText(boolean s) {
        try {
            return String.format(MyApp.getContext().getString(R.string.txtText5), s);
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
        return "";
    }

//    public static String mySetText(int i, float f, String s) {
//        return String.format(MyApp.getContext().getString(R.string.txtText4), i, f, s);
//    }
}
