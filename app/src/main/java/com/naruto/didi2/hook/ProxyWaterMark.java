package com.naruto.didi2.hook;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.widget.FrameLayout;


/**
 * Created by wzl on 9/9/20.
 */

public class ProxyWaterMark extends Drawable {
    static String TAG = "ProxyWaterMark";
    Drawable old = null;
    static ProxyWaterMark self;

    public static XC_MethodHook draw = new XC_MethodHook();

    public static synchronized ProxyWaterMark getInstance(){
        if(null == self)
            self = new ProxyWaterMark();

        return self;
    }

    public void hook(Window window){
        FrameLayout frameLayout = (FrameLayout) window.getDecorView();
        Drawable foreground = frameLayout.getForeground();
        if(foreground == null || !(foreground instanceof ProxyWaterMark)){
            old = foreground;
            window.getDecorView().post(new SetForeground(frameLayout, ProxyWaterMark.getInstance()));
            return;
        }

        //MsmLog.print("foreground.invalidateSelf()");
        //foreground.invalidateSelf();
    }

    private ProxyWaterMark(){
    }

    @Override
    public void draw(Canvas canvas) {
        if(null != old)
            old.draw(canvas);

        XC_MethodHook.MethodHookParam param = new XC_MethodHook.MethodHookParam();
        param.args = new Object[1];
        param.args[0] = canvas;
        try {
            draw.afterHookedMethod(param);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @SuppressLint("WrongConstant")
    @Override
    public int getOpacity() {
        return -3;
    }
}
