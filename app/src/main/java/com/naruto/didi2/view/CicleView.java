package com.naruto.didi2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by DELL on 2022/2/18.
 */

public class CicleView extends View {
    //定义画笔和初始位置
    Paint p = new Paint();
    public float currentX = 50;
    public float currentY = 50;
    public int textColor;

    public CicleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取资源文件里面的属性，由于这里只有一个属性值，不用遍历数组，直接通过R文件拿出color值
        //把属性放在资源文件里，方便设置和复用
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.cicleview);
//        textColor = array.getColor(R.styleable.cicleview_TextColor, Color.BLACK);
//        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画一个蓝色的圆形
        p.setColor(Color.BLUE);
        canvas.drawCircle(currentX, currentY, 30, p);
        //设置文字和颜色，这里的颜色是资源文件values里面的值
//        p.setColor(textColor);
        canvas.drawText("BY finch", currentX - 30, currentY + 50, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();
        currentY = event.getY();
        invalidate();//重新绘制图形
        return true;
    }
}
