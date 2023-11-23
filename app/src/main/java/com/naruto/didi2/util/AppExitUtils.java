/**
 * Copyright (C) 2016, 上海上讯信息技术有限公司. All rights reserved.
 *
 * @version V3.0
 */

package com.naruto.didi2.util;

import android.app.Activity;

import java.util.LinkedList;

/**
 * <p>
 * 管理activity栈和退出应用
 *
 * @author Administrator
 * @version V3.0
 * @date 2016-2-18 下午1:31:57
 */

public class AppExitUtils {
    //    private List<Activity> mActivityList = new ArrayList<Activity>();
    private LinkedList<Activity> mActivityList = new LinkedList<>();
    //	private static AppExitUtils instance;

    public static AppExitUtils getInstance() {
        return Inner.instance;
    }

    /**
     * <p>
     * 构造函数
     *
     * @throws
     * @author Administrator
     * @since 2016-2-18 下午1:40:07
     * 描述参数作用
     */

    private AppExitUtils() {
    }

    private static class Inner {
        private static AppExitUtils instance = new AppExitUtils();
    }

    /**
     * <p>
     * 添加activity
     *
     * @author Administrator
     * @since 2016-2-18 下午1:35:38
     */

    public void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    /**
     * <p>
     * 移除activity
     *
     * @throws
     * @author Administrator
     * @since 2016-2-18 下午1:36:43
     */

    public void removeActivity(Activity activity) {
        if (mActivityList.contains(activity)) {
            mActivityList.remove(activity);
        }
    }

    /**
     * <p>
     * activity逐一finish，退出应用
     */

    public void exit() {
        try {
            LogUtils.e("当前activitylist:" + mActivityList.size());
            for (Activity activity : mActivityList) {
                activity.finish();
                LogUtils.e("退出activity:" + activity.getLocalClassName());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
