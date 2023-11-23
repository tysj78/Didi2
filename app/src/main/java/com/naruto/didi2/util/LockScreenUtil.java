package com.naruto.didi2.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.naruto.didi2.MyApp;
import com.naruto.didi2.R;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by yangyong on 2022/3/8.
 * 用于兼容vivomdm版本应用黑白名单后台弹窗功能
 */

public class LockScreenUtil {
    private static final LockScreenUtil INSTANCE = new LockScreenUtil();
    private WindowManager windowManager;
    private View view;
    private Context mContext = MyApp.getContext();
    private boolean locked = false;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            //不做延时更新存在关闭屏蔽页面立刻又弹出一次的问题
            locked = false;
        }
    };


    public void stop() {
        try {
            if (locked && windowManager != null) {
                windowManager.removeView(view);
                handler.sendEmptyMessageDelayed(0, 600L);
            }
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }


    private LockScreenUtil() {
        try {
            windowManager = (WindowManager) MyApp.getContext().getSystemService(WINDOW_SERVICE);
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    public static LockScreenUtil getInstance() {
        return INSTANCE;
    }

    public boolean isLock() {
        return locked;
    }

    public void showWindow() {
        try {
            if (locked) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(MyApp.getContext())) {
                    locked = true;
                    // 获取WindowManager服务
                    if (windowManager == null) {
                        windowManager = (WindowManager) MyApp.getContext().getSystemService(WINDOW_SERVICE);
                    }

                    // 新建悬浮窗控件
//                    final Button button = new Button(MyApp.getContext());
                    view = LayoutInflater.from(mContext).inflate(R.layout.layout_lock, null);
                    view.setBackground(mContext.getResources().getDrawable(R.drawable.lockscreen));

                    // 设置LayoutParam
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                    } else {
                        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                    }
                    layoutParams.format = PixelFormat.RGBA_8888;

//                    layoutParams.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;//占满整个屏幕，包括状态栏


//                    view.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            windowManager.removeView(view);
//                            startHome();
//                            handler.sendEmptyMessageDelayed(0, 600L);
//                        }
//                    });

                    // 将悬浮窗控件添加到WindowManager
                    windowManager.addView(view, layoutParams);
                    LogUtils.e("启动悬浮窗成功");
                } else {
                    LogUtils.e("无悬浮窗权限");
                }
            }
        } catch (Exception e) {
            LogUtils.e("LockScreenUtil:" + e.toString());
        }
    }

    private void startHome() {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
            intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
            mContext.startActivity(intent);
        } catch (Exception e) {
            LogUtils.e("LockScreenUtil:启动" + e.toString());
        }
    }

}
