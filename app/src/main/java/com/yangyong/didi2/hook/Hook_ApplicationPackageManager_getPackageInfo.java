//package com.yangyong.didi2.hook;
//
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//
//import com.msmsdk.Sandbox;
////import com.taobao.android.dexposed.XC_MethodHook;
//
///**
// * Created by uniking on 17-11-20.
// */
//
//public class Hook_ApplicationPackageManager_getPackageInfo extends XC_MethodHook implements IHook
////public class Hook_ApplicationPackageManager_getPackageInfo extends CallBackProxy implements IHook
//{
//    private static String Tag = "epic";
//    public static String className = "android.app.ApplicationPackageManager";
//    public static String methodName = "getPackageInfo";
//    public static String methodSig =
//            "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;";
//    public static IHookListener m_listener;
//
//    private PackageInfo backup(String packageName,
//                               int flags) throws Exception {
//        throw new Exception("stub can't be call getPackageInfo");
//    }
//
//    public static void hook(XC_MethodHook.MethodHookParam param, String var1, int var2)
//    {
//        PackageInfo ret = null;
//        if(var2 == PackageManager.GET_SIGNATURES)
//        {
//            ret = GlobalData.mSignInfo;
//            if (ret != null) {
//                if (ret.packageName.equals(var1)) {
//                    param.setResult(ret);
//                } else {
//                    MsmLog.print(Tag + " Hook_ApplicationPackageManager_getPackageInfo " + var1 + " " + ret.packageName);
//                    ret = Sandbox.getPackageInfo(var1);
//                    if(ret != null)
//                        param.setResult(ret);
//                }
//            } else {
//                MsmLog.print(Tag+" Hook_ApplicationPackageManager_getPackageInfo, m_SignInfo is NULL");
//            }
//        }
//    }
//
//    @Override
//    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
//        super.beforeHookedMethod(param);
//        MsmLog.print(Tag+" Hook_ApplicationPackageManager_getPackageInfo beforeHookedMethod");
//        hook(param, (String)param.args[0], (int)param.args[1]);
//    }
//
//    @Override
//    public void setListener(IHookListener listener) {
//        m_listener = listener;
//    }
//
//    @Override
//    public String description() {
//        return "";
//    }
//}
