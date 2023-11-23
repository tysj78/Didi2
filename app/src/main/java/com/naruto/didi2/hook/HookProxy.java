package com.naruto.didi2.hook;

import com.naruto.didi2.util.LogUtils;


/**
 * Created by wzl on 3/26/19.
 */
public class HookProxy {
    private static String Tag = "HookProxy";

    static {
        disableHuaweiCache();
    }

    public static void disableHuaweiCache(){
        try {
            Class.forName("huawei.android.app.HwApiCacheMangerEx");
            Object HwApiCacheMangerEx = RefInvoke.invokeStaticDeclaredMethod("huawei.android.app.HwApiCacheMangerEx", "getDefault", new Class[]{}, new Object[]{});

            RefInvoke.setFieldOjbect("huawei.android.app.HwApiCacheMangerEx", "bCanCache", HwApiCacheMangerEx, false);
            RefInvoke.setFieldOjbect("huawei.android.app.HwApiCacheMangerEx", "USE_CACHE", HwApiCacheMangerEx, false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized static Object findAndHookMethod(Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {
        if (parameterTypesAndCallback.length == 0)
            throw new IllegalArgumentException("no callback defined");

        final Object callback = parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
        if(callback instanceof XC_MethodHook){
            LogUtils.e("XC_MethodHook");
            XposedHelpers.findAndHookMethod(clazz, methodName, parameterTypesAndCallback);
        }
        else if(callback instanceof com.naruto.didi2.hook.binder.XC_MethodHook){
            LogUtils.e("binder.XC_MethodHook");
//            com.yangyong.didi2.hook.binder.XposedHelpers.findAndHookMethod(clazz, methodName, parameterTypesAndCallback);
        }
        else{
            LogUtils.e("===========");
        }

//            if(callback instanceof  com.taobao.android.dexposed.XC_MethodHook)
//        {
//            if(Build.VERSION.SDK_INT >= 30)
//                YAHFA.findAndHookMethod(clazz, methodName, parameterTypesAndCallback);
//            else
//                com.taobao.android.dexposed.DexposedBridge.findAndHookMethod(clazz, methodName, parameterTypesAndCallback);
//        }

        return null;
    }

//    public synchronized static Object findAndHookMethod(String className, String methodName, Object... parameterTypesAndCallback) {
//        if (parameterTypesAndCallback.length == 0)
//            throw new IllegalArgumentException("no callback defined");
//        Class clazz = null;
//        try {
//            clazz = Class.forName(className);
//
//            final Object callback = parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
//            if(callback instanceof com.msmsdk.hook.javaHook.serverhook.XC_MethodHook)
//                com.msmsdk.hook.javaHook.serverhook.XposedHelpers.findAndHookMethod(clazz, methodName, parameterTypesAndCallback);
//            else if(callback instanceof com.msmsdk.hook.javaHook.binder.XC_MethodHook)
//                com.msmsdk.hook.javaHook.binder.XposedHelpers.findAndHookMethod(clazz, methodName, parameterTypesAndCallback);
//            else if(callback instanceof  com.taobao.android.dexposed.XC_MethodHook)
//            {
//                if(Build.VERSION.SDK_INT >= 30)
//                    YAHFA.findAndHookMethod(clazz, methodName, parameterTypesAndCallback);
//                else
//                    com.taobao.android.dexposed.DexposedBridge.findAndHookMethod(clazz, methodName, parameterTypesAndCallback);
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}