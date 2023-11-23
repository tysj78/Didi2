package com.naruto.didi2.activity;

import android.Manifest;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.bean.Student;
import com.naruto.didi2.broadcast.EMMDeviceAdminReceiver;
import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.util.LockScreenUtil;
import com.naruto.didi2.util.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class DeviceAdminActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private Button main_admin;
    private Button jin;
    private Context context;
    private Button tiao;
    private Button jinyong;
    private TextView result;
    private HomeKeyEventBroadCastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_admin);
        context = this;
        initView();
//        requestP();
//        openManagePage(this);
//        Log.e("", "设备管理器已激活=============");
//        openManagePage(this);
//        ifTest();
//        StaticInit.getA();
        /*boolean inEmulator = EasyProtectorLib.checkIsRunningInEmulator(this, new EmulatorCheckCallback() {
            @Override
            public void findEmulator(String emulatorInfo) {
                Log.e(TAG, "findEmulator: " + emulatorInfo);
            }
        });*/

//        boolean inEmulator = EmulatorCheck.getSingleInstance().readSysProperty(this);

//        boolean inEmulator = EmulatorCheckUtil.getSingleInstance().checkIsRunningInEmulator(context);
//        result.setText("检测模拟器运行："+inEmulator);
//        Log.e(TAG, "当前设备是否为模拟器: " + inEmulator);
        receiver = new HomeKeyEventBroadCastReceiver();
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    private void ifTest() {
        int uu = 5;
        if (uu >= 5) {
            Log.e(TAG, "ifTest1: ");
        } else if (uu > 4) {
            Log.e(TAG, "ifTest2: ");
        } else {
            Log.e(TAG, "ifTest2: ");
        }
    }


    //开启设备管理器
    public void openManagePage(final Activity context) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        try {
            DevicePolicyManager policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName componentName = new ComponentName(context, EMMDeviceAdminReceiver.class);
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "EMM致力于移动设备管理");
            if (!policyManager.isAdminActive(componentName)) {
                context.startActivity(intent);
            } else {
                Log.e("DevicesAdminReceiver", "设备管理器已激活");
            }
        } catch (Exception e) {
            Log.e("DevicesAdminReceiver", "Exception: " + e.getMessage());
        }
//            }
//        }).start();


    }

    @Override
    protected void onResume() {
        super.onResume();
//        openManagePage(this);
    }

//    private void initView() {
//        main_admin = (Button) findViewById(R.id.main_admin);
//        main_admin.setOnClickListener(this);
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.main_admin:
////                openManagePage(this);
////                try {
////                    ComponentName componentName = new ComponentName(this, EMMDeviceAdminReceiver.class);
////                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
////                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
////                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "EMM致力于移动设备管理");
////                    startActivity(intent);
////                } catch (Exception e) {
////                    Log.e("DevicesAdminReceiver", "Exception: " + e.getMessage());
////                }
//                break;
//        }
//    }


    /**
     * <p>
     * 禁用或者取消禁用相机
     */
    public boolean setCameraState(boolean state) {
        //获取设备管理服务
        DevicePolicyManager policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, EMMDeviceAdminReceiver.class);
        if (policyManager.isAdminActive(componentName)) {
            policyManager.setCameraDisabled(componentName, state);
        }
        if (policyManager.getCameraDisabled(componentName)) {
//            LogUtils.i(TAG, "相机不可以使用");
            Log.e("DevicesAdminReceiver", "相机不可以使用");
            return true;
        } else {
//            LogUtils.i(TAG, "相机可以使用");
            Log.e("DevicesAdminReceiver", "相机可以使用");
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("DevicesAdminReceiver", "DeviceAdminActivityonDestroy========");
        unregisterReceiver(receiver);
    }

    private void initView() {
        jin = (Button) findViewById(R.id.jin);

        jin.setOnClickListener(this);
        tiao = (Button) findViewById(R.id.tiao);
        tiao.setOnClickListener(this);
        jinyong = (Button) findViewById(R.id.jinyong);
        jinyong.setOnClickListener(this);
        result = (TextView) findViewById(R.id.result);
        result.setOnClickListener(this);
    }

    public class HomeKeyEventBroadCastReceiver extends BroadcastReceiver {
        static final String SYSTEM_REASON = "reason";
        static final String SYSTEM_HOME_KEY = "homekey";
        static final String SYSTEM_RECENT_APPS = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (reason != null) {
                    if (reason.equals(SYSTEM_HOME_KEY)) {
                        LogUtils.e("按下了home键1");
                        LockScreenUtil.getInstance().stop();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jin:
//                setCameraState(true);
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
//                        LockScreenUtil.getInstance().showWindow();
                        try {
                            Intent intent = new Intent();
                            intent.setClass(DeviceAdminActivity.this, MyDiyViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } catch (Exception e) {
                            LogUtils.exception(e);
                        }
                    }
                }.sendEmptyMessageDelayed(12, 3000);
                break;
            case R.id.tiao:
//                startActivity(new Intent(this, AActivity.class));
//                finish();
                ComponentName componentName = new ComponentName("com.yangyong.guard", "com.yangyong.guard.MainActivity");//这里是 包名  以及 页面类的全称
                Intent intent = new Intent();
                intent.setComponent(componentName);
                intent.putExtra("type", "110");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.jinyong:
//                jinWifi();
                encrypt();
                break;
        }
    }

    private String getTname() {
        String tn = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            //查询这段时间内的所有使用事件
            UsageEvents usageEvents = null;
            usageEvents = mUsageStatsManager.queryEvents(time - 1000 * 60 * 60 * 24, time);
            UsageEvents.Event event = new UsageEvents.Event();
            //遍历这个事件集合，如果还有下一个事件
            while (usageEvents.hasNextEvent()) {
                //得到下一个事件放入event中,先得得到下个一事件，如果这个时候直接调用，则	event的package是null，type是0。
                usageEvents.getNextEvent(event);
                //如果这是个将应用置于前台的事件
                if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    //获取这个前台事件的packageName.
                    tn = event.getPackageName();
                    Log.e(TAG, "前台应用: " + tn);
                }
            }
        }
        return tn;
    }

    private void mapTest() {
        SortedMap<Integer, String> ts = new TreeMap<>();
        ts.put(1, "小亮");
        ts.put(5, "小蓝");
        ts.put(55, "小李");
        ts.put(6, "张");
        ts.put(0, "小明");
        ts.remove(ts.lastKey());
        for (SortedMap.Entry<Integer, String> entry : ts.entrySet()) {
            Log.e(TAG, "mySortedMapKey = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static List<UsageStats> px(List<UsageStats> lists) {
//        lists.add(23);
//        lists.add(0);
//        lists.add(5);
//        lists.add(6);
//        lists.add(8);
//        lists.add(0);
//        lists.add(0);
//        lists.add(5);
//        lists.add(23);
//        lists.add(1);
        Iterator<UsageStats> it = lists.iterator();
        while (it.hasNext()) {
            UsageStats value = it.next();
            if (0 == value.getLastTimeUsed()) {
                it.remove();
            }
        }

        String s = lists.toString();
//        Log.e(TAG, "去0后的集合: " + s);

        //排序，正序
        int i, j;
        for (i = 1; i < lists.size(); i++) {
            //i表示要比较的次数
            for (j = 0; j < lists.size() - 1; j++) {
                if (lists.get(j).getLastTimeUsed() > lists.get(j + 1).getLastTimeUsed()) {
                    //按顺序对相邻的两个数进行比较，较大的放到后面
                    UsageStats tem = lists.get(j);
                    lists.set(j, lists.get(j + 1));
                    lists.set(j + 1, tem);
                }
            }
        }
//        Log.e(Constants.TAG, lists.toString());

        return lists;
    }

    private void paixu() {
        List<Student> stuList = new ArrayList();
        stuList.add(new Student(5));
        stuList.add(new Student(3));
        stuList.add(new Student(7));
        stuList.add(new Student(2));
        stuList.add(new Student(4));
        stuList.add(new Student(6));
        stuList.add(new Student(1));

        Collections.sort(stuList);  // 调用排序方法

        for (Student student : stuList) {
            Log.e(Constants.TAG, student.getAge() + "");
        }
    }


   /* private void jinWifi() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // WIFI管理器
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        String connectSsid = connectionInfo.getSSID();
        Log.e(Constants.TAG, "当前wifissid: " + connectSsid);
        int mNetworkId = connectionInfo.getNetworkId();
        Log.e(TAG, "mNetworkId: " + mNetworkId);

//        boolean b1 = wifiManager.disableNetwork(mNetworkId);//指定热点断开连接，同时不再连接
//        boolean b2 = wifiManager.disconnect();
//        if (b2) {
//            Log.e(TAG, "断开wifi 成功: " );
//        }else {
//            Log.e(TAG, "断开wifi 失败: " );
//        }
    }*/

    void requestP() {
        //8.0动态权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkPermission1 = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            int checkPermission2 = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (checkPermission1 != PackageManager.PERMISSION_GRANTED || checkPermission2 != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1); //后面的1为请求码
            }
        }
    }

    private void jinWifi1() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            String wifiName = wifiInfo.getExtraInfo();
            Log.e(Constants.TAG, "当前wifissid: " + wifiName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void lianjie() {
//        NetworkSpecifier specifier = new Builder().setSsidPattern(new PatternMatcher("test", PatterMatcher.PATTERN_PREFIX))
//                .setBssidPattern(MacAddress.fromString("10:03:23:00:00:00"), MacAddress.fromString("ff:ff:ff:00:00:00")).build();
//        NetworkRequest request = new NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).setNetworkSpecifier(specifier).build();
//        ConnectivityManager connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkCallback networkCallback = new NetworkCallback() {
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.e(TAG, "onPause时间戳: " + System.currentTimeMillis());
//        String currentPkgName = getCurrentPkgName(this);
//        Log.e(TAG, "onPause: " + currentPkgName);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStop() {
        super.onStop();
//        String topPackage = getTopPackage(this);
//        Log.e(TAG, "onStoptopPackage: "+topPackage);
//        Log.e(TAG, "onStop时间戳: " + System.currentTimeMillis());
//        String currentPkgName = getCurrentPkgName(this);
//        Log.e(TAG, "onStop: " + currentPkgName);
    }


    class RecentUseComparator implements Comparator<UsageStats> {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public int compare(UsageStats lhs, UsageStats rhs) {
            return (lhs.getLastTimeUsed() > rhs.getLastTimeUsed()) ? -1 : (lhs.getLastTimeUsed() == rhs.getLastTimeUsed()) ? 0 : 1;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private String getTopPackage(Context context) {

        long ts = System.currentTimeMillis();

        RecentUseComparator mRecentComp = new RecentUseComparator();

        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        //查询ts-10000 到ts这段时间内的UsageStats，由于要设定时间限制，所以有可能获取不到
        List<UsageStats> usageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ts - 1000 * 60 * 60 * 24, ts);

        if (usageStats == null) return "";
        if (usageStats.size() == 0) return "";
        Collections.sort(usageStats, mRecentComp);
        Log.d(TAG, "====usageStats.get(0).getPackageName()" + usageStats.get(0).getPackageName());

        return usageStats.get(0).getPackageName();
    }

    public void encrypt() {
        DevicePolicyManager mPolicyManager = (DevicePolicyManager) this.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName mComponentName = new ComponentName(this, EMMDeviceAdminReceiver.class);
        boolean active = mPolicyManager.isAdminActive(mComponentName);
        if (!active) {
            Toast.makeText(this, "请激活设备管理器", Toast.LENGTH_SHORT).show();
            return;
        }
        int i = mPolicyManager.setStorageEncryption(mComponentName, true);
        Toast.makeText(this, "开启了存储加密--" + i, Toast.LENGTH_SHORT).show();
    }

}
