package com.yangyong.didi2;

import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.os.SystemClock;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.tinker.entry.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.yangyong.didi2.BuildConfig;

import net.sqlcipher.database.SQLiteDatabase;

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

public class MyApp extends Application {
    private static final String TAG = "yy";

    private ApplicationLike tinkerApplicationLike;
    public static Context mContext;

    public MyApp() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Log.e(TAG, "MyApp_onCreate: ");
        loadJiaMi();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
//        initTinkerPatch();
//        Debug.startMethodTracing(Environment.getExternalStorageDirectory().getAbsolutePath()+"/av8d1");
//        sleep();
//        Debug.stopMethodTracing();
    }

    private void loadJiaMi() {
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            SQLiteDatabase.loadLibs(mContext);
                            SQLiteDatabase.loadLibs(mContext);
//                        } catch (Exception e) {
//                            Log.e(Constants.TAG, "初始化加密数据库异常: " + e.getMessage());
//                        }
//                    }
//                }
//        ).start();
    }

    private void sleep() {
        SystemClock.sleep(2000);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
        Log.e(TAG, "attachBaseContext: ");
    }

    /**
     * 我们需要确保至少对主进程跟patch进程初始化 TinkerPatch
     */
    private void initTinkerPatch() {
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
    }


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
}
