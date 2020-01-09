package com.yangyong.didi2.util;

import android.Manifest;
import android.app.Activity;
import android.os.Build;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangyong.didi2.PermissionActivity;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by yangyong on 2019/12/26/0026.
 */

public class AppUtil {
    public static void requestPermissions(Activity activity, String[] permissions, Consumer<Boolean> consumer) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        String[] s = new String[]{
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,//文件
//                Manifest.permission.READ_PHONE_STATE,//imei
//                Manifest.permission.ACCESS_FINE_LOCATION,//定位
//                Manifest.permission.ACCESS_COARSE_LOCATION,//定位


//                Manifest.permission.CALL_PHONE,
//                Manifest.permission.SEND_SMS,
//                Manifest.permission.RECEIVE_SMS,
//                Manifest.permission.READ_CONTACTS,
//                Manifest.permission.WRITE_CONTACTS,
//                Manifest.permission.READ_SMS,
                Manifest.permission.READ_EXTERNAL_STORAGE,//文件
//                Manifest.permission.CAMERA,
//                Manifest.permission.GET_ACCOUNTS
        };
        RxPermissions permission = new RxPermissions(activity);
        Observable<Boolean> request = permission.request(s);
        request.subscribe(consumer);
    }
}
