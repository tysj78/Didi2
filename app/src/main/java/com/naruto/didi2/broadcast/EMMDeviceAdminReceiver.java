package com.naruto.didi2.broadcast;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


public class EMMDeviceAdminReceiver extends android.app.admin.DeviceAdminReceiver {
    public EMMDeviceAdminReceiver() {
    }

//    private static final int RESET_PASSWORD_NOT_REQUIRE_ENTRY = 0;
//    private static int RESET_PASSWORD_TIME_OUT = 5 * 1000;
//    private static String TEMP_PASSWORD = "1234";

    private String TAG = "DevicesAdminReceiver";
    private DevicePolicyManager mDevicepolicymanager;
    @Override
    public DevicePolicyManager getManager(Context context) {
        // TODO Auto-generated method stub
        Log.e(TAG, "getManager");
        return super.getManager(context);
    }

    @Override
    public ComponentName getWho(Context context) {
        // TODO Auto-generated method stub
        Log.e(TAG, "getWho");
        return super.getWho(context);
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onEnabled(context, intent);
        //激活设备的时候上传硬件信息，硬件信息里存在激活状态
        Log.e(TAG,"onEnabled");
//        RecordUtils.upLoadHardwareInfo(context);

    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.e(TAG, "onDisableRequested");

        return "取消EMM设备激活，会导致EMM功能不能正常使用。";
    }


    @Override
    public void onDisabled(Context context, Intent intent) {
        //取消激活设备的时候上传硬件信息，硬件信息里存在激活状态
        Log.e(TAG,"onDisabled");

//        RecordUtils.upLoadHardwareInfoForAdmin(context);

    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onPasswordChanged(context, intent);
        Log.e(TAG, "onPasswordChanged");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onPasswordFailed(context, intent);
        Log.e(TAG, "onPasswordFailed");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onPasswordSucceeded(context, intent);
        Log.e(TAG, "onPasswordSucceeded");
    }

    @Override
    public void onPasswordExpiring(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onPasswordExpiring(context, intent);
//		Toast.makeText(context, "锁屏密码已过期，请重置密码！", Toast.LENGTH_LONG).show();
        Log.e(TAG, "onPasswordExpiring");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);
        Log.e(TAG, "onReceive");
    }
    private void resetPassword(Context context, String key) {

        mDevicepolicymanager = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        // Setting reset system password
        try {
            mDevicepolicymanager.resetPassword(key, 0);
        } catch (Exception e) {
            Log.e("EMM","DeviceUtils->"+e.getMessage());
        }
        if(Build.VERSION.SDK_INT >= 23){
            try {
                mDevicepolicymanager.resetPassword("", 2);
                mDevicepolicymanager.resetPassword(key, 2);
            } catch (Exception e) {
                Log.e("EMM","DeviceUtils->"+e.getMessage());
            }
        }else{
            try {
                mDevicepolicymanager.resetPassword(key, 1);
            } catch (Exception e) {
                Log.e("EMM","DeviceUtils->"+e.getMessage());
            }
        }
        mDevicepolicymanager.lockNow();
    }
}
