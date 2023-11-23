//package com.yangyong.didi2.hook;
//
//import android.app.Activity;
//import android.app.PendingIntent;
//import android.bluetooth.BluetoothAdapter;
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.ContentProviderOperation;
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Canvas;
//import android.graphics.drawable.Drawable;
//import android.hardware.Sensor;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.location.GnssStatus;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.media.AudioRecord;
//import android.media.MediaRecorder;
//import android.net.Uri;
//import android.net.wifi.WifiConfiguration;
//import android.net.wifi.WifiManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.telephony.SmsManager;
//import android.telephony.TelephonyManager;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.inputmethod.InputMethodManager;
//import android.webkit.WebView;
//
//import com.msmsdk.callbacklayer.SystemActionSubject;
//import com.msmsdk.filesecurity.FileSecurity;
//import com.msmsdk.hook.inlineHook.InlineHookMain;
//import com.msmsdk.hook.javaHook.epic.Hook_Activity_StartActivityForResult;
//import com.msmsdk.hook.javaHook.epic.Hook_Activity_onPause;
//import com.msmsdk.hook.javaHook.epic.Hook_Activity_onResume;
//import com.msmsdk.hook.javaHook.epic.Hook_ApplicationPackageManager_getPackageInfo;
//import com.msmsdk.hook.javaHook.epic.Hook_AudioRecord_native_start;
//import com.msmsdk.hook.javaHook.epic.Hook_BluetoothAdapter_enable;
//import com.msmsdk.hook.javaHook.epic.Hook_BluetoothAdapter_startDiscovery;
//import com.msmsdk.hook.javaHook.epic.Hook_Camera_startPreview;
//import com.msmsdk.hook.javaHook.epic.Hook_ClipboardManager_getPrimaryClip;
//import com.msmsdk.hook.javaHook.epic.Hook_ClipboardManager_setPrimaryClip;
//import com.msmsdk.hook.javaHook.epic.Hook_ContentResolver_applyBatch;
//import com.msmsdk.hook.javaHook.epic.Hook_ContentResolver_call;
//import com.msmsdk.hook.javaHook.epic.Hook_ContentResolver_delete;
//import com.msmsdk.hook.javaHook.epic.Hook_ContentResolver_insert;
//import com.msmsdk.hook.javaHook.epic.Hook_ContentResolver_query;
//import com.msmsdk.hook.javaHook.epic.Hook_ContentResolver_update;
//import com.msmsdk.hook.javaHook.epic.Hook_EditableInputConnection_commitText;
//import com.msmsdk.hook.javaHook.epic.Hook_IOUtils_inputStreamToString;
//import com.msmsdk.hook.javaHook.epic.Hook_InputMethodManager_showSoftInput;
//import com.msmsdk.hook.javaHook.epic.Hook_LocationListener_onLocationChanged;
//import com.msmsdk.hook.javaHook.epic.Hook_LocationManager_getAllProviders;
//import com.msmsdk.hook.javaHook.epic.Hook_LocationManager_getLastKnownLocation;
//import com.msmsdk.hook.javaHook.epic.Hook_LocationManager_isProviderEnabled;
//import com.msmsdk.hook.javaHook.epic.Hook_LocationManager_isProviderEnabledForUser;
//import com.msmsdk.hook.javaHook.epic.Hook_LocationManager_registerGnssStatusCallback;
//import com.msmsdk.hook.javaHook.epic.Hook_MediaRecorder_start;
//import com.msmsdk.hook.javaHook.epic.Hook_OnekeyShare_show;
//import com.msmsdk.hook.javaHook.epic.Hook_Platform_share;
//import com.msmsdk.hook.javaHook.epic.Hook_PrintHelper_printBitmap;
//import com.msmsdk.hook.javaHook.epic.Hook_SamsungClipboardManager_addClip;
//import com.msmsdk.hook.javaHook.epic.Hook_SamsungClipboardManager_getClipData;
//import com.msmsdk.hook.javaHook.epic.Hook_SamsungClipboardManager_setClipData;
//import com.msmsdk.hook.javaHook.epic.Hook_SensorManager_registerListener;
//import com.msmsdk.hook.javaHook.epic.Hook_Settings_Secure_getString;
//import com.msmsdk.hook.javaHook.epic.Hook_SmsManager_sendTextMessage;
//import com.msmsdk.hook.javaHook.epic.Hook_TelephonyManager_getCellLocation;
//import com.msmsdk.hook.javaHook.epic.Hook_TelephonyManager_getLine1Number;
//import com.msmsdk.hook.javaHook.epic.Hook_TelephonyManager_setDataEnabled;
//import com.msmsdk.hook.javaHook.epic.Hook_WebView_loadUrl;
//import com.msmsdk.hook.javaHook.epic.Hook_WifiManager_addNetwork;
//import com.msmsdk.hook.javaHook.epic.Hook_WifiManager_setWifiEnabled;
//import com.msmsdk.hook.javaHook.epic.Hook_WindowManagerImpl_addView;
//import com.msmsdk.hook.javaHook.epic.Hook_Window_dispatchTouchEvent;
//import com.msmsdk.hook.javaHook.epic.Native_Message;
//import com.msmsdk.hook.javaHook.serverhook.ActivityLifecycle;
//import com.msmsdk.hook.javaHook.serverhook.LocationManagerHook;
//import com.msmsdk.policy.HookManager;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Set;
//
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_IMAGE;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_DELETE_CALL_LOG;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_DELETE_RAW_CONTACTS;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_DELETE_SMS;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_INSERT_CALL_LOG;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_INSERT_RAW_CONTACTS;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_INSERT_SMS;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_QUERY_CALL_LOG;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_QUERY_RAW_CONTACTS;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_QUERY_SMS;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_UPDATE_CALL_LOG;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_UPDATE_RAW_CONTACTS;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_CONTENT_PROVIDER_UPDATE_SMS;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_ICLIPBOARD_TRANSACTION_GETPRIMARYCLIP;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_ICLIPBOARD_TRANSACTION_SETPRIMARYCLIP;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_ILOCATION_MANAGER_TRANSACTION_GETLASTLOCATION;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_IPHONE_SUBINFO_TRANSACTION_GETLINE1NUMBER;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_MEDIARECORDER_START;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_SCREENSHOT;
//import static com.msmsdk.callbacklayer.CodeSet.FuncCode.F_SMSMANAGER_SEND;
//
///**
// * Created by wzl on 9/21/18.
// */
//
//public class Epic {
//    private static Context m_context = null;
//    private static Epic m_self = null;
//    private String Tag = "epic";
//    private static int[] hooked = new int[512];
//    private static boolean native_msg = false;
//
//    public static Epic getInstance(Context context)
//    {
//        m_context = context;
//        if(m_self == null)
//            m_self = new Epic();
//
//        return m_self;
//    }
//
//    public void startSignHook()
//    {
//        MsmLog.print("hook getPackageInfo");
//        Hook_ApplicationPackageManager_getPackageInfo b = new Hook_ApplicationPackageManager_getPackageInfo();
//        SystemActionSubject.getInstance(null).addHooks(b);
//        try {
//            HookProxy.findAndHookMethod(Class.forName("android.app.ApplicationPackageManager"), "getPackageInfo", String.class, int.class, b);
//        } catch (ClassNotFoundException e) {
//            MsmLog.print(e);
//        }
//    }
//
//
//    public static void sandboxCompatible(){
//        if(Build.VERSION.SDK_INT >= 28){
//            try{//android9.0 钉钉打包异常
//                Class sts = Class.forName("com.alibaba.doraemon.utils.IOUtils");
//                Hook_IOUtils_inputStreamToString hInputStreamToString = new Hook_IOUtils_inputStreamToString();
//                HookProxy.findAndHookMethod(sts, "inputStreamToString", InputStream.class, String.class,hInputStreamToString);
//            }catch (ClassNotFoundException e){
//                ;
//            }
//        }
//    }
//
//    public void startFuncHookNoDelay()
//    {
//        //hook binder
//        InlineHookMain.hookBinder();
//
//        //hook activity
//        ActivityLifecycle.getInstance().register.html(GlobalData.mApplication);
//        GlobalData.mHookWebView = true;
//        MsmLog.print(Tag + "hook startActivityForResult");
//        Hook_Activity_StartActivityForResult a = new Hook_Activity_StartActivityForResult();
//        SystemActionSubject.getInstance(null).addHooks(a);
//        HookProxy.findAndHookMethod(Activity.class, "startActivityForResult", Intent.class, int.class, Bundle.class, a);
//
//        //不要放到线程中，会偶发首次启动没有水印的情况
////        if(HookManager.getInstance().GetSwitch(F_WATER_MARK_CFG) == 1){
////            Hook_Activity_onResume mOnResume3 = new Hook_Activity_onResume();
////            hooks.add(mOnResume3);
////            HookProxy.findAndHookMethod(Activity.class, "onResume", mOnResume3);
////
////            Hook_ViewGroup_addView av = new Hook_ViewGroup_addView();
////            hooks.add(av);
////            try {
////                HookProxy.findAndHookMethod(Class.forName("android.view.ViewGroup"), "addView", View.class, int.class, ViewGroup.LayoutParams.class, av);
////            }catch (Exception e) {
////                MsmLog.print(e);
////            }
////        }
//
//        //
//    }
//
//    public void startFuncHook()
//    {
//        hookByPolicy();
//    }
//
//    public void hookByPolicy()
//    {
//
//        try {
//           /* Set<CodeSet.FuncCode> hookkeys = HookManager.getInstance().getHookKeys();
//            if(hookkeys == null)
//                return;
//
//            //sdk敏感信息使用别的功能码进行hook
//            if(hookkeys.contains(CodeSet.FuncCode.F_SDK_SENSITIVE_INFO)){
//                hookkeys.add(F_ICLIPBOARD_TRANSACTION_GETPRIMARYCLIP);
//                hookkeys.add(F_ILOCATION_MANAGER_TRANSACTION_GETLASTLOCATION);
//                hookkeys.add(F_IPHONE_SUBINFO_TRANSACTION_GETLINE1NUMBER);
//                hookkeys.add(F_MEDIARECORDER_START);
//                hookkeys.add(F_SMSMANAGER_SEND);
//                hookkeys.add(F_CONTENT_PROVIDER_INSERT_SMS);
//                hookkeys.add(F_CONTENT_PROVIDER_DELETE_SMS);
//                hookkeys.add(F_CONTENT_PROVIDER_UPDATE_SMS);
//                hookkeys.add(F_CONTENT_PROVIDER_QUERY_SMS);
//                hookkeys.add(F_CONTENT_PROVIDER_INSERT_CALL_LOG);
//                hookkeys.add(F_CONTENT_PROVIDER_DELETE_CALL_LOG);
//                hookkeys.add(F_CONTENT_PROVIDER_UPDATE_CALL_LOG);
//                hookkeys.add(F_CONTENT_PROVIDER_QUERY_CALL_LOG);
//                hookkeys.add(F_CONTENT_PROVIDER_INSERT_RAW_CONTACTS);
//                hookkeys.add(F_CONTENT_PROVIDER_DELETE_RAW_CONTACTS);
//                hookkeys.add(F_CONTENT_PROVIDER_UPDATE_RAW_CONTACTS);
//                hookkeys.add(F_CONTENT_PROVIDER_QUERY_RAW_CONTACTS);
//            }*/
//
//            /*//分享与好多功能共用
//            if(hookkeys.contains(CodeSet.FuncCode.F_SHARE_CONTENT_SYS)){
//                hookkeys.add(F_SCREENSHOT);
//                hookkeys.add(F_ICLIPBOARD_TRANSACTION_GETPRIMARYCLIP);
//                hookkeys.add(F_ICLIPBOARD_TRANSACTION_SETPRIMARYCLIP);
//            }*/
//
////            for(CodeSet.FuncCode one: hookkeys)
////            {
////                if(one == null ||
////                        hooked[one.ordinal()] == 1)
////                    continue;
////
////                switch(one)
////                {
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_CALL:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_DIAL:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_SENDTO_SMS:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_SENDTO_EMAIL:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_RECORD_SOUND:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_VIEW_CONTACTS_PEOPLE:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_CONTACT:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_IMAGE:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_VIDEO:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_IMAGE:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_AUDIO:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_VIDEO:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_IMAGE_CAPTURE:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_MANAGE_OVERLAY_PERMISSION:
////                        hooked[one.ordinal()]=1;
////                    case F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_BROWSER_VIEW:
////                        hooked[one.ordinal()]=1;
////                    case F_SHARE_CONTENT_SYS:
////                        hooked[one.ordinal()]=1;
//////                        Hook_Activity_StartActivityForResult a = new Hook_Activity_StartActivityForResult();
//////                        hooks.add(a);
//////                        HookProxy.findAndHookMethod(Activity.class, "startActivityForResult", Intent.class, int.class, Bundle.class, a);
////                        break;
////                    case F_SHARE_CONTENT_SHARESDK:
////                        hooked[one.ordinal()]=1;
////                        try{
////                            Class<?> onkeyshare = Class.forName("cn.sharesdk.onekeyshare.OnekeyShare");
////                            Hook_OnekeyShare_show sharesdk = new Hook_OnekeyShare_show();
////                            SystemActionSubject.getInstance(null).addHooks(sharesdk);
////                            HookProxy.findAndHookMethod(onkeyshare, "show", Context.class, sharesdk);
////                        }catch (Exception e){
////                            ;
////                        }
////
////                        try{
////                            Class<?> platform = Class.forName("cn.sharesdk.framework.Platform");
////                            Class<?> shareParams = Class.forName("cn.sharesdk.framework.Platform$ShareParams");
////                            Hook_Platform_share platform_share = new Hook_Platform_share();
////                            SystemActionSubject.getInstance(null).addHooks(platform_share);
////                            HookProxy.findAndHookMethod(platform, "share", shareParams, platform_share);
////                        }catch (Exception e){
////                            ;
////                        }
////
////
////
////                        break;
////                    case F_CAMERA_OPEN:
////                        hooked[one.ordinal()]=1;
//////                        Hook_Camera_getCameraInfo u2 = new Hook_Camera_getCameraInfo();
//////                        hooks.add(u2);
//////                        HookProxy.findAndHookMethod(Class.forName("android.hardware.Camera"), "_getCameraInfo", int.class, Camera.CameraInfo.class, u2);
////
////                        Hook_Camera_startPreview sp = new Hook_Camera_startPreview();
////                        SystemActionSubject.getInstance(null).addHooks(sp);
////                        HookProxy.findAndHookMethod(Class.forName("android.hardware.Camera"), "startPreview", sp);
////
////                        break;
////                    case F_ICLIPBOARD_TRANSACTION_GETPRIMARYCLIP:
////                        hooked[one.ordinal()]=1;
////                        try{
////                            Class scm = Class.forName("com.samsung.android.content.clipboard.SemClipboardManager");
////                            Hook_SamsungClipboardManager_getClipData sg = new Hook_SamsungClipboardManager_getClipData();
////                            SystemActionSubject.getInstance(null).addHooks(sg);
////                            HookProxy.findAndHookMethod(scm, "getClipData", scm, sg);
////                        }catch (Exception e){
////                            ;
////                        }
////
////                        Hook_ClipboardManager_getPrimaryClip c2 = new Hook_ClipboardManager_getPrimaryClip();
////                        SystemActionSubject.getInstance(null).addHooks(c2);
////                        HookProxy.findAndHookMethod(ClipboardManager.class, "getPrimaryClip", c2);
////
////                        break;
////                    case F_ICLIPBOARD_TRANSACTION_SETPRIMARYCLIP:
////                        hooked[one.ordinal()]=1;
////                        try{
////                            Class scm = Class.forName("com.samsung.android.content.clipboard.SemClipboardManager");
////                            Hook_SamsungClipboardManager_setClipData ss = new Hook_SamsungClipboardManager_setClipData();
////                            SystemActionSubject.getInstance(null).addHooks(ss);
////                            HookProxy.findAndHookMethod(scm, "setClipData", scm, ss);
////
////                            Hook_SamsungClipboardManager_addClip addClip = new Hook_SamsungClipboardManager_addClip();
////                            SystemActionSubject.getInstance(null).addHooks(addClip);
////                            HookProxy.findAndHookMethod(scm, "addClip", scm, addClip);
////                        }catch (Exception e){
////                            ;
////                        }
////
////                        Hook_ClipboardManager_setPrimaryClip c = new Hook_ClipboardManager_setPrimaryClip();
////                        SystemActionSubject.getInstance(null).addHooks(c);
////                        HookProxy.findAndHookMethod(ClipboardManager.class, "setPrimaryClip", ClipData.class, c);
////
////                        break;
////                    case F_SCREENSHOT:
////                        hooked[one.ordinal()]=1;
////                        Hook_Activity_onPause e1 = new Hook_Activity_onPause();
////                        SystemActionSubject.getInstance(null).addHooks(e1);
////                        HookProxy.findAndHookMethod(Activity.class, "onPause", e1);
////
////                        Hook_Activity_onResume mOnResume = new Hook_Activity_onResume();
////                        SystemActionSubject.getInstance(null).addHooks(mOnResume);
////                        HookProxy.findAndHookMethod(Activity.class, "onResume", mOnResume);
////                        break;
////                    case F_SSO:
////                        hooked[one.ordinal()]=1;
////
////                        Hook_Activity_onResume mOnResume2 = new Hook_Activity_onResume();
////                        SystemActionSubject.getInstance(null).addHooks(mOnResume2);
////                        HookProxy.findAndHookMethod(Activity.class, "onResume", mOnResume2);
////                        break;
////
////                    case F_WATER_MARK_CFG:
////                        hooked[one.ordinal()]=1;
////
//////                        Hook_Activity_onResume mOnResume3 = new Hook_Activity_onResume();
//////                        hooks.add(mOnResume3);
//////                        HookProxy.findAndHookMethod(Activity.class, "onResume", mOnResume3);
//////
//////                        Hook_ViewGroup_addView av = new Hook_ViewGroup_addView();
//////                        hooks.add(av);
//////                        try {
//////                            HookProxy.findAndHookMethod(Class.forName("android.view.ViewGroup"), "addView", View.class, int.class, ViewGroup.LayoutParams.class, av);
//////                        }catch (Exception e) {
//////                            MsmLog.print(e);
//////                        }
//
//                        Hook_Drawable_draw draw = new Hook_Drawable_draw();
//                        SystemActionSubject.getInstance(null).addHooks(draw);
//                        HookProxy.findAndHookMethod(Drawable.class, "draw", Canvas.class, draw);
//
////                        break;
////                    case F_IWINDOW_SESSION_TRANSACTION_ADDTODISPLAY:
////                        hooked[one.ordinal()]=1;
////                        Hook_WindowManagerImpl_addView w = new Hook_WindowManagerImpl_addView();
////                        SystemActionSubject.getInstance(null).addHooks(w);
////                        try {
////                            HookProxy.findAndHookMethod(Class.forName("android.view.WindowManagerImpl"), "addView", View.class, ViewGroup.LayoutParams.class, w);
////                        } catch (ClassNotFoundException e) {
////                            MsmLog.print(e);
////                        }
////                        break;
////                    case F_ILOCATION_MANAGER_TRANSACTION_GETLASTLOCATION:
////                        hooked[one.ordinal()]=1;
//////                        try {
//////                            Class<?> alcClass = Class.forName("com.amap.api.location.AMapLocationClient");
//////                            Hook_AMapLocationClient_startLocation alc = new Hook_AMapLocationClient_startLocation();
//////                            hooks.add(alc);
//////                            HookProxy.findAndHookMethod(alcClass, "startLocation", alc);
//////                        } catch (ClassNotFoundException e) {
//////                            ;//e.printStackTrace();
//////                        }
////
////                        Hook_LocationManager_getLastKnownLocation sysc = new Hook_LocationManager_getLastKnownLocation();
////                        SystemActionSubject.getInstance(null).addHooks(sysc);
////                        HookProxy.findAndHookMethod(LocationManager.class, "getLastKnownLocation", String.class, sysc);
////
////                        Hook_LocationListener_onLocationChanged syslis = new Hook_LocationListener_onLocationChanged();
////                        SystemActionSubject.getInstance(null).addHooks(syslis);
////                        HookProxy.findAndHookMethod(LocationListener.class, "onLocationChanged", Location.class, syslis);
////
////                        Hook_LocationManager_isProviderEnabled sysProvider = new Hook_LocationManager_isProviderEnabled();
////                        SystemActionSubject.getInstance(null).addHooks(sysProvider);
////                        HookProxy.findAndHookMethod(LocationManager.class, "isProviderEnabled", String.class, sysProvider);
////
////                        Hook_LocationManager_isProviderEnabledForUser sysProviderForUser = new Hook_LocationManager_isProviderEnabledForUser();
////                        SystemActionSubject.getInstance(null).addHooks(sysProviderForUser);
////                        HookProxy.findAndHookMethod(LocationManager.class, "isProviderEnabledForUser", String.class, int.class, sysProviderForUser);
////
////                        Hook_LocationManager_getAllProviders getAllProviders = new Hook_LocationManager_getAllProviders();
////                        SystemActionSubject.getInstance(null).addHooks(getAllProviders);
////                        HookProxy.findAndHookMethod(LocationManager.class, "getAllProviders", getAllProviders);
////
////                        if(Build.VERSION.SDK_INT >= 24){
////                            Hook_LocationManager_registerGnssStatusCallback registerGnssStatusCallback = new Hook_LocationManager_registerGnssStatusCallback();
////                            SystemActionSubject.getInstance(null).addHooks(registerGnssStatusCallback);
////                            HookProxy.findAndHookMethod(LocationManagerHook.class, "registerGnssStatusCallback", GnssStatus.Callback.class, registerGnssStatusCallback);
////                        }
////
////                        Hook_ContentResolver_call ccall = new Hook_ContentResolver_call();
////                        SystemActionSubject.getInstance(null).addHooks(ccall);
////                        HookProxy.findAndHookMethod(ContentResolver.class, "call", String.class, String.class, String.class, Bundle.class, ccall);
////
////                        Hook_TelephonyManager_getCellLocation getCellLocation = new Hook_TelephonyManager_getCellLocation();
////                        SystemActionSubject.getInstance(null).addHooks(getCellLocation);
////                        HookProxy.findAndHookMethod(TelephonyManager.class, "getCellLocation", getCellLocation);
////
////                        Hook_Settings_Secure_getString getString = new Hook_Settings_Secure_getString();
////                        SystemActionSubject.getInstance(null).addHooks(getString);
////                        HookProxy.findAndHookMethod(Settings.Secure.class, "getString", ContentResolver.class, String.class, getString);
////                        break;
////                    case F_INPUT_SENSITIVE_DATA:
////                        hooked[one.ordinal()]=1;
////                        Hook_EditableInputConnection_commitText d = new Hook_EditableInputConnection_commitText();
////                        SystemActionSubject.getInstance(null).addHooks(d);
////                        try {
////                            HookProxy.findAndHookMethod(Class.forName("com.android.internal.widget.EditableInputConnection"), "commitText", CharSequence.class, int.class, d);
////                        } catch (ClassNotFoundException e) {
////                            MsmLog.print(e);
////                        }
////                        break;
////                    case F_IWIFI_MANAGER_ADD_OR_UPDATE_NETWORK:
////                        hooked[one.ordinal()]=1;
////                        Hook_WifiManager_addNetwork g = new Hook_WifiManager_addNetwork();
////                        SystemActionSubject.getInstance(null).addHooks(g);
////                        HookProxy.findAndHookMethod(WifiManager.class, "addNetwork", WifiConfiguration.class, g);
////                        break;
////                    case F_IWIFI_MANAGER_SET_WIFI_ENABLED:
////                        hooked[one.ordinal()]=1;
////                        Hook_WifiManager_setWifiEnabled h = new Hook_WifiManager_setWifiEnabled();
////                        SystemActionSubject.getInstance(null).addHooks(h);
////                        HookProxy.findAndHookMethod(WifiManager.class, "setWifiEnabled", boolean.class, h);
////                        break;
////                    case F_IBLUETOOTH_MANAGER_TRANSACTION_ENABLE:
////                        hooked[one.ordinal()]=1;
////                        Hook_BluetoothAdapter_enable i = new Hook_BluetoothAdapter_enable();
////                        SystemActionSubject.getInstance(null).addHooks(i);
////                        HookProxy.findAndHookMethod(BluetoothAdapter.class, "enable", i);
////                        break;
////                    case F_IBLUETOOTH_MANAGER_TRANSACTION_START_DISCOVER:
////                        hooked[one.ordinal()]=1;
////                        Hook_BluetoothAdapter_startDiscovery j = new Hook_BluetoothAdapter_startDiscovery();
////                        SystemActionSubject.getInstance(null).addHooks(j);
////                        HookProxy.findAndHookMethod(BluetoothAdapter.class, "startDiscovery", j);
////                        break;
////                    case F_WEBVIEW_LOADURL:
////                        hooked[one.ordinal()]=1;
////                        Hook_WebView_loadUrl k = new Hook_WebView_loadUrl();
////                        SystemActionSubject.getInstance(null).addHooks(k);
////                        HookProxy.findAndHookMethod(WebView.class, "loadUrl", String.class, k);
////                        break;
////                    case F_CONTENT_PROVIDER_INSERT_RAW_CONTACTS:
////                        hooked[one.ordinal()]=1;
////                    case F_CONTENT_PROVIDER_DELETE_RAW_CONTACTS:
////                        hooked[one.ordinal()]=1;
////                    case F_CONTENT_PROVIDER_UPDATE_RAW_CONTACTS:
////                        hooked[one.ordinal()]=1;
////                        Hook_ContentResolver_applyBatch ab = new Hook_ContentResolver_applyBatch();
////                        SystemActionSubject.getInstance(null).addHooks(ab);
////                        ArrayList<ContentProviderOperation> arrayCp = new ArrayList<ContentProviderOperation>();
////                        HookProxy.findAndHookMethod(ContentResolver.class, "applyBatch", String.class, arrayCp.getClass(), ab);
////                        break;
////                    case F_CONTENT_PROVIDER_DELETE_CALL_LOG:
////                        hooked[one.ordinal()]=1;
////                    case F_CONTENT_PROVIDER_DELETE_SMS:
////                        hooked[one.ordinal()]=1;
////                        Hook_ContentResolver_delete o = new Hook_ContentResolver_delete();
////                        SystemActionSubject.getInstance(null).addHooks(o);
////                        HookProxy.findAndHookMethod(ContentResolver.class, "delete", Uri.class, String.class, String[].class, o);
////                        break;
////                    case F_CONTENT_PROVIDER_INSERT_CALL_LOG:
////                        hooked[one.ordinal()]=1;
////                    case F_CONTENT_PROVIDER_INSERT_SMS:
////                        hooked[one.ordinal()]=1;
////                        Hook_ContentResolver_insert p = new Hook_ContentResolver_insert();
////                        SystemActionSubject.getInstance(null).addHooks(p);
////                        HookProxy.findAndHookMethod(ContentResolver.class, "insert", Uri.class, ContentValues.class, p);
////                        break;
////                    case F_CONTENT_PROVIDER_QUERY_RAW_CONTACTS:
////                        hooked[one.ordinal()]=1;
////                    case F_CONTENT_PROVIDER_QUERY_CALL_LOG:
////                        hooked[one.ordinal()]=1;
////                    case F_CONTENT_PROVIDER_QUERY_SMS:
////                        hooked[one.ordinal()]=1;
////                        Hook_ContentResolver_query q = new Hook_ContentResolver_query();
////                        SystemActionSubject.getInstance(null).addHooks(q);
////                        HookProxy.findAndHookMethod(ContentResolver.class, "query", Uri.class, String[].class, String.class, String[].class, String.class, q);
////                        break;
////                    case F_CONTENT_PROVIDER_UPDATE_CALL_LOG:
////                        hooked[one.ordinal()]=1;
////                    case F_CONTENT_PROVIDER_UPDATE_SMS:
////                        hooked[one.ordinal()]=1;
////                        Hook_ContentResolver_update r = new Hook_ContentResolver_update();
////                        SystemActionSubject.getInstance(null).addHooks(r);
////                        HookProxy.findAndHookMethod(ContentResolver.class, "update", Uri.class, ContentValues.class, String.class, String[].class, r);
////                        break;
////                    case F_SAFE_KEYBOARD:
////                    case F_SOFT_INPUT:
////                        hooked[one.ordinal()]=1;
////                        Hook_InputMethodManager_showSoftInput s = new Hook_InputMethodManager_showSoftInput();
////                        SystemActionSubject.getInstance(null).addHooks(s);
////                        HookProxy.findAndHookMethod(InputMethodManager.class, "showSoftInput", View.class, int.class, s);
////                        break;
////                    case F_IPHONE_SUBINFO_TRANSACTION_GETLINE1NUMBER:
////                        hooked[one.ordinal()]=1;
////                        Hook_TelephonyManager_getLine1Number u = new Hook_TelephonyManager_getLine1Number();
////                        SystemActionSubject.getInstance(null).addHooks(u);
////                        HookProxy.findAndHookMethod(TelephonyManager.class, "getLine1Number", u);
////                        break;
////                    case F_MEDIARECORDER_START:
////                        hooked[one.ordinal()]=1;
////                        Hook_MediaRecorder_start mrs = new Hook_MediaRecorder_start();
////                        SystemActionSubject.getInstance(null).addHooks(mrs);
////                        HookProxy.findAndHookMethod(MediaRecorder.class, "start", mrs);
////
////                        Hook_AudioRecord_native_start ars = new Hook_AudioRecord_native_start();
////                        SystemActionSubject.getInstance(null).addHooks(ars);
////                        HookProxy.findAndHookMethod(AudioRecord.class, "native_start", int.class, int.class, ars);
////                        break;
////                    case F_TELEPHONY_DATA_SET_ENABLED:
////                        hooked[one.ordinal()]=1;
////                        Hook_TelephonyManager_setDataEnabled sde = new Hook_TelephonyManager_setDataEnabled();
////                        SystemActionSubject.getInstance(null).addHooks(sde);
////                        HookProxy.findAndHookMethod(TelephonyManager.class, "setDataEnabled", boolean.class, sde);
////                        break;
////                    case F_ISENSOR_EVENT_ENABLE_DISABLE:
////                        hooked[one.ordinal()]=1;
////                        Hook_SensorManager_registerListener regl = new Hook_SensorManager_registerListener();
////                        SystemActionSubject.getInstance(null).addHooks(regl);
////                        HookProxy.findAndHookMethod(SensorManager.class, "registerListener", SensorEventListener.class, Sensor.class, int.class, regl);
////                        break;
////                    case F_IPRINT_MANAGER_TRANSACTION_PRINT:
////                        hooked[one.ordinal()]=1;
////                        try{
////                            Class.forName("android.support.v4.print.PrintHelper");
////                        }catch (Exception e)
////                        {
////                            break;
////                        }
////                        Hook_PrintHelper_printBitmap pb = new Hook_PrintHelper_printBitmap();
////                        SystemActionSubject.getInstance(null).addHooks(pb);
////                        //不要依赖v4，v7， 如果需要用反射代替PrintHelper.class
////                        //HookProxy.findAndHookMethod(PrintHelper.class, "printBitmap",String.class, Bitmap.class, pb);
////                        break;
////                    case F_SMSMANAGER_SEND:
////                        hooked[one.ordinal()]=1;
////                        Hook_SmsManager_sendTextMessage stm = new Hook_SmsManager_sendTextMessage();
////                        SystemActionSubject.getInstance(null).addHooks(stm);
////                        HookProxy.findAndHookMethod(SmsManager.class, "sendTextMessage",
////                                String.class, String.class, String.class, PendingIntent.class, PendingIntent.class,
////                                stm);
////                        break;
////                    case F_PATTERN_LOCK:
////                    case F_CLICK_FRAUD:
////                        hooked[one.ordinal()]=1;
////                        Hook_Window_dispatchTouchEvent pdte = new Hook_Window_dispatchTouchEvent();
////                        SystemActionSubject.getInstance(null).addHooks(pdte);
////                        HookProxy.findAndHookMethod(Window.class, "dispatchTouchEvent", MotionEvent.class, pdte);
////                        break;
////                    default:
////                        break;
////                }//switch
////            }//for
////            if(false == native_msg){
////                native_msg = true;
////                Native_Message nm = new Native_Message();
////                SystemActionSubject.getInstance(null).addHooks(nm);
////            }
////
////            //file crypt lib
////            if(HookManager.getInstance().GetSwitch(F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_IMAGE) == 1)
////                FileSecurity.getInstance().startSign();//hook open
//
//        } catch (Exception e) {
//            MsmLog.print(e);
//        }
//    }
//}