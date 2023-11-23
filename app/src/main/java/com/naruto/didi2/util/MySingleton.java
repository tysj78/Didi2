package com.naruto.didi2.util;

/**
 * Created by DELL on 2022/6/16.
 */

public class MySingleton {
    private static final MySingleton ourInstance = new MySingleton();

    public static MySingleton getInstance() {
        return ourInstance;
    }

    private MySingleton() {

    }
}
