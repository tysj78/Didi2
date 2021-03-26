/**
		* Copyright (C) 2016, 上海上讯信息技术有限公司. All rights reserved.
		* @version V3.0
		*/
	
package com.mobilewise.mobileware;

import android.annotation.SuppressLint;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;



@SuppressLint("NewApi")
public class DevicesAdminReceiver extends DeviceAdminReceiver {
	
	@Override
	public DevicePolicyManager getManager(Context context) {
		// TODO Auto-generated method stub
		return super.getManager(context);
	}

	@Override
	public ComponentName getWho(Context context) {
		// TODO Auto-generated method stub
		return super.getWho(context);
	}
//激活
	@Override
	public void onEnabled(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onEnabled(context, intent);
		
	}

	@Override
	public CharSequence onDisableRequested(Context context, Intent intent) {
		// TODO Auto-generated method stub
		return "取消安全桌面设备激活，会导致安全桌面功能不能正常使用。";
	}
//取消激活，没接收到取消广播
	@Override
	public void onDisabled(Context context, Intent intent) {
		//取消设备激活回调
		super.onDisabled(context, intent);
	}

	@Override
	public void onPasswordChanged(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onPasswordChanged(context, intent);
	}

	@Override
	public void onPasswordFailed(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onPasswordFailed(context, intent);
	}

	@Override
	public void onPasswordSucceeded(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onPasswordSucceeded(context, intent);
	}

	@Override
	public void onPasswordExpiring(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onPasswordExpiring(context, intent);
		Toast.makeText(context, "锁屏密码已过期，请重置密码！", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
	}
}
