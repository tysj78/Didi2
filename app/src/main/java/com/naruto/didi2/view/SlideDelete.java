package com.naruto.didi2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.naruto.didi2.R;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/4/28/0028
 */

public class SlideDelete extends HorizontalScrollView {
    private TextView deleteTv;//删除按钮
    private int iScrollWidth;//横向滚动范围
    private Boolean first=false;//标记第一次进入获取删除按钮控件
    public SlideDelete(Context context) {
        super(context,null);
    }

    public SlideDelete(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public SlideDelete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
//        this.setOverScrollMode(OVER_SCROLL_ALWAYS);
//        this.setOverScrollMode(OVER_SCROLL_IF_CONTENT_SCROLLS);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //第一次获取删除按钮
        deleteTv=(TextView) findViewById(R.id.item_delete);
        first=true;//修改标记状态
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //默认隐藏删除按钮
        if(changed==false){
            this.scrollTo(0,0);
            //获取水平滚动条可以滚动的范围，也就是右侧删除按钮的宽度
            iScrollWidth=deleteTv.getWidth();
        }
    }

    //滑动收拾判断
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeScrollX();
                break;
        }
        return super.onTouchEvent(ev);
    }

    //实现根据滑动距离判断是否显示删除按钮
    private void changeScrollX() {
        //触摸滑动的距离大于删除控件的一半时
        if(getScaleX()>((int)(iScrollWidth/3)*2)){
            //显示删除按钮
            this.smoothScrollTo(iScrollWidth,0);
        }else{
            //隐藏删除按钮
            this.smoothScrollTo(0,0);
        }
    }
}
