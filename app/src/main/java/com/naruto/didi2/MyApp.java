package com.naruto.didi2;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.multidex.MultiDexApplication;

import com.aixunyun.cybermdm.util.sm.SM;
import com.naruto.didi2.greendao.DaoMaster;
import com.naruto.didi2.greendao.DaoSession;
import com.naruto.didi2.hook.HookProxy;
import com.naruto.didi2.hook.Hook_Drawable_draw;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.MyActivityManager;
import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

import org.greenrobot.greendao.database.Database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by yangyong on 2019/9/4/0004.
 */

public class MyApp extends MultiDexApplication {
    private static final String TAG = "yylog";

    //    private ApplicationLike tinkerApplicationLike;
    private static Context mContext;
    public static Activity mActivity;
    private String logPath = Environment.getExternalStorageDirectory().getPath() + "/xcrash";


    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static long sApplicationStartTime;

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

//        sApplicationStartTime = System.currentTimeMillis();
        initSM();
//        CrashCollector.getInstance().init(mContext);
        initX5();
    }

    private void initX5() {
//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                // TODO Auto-generated method stub
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                LogUtils.e(" onViewInitFinished is " + arg0);
//            }
//
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//            }
//        };
//        boolean b = QbSdk.canLoadX5(this);
//        LogUtils.e("当前设备是否可以加载x5:" + b);
//
//        QbSdk.setDownloadWithoutWifi(true);
//        //x5内核初始化接口
//        QbSdk.setTbsListener(new TbsListener() {
//            @Override
//            public void onDownloadFinish(int i) {
//                LogUtils.e("onDownloadFinish:" + i);
//            }
//
//            @Override
//            public void onInstallFinish(int i) {
//                LogUtils.e("onInstallFinish:" + i);
//            }
//
//            @Override
//            public void onDownloadProgress(int i) {
//                LogUtils.e("onDownloadProgress:" + i);
//            }
//        });
//        QbSdk.initX5Environment(getApplicationContext(), cb);
//        HashMap map = new HashMap();
//        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
//        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
//        QbSdk.initTbsSettings(map);
    }

    private void initGreendao() {
        DaoMaster.DevOpenHelper mDevOpenHelper = new DaoMaster.DevOpenHelper(this, "test.db");
        // regular SQLite database
//        ExampleOpenHelper helper = new ExampleOpenHelper(this, "notes-db");
//        Database db = helper.getWritableDb();

        // encrypted SQLCipher database
        // note: you need to add SQLCipher to your dependencies, check the build.gradle file
        // ExampleOpenHelper helper = new ExampleOpenHelper(this, "notes-db-encrypted");
        Database db = mDevOpenHelper.getEncryptedWritableDb("encryption-key");

        daoSession = new DaoMaster(db).newSession();
    }

    public static Context getContext() {
        return mContext;
    }

    private void regActivity() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                LogUtils.e("onActivityCreated：" + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                mActivity = activity;
//                ProxyWaterMark.getInstance().hook(activity.getWindow());
                LogUtils.e("MyApp onActivityResumed");
                MyActivityManager.getInstance().setCurrentActivity(activity);
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
        Log.d("yylog", logPath);
        Xlog.setConsoleLogOpen(true);
        Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "didi2", 0, "");
        //1.调用此方法xlog才会写入日志
        Log.setLogImp(new Xlog());
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
//        XCrash.InitParameters initParameters = new XCrash.InitParameters();
//        initParameters.setAppVersion("1.1");
//        initParameters.setLogDir(logPath);
//        XCrash.init(this, initParameters);

//        startHook();
    }

    private void initSM() {
        SM.saveSecretPass(mContext);
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

