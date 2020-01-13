package com.yangyong.didi2;

import android.util.Log;

public class Cat {
    public String name;

    public Cat(String name) {
        this.name = name;
        Log.e(Constants.TAG, "Cat构造方法: " );
    }
}
