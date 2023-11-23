package com.naruto.didi2.util;

import com.naruto.didi2.intf.TimeChangListener;

/**
 * Created by yangyong on 2019/12/9/0009.
 */

public class TimeUtils {
    private static volatile TimeUtils timeUtils;

    private TimeUtils() {

    }

    public static TimeUtils getInstance() {
        if (timeUtils == null) {
            timeUtils = new TimeUtils();
        }
        return timeUtils;
    }

    private TimeChangListener timeChangListener;

    //提供给接收方
    public void setTimeChangListener(TimeChangListener listener) {
        if (listener != null) {
            timeChangListener = listener;
//            timeChangListener.onTimeChange();
        }
    }

    public void onTimeChangListener(String s) {
        if (timeChangListener != null) {
            timeChangListener.onTimeChange(s);
        }
    }
}
