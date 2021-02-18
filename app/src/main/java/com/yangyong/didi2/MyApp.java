package com.yangyong.didi2;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.SystemClock;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

//import com.squareup.leakcanary.LeakCanary;
import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;
//import com.tencent.tinker.entry.ApplicationLike;
//import com.tinkerpatch.sdk.TinkerPatch;
//import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.yangyong.didi2.BuildConfig;
import com.yangyong.didi2.hook.HookProxy;
import com.yangyong.didi2.hook.Hook_Drawable_draw;
import com.yangyong.didi2.hook.ProxyWaterMark;
import com.yangyong.didi2.util.CrashCollector;
import com.yangyong.didi2.util.LogUtils;
import com.yangyong.didi2.zlog.ZLog;

//import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import xcrash.XCrash;


/**
 * Created by yangyong on 2019/9/4/0004.
 */

public class MyApp extends MultiDexApplication {
    private static final String TAG = "yylog";

    //    private ApplicationLike tinkerApplicationLike;
    private static Context mContext;
    public static Activity mActivity;
    private String logPath = Environment.getExternalStorageDirectory().getPath() + "/xcrash";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Log.e(TAG, "MyApp_onCreate: ");
        loadJiaMi();
        regActivity();
//        CrashCollector.getInstance().init(this);
//        MyCrashHandler.getInstance().init(this);
//        ZLog.Init(Environment.getExternalStorageDirectory().getPath()+"/emm_crashFolder");
//        ZLog.openSaveToFile();


//        initXlog();


//        handleSSLHandshake();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
//        initTinkerPatch();
//        Debug.startMethodTracing(Environment.getExternalStorageDirectory().getAbsolutePath()+"/av8d1");
//        sleep();
//        Debug.stopMethodTracing();
    }

    public static Context getContext(){
        return mContext;
    }

    private void regActivity() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
//                ProxyWaterMark.getInstance().hook(activity.getWindow());
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initXlog() {
        String logPath = Environment.getExternalStorageDirectory().getPath() + "/logsample/xlog";
        Log.d("test", logPath);
        Xlog.setConsoleLogOpen(true);
        Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "LOGSAMPLE", 0, "");
    }

    private void loadJiaMi() {
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            SQLiteDatabase.loadLibs(mContext);
//        SQLiteDatabase.loadLibs(mContext);
//                        } catch (Exception e) {
//                            Log.e(Constants.TAG, "初始化加密数据库异常: " + e.getMessage());
//                        }
//                    }
//                }
//        ).start();

        net.sqlcipher.database.SQLiteDatabase.loadLibs(this);
    }

    private void sleep() {
        SystemClock.sleep(2000);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LogUtils.e("attachBaseContext");
//        MultiDex.install(this);
//        hookNotificationManager(base);
        //初始化xcrash
        XCrash.InitParameters initParameters = new XCrash.InitParameters();
        initParameters.setAppVersion("1.1");
        initParameters.setLogDir(logPath);
        XCrash.init(this, initParameters);

//        startHook();
    }

    private void startHook() {
        Hook_Drawable_draw draw = new Hook_Drawable_draw();
//        SystemActionSubject.getInstance(null).addHooks(draw);
        HookProxy.findAndHookMethod(Drawable.class, "draw", Canvas.class, draw);
    }

    /**
     * 我们需要确保至少对主进程跟patch进程初始化 TinkerPatch
     */
    /*private void initTinkerPatch() {
        // 我们可以从这里获得Tinker加载过程的信息
        if (BuildConfig.TINKER_ENABLE) {
            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
            // 初始化TinkerPatch SDK
            TinkerPatch.init(
                    tinkerApplicationLike
//                new TinkerPatch.Builder(tinkerApplicationLike)
//                    .requestLoader(new OkHttp3Loader())
//                    .build()
            )
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true)
                    .setFetchPatchIntervalByHours(3)
            ;
            // 获取当前的补丁版本
            Log.d(TAG, "Current patch version is " + TinkerPatch.with().getPatchVersion());

            // fetchPatchUpdateAndPollWithInterval 与 fetchPatchUpdate(false)
            // 不同的是，会通过handler的方式去轮询
            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
        }
    }*/
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

    private void hookNotificationManager(Context context) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // 得到系统的 sService
            Method getService = NotificationManager.class.getDeclaredMethod("getService");
            getService.setAccessible(true);
            final Object sService = getService.invoke(notificationManager);

            Class iNotiMngClz = Class.forName("android.app.INotificationManager");
            // 动态代理 INotificationManager
            Object proxyNotiMng = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{iNotiMngClz}, new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    LogUtils.e("invoke(). method:{}" + method);
                    if (args != null && args.length > 0) {
                        for (Object arg : args) {
                            LogUtils.e("拦截到通知：" + arg);
                        }
                    }
                    // 操作交由 sService 处理，不拦截通知
                    return method.invoke(sService, args);
                    // 拦截通知，什么也不做
//                    return null;
                    // 或者是根据通知的 Tag 和 ID 进行筛选
                }
            });
            // 替换 sService
            Field sServiceField = NotificationManager.class.getDeclaredField("sService");
            sServiceField.setAccessible(true);
            sServiceField.set(notificationManager, proxyNotiMng);
        } catch (Exception e) {
            LogUtils.e("Hook NotificationManager failed!" + e.toString());
        }
    }
}

