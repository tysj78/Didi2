package com.naruto.didi2.service;

import android.accessibilityservice.AccessibilityService;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

import com.naruto.didi2.util.SpUtils;

import java.util.List;

/**
 * Created by yangyong on 2019/11/28/0028.
 */

public class DidiService extends AccessibilityService {

    private static final String TAG = "yy";
    private int value;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG,"accessibility-- onServiceConnected");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG,"accessibility-- onAccessibilityEvent");
//        value = SpUtils.getValue(this, SpUtils.RUNCOUNT);
//        if (value==0) {
            SpUtils.saveValue(this,SpUtils.RUNCOUNT,1);
//            SystemClock.sleep(50*1000);
//            try {
//                Log.e(TAG,"accessibility-- onAccessibilityEvent"+(0/0));
//            } catch (Exception e) {
//                Log.e("yy", "Exception: " + e.toString());
//            }
//        }
    }


    @Override
    public void onInterrupt() {
        Log.e(TAG, "accessibility-- onInterrupt无障碍服务中断。。。。: ");
    }

    /**
     * 点击Button 通过 button上的文字
     *
     * @param text  文字
     * @param event 事件
     */
    private void clickButtonByText(String text, AccessibilityEvent event) {
        if (event.getSource() == null) {
            return;
        }
        List<AccessibilityNodeInfo> Nodes = event.getSource().findAccessibilityNodeInfosByText(text);
        if (Nodes != null && !Nodes.isEmpty()) {

            for (AccessibilityNodeInfo node : Nodes) {
                Log.e(TAG, "clickButtonByText1: ");
                //普通Button
                if (node.getClassName().equals(Button.class.getName()) && node.isEnabled()) {
                    Log.e(TAG, "clickButtonByText2: ");
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                //5.0 风格Button
                if (node.getClassName().equals(AppCompatButton.class.getName()) && node.isEnabled()) {
                    Log.e(TAG, "clickButtonByText3: ");
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
    }
}
