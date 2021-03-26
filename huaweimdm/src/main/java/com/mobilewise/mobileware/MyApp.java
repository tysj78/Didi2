package com.mobilewise.mobileware;

import android.app.Application;
import android.content.ComponentName;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/3/23/0023
 */

public class MyApp extends Application {

    public static ComponentName componentName;

    @Override
    public void onCreate() {
        super.onCreate();
        componentName = new ComponentName(this, DevicesAdminReceiver.class);
    }

}
