package com.yangyong.didi2.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

import java.util.List;

/**
 * Created by yangyong on 2019/11/28/0028.
 */

public class DidiService extends AccessibilityService {

    private static final String TAG = "yy";
    static final String ENVELOPE_TEXT_KEY = "[QQ红包]";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
//        Log.e(TAG, "当前事件类型: " + eventType);
        AccessibilityNodeInfo node = getRootInActiveWindow();
//        Log.e(TAG, "当前事件类型: " + eventType);
        if (node == null) {
            return;
        }
        Log.e(TAG, "当前点击事件包名: " + node.getPackageName());
        //自动关闭蓝牙
        if (node.getPackageName().equals("com.android.settings")) {
            List<AccessibilityNodeInfo> in = node.findAccessibilityNodeInfosByText("关闭蓝牙");
            Log.e(TAG, "===: " + in.size());
            if (in.size() > 0) {
//                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                clickButtonByText("允许", event);
            }
        }
        /*switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                if (node == null) {
                    return;
                }
//                Log.e(TAG, "当前点击事件包名: " + node.getPackageName());
                //自动关闭蓝牙
                if (node.getPackageName().equals("com.android.settings")) {
                    List<AccessibilityNodeInfo> in = node.findAccessibilityNodeInfosByText("关闭蓝牙");
                    Log.e(TAG, "===: " + in.size());
                    if (in.size() > 0) {
//                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                        clickButtonByText("允许", event);
                    }
                }
                List<AccessibilityNodeInfo> nodeInfos = node.findAccessibilityNodeInfosByText("蓝缔");
                List<AccessibilityNodeInfo> xz = node.findAccessibilityNodeInfosByText("卸载");
                node.recycle();
                for (AccessibilityNodeInfo nodeInfo : nodeInfos) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
                }
                for (AccessibilityNodeInfo nodeInfo : xz) {
                    Log.e(TAG, "onAccessibilityEvent取消操作: ");
                    performGlobalAction(GLOBAL_ACTION_BACK);
                }
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
//                Log.e(TAG, "onAccessibilityEvent1: " );
//                List<CharSequence> texts = event.getText();
//                if (!texts.isEmpty()) {
//                    for (CharSequence t : texts) {
//                        String text = String.valueOf(t);
//                        Log.v("tag", "监控QQ消息==" + text);
//                        if (text.contains(ENVELOPE_TEXT_KEY)) {
//                            openNotification(event);
//                            break;
//                        }
//                    }
//                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
//                Log.e(TAG, "onAccessibilityEvent2: " );
//                openEnvelope(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
//                Log.e(TAG, "onAccessibilityEvent3: " );
//                openEnvelopeContent(event);
                break;
        }*/
    }

    /**
     * 打开通知栏消息并跳转到红包页面
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void openNotification(AccessibilityEvent event) {
        if (event.getParcelableData() == null || !(event.getParcelableData() instanceof Notification)) {
            return;
        }
        //以下是精华，将微信的通知栏消息打开
        Notification notification = (Notification) event.getParcelableData();
        PendingIntent pendingIntent = notification.contentIntent;
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openEnvelope(AccessibilityEvent event) {
        if ("com.tencent.mobileqq.activity.SplashActivity".equals(event.getClassName())) {
            checkKey();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void checkKey() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("大吉大利");
        if (list.isEmpty()) {
            return;
        }
        for (AccessibilityNodeInfo info : list) {
            AccessibilityNodeInfo parent = info.getParent();
            if (parent != null) {
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openEnvelopeContent(AccessibilityEvent event) {

        if ("android.widget.TextView".equals(event.getClassName())) {
            checkKeyFirst();
        } else if ("com.tencent.mm.plugin.luckymoney.ui.En_fba4b94f".equals(event.getClassName())) {
            checkOpen();
        } else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI".equals(event.getClassName())) {
            // Toast.makeText(this, "红包已经抢完--------", Toast.LENGTH_SHORT).show();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void checkOpen() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("恭喜");
        for (int i = list.size() - 1; i >= 0; i--) {

            AccessibilityNodeInfo parent = list.get(i).getParent();
            for (int j = 0; j < parent.getChildCount(); j++) {
                AccessibilityNodeInfo child = parent.getChild(j);
                if (child != null && child.isClickable()) {
                    child.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    break;
                }
            }

        }
    }

    private void checkKeyFirst() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("领取红包");
        if (list.isEmpty()) {
            return;
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            AccessibilityNodeInfo parent = list.get(i).getParent();
            if (parent != null) {
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt无障碍服务中断。。。。: ");
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
