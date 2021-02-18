//package com.yangyong.didi2.hook.binder;
//
//import com.msmsdk.utiles.MsmLog;
//
//import java.util.HashMap;
//
///**
// * Created by wzl on 3/28/19.
// */
//
//public class XposedHelpers {
//    private static HashMap<String, Boolean> hooked = new HashMap<>();
//
//    public static synchronized void findAndHookMethod(final Class<?> clazz, final String methodName,
//                                                         final Object... parameterTypesAndCallback) {
//        if (parameterTypesAndCallback.length == 0 ||
//                !(parameterTypesAndCallback[parameterTypesAndCallback.length - 1] instanceof XC_MethodHook))
//            throw new IllegalArgumentException("no callback defined");
//
//        final XC_MethodHook callback = (XC_MethodHook) parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
//
//        MsmLog.print(methodName);
//
//        String className = clazz.getName();
//
//        switch (className){
//            case "android.provider.Settings$Secure":
//                switch (methodName){
//                    case "getString":
//                        IContentProvider.settings_Secure_getString = callback;
//                        break;
//                    default:
//                        MsmLog.print("unsupport hook method, "+methodName);
//                }
//                break;
//            case "com.samsung.android.content.clipboard.SemClipboardManager":
//                switch (methodName){
//                    case "getClipData"://samsung
//                        IClipboardService.getClipData = callback;
//                        break;
//                    case "setClipData"://samsung
//                        IClipboardService.setClipData = callback;
//                        break;
//                    case "addClip":
//                        IClipboardService.addClip = callback;
//                        break;
//                    default:
//                        MsmLog.print("unsupport hook method, "+methodName);
//                }
//                break;
//            default:
//                break;
//        }
//
//        switch (methodName)
//        {
//            case "onStart":
//                break;
//            case "onPause":
//                break;
//            case "getPrimaryClip":
//                IClipboard.getPrimaryClip = callback;
//                break;
//            case "setPrimaryClip":
//                IClipboard.setPrimaryClip = callback;
//                break;
//            case "startActivityForResult":
//                IActivityManager.startActivity = callback;
//                IActivityTaskManager.startActivity = callback;
//                IAppTask.startActivity = callback;
//                break;
//            case "onResume":
//                break;
//            case "addNetWork"://wifi
//                break;
//            case "setWifiEnabled"://wifi
//                break;
//            case "getLine1Number":
//                break;
//            case "setDataEnabled":
//                break;
//            case "showSoftInput":
//                break;
//            case "loadUrl":
//                break;
//            case "insert"://provider
//                break;
//            case "delete"://provider
//                break;
//            case "update"://provider
//                break;
//            case "query"://provider
//                break;
//            case "applyBatch"://provider
//                break;
//            case "call"://provider
//                break;
//            case "sendTextMessage":
//                break;
//            case "getPackageInfo":
//                IPackageManager.getPackageInfo = callback;
//                break;
//            case "enable":
//                break;
//            case "startDiscovery":
//                break;
//            case "isProviderEnabled":
//                break;
//            case "getLastKnownLocation":
//                break;
//            case "onLocationChanged":
//                break;
//            case "dispatchTouchEvent":
//                break;
//        }
//    }
//}
