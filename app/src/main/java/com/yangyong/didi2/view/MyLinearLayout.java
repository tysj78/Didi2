package com.yangyong.didi2.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
