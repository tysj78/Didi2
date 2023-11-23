package com.naruto.didi2.activity;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;

import com.naruto.didi2.util.ForegroundAppUtil;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.TreeMap;

public class JylyActivity extends AppCompatActivity implements View.OnClickListener {

    private Button main_disable;
    private Context context;
    public static boolean mScreenOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyly);
        context = this;
        initView();
        Log.e(Constants.TAG, "onCreate");
        mScreenOn = false;
        /*Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                String currentPkgName1 = getCurrentPkgName(context);
                String foregroundActivityName = ForegroundAppUtil.getForegroundActivityName(context);
                Log.e(Constants.TAG, "run: "+foregroundActivityName);
            }
        };
        timer.schedule(timerTask,0,2000);*/

    }

    private void getPkg() {
        try {
            ActivityManager.RunningAppProcessInfo info = new ActivityManager.RunningAppProcessInfo();
            String[] packagesForUid = getPackageManager().getPackagesForUid(info.uid);
            String s = packagesForUid[0];
            Log.e(Constants.TAG, s);
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
        }
    }

    private void initView() {
        main_disable = (Button) findViewById(R.id.main_disable);

        main_disable.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_disable:
//                BluetoothManager bm = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//                if (bm == null)
//                    return;
//                BluetoothAdapter adapter = bm.getAdapter();
//                adapter.disable();


//                String currentPkgName1 = getCurrentPkgName(context);
//                Log.e(Constants.TAG, "run: "+currentPkgName1);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStop() {
        super.onStop();
//        String currentPkgName = getCurrentPkgName(this);
//        Log.e(Constants.TAG, "onStop:=========== " + currentPkgName);
        String foregroundActivityName = ForegroundAppUtil.getForegroundActivityName(context);
//        String currentPkgName = getCurrentPkgName(this);
        Log.e(Constants.TAG, "onStoprun: "+foregroundActivityName);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(Constants.TAG, "onRestart: ");
        //休眠100毫秒再去查询，为了系统将使用信息存储完毕
//        SystemClock.sleep(100);
//        String currentPkgName = getCurrentPkgName(this);
//        Log.e(Constants.TAG, "onRestart:=========== " + currentPkgName);
        String foregroundActivityName = ForegroundAppUtil.getForegroundActivityName(context);

        Log.e(Constants.TAG, "run: "+foregroundActivityName);
        if (mScreenOn) {
            Log.e(Constants.TAG, "true: ");
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
//        SystemClock.sleep(100);
//        Log.e(Constants.TAG, "onPause: ");
//        String foregroundActivityName = ForegroundAppUtil.getForegroundActivityName(context);
//        String currentPkgName = getCurrentPkgName(this);
//        Log.e(Constants.TAG, "run: "+foregroundActivityName);
    }




    /**
     * <p>
     * 获取当前正在使用的应用的包名
     */
    public String getCurrentPkgName(Context context) {
        SystemClock.sleep(100);
        String topPackageName = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
//            Log.e(Constants.TAG, ""+time );
//            Log.e(Constants.TAG, time - 1000 * 60 * 60 * 24+"" );
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                    time - 1000 * 60 * 60 * 24, time);
//            Log.e(Constants.TAG, "stats.size: " + stats.size());
//            Log.e(Constants.TAG, "time: " + time);
            if (stats != null&&stats.size()>0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : stats) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                /*for (Map.Entry<Long, UsageStats> entry : mySortedMap.entrySet()) {
                    Long mapKey = entry.getKey();
                    String mapValue = entry.getValue().getPackageName();
                    Log.e(Constants.TAG, mapKey + ":" + mapValue);
                }*/
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }

        } else {
            ActivityManager am = (ActivityManager) context.getSystemService("activity");
            topPackageName = am.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        return topPackageName;
    }

    public String getCurrentPkgName1(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        return am.getRunningTasks(1).get(0).topActivity.getPackageName();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public String getCurrentPkgName3(Context context) {
        long start = System.currentTimeMillis();
        String topPackageName = null;
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
//            Log.e(Constants.TAG, ""+time );
//            Log.e(Constants.TAG, time - 1000 * 60 * 60 * 24+"" );
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                time - 1000 * 60 * 60 * 24, time);
        if (stats != null) {
            TreeMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : stats) {
                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
            }
            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                Field mLastEventField = null;
                NavigableSet<Long> keySet = mySortedMap.navigableKeySet();
                Iterator iterator = keySet.descendingIterator();
                while (iterator.hasNext()) {
                    UsageStats usageStats = mySortedMap.get(iterator.next());
                    if (mLastEventField == null) {
                        try {
                            mLastEventField = UsageStats.class.getField("mLastEvent");
                        } catch (NoSuchFieldException e) {
                            break;
                        }
                    }
                    if (mLastEventField != null) {
                        int lastEvent = 0;
                        try {
                            lastEvent = mLastEventField.getInt(usageStats);
                        } catch (IllegalAccessException e) {
                            break;
                        }
                        if (lastEvent == 1) {
                            Log.e(Constants.TAG, "lastEvent == 1: ");
                            topPackageName = usageStats.getPackageName();
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (topPackageName == null) {
                    topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        }
        long nd = System.currentTimeMillis();
        Log.e(Constants.TAG, "获取用时: " + (nd - start));
        return topPackageName;

    }
}
