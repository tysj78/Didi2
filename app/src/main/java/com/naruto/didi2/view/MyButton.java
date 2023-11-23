package com.naruto.didi2.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.naruto.didi2.util.LogUtils;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/9/3/0003
 */

public class MyButton extends AppCompatButton {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtils.e("button dispatchTouchEvent--");
        return super.dispatchTouchEvent(event);
    }

    /**
     * view特有事件，是否消费事件
     *
     * @param event
     * @return true消费事件，不再传递，false 不消费事件，向下传递，调用view的点击监听处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        return false;
    }

}
