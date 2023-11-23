package com.naruto.didi2.util;

import android.Manifest;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/3/12/0012
 */

public class PermissionUtils {
    /**
     * 主线程
     *
     * @param activity
     * @param permissions
     * @param consumer
     */
    public static void requestPermissions(Activity activity, String[] permissions, Consumer<Boolean> consumer) {
        String[] s = new String[]{
                Manifest.permission.READ_PHONE_STATE,//imei
//                Manifest.permission.ACCESS_FINE_LOCATION,//定位
//                Manifest.permission.ACCESS_COARSE_LOCATION,//定位


//                Manifest.permission.CALL_PHONE,
//                Manifest.permission.SEND_SMS,
//                Manifest.permission.RECEIVE_SMS,
//                Manifest.permission.READ_CONTACTS,
//                Manifest.permission.WRITE_CONTACTS,
//                Manifest.permission.READ_SMS,
//                Manifest.permission.READ_EXTERNAL_STORAGE,//文件
//                Manifest.permission.CAMERA,
//                Manifest.permission.GET_ACCOUNTS
        };
        if (activity == null) {
            LogUtils.e("activity null==");
            return;
        }

        try {
            RxPermissions permission = new RxPermissions(activity);
            Observable<Boolean> request = permission.request(permissions);
            request.subscribe(consumer);
        } catch (Exception e) {
            LogUtils.e("动态权限Exception: " + e.toString());
        }
    }

    /**
     * 子线程
     *
     * @param activity
     * @param permissions
     * @param consumer
     */
    public static void requestPermissionsOnThread(final Activity activity, final String[] permissions, final Consumer<Boolean> consumer) {
        try {
            if (activity == null) {
                LogUtils.e("获取动态权限异常：activity null");
                return;
            }

            final Handler handler = new Handler(Looper.getMainLooper());
            Looper.prepare();
            final Handler t_handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Observable<Boolean> observable = (Observable<Boolean>) msg.obj;
                    observable.subscribe(consumer);

                    Looper looper = Looper.myLooper();
                    if (looper != null) {
                        looper.quit();
                        LogUtils.e("结束looper轮询");
                    }
                }
            };
            handler.post(new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //主线程中初始化RxPermissions
                                RxPermissions permission = new RxPermissions(activity);
                                Observable<Boolean> request = permission.request(permissions);
                                Message obtain = Message.obtain();
                                obtain.obj = request;

                                //申请权限结果返回给子线程
                                t_handler.sendMessage(obtain);
                            } catch (Exception e) {
                                try {
                                    consumer.accept(true);
                                } catch (Exception e1) {
                                    LogUtils.e("Exception1: " + e.toString());
                                }
                                LogUtils.e("Exception2: " + e.toString());
                            }
                        }
                    });
                }
            });
            Looper.loop();
        } catch (Exception e) {
            LogUtils.e("Exception3:" + e.toString());
        }
    }

    /**
     * 子线程
     *
     * @param activity
     * @param permissions
     * @param consumer
     */
    public static void requestPermissionsOnThread2(final Activity activity, final String[] permissions, final Consumer<Boolean> consumer) {
        try {
            if (activity == null) {
                LogUtils.e("获取动态权限异常：activity null");
                return;
            }

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        //主线程中初始化RxPermissions
                        RxPermissions permission = new RxPermissions(activity);
                        Observable<Boolean> request = permission.request(permissions);
                        request.subscribe(consumer);
                    } catch (Exception e) {
                        try {
                            consumer.accept(true);
                        } catch (Exception e1) {
                            LogUtils.e("Exception1: " + e.toString());
                        }
                        LogUtils.e("Exception2: " + e.toString());
                    }
                }
            });
        } catch (Exception e) {
            LogUtils.e("Exception3:" + e.toString());
        }
    }

}
