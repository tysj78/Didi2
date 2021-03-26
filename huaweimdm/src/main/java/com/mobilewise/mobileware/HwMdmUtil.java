package com.mobilewise.mobileware;

import android.content.Context;
import android.os.Message;

import com.huawei.android.app.admin.DeviceControlManager;
import com.huawei.android.app.admin.DeviceRestrictionManager;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/3/23/0023
 */

public class HwMdmUtil {
    /**
     * 设置是否禁用蓝牙
     *
     * @param disabled
     */
    public static boolean setBluetoothDisabled(boolean disabled) {
        try {
            DeviceRestrictionManager deviceRestrictionManager = new DeviceRestrictionManager();
            deviceRestrictionManager.setBluetoothDisabled(MyApp.componentName, disabled);
            return true;
        } catch (Throwable e) {
            LogUtils.e("ExceptionsetBluetoothDisabled: " + e.toString());
        }
        return false;
    }

    /**
     * 设置静默激活设备管理器
     */
    public static void setSilentActiveAdmin(Context context, boolean b) {
        try {
            DeviceControlManager deviceControlManager = new DeviceControlManager();
            deviceControlManager.setSilentActiveAdmin(MyApp.componentName);
            //EMUI11.0.0新增接口，测试在低版本是否兼容
//            if (b) {
//                boolean b1 = deviceControlManager.setForcedActiveDeviceAdmin(MyApp.componentName, context);
//                LogUtils.e("设置静默激活设备管理器：" + b1);
//            } else {
//                boolean b1 = deviceControlManager.removeActiveDeviceAdmin(MyApp.componentName);
//                LogUtils.e("解除设备管理器：" + b1);
//            }

        } catch (Throwable e) {
            LogUtils.e("Exception setSilentActiveAdmin: " + e.toString());
        }
    }
}
