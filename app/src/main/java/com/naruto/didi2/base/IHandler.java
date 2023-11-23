package com.naruto.didi2.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class IHandler extends Handler {
    private MHandler handler;// 回调接口，消息传递给注册者
    public IHandler() {
    }

    public IHandler(Callback callback) {
        super(callback);
    }

    public IHandler(Looper looper) {
        super(looper);
    }

    public IHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }
    public void setHandler(MHandler handler) {
        this.handler = handler;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

    }
}
