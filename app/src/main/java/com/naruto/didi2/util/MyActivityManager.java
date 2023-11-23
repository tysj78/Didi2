package com.naruto.didi2.util;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * 保存,获取栈顶activity管理类
 * Created by DELL on 2021/9/1.
 */

public class MyActivityManager {
    private static MyActivityManager sInstance = new MyActivityManager();

    private WeakReference<Activity> sCurrentActivityWeakRef;

    //    private final Object activityUpdateLock = new Object();
    private MyActivityManager() {

    }

    public static MyActivityManager getInstance() {
        return sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
//        synchronized (activityUpdateLock){
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
//        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
//        synchronized (activityUpdateLock){
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
//        }

    }
}
