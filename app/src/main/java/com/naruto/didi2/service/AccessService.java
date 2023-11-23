package com.naruto.didi2.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.naruto.didi2.bean.LocationModel;
import com.naruto.didi2.bean.OperationModel;
import com.naruto.didi2.util.LogUtils;

import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/7/9/0009
 */

public class AccessService extends AccessibilityService {
    public static final String TAG = "yy";

    private GestureDescription gestureDescription;
    public AccessibilityService mService;
    private AccessReceive accessReceive;

    private void initService(OperationModel operationModel) {
        GestureDescription.Builder builder = null;
        int durationTime = operationModel.getDurationTime();
        List<LocationModel> list = operationModel.getList();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new GestureDescription.Builder();
            Path path = new Path();
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) {
                    //600,700
                    path.lineTo(list.get(i).getX(), list.get(i).getY());
                } else {
                    //起点
                    //600,0
                    path.moveTo(list.get(i).getX(), list.get(i).getY());
                }
            }
            gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 0, durationTime)).build();
        }
    }

    private void initClick() {
        GestureDescription.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new GestureDescription.Builder();
            Path path = new Path();
            path.moveTo(424, 248);
            gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 1)).build();
        }
    }


    public AccessService() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        /*int eventType = event.getEventType();
        String packageName = event.getPackageName().toString();
        String className = event.getClassName().toString();
        Log.d(TAG, "----1-----" + packageName);
        Log.d(TAG, "----2-----" + className);
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == eventType) {
            //被监听的界面
            if ("com.yangyong.didi2.activity.GestureActivity".equals(className)) {
//                for (; ; ) {
                try {
                    LogUtils.e("开始模拟手势");
//                        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
                    dispatchGesture(gestureDescription, new GestureResultCallback() {
                        @Override
                        public void onCompleted(GestureDescription gestureDescription) {
                            super.onCompleted(gestureDescription);
                            Log.d(TAG, "----模拟手势成功-----");
                        }

                        @Override
                        public void onCancelled(GestureDescription gestureDescription) {
                            super.onCancelled(gestureDescription);
                            Log.d(TAG, "----模拟手势失败-----");
                        }
                    }, null);
//                        Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
//                        break;
                }
//                }
            }
        }*/

    }

    @Override
    public void onInterrupt() {
        LogUtils.e("onInterrupt");
        unregisterReceiver(accessReceive);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        regBroad();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getSystemService(AccessibilityService.class);
//        }
        mService = this;
        Log.d(TAG, "----onServiceConnected-----");
//        initService();
    }


    private void regBroad() {
        accessReceive = new AccessReceive();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.yangyong.access.move");
        filter.addAction("com.yangyong.access.click");
        registerReceiver(accessReceive, filter);
    }

    private class AccessReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.yangyong.access.move".equals(action)) {
                OperationModel operation = (OperationModel) intent.getSerializableExtra("operation");
                initService(operation);
            }
            if ("com.yangyong.access.click".equals(action)) {
                initClick();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                LogUtils.e("手势命令转换成功 开始执行 ");
                dispatchGesture(gestureDescription, null, null);
            }
        }
    }


}
