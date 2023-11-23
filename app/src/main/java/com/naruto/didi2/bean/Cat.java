package com.naruto.didi2.bean;

import android.util.Log;

import com.naruto.didi2.constant.Constants;

public class Cat {
    public String name;

    public Cat(String name) {
        this.name = name;
        Log.e(Constants.TAG, "Cat构造方法: " );
    }
}
