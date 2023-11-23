package com.naruto.didi2.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.naruto.didi2.util.LogUtils;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/7/13/0013
 */

public class MyLinearLayout extends LinearLayout {

    public MyLinearLayout(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 是否拦截子view的事件
     * @param ev
     * @return 返回false 不拦截，交由子view处理事件  true,拦截，由自身处理事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.e("MyLinearLayout onInterceptTouchEvent--");
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.e("MyLinearLayout dispatchTouchEvent--");
        return super.dispatchTouchEvent(ev);
    }
}
