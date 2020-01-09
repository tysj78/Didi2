/**
 * Copyright (C) 2016, 上海上讯信息技术有限公司. All rights reserved.
 * @version V3.0
 */

package com.yangyong.didi2.util;

import android.app.Activity;
import android.widget.Toast;

import com.yangyong.didi2.MyApp;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 管理activity栈和退出应用
 * 
 * @author Administrator
 * @date 2016-2-18 下午1:31:57
 * @version V3.0
 *
 */

public class AppExitUtils {
	public List<Activity> mActivityList = new ArrayList<Activity>();
	private static AppExitUtils instance;

	/**
	 * <p>
	 * 构造函数
	 * 
	 * @author Administrator
	 * @since 2016-2-18 下午1:40:07
	 *        描述参数作用
	 * @throws
	 * 
	 */

	private AppExitUtils() {
	}

	/**
	 * <p>
	 * AppExitUtils的单例
	 * 
	 * @author Administrator
	 * @since 2016-2-18 下午1:34:25
	 * @throws
	 * 
	 */

	public static AppExitUtils getInstance() {
		if (null == instance) {
			instance = new AppExitUtils();
		}
		return instance;
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
	 * @author Administrator
	 * @since 2016-2-18 下午1:36:43
	 * @throws
	 * 
	 */

	public void removeActivity(Activity activity) {
		mActivityList.remove(activity);
	}

	/**
	 * <p>
	 * activity逐一finish，退出应用
	 * 
	 */

	public void exit() {
		try {
			for (Activity activity : mActivityList) {
				activity.finish();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}
}
