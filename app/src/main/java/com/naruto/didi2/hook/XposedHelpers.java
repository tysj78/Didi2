package com.naruto.didi2.hook;

import java.util.HashMap;

/**
 * Created by wzl on 3/28/19.
 */

public class XposedHelpers {
    private static HashMap<String, Boolean> hooked = new HashMap<>();

    public static synchronized void findAndHookMethod(final Class<?> clazz, final String methodName,
                                                         final Object... parameterTypesAndCallback) {
        if (parameterTypesAndCallback.length == 0 ||
                !(parameterTypesAndCallback[parameterTypesAndCallback.length - 1] instanceof XC_MethodHook))
            throw new IllegalArgumentException("no callback defined");

        final XC_MethodHook callback = (XC_MethodHook) parameterTypesAndCallback[parameterTypesAndCallback.length - 1];

        MsmLog.print("com.msmsdk.hook.javaHook.serverhook.XposedHelpers "+methodName);

        switch (methodName)
        {
//            case "onStart":
//                //ActivityProxyInstrumentation.onStart = callback;
//                //ApplicationInstrumentation.onStart = callback;
//                break;
//            case "onPause":
//                //ActivityProxyInstrumentation.onPause = callback;
//                //ApplicationInstrumentation.onPause = callback;
//                ActivityLifecycle.m_onActivityPaused = callback;
//                break;
//            case "startActivityForResult":
//                //ActivityProxyInstrumentation.StartActivityForResult = callback;
//                //ApplicationInstrumentation.StartActivityForResult = callback;
//                break;
//            case "onResume":
//                //ActivityProxyInstrumentation.onResume = callback;
//                //ApplicationInstrumentation.onResume = callback;
//                ActivityLifecycle.m_onActivityResumed = callback;
//                break;
//            case "getPrimaryClip":
//                ClipboardHook.getPrimaryClip = callback;
//                break;
//            case "setPrimaryClip":
//                ClipboardHook.setPrimaryClip = callback;
//                break;
//            case "getClipData":
//                SamsungClipboardHook.getPrimaryClip = callback;
//                break;
//            case "setClipData":
//                SamsungClipboardHook.setPrimaryClip = callback;
//                break;
//            case "addNetWork"://wifi
//                WifiManagerHook.addNetWork = callback;
//                break;
//            case "setWifiEnabled"://wifi
//                WifiManagerHook.setWifiEnabled = callback;
//                break;
//            case "getLine1Number":
//                TelephonyManagerHook.getLine1Number = callback;
//                break;
//            case "setDataEnabled":
//                TelephonyManagerHook.setDataEnabled = callback;
//                break;
//            case "getCellLocation":
//                TelephonyManagerHook.getCellLocation = callback;
//                break;
//            case "showSoftInput":
//                InputMethodManagerHook.showSoftInput = callback;
//                break;
//            case "loadUrl":
//                WebViewHook.WebViewProviderHandler.loadUrl = callback;
//                break;
//            case "insert"://provider
//                ActivityManagerHook.ContentProviderHandler.insert = callback;
//                break;
//            case "delete"://provider
//                ActivityManagerHook.ContentProviderHandler.delete = callback;
//                break;
//            case "update"://provider
//                ActivityManagerHook.ContentProviderHandler.update = callback;
//                break;
//            case "query"://provider
//                ActivityManagerHook.ContentProviderHandler.query = callback;
//                break;
//            case "applyBatch"://provider
//                ActivityManagerHook.ContentProviderHandler.applyBatch = callback;
//                break;
//            case "call"://provider
//                ActivityManagerHook.ContentProviderHandler.call = callback;
//                break;
//            case "sendTextMessage":
//                SmsServiceHook.sendTextForSubscriber = callback;
//                break;
//            case "getPackageInfo":
//                PackageManagerHook.getPackageInfo = callback;
//                break;
//            case "enable":
//                BluetoothManagerServerHook.enable = callback;
//                break;
//            case "startDiscovery":
//                BluetoothManagerServerHook.startDiscovery = callback;
//                break;
//            case "isProviderEnabled":
//                LocationManagerHook.isProviderEnabled = callback;
//                break;
//            case "isProviderEnabledForUser":
//                LocationManagerHook.isProviderEnabledForUser = callback;
//                break;
//            case "getAllProviders":
//                LocationManagerHook.getAllProviders = callback;
//                break;
//            case "getLastKnownLocation":
//                LocationManagerHook.getLastKnownLocation = callback;
//                break;
//            case "onLocationChanged":
//                LocationManagerHook.onLocationChanged = callback;
//                break;
//            case "registerGnssStatusCallback":
//                LocationManagerHook.registerGnssStatusCallback = callback;
//                break;
//            case "dispatchTouchEvent":
//                PhoneWindowHook.dispatchTouchEvent = callback;
//                break;
            case "draw":
                ProxyWaterMark.draw = callback;
                break;
        }

        if(hooked.get(methodName) != null && hooked.get(methodName)) {
            MsmLog.print("rehook same function, "+ methodName);
            return;
        }

        switch (methodName)
        {
//            case "onStart":
//            case "onPause":
//            case "startActivityForResult":
//            case "onResume":
//                hooked.put("onStart", true);
//                hooked.put("onPause", true);
//                hooked.put("StartActivityForResult", true);
//                hooked.put("startActivityForResult", true);
//                hooked.put("onResume", true);
////                try {
////                    ActivityHook.replaceThreadInstrumentation();
////                } catch (Exception e) {
////                    MsmLog.print(e);
////                }
//                break;
//
//            case "getPrimaryClip":
//            case "setPrimaryClip":
//                hooked.put("getPrimaryClip", true);
//                hooked.put("setPrimaryClip", true);
//                ClipboardHook.hookService(GlobalData.mContext);
//                break;
//
//            case "getClipData":
//            case "setClipData":
//                hooked.put("getClipData", true);
//                hooked.put("setClipData", true);
//                SamsungClipboardHook.hookService(GlobalData.mContext);
//                break;
//
//            case "addNetWork":
//            case "setWifiEnabled":
//                hooked.put("addNetWork", true);
//                hooked.put("setWifiEnabled", true);
//                WifiManagerHook.hookService(GlobalData.mContext);
//                break;
//
//            case "setDataEnabled":
//            case "getLine1Number":
//            case "getCellLocation":
//                hooked.put("setDataEnabled", true);
//                hooked.put("getLine1Number", true);
//                hooked.put("getCellLocation", true);
//                TelephonyManagerHook.hookService(GlobalData.mContext);
//                break;
//
//            case "showSoftInput":
//                hooked.put("showSoftInput", true);
//                InputMethodManagerHook.hookService(GlobalData.mContext);
//                break;
//
//            case "loadUrl":
//                hooked.put("loadUrl", true);
//                GlobalData.mHookWebView = true;
//                break;
//
//            case "insert"://provider
//            case "delete":
//            case "update":
//            case "query":
//            case "applyBatch":
//            case "call":
//                hooked.put("insert", true);
//                hooked.put("delete", true);
//                hooked.put("update", true);
//                hooked.put("query", true);
//                hooked.put("applyBatch", true);
//                hooked.put("call", true);
//                ActivityManagerHook.hookService(GlobalData.mContext);
//                break;
//
//            case "sendTextMessage":
//                hooked.put("sendTextMessage", true);
//                SmsServiceHook.hookService(GlobalData.mContext);
//                break;
//
//            case "getPackageInfo":
//                hooked.put("getPackageInfo", true);
//                PackageManagerHook.hook();
//                break;
//
//            case "startDiscovery":
//            case "enable":
//                hooked.put("startDiscovery", true);
//                hooked.put("enable", true);
//                BluetoothManagerServerHook.hookService(GlobalData.mContext);
//                break;
//
//            case "isProviderEnabled":
//            case "getLastKnownLocation":
//            case "onLocationChanged":
//            case "isProviderEnabledForUser":
//            case "getAllProviders":
//            case "registerGnssStatusCallback":
//                hooked.put("isProviderEnabled", true);
//                hooked.put("getLastKnownLocation", true);
//                hooked.put("onLocationChanged", true);
//                hooked.put("isProviderEnabledForUser", true);
//                hooked.put("getAllProviders", true);
//                hooked.put("registerGnssStatusCallback", true);
//                LocationManagerHook.hookService(GlobalData.mContext);
//                break;
//            case "dispatchTouchEvent":
//                hooked.put("dispatchTouchEvent", true);
//                PhoneWindowHook.hookService();
            case "draw":
                hooked.put("draw", true);
        }
    }
}
