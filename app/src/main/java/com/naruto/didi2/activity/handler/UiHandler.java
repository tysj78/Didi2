package com.naruto.didi2.activity.handler;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 主线程handler封装类
 * Created by DELL on 2021/8/24.
 */

public class UiHandler extends Handler{
    private IHandler iHandler;
    public UiHandler(Looper looper,IHandler handler) {
        super(looper);
        iHandler=handler;
    }

    @Override
    public void handleMessage(Message msg) {
        if (iHandler!=null){
            iHandler.setHandler(msg);
        }
    }
}
