package com.naruto.didi2.activity.test;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.didi2.ndkdemo.NdkUtil;
import com.naruto.didi2.MyApp;
import com.naruto.didi2.R;
import com.naruto.didi2.activity.Main2Activity;
import com.naruto.didi2.bean.MWifiInfo;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.ThreadPoolUtil;
import com.naruto.didi2.util.WorkThread;

import org.json.JSONArray;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ThreadTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yylog";
    private Button methodlock;
    private P mP;
    private P mP2;
    private Q mQ;
    private Q mQ2;
    private Button method;
    private Button lock_screen;
    private Button set_lock_screen;
    private Button recover;
    private Button close_bluetooth;
    private Button close_wifi;
    private Button stop_wifi;
    private final Object lock = new Object();
    private Timer timer;
    private TimerTask timerTask;
    private Button stop_hot_a;
    /**
     * 获取移动流量
     */
    private Button mQueryFlow;
    private Button mSdcardQuery;
    private Button mBatteryQuery;
    private Button mDatasizeQuery;
    private Button mWifiConnect;
    private myBrocaster mBrocaster;
    private ScrollView mGroupSv;
    private LinearLayout mGroupLl;
    private Button mAppusertime;
    private Button mApppackegname;
    private HashMap<String, StatisticsEvent> mPkgs;
    private ArrayList<StatisticsEvent> eventList;
    private Button mAppuserflow;
    private NetworkStatsManager networkStatsManager;
    private List<String> strings;
    private final Object object = new Object();
    private int open = 0;
    private Timer timer1;
    private TimerTask timerTask1;
    private long lastRX = -1;
    private long allTX;
    private EditText mContentEt;
    private UseTimeDataManager mUseTimeDataManager;
    private int wifiCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test2);
        initView();

//        AppUtil.getInstance().openDeviceAdmin(this);
//        Intent intent1 = new Intent(this, ForegroundService.class);
//        if (Build.VERSION.SDK_INT >= 26) {
//            startForegroundService(intent1);
//        } else {
//            startService(intent1);
//        }

        request();
        registRec();
        initEdit();

    }

    private void initEdit() {
     /*   mContentEt.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

        });*/
        mContentEt.setFocusable(false);

        mContentEt.setFocusableInTouchMode(false);
    }

    private void registRec() {
        mBrocaster = new myBrocaster();
        IntentFilter filter = new IntentFilter();
        // 监视蓝牙关闭和打开的状态
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//蓝牙
        filter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);//WIFI
        filter.addAction(Intent.ACTION_SCREEN_ON);//解锁广播
        filter.addAction("com.aixunyun.emm.clock");//闹钟触发广播
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//启用网络变化广播
        filter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");//个人热点   gxb add 20190117
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);

        // 监视蓝牙设备与APP连接的状态
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mBrocaster, filter);
    }


    private void initView() {
        methodlock = (Button) findViewById(R.id.methodlock);

        methodlock.setOnClickListener(this);
        method = (Button) findViewById(R.id.method);
        method.setOnClickListener(this);
        lock_screen = (Button) findViewById(R.id.lock_screen);
        lock_screen.setOnClickListener(this);
        set_lock_screen = (Button) findViewById(R.id.set_lock_screen);
        set_lock_screen.setOnClickListener(this);
        recover = (Button) findViewById(R.id.recover);
        recover.setOnClickListener(this);
        close_bluetooth = (Button) findViewById(R.id.close_bluetooth);
        close_bluetooth.setOnClickListener(this);
        close_wifi = (Button) findViewById(R.id.close_wifi);
        close_wifi.setOnClickListener(this);
        stop_wifi = (Button) findViewById(R.id.stop_wifi);
        stop_wifi.setOnClickListener(this);
        stop_hot_a = (Button) findViewById(R.id.stop_hot_a);
        stop_hot_a.setOnClickListener(this);
        mQueryFlow = (Button) findViewById(R.id.query_flow);
        mQueryFlow.setOnClickListener(this);
        mSdcardQuery = (Button) findViewById(R.id.query_sdcard);
        mSdcardQuery.setOnClickListener(this);
        mBatteryQuery = (Button) findViewById(R.id.query_Battery);
        mBatteryQuery.setOnClickListener(this);
        mDatasizeQuery = (Button) findViewById(R.id.query_datasize);
        mDatasizeQuery.setOnClickListener(this);
        mWifiConnect = (Button) findViewById(R.id.connect_wifi);
        mWifiConnect.setOnClickListener(this);
        mGroupSv = (ScrollView) findViewById(R.id.sv_group);
        mGroupLl = (LinearLayout) findViewById(R.id.ll_group);
        mAppusertime = (Button) findViewById(R.id.appusertime);
        mAppusertime.setOnClickListener(this);
        mApppackegname = (Button) findViewById(R.id.apppackegname);
        mApppackegname.setOnClickListener(this);
        mAppuserflow = (Button) findViewById(R.id.appuserflow);
        mAppuserflow.setOnClickListener(this);
        mContentEt = (EditText) findViewById(R.id.et_content);
    }

    class myBrocaster extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.e("接收到广播：" + intent.getAction());
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
                LogUtils.e("蓝牙开关状态发生改变");
//                Toast.makeText(context, "蓝牙开关状态发生改变", Toast.LENGTH_SHORT).show();
                //蓝牙状态改变
                //不允许蓝牙适应关闭蓝牙
//                    new Thread() {
//                        public void run() {
////                            DeviceUtils.setBluetooth(false);//关闭蓝牙(耗时操作，线程执行)
//                        }
//                    }.start();
            } else if (WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION.equals(intent.getAction())) {
                LogUtils.e("wifi开关状态发生改变");
                Toast.makeText(context, "wifi开关状态发生改变", Toast.LENGTH_SHORT).show();
                //Wifi状态改变
//                if ("false".equals(allowWifi)) {
//                    //不允许
//                    DeviceUtils.setWifiState(false);//关闭WIFI
//                }
            } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                ThreadPoolUtil.getInstance().start(new WorkThread() {
                    @Override
                    public void runInner() {
                        wifiNetworkStateChangedProc(context, intent);
                    }
                });
            }
//
//            else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
//                //解锁动作
//                //DeviceUtils.cleanPassword();
////				if("true".equals(deviceLock)){
////					//永久锁屏
////					DeviceUtils.lockScreen(mRestrictInfo.pin);
////				}else{
////
////				}
//            }
//            else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
////                if (isRestrict) {
//                if ("false".equals(allowNetwork)) {
//                    //禁用移动数据网络
//                    boolean isConn = DeviceUtils.getMobileDataState(mContext, null);
//                    if (isConn) {
//                        //DeviceUtils.closeApn(mContext);
//                    }
//                }
//                }
//            } else if ("com.aixunyun.emm.clock".equals(intent.getAction())) {
//                LogUtils.e("闹钟触发");
//                initAlermClock(getNextClockTime());
////                upDateStateValueByNow(1);
//                //暂时去掉闹钟触发围栏检测，防止出现异常
////                checkGeoTimeFence();//add by gxb 20210122
//
//
//                //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                //Ringtone r = RingtoneManager.getRingtone(context,notification);
//                //r.play();//播放通知铃声
//            }
            else if ("android.net.wifi.WIFI_AP_STATE_CHANGED".equals(intent.getAction())) {//gxb add 20190117
                LogUtils.e("热点开关状态发生改变");
//                Toast.makeText(context, "热点开关状态发生改变", Toast.LENGTH_SHORT).show();
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(intent.getAction()) || BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(intent.getAction())) {
                LogUtils.e("蓝牙连接状态发生改变");
                AppUtil.getInstance().getBlueTooList(ThreadTestActivity.this);
            }

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(ThreadTestActivity.this, "当前连接wifi为黑名单，请更换wifi连接", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //WifiManager.NETWORK_STATE_CHANGED_ACTION
    private void wifiNetworkStateChangedProc(Context context, Intent intent) {
        try {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
//            WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
            if (Build.VERSION.SDK_INT >= 28) {
                //Android9及其以上,因为在Android9上只有当wifi连接成功以后才能获取到连接wifi的ssid和bssid，而在Android9以下连接的时候就可以在广播中获取到
                if (networkInfo != null) {
                    boolean isWifiConnected = networkInfo.isConnected();
                    if (isWifiConnected) {
                        //连上以后获取ssid和bssid
                        WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        WifiInfo info = mWifiManager.getConnectionInfo();
                        if (info != null) {
                            String ssid = info.getSSID();
                            String bssid = info.getBSSID();
                            LogUtils.e("wifi连接发生变化，当前连接：" + ssid + bssid);

//                            Toast.makeText(context, "wifi连接发生变化，当前连接：" + ssid + bssid, Toast.LENGTH_SHORT).show();
                            if ("\"mi666\"".equals(ssid)) {
                                if (wifiCount == 0 || wifiCount == 10) {
                                    handler.sendEmptyMessage(1);
                                    wifiCount = 0;
                                }
                                LogUtils.e("匹配wifi黑名单");
                                AppUtil.getInstance().closeWifi(this);
                                wifiCount++;
                            }
                        }
                    }
                }

            }
//            else { //Android9以下
//                if (null == networkInfo || null == wifiInfo) {
//                    return;
//                }
//                NetworkInfo.State state = networkInfo.getState();
//                if (state == NetworkInfo.State.CONNECTED) {
//                    String ssid = wifiInfo.getSSID();
//                    String bssid = wifiInfo.getBSSID();
//                    performWifiFilter(context, ssid, bssid);
//                }
//            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }


    }

    private void request() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.PACKAGE_USAGE_STATS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 321);
//        AppUtil.getInstance().hasPermissionToReadNetworkStats(this);
//        AppUtil.getInstance().requestReadNetworkStats(this);

        networkStatsManager = (NetworkStatsManager) getSystemService(NETWORK_STATS_SERVICE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.methodlock:
                AppUtil.getInstance().openEmm(this);
//                boolean exist = AppUtil.getInstance().checkAppExist(this, "com.didi2.myapplication");
//                LogUtils.e("应用是否安装："+exist);
                break;
            case R.id.method:
                AppUtil.getInstance().getBlueTooList(this);
                break;
            case R.id.lock_screen:
//                AppUtil.getInstance().lockScreen(this, "");
                AppUtil.getInstance().getBlueTooListNew(this);
                break;
            case R.id.set_lock_screen:
                AppUtil.getInstance().setScreenPd(ThreadTestActivity.this, "1234");
//                String domain = AppUtil.getInstance().getDomain("http://www.baidu.com");
//                string sDomain = Regex.Match(url, @"(?<=://)[a-zA-Z\.0-9]+(?=\/)").Value.ToString();

//                LogUtils.e("域名："+domain);
                break;
            case R.id.recover:
//                AppUtil.getInstance().eraseDevice(this);
                break;
            case R.id.close_bluetooth:
                AppUtil.getInstance().setBluetooth(this, false);
                break;
            case R.id.close_wifi:
                //断开wifi连接
                AppUtil.getInstance().closeWifi(this);
                break;
            case R.id.stop_wifi:
                //关闭wifi开关
                AppUtil.getInstance().setWifiState(this, false);
                break;
            case R.id.stop_hot_a:
                //关闭移动热点
//                AppUtil.getInstance().setWifiApEnabled(this, false);
                AppUtil.getInstance().setWifiApEnabled(MyApp.getContext(), false);

//                AppUtil.getInstance().closeAp(MyApp.getContext());
//                addView(this);
//                AppUtil.getInstance().setWifiApEnabledForAndroidO(MyApp.getContext(), false);
                break;
            case R.id.query_flow:
                long lf = AppUtil.getInstance().queryFlow(this);
                LogUtils.e("获取到流量：" + lf);
                break;
            case R.id.query_sdcard:// TODO 21/07/08
                boolean sdMounted = AppUtil.getInstance().isSDMounted(this);
                LogUtils.e("获取到sd卡挂载：" + sdMounted);
                break;
            case R.id.query_Battery:// TODO 21/07/08
                AppUtil.getInstance().checkBattery(this, "60");
                break;
            case R.id.query_datasize:// TODO 21/07/08
//                AppUtil.getInstance().checkAvailable(this, "10");
                break;
            case R.id.connect_wifi:// TODO 21/07/08
                MWifiInfo mWifiInfo = new MWifiInfo();
//                mWifiInfo.setWfSSID("94:0e:6b:54:2c:4c");
                mWifiInfo.setWfSSID("LieBaoWiFi59");
//                mWifiInfo.setWfSSID("Good_I_Deer");
                mWifiInfo.setWfIsHide("false");
                mWifiInfo.setWfSecurityType("WPA");
                mWifiInfo.setWfPassword("m1234567");
//                mWifiInfo.setWfPassword("123456789");
                mWifiInfo.setAutoJoin("true");
                ThreadPoolUtil.getInstance().start(new WorkThread() {
                    @Override
                    public void runInner() {
                        try {
                            AppUtil.getInstance().executeProfileWifi(ThreadTestActivity.this, mWifiInfo);
                        } catch (Exception e) {
                            LogUtils.e(e.toString());
                        }
                    }
                });
                break;
            case R.id.appusertime:// TODO 21/07/21
                ThreadPoolUtil.getInstance().start(new WorkThread() {
                    @Override
                    public void runInner() {
//                        String pk2 = "com.tencent.mm";
//                        String pk = "com.android.browser";
//                        String pk = "com.naruto.didi2";

//                        String pk = "com.huawei.android.thememanager";
//                        String pk = "com.autonavi.minimap";
//                        String pk = "com.heytap.browser";
//                        String pk1 = "com.tencent.mobileqq";
//                        String pk = "com.miui.securitycenter";
//                        String pk = "com.huawei.browser";
//                        String pk = "com.android.chrome";
                        String pk3 = "com.naruto.aotosize";
//                        String pk = getPackageName();
                        long currentTimeMillis = System.currentTimeMillis();
//                        readAppInfo(pk1);
//                        readAppInfo(pk2);
                        readAppInfo(pk3);
                        long timeMillis = System.currentTimeMillis();

                        LogUtils.e("读取用时：" + (timeMillis - currentTimeMillis));

//                        appFlow(pk);
                    }
                });
                break;
            case R.id.apppackegname:// TODO 21/07/21
                getPackage();
//                Intent d = new Intent(this, DuanDianActivity.class);
//                startActivity(d);

                break;
            case R.id.appuserflow:// TODO 21/07/22
                ThreadPoolUtil.getInstance().start(new WorkThread() {
                    @Override
                    public void runInner() {
//                        getFlow("com.tencent.mobileqq");
//                String pkg = "com.tencent.mm";

//                        String pkg = "com.naruto.didi2";

//                String pkg = "com.android.browser";
                        String pkg = "com.moji.mjweather";
//                String pkg = "com.hihonor.vmall";
//                String pkg = "com.huawei.android.thememanager";
//                String pkg = "com.huawei.camera";
//                String pkg = "cn.wps.moffice_eng";
//                        String pkg = "com.autonavi.minimap";
//                        getAppFlow(pkg);
//                        getAppWifiFlow(pkg);

                        appFlow(pkg);
//                        getAppFlow2(pkg);
                    }
                });
//                getAppWifiFlow(pkg);
//                appFlow(pkg);
                //...添加自己的代码
//                LogUtils.e(pkg + ":" + mobileTodayRx + "==" + (mobileTodayRx / 1024 / 1024));

                String treasure = NdkUtil.treasure();
                LogUtils.e("来自ndk:" + treasure);
                break;
        }
    }

    private void threadTest() {
        strings = new ArrayList<>();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        synchronized (object) {
                            for (int i = 0; i < 500; i++) {
                                strings.add("数据" + i);
                            }

                            LogUtils.e("集合数据添加结束");
                        }

                    }
                }
        ).start();
        readData();
    }

    private void readData() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        synchronized (object) {
                            LogUtils.e("开始读取集合数据");
                            for (String data : strings) {

                                LogUtils.e("打印：" + data);
                            }
                        }
                    }
                }
        ).start();
    }

    private void readAppInfo(String pkg) {


        long everyZeroMollis = AppUtil.getInstance().getEveryZeroMollis(0);
        long currentTimeMillis = System.currentTimeMillis();
//        long everyZeroMollis = 1631513951661l;

//        getUsageStats(this, pkg, everyZeroMollis, currentTimeMillis);

        String pk2 = "com.tencent.mm";
        String pk3 = "com.naruto.aotosize";
        ArrayList<String> lsi = new ArrayList<>();
        lsi.add(pk2);
        lsi.add(pk3);

//        UseTimeDataManager.getInstance(this).refreshData(lsi,everyZeroMollis,currentTimeMillis);


//        try {
//            printEvents(lsi, everyZeroMollis, currentTimeMillis);
//        } catch (Exception e) {
//            LogUtils.exception(e);
//        }

        getAppFlow(lsi, everyZeroMollis, currentTimeMillis);
        getAppWifiFlow(lsi, everyZeroMollis, currentTimeMillis);
    }


    private void getPackage() {
        if (open == 0) {
            timer1 = new Timer();
            timerTask1 = new TimerTask() {
                @Override
                public void run() {
                    String currentPkgName = AppUtil.getInstance().getCurrentPkgName(ThreadTestActivity.this);
                    if (!getPackageName().equals(currentPkgName)
                            && !"com.android.launcher3".equals(currentPkgName)
                            && !"com.rwen.activity".equals(currentPkgName)
                            ) {
                        Intent intent = new Intent();
                        intent.setClass(ThreadTestActivity.this, Main2Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                }
            };
            timer1.schedule(timerTask1, 0, 5000);
            Toast.makeText(this, "开启检测器", Toast.LENGTH_SHORT).show();
            open = 1;
        } else {
            if (timerTask1 != null) {
                timerTask1.cancel();
                timerTask1 = null;
            }
            if (timer1 != null) {
                timer1.cancel();
                timer1 = null;
            }
            Toast.makeText(this, "关闭检测器", Toast.LENGTH_SHORT).show();
            open = 0;
        }


    }

    @SuppressWarnings("ResourceType")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UsageStats getUsageStats(Context context, String pag, long startTime, long endTime) {

        Log.i(TAG, " EventUtils-getUsageList()   Range start:" + startTime + "  Range end:" + endTime);
        Log.i(TAG, " EventUtils-getUsageList()   Range start:" + AppUtil.getInstance().formatTime(startTime));
        Log.i(TAG, " EventUtils-getUsageList()   Range end:" + AppUtil.getInstance().formatTime(endTime));

        UsageStats usageStats = null;

        UsageStatsManager mUsmManager = (UsageStatsManager) context.getSystemService("usagestats");
        Map<String, UsageStats> map = mUsmManager.queryAndAggregateUsageStats(startTime, endTime);
        if (map == null)
            return usageStats;
        for (Map.Entry<String, UsageStats> entry : map.entrySet()) {
            UsageStats stats = entry.getValue();
            if (pag.equals(stats.getPackageName())) {
                if (stats.getTotalTimeInForeground() > 0) {
                    Log.i(TAG, " EventUtils-getUsageList()   stats:" + stats.getPackageName() + "   TotalTimeInForeground = " + stats.getTotalTimeInForeground() + "==" + (stats.getTotalTimeInForeground() / 1000 / 60));
                    return stats;
                }
            }
        }
        LogUtils.e("未获取到该应用使用时长");
        return usageStats;
    }

    private int getLaunchCount(UsageStats usageStats) {
        Field field = null;
        int launcherCount = 0;
        try {
            field = usageStats.getClass().getDeclaredField("mLaunchCount");
            launcherCount = (int) field.get(usageStats);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return launcherCount;
    }

    /**
     * 只查询当天的应用打开事件
     *
     * @throws NoSuchFieldException
     */
    public void printEvents(List<String> pkg, long startTime, long endTime) throws NoSuchFieldException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
//        long endTime = System.currentTimeMillis();
//        long startTime = DateUtil.getTimesmorning();
//        Log.d(TAG_U, "Range start:" + dateFormat.format(startTime));
//        Log.d(TAG_U, "Range end:" + dateFormat.format(endTime));
        UsageStatsManager mUsmManager = (UsageStatsManager) getSystemService("usagestats");
        UsageEvents events = mUsmManager.queryEvents(startTime, endTime);
        if (eventList != null) {
            eventList.clear();
        }
        eventList = new ArrayList<StatisticsEvent>();
        while (events.hasNextEvent()) {
            UsageEvents.Event e = new UsageEvents.Event();
            events.getNextEvent(e);
            StatisticsEvent event = null;
//            if (e != null) {
//                LogUtils.e("e.getPackageName():" + e.getPackageName() + "==" + e.getEventType());
//            }

            if (e.getEventType() == 1) {
                event = new StatisticsEvent();
                event.setClazz(e.getClassName());
                event.setPkgname(e.getPackageName());
                event.setLasttime(e.getTimeStamp());
                event.setType(e.getEventType());
                eventList.add(event);
//                Log.d(TAG, "Event:----lasttime = " + DateUtils.formatSameDayTime(e.getTimeStamp(), System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM)
//                        + "---- pkgname = " + e.getPackageName()
//                        + "----class = " + e.getClassName()
//                        + "----type =" + e.getEventType());
            }
        }
        printEventsMine(pkg);
    }

    /**
     * 对查询出来所有的事件进行过滤
     *
     * @throws NoSuchFieldException
     */
    public void printEventsMine(List<String> pkg) throws NoSuchFieldException {
//        HashMap<String, String> pkgNames = new HashMap<String, String>();
//        if (mPkgs != null) {
//            mPkgs.clear();
//        }
        Map<String, StatisticsEvent> mPkgs = new HashMap<>();
        for (int i = 0; i < eventList.size(); i++) {
            StatisticsEvent e = eventList.get(i);
            String packageName = e.getPkgname();
            //判断所有事件中当前如果为launcher时去判断上一个事件,上一个事件不是launcher就手动添加到mPkgs，并且打开次数+1
            if (checkLauncher(packageName) && i > 0) {
                StatisticsEvent e1 = eventList.get(i - 1);
                String pkgname = e1.getPkgname();
                if (!pkgname.equals(packageName)) {
                    e1.setCount(mPkgs.get(pkgname) == null ? 1 : mPkgs.get(pkgname).getCount() + 1);
//                    if (pkg.equals(pkgname)) {
//                        mPkgs.put(pkgname, e1);
//                    }

                    for (String pname : pkg) {
                        if (pkgname.equals(pname)) {
                            mPkgs.put(pkgname, e1);
                            break;
                        }

                    }
                }
            }

            //如果为最后一个事件，不是launcher就做上面相同的操作
            if (i == eventList.size() - 1) {
                if (!checkLauncher(packageName)) {
                    e.setCount(mPkgs.get(packageName) == null ? 1 : mPkgs.get(packageName).getCount() + 1);
//                    if (pkg.equals(packageName)) {
//                        mPkgs.put(packageName, e);
//                    }
                    for (String pname : pkg) {
                        if (packageName.equals(pname)) {
                            mPkgs.put(packageName, e);
                            break;
                        }
                    }
                }
            }
        }
//        LogUtils.e("eventList:" + eventList.size());

        List<OneTimeDetails> oneTimeDetails = new ArrayList<>();


        for (String pname : pkg) {
            StatisticsEvent statisticsEvent = mPkgs.get(pname);
            int count = 0;
            if (statisticsEvent != null) {
                count = statisticsEvent.getCount();
            }
            OneTimeDetails oneTimeDetails1 = new OneTimeDetails(pname, count);
            oneTimeDetails.add(oneTimeDetails1);
            LogUtils.e(oneTimeDetails1.toString());
        }
    }

    /**
     * 是否为桌面
     * 兼容小米手机,
     * 谷歌6.0com.google.android.googlequicksearchbox
     *
     * @param pkg
     */

    private boolean checkLauncher(String pkg) {
        try {
            if (pkg.contains("launcher") || pkg.contains("com.miui.home") || pkg.contains("googlequicksearchbox")) {
                return true;
            }
            return false;
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
        return false;
    }

    private class StatisticsEvent {
        private String Clazz;
        private String pkgname;
        private long lasttime;
        private int type;
        private int count;

        public String getClazz() {
            return Clazz;
        }

        public void setClazz(String clazz) {
            Clazz = clazz;
        }

        public String getPkgname() {
            return pkgname;
        }

        public void setPkgname(String pkgname) {
            this.pkgname = pkgname;
        }

        public long getLasttime() {
            return lasttime;
        }

        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    private void method1() {
        stopTimer();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    LogUtils.e("执行任务：" + i);
                    SystemClock.sleep(3 * 1000);
                }
            }
        };
        timer.schedule(timerTask, 0, 50 * 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    private void method5() {
        for (int i = 0; i < 5; i++) {

        }
    }

    private void jsonTest() {
        try {
            List<String> list = new ArrayList<>();
//            list.add("<2021-06-22 10:30:38>检测到允许模拟位置被打开");
//            list.add("<2021-06-22 10:30:38>判定为模拟位置位置开启");

//        String decode = list.toString();
            String decode = new JSONArray().toString();
            LogUtils.e("decode:" + decode);
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }

    }

    private void methodTest3() {
        mP.sum = 0;
        SystemClock.sleep(100);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 5; i++) {
                            mP.method3(5);
                        }
                    }
                }
        ).start();
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        mP.method3(5);
//                    }
//                }
//        ).start();
    }

    private void timerTest() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LogUtils.e("timer运行：" + Thread.currentThread().getId());
            }
        };
        timer.schedule(timerTask, 1000, 7 * 1000);
    }

    private void methodTest() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        P.aMethod();
                    }
                }
        ).start();

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        P.aMethod();
                    }
                }
        ).start();

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        P.aMethod();
                    }
                }
        ).start();
    }

    public void iterator() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Thread thread1 = new Thread() {
            public void run() {
                synchronized (lock) {
                    Iterator<Integer> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        Integer integer = iterator.next();
                        LogUtils.e("integer:" + integer);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
                synchronized (lock) {
                    Iterator<Integer> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        Integer integer = iterator.next();
                        if (integer == 2)
                            iterator.remove();
                    }
                }
            }
        };
        thread2.start();
        thread1.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unreg();
    }

    private void unreg() {
        try {
            if (mBrocaster != null) {
                unregisterReceiver(mBrocaster);
            }
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    public void iteratorTest() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Thread thread1 = new Thread() {
            public void run() {
//                synchronized (lock) {
                for (Integer integer : list) {
                    LogUtils.e("");
                    LogUtils.e("integer:" + integer);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                }
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
//                synchronized (lock) {
                Iterator<Integer> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Integer integer = iterator.next();
                    if (integer == 2)
                        iterator.remove();
                }
//                }
            }
        };
        thread2.start();
        thread1.start();
    }


    private void addView(Context context) {
        try {
            Class clazz = Class.forName("android.widget.AppSecurityPermissions");

            Constructor[] constructors = clazz.getConstructors();
            Constructor c = constructors[0];
            Object obj = c.newInstance(new Object[]{this, context.getPackageName()});

            Method method = clazz.getMethod("getPermissionsView");
            View view = (View) method.invoke(obj);
            mGroupLl.addView(view);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    /**
     * 本机使用的 wifi 总流量
     */

    public long getAllBytesWifi() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return 0;
        }
        NetworkStats.Bucket bucket;

        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI, "",
                    0, System.currentTimeMillis());
        } catch (RemoteException e) {
            return -1;

        }
        //这里可以区分发送和接收
        return bucket.getTxBytes() + bucket.getRxBytes();
    }

    /**
     * 本机使用的 mobile 总流量
     */

    public long getAllBytesMobile() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return 0;
        }
        NetworkStats.Bucket bucket;

        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE,
                    getSubscriberId(this, ConnectivityManager.TYPE_MOBILE), 0, System.currentTimeMillis());

        } catch (RemoteException e) {
            return -1;

        }

//这里可以区分发送和接收

        return bucket.getTxBytes() + bucket.getRxBytes();
    }

    private String getSubscriberId(Context context, int networkType) {
//        if (ConnectivityManager.TYPE_MOBILE == networkType) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return tm.getSubscriberId();

//        }
//        return "";

    }

    /**
     * 获取指定应用 wifi 发送的当天总流量
     *
     * @param packageUid 应用的uid
     * @return
     */

    public long getPackageTxDayBytesWifi(int packageUid) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return 0;
        }
        NetworkStats networkStats = null;

        try {
            networkStats = networkStatsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_WIFI, "", getTimesmorning(),
                    System.currentTimeMillis(), packageUid);
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
            return -1;
        }

        NetworkStats.Bucket bucket = new NetworkStats.Bucket();

        networkStats.getNextBucket(bucket);

        return bucket.getTxBytes();
    }

    /**
     * 获取指定应用 移动流量 当天总流量
     *
     * @param packageUid 应用的uid
     * @return
     */

    public long getPackageTxDayBytesMobile(int packageUid) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return 0;
        }
        NetworkStats networkStats = null;

        try {
            networkStats = networkStatsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_WIFI, getSubscriberId(this, 0), getTimesmorning(),
                    System.currentTimeMillis(), packageUid);
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
            return -1;
        }

        NetworkStats.Bucket bucket = new NetworkStats.Bucket();

        networkStats.getNextBucket(bucket);

        return bucket.getTxBytes() + bucket.getRxBytes();
    }


    /**
     * 获取当天的零点时间
     *
     * @return
     */

    private long getTimesmorning() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 0);

        cal.set(Calendar.SECOND, 0);

        cal.set(Calendar.MINUTE, 0);

        cal.set(Calendar.MILLISECOND, 0);

        return (cal.getTimeInMillis());

    }
    //获得本月第一天0点时间

    public int getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();

        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

        return (int) (cal.getTimeInMillis());

    }

    /**
     * 根据包名获取uid
     *
     * @param context     上下文
     * @param packageName 包名
     */

    public int getUidByPackageName(Context context, String packageName) {
        int uid = -1;

        PackageManager packageManager = context.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);

            uid = packageInfo.applicationInfo.uid;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }

        return uid;

    }

    public void getFlow(String pkg) {
        PackageManager packageManager = getPackageManager();

        List<PackageInfo> applicationInfoList = packageManager.getInstalledPackages(0);

        for (PackageInfo info : applicationInfoList) {
            try {
                PackageInfo pack = packageManager.getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS);

                String[] requestedPermissions = pack.requestedPermissions;

                if (requestedPermissions == null)

                    continue;

                if (info.applicationInfo.uid == 1000)

                    continue;

                for (String str : requestedPermissions) {
                    if (str.equals("android.permission.INTERNET") && pack.packageName.equals(pkg)) {
                        long mobileTodayRx = getPackageTxDayBytesMobile(getUidByPackageName(this, pkg));
                        //...添加自己的代码
                        LogUtils.e(pkg + ":" + (mobileTodayRx / 1024 / 1024));
                        break;
                    }
                }

            } catch (PackageManager.NameNotFoundException exception) {
                exception.printStackTrace();
                LogUtils.e("" + exception.toString());
            }

        }
    }

    public void getAppFlow(List<String> pkg, long everyZeroMollis, long currentTimeMillis) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

//        int uid = getUidByPackageName(this, pkg);
//        LogUtils.e("应用uid:" + uid);
        try {
            // 获取subscriberId
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            String subId = tm.getSubscriberId();


            LogUtils.e("subId:" + subId);

            NetworkStats summaryStats;
//            long summaryRx = 0;//接收
//            long summaryTx = 0;//发送
            NetworkStats.Bucket summaryBucket = new NetworkStats.Bucket();
//            long summaryTotal = 0;


            Map<String, OneTimeDetails> map = new HashMap<>();
            summaryStats = networkStatsManager.querySummary(0, subId, everyZeroMollis, currentTimeMillis);
            do {
//                summaryStats.getNextBucket(summaryBucket);
//                int summaryUid = summaryBucket.getUid();
//                if (uid == summaryUid) {
//                    summaryRx += summaryBucket.getRxBytes();
//                    summaryTx += summaryBucket.getTxBytes();
//                    summaryTotal += (summaryRx + summaryTx);
//                }

                summaryStats.getNextBucket(summaryBucket);
                int summaryUid = summaryBucket.getUid();


                for (String p : pkg) {
                    int uid = getUidByPackageName(this, p);
                    if (uid == summaryUid) {
                        long summaryRx = summaryBucket.getRxBytes();
                        long summaryTx = summaryBucket.getTxBytes();

                        OneTimeDetails oneTimeDetails = map.get(p);
                        OneTimeDetails oneTimeDetails1 = new OneTimeDetails(p, oneTimeDetails == null ? 0 : oneTimeDetails.getUseFlow() + (summaryRx + summaryTx));
                        map.put(p, oneTimeDetails1);
                        break;
                    }
                }

            } while (summaryStats.hasNextBucket());

            List<OneTimeDetails> list = new ArrayList<>();
            for (String p : pkg) {
                long useFlow = 0;
                OneTimeDetails oneTimeDetails = map.get(p);
                if (oneTimeDetails != null) {
                    useFlow = oneTimeDetails.getUseFlow();
                }

                OneTimeDetails oneTimeDetails1 = new OneTimeDetails(p, useFlow);
                list.add(oneTimeDetails1);
                LogUtils.e(oneTimeDetails1.toString());
            }
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    public void getAppFlow2(String pkg) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        int uid = getUidByPackageName(this, pkg);
        LogUtils.e("应用uid:" + uid);
        try {
            // 获取subscriberId
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            String subId = tm.getSubscriberId();

//            Class<?> aClass = Class.forName("android.telephony.TelephonyManager");
//            Method getSubId = aClass.getMethod("getSubId");
//            getSubId.setAccessible(true);
//            int sub = (int) getSubId.invoke(tm);
//
//            Method getSubscriberId = aClass.getMethod("getSubscriberId", int.class);
//            String subId = (String) getSubscriberId.invoke(tm, sub);


            LogUtils.e("subId:" + subId);

            NetworkStats summaryStats;
            long summaryRx = 0;//接收
            long summaryTx = 0;//发送
            NetworkStats.Bucket summaryBucket = new NetworkStats.Bucket();
            long summaryTotal = 0;

            summaryStats = networkStatsManager.queryDetails(0, subId + "", AppUtil.getInstance().getEveryZeroMollis(0), System.currentTimeMillis());

            do {
                summaryStats.getNextBucket(summaryBucket);
                int summaryUid = summaryBucket.getUid();
                if (uid == summaryUid) {
                    summaryRx += summaryBucket.getRxBytes();
                    summaryTx += summaryBucket.getTxBytes();
                    summaryTotal += (summaryRx + summaryTx);
                }
//                Log.i(TAG, "uid:" + summaryBucket.getUid() + " rx:" + summaryBucket.getRxBytes() +
//                        " tx:" + summaryBucket.getTxBytes());
//                summaryTotal += summaryBucket.getRxBytes() + summaryBucket.getTxBytes();
            } while (summaryStats.hasNextBucket());
            LogUtils.e("应用移动总流量：" + summaryTotal + "==" + (summaryTotal / 1024 / 1024));
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    public void getAppWifiFlow(List<String> pkg, long everyZeroMollis, long currentTimeMillis) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

//        int uid = getUidByPackageName(this, pkg);
//        LogUtils.e("应用uid:" + uid);
        try {
            // 获取subscriberId
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            String subId = tm.getSubscriberId();


            LogUtils.e("subId:" + subId);

            NetworkStats summaryStats;
//            long summaryRx = 0;//接收
//            long summaryTx = 0;//发送
            NetworkStats.Bucket summaryBucket = new NetworkStats.Bucket();
//            long summaryTotal = 0;


            Map<String, OneTimeDetails> map = new HashMap<>();
            summaryStats = networkStatsManager.querySummary(1, subId, everyZeroMollis, currentTimeMillis);
            do {
//                summaryStats.getNextBucket(summaryBucket);
//                int summaryUid = summaryBucket.getUid();
//                if (uid == summaryUid) {
//                    summaryRx += summaryBucket.getRxBytes();
//                    summaryTx += summaryBucket.getTxBytes();
//                    summaryTotal += (summaryRx + summaryTx);
//                }

                summaryStats.getNextBucket(summaryBucket);
                int summaryUid = summaryBucket.getUid();


                for (String p : pkg) {
                    int uid = getUidByPackageName(this, p);
                    if (uid == summaryUid) {
                        long summaryRx = summaryBucket.getRxBytes();
                        long summaryTx = summaryBucket.getTxBytes();

                        OneTimeDetails oneTimeDetails = map.get(p);
                        OneTimeDetails oneTimeDetails1 = new OneTimeDetails(p, oneTimeDetails == null ? 0 : oneTimeDetails.getUseFlow() + (summaryRx + summaryTx));
                        map.put(p, oneTimeDetails1);
                        break;
                    }
                }

            } while (summaryStats.hasNextBucket());

            List<OneTimeDetails> list = new ArrayList<>();
            for (String p : pkg) {
                long useFlow = 0;
                OneTimeDetails oneTimeDetails = map.get(p);
                if (oneTimeDetails != null) {
                    useFlow = oneTimeDetails.getUseFlow();
                }

                OneTimeDetails oneTimeDetails1 = new OneTimeDetails(p, useFlow);
                list.add(oneTimeDetails1);
                LogUtils.e(oneTimeDetails1.toString());
            }
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    /**
     * Use reflect to get Package Usage Statistics data.<br>
     */
    public void getPkgUsageStats() {
        Log.d(TAG, "[getPkgUsageStats]");
        try {
            Class<?> cServiceManager = Class
                    .forName("android.os.ServiceManager");
            Method mGetService = cServiceManager.getMethod("getService",
                    String.class);
            Object oRemoteService = mGetService.invoke(null, "usagestats");

            // IUsageStats oIUsageStats =
            // IUsageStats.Stub.asInterface(oRemoteService)
            Class<?> cStub = Class.forName("com.android.internal.app.IUsageStats$Stub");
            Method mUsageStatsService = cStub.getMethod("asInterface",
                    IBinder.class);
            Object oIUsageStats = mUsageStatsService.invoke(null,
                    oRemoteService);

            // PkgUsageStats[] oPkgUsageStatsArray =
            // mUsageStatsService.getAllPkgUsageStats();
            Class<?> cIUsageStatus = Class
                    .forName("com.android.internal.app.IUsageStats");
            Method mGetAllPkgUsageStats = cIUsageStatus.getMethod(
                    "getAllPkgUsageStats", (Class[]) null);
            Object[] oPkgUsageStatsArray = (Object[]) mGetAllPkgUsageStats
                    .invoke(oIUsageStats, (Object[]) null);
            Log.d(TAG, "[getPkgUsageStats] oPkgUsageStatsArray = " + oPkgUsageStatsArray);

            Class<?> cPkgUsageStats = Class
                    .forName("com.android.internal.os.PkgUsageStats");

            StringBuffer sb = new StringBuffer();
            sb.append("nerver used : ");
            for (Object pkgUsageStats : oPkgUsageStatsArray) {
                // get pkgUsageStats.packageName, pkgUsageStats.launchCount,
                // pkgUsageStats.usageTime
                String packageName = (String) cPkgUsageStats.getDeclaredField(
                        "packageName").get(pkgUsageStats);
                int launchCount = cPkgUsageStats
                        .getDeclaredField("launchCount").getInt(pkgUsageStats);
                long usageTime = cPkgUsageStats.getDeclaredField("usageTime")
                        .getLong(pkgUsageStats);
                if (launchCount > 0)
                    Log.d(TAG, "[getPkgUsageStats] " + packageName + "  count: "
                            + launchCount + "  time:  " + usageTime);
                else {
                    sb.append(packageName + "; ");
                }
            }
            Log.d(TAG, "[getPkgUsageStats] " + sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.toString());
        }
    }

    private void appFlow(String pkg) {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            int uId = info.applicationInfo.uid;
            long rx = TrafficStats.getUidRxBytes(uId); // 获取接收的流量
            long tx = TrafficStats.getUidTxBytes(uId); // 获取发送的流量

            long total = rx + tx;
            LogUtils.e(pkg + ":" + total + "##" + (total / 1024 / 1024));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
