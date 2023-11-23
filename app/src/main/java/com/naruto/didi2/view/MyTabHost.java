package com.naruto.didi2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TabHost;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/7/13/0013
 */

public class MyTabHost extends TabHost {
    public MyTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
