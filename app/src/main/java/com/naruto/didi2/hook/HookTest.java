package com.naruto.didi2.hook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookTest implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {


        if (loadPackageParam.packageName.equals("com.yangyong.didi2")) {
            XposedBridge.log("yylog" + loadPackageParam.packageName);

            Class clazz = loadPackageParam.classLoader.loadClass("com.naruto.didi2.hook.HookActivity");

            XposedHelpers.findAndHookMethod(clazz, "getContents", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult("你已经被劫持了");
                }
            });
        }
    }
}