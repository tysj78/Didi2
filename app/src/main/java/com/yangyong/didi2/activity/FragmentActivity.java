package com.yangyong.didi2.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilewise.didi2.R;
import com.yangyong.aotosize.IMyAidlInterface;
import com.yangyong.didi2.activity.test.T1Activity;
import com.yangyong.didi2.bean.ThreadInfo;
import com.yangyong.didi2.broadcast.MustInstallAppReceiver;
import com.yangyong.didi2.broadcast.NteWorkChangeReceive;
import com.yangyong.didi2.constant.Constants;
import com.yangyong.didi2.dbdao.DownLoadDao;
import com.yangyong.didi2.fanshe.CatInfo;
import com.yangyong.didi2.fragment.HomeFragment;
import com.yangyong.didi2.fragment.MyFragment;
import com.yangyong.didi2.intf.ProgressCallBack;
import com.yangyong.didi2.service.ForegroundService;
import com.yangyong.didi2.util.AppUtil;
import com.yangyong.didi2.util.DownLoadUtils;
import com.yangyong.didi2.util.FileUtils;
import com.yangyong.didi2.util.LogUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class FragmentActivity extends BaseActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private HomeFragment home;
    private MyFragment my;
    private FrameLayout fl_content;
    private Button bt_fg1;
    private Button bt_fg2;
    private RadioButton rbProject;
    private RadioButton rbMine;
    private RadioGroup radioGroup;
    private Button bt_alert;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AppUtil.getInstance().showDialog(FragmentActivity.this, "应用宝");
                    break;
                case 1:
                    tv_net_status.setText("后台任务处理完成");
                    Toast.makeText(FragmentActivity.this, "文件拷贝完成", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
//                    PermissionUtils.requestPermissions(FragmentActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Consumer<Boolean>() {
//                        @Override
//                        public void accept(Boolean aBoolean) throws Exception {
//                            LogUtils.e("申请权限 ");
//                            if (aBoolean) {
//                                LogUtils.e("同意了权限");
//                            }else {
//                                LogUtils.e("未同意权限");
//                            }
//                        }
//                    });
                    if (!AppUtil.getInstance().isBackground(FragmentActivity.this)) {
                        LogUtils.e("应用位于前台，请求权限");
                        ActivityCompat.requestPermissions(FragmentActivity.this, permissions, 321);
                    } else {
                        LogUtils.e("应用位于后台，不请求权限");
                    }
                    break;
                case 3:
                    String data = (String) msg.obj;
                    tv_sum.setText(data);
                    break;
                default:
                    break;
            }
        }
    };
    private EditText et_input;
    private long lastTime = 0L;
    private int aa = 30;
    private AtomicInteger mWriteCounter = new AtomicInteger();//自增长类
    private MustInstallAppReceiver mustInstallAppReceiver;
    private NteWorkChangeReceive nteWorkChangeReceive;
    private TextView tv_net_status;
    private Button bt_down_app;
    private String downLoadPath1 = "https://imtt.dd.qq.com/16891/apk/7884ACB68E413A5B22A7FE044DD3BF3C.apk";
    private String downLoadPath2 = "https://e.vipgz1.idcfengye.com/rest/mobile/appstore/app/appdownload";
    private Button bt_stop_app;
    private Button bt_reset_app;
    private int CORE_THREAD = 2;
    private ProgressBar pb_load;
    private static final String ID = "DIDI2";
    private static final CharSequence NAME = "下载进度更新";
    private final int id = 107;
    private RemoteViews remoteViews;
    private ExecutorService mExecutorService;
    private int mCount = 0;
    private EditText et_sumone;
    private EditText et_sumtwo;
    private TextView tv_sum;

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("c++_shared");
//        System.loadLibrary("marsxlog");
//        System.loadLibrary("native-lib");
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initView();

//        MyApp.mActivity=this;
        initEvent();
        setFg();

        AppUtil.getInstance().setHandler(mHandler);
        regReceiver();
        regCb();
        initThreadPool();
    }


    private void initThreadPool() {
        //初始化线程池
        mExecutorService = Executors.newFixedThreadPool(3);
    }

    private void regCb() {
//        AppUtil.getInstance().regCallBack(new CallBack() {
//            @Override
//            public void doEvent(String str) {
//                tv_net_status.setText(str);
//            }
//        });
        AppUtil.getInstance().regProCallBack(new ProgressCallBack() {
            @Override
            public void updateProgress(int pro) {
                LogUtils.e("回调进度：" + pro);
                pb_load.setProgress(pro);
                sendMsg(pro);
            }
        });
    }

    private void initEvent() {
//        et_input.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                //update by gxb 20200327
//                long currentTimeMillis = System.currentTimeMillis();
//                if (lastTime != 0 && currentTimeMillis - lastTime < 2000) {
//                    mHandler.removeMessages(0);
//                }
//                lastTime=currentTimeMillis;
//                //延迟两秒执行搜索
//                mHandler.sendEmptyMessageDelayed(0,2000);
//            }
//        });

        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    String trim = et_input.getText().toString().trim();
                    if (trim.equals("")) {
                        Toast.makeText(FragmentActivity.this, "输入内容为空", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    LogUtils.e("开始搜索");
                    return true;
                }
                return false;
            }
        });
    }

    private void setFg() {
        fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.fl_content, new MyFragment());
//        fragmentTransaction.commit();
    }

    /**
     * Fragment切换
     *
     * @param index
     */
    private void setChoiceItem(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (home == null) {
                    home = new HomeFragment();
                    transaction.add(R.id.fl_content, home);
                } else {
                    transaction.show(home);
                }

                break;

            case 1:
                if (my == null) {
                    my = new MyFragment();
                    transaction.add(R.id.fl_content, my);
                } else {
                    transaction.show(my);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏片段
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (home != null) {
            transaction.hide(home);
        }
        if (my != null) {
            transaction.hide(my);
        }
    }


    private void initView() {
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        bt_fg1 = (Button) findViewById(R.id.bt_fg1);
        bt_fg2 = (Button) findViewById(R.id.bt_fg2);

        bt_fg1.setOnClickListener(this);
        bt_fg2.setOnClickListener(this);
        rbProject = (RadioButton) findViewById(R.id.rbProject);
        rbProject.setOnClickListener(this);
        rbMine = (RadioButton) findViewById(R.id.rbMine);
        rbMine.setOnClickListener(this);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnClickListener(this);
        bt_alert = (Button) findViewById(R.id.bt_alert);
        bt_alert.setOnClickListener(this);
        et_input = (EditText) findViewById(R.id.et_input);
        et_input.setOnClickListener(this);
        tv_net_status = (TextView) findViewById(R.id.tv_net_status);
        tv_net_status.setOnClickListener(this);
        bt_down_app = (Button) findViewById(R.id.bt_down_app);
        bt_down_app.setOnClickListener(this);
        bt_stop_app = (Button) findViewById(R.id.bt_stop_app);
        bt_stop_app.setOnClickListener(this);
        bt_reset_app = (Button) findViewById(R.id.bt_reset_app);
        bt_reset_app.setOnClickListener(this);
        pb_load = (ProgressBar) findViewById(R.id.pb_load);
        pb_load.setOnClickListener(this);

        remoteViews = new RemoteViews(getPackageName(), R.layout.progress_layout);
        tv_sum = (TextView) findViewById(R.id.tv_sum);
        tv_sum.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_fg1:
                setChoiceItem(0);
                ActivityCompat.requestPermissions(FragmentActivity.this, permissions, 321);
                break;
            case R.id.bt_fg2:
                setChoiceItem(1);
                DownLoadDao.getInstance().selectAll();
                break;
            case R.id.bt_alert:
                AppUtil.getInstance().showDialog(this, "应用宝");
//                for (int i = 0; i < 10; i++) {
//                    test1(i);
//                }
                break;
            case R.id.bt_down_app:
              /*  PermissionUtils.requestPermissions(AppManager.getAppManager().currentActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            LogUtils.e("");
//                            DownLoadUtils.getInstance().start(downLoadPath2);
                          *//*  DownLoadThread downLoadThread = new DownLoadThread();
                            mExecutorService.execute(downLoadThread);


                            TestThread testThread = new TestThread();
                            mExecutorService.execute(testThread);*//*
//                            threadDown();

//                            LogUtils.e(sdPath);

//                            NoticeQueue.getInstance().start();
                        }
                    }
                });*/
//                processDownload(null,10159880,null);
//                mHandler.sendEmptyMessageDelayed(2, 3000);

                break;
            case R.id.bt_stop_app:
//                DownLoadUtils.getInstance().stop();
//                startActivity(new Intent(this, T1Activity.class));
                Intent intent1 = new Intent(this, ForegroundService.class);
                if (Build.VERSION.SDK_INT >= 26) {
                    startForegroundService(intent1);
                } else {
                    startService(intent1);
                }

//                NoticeQueue.getInstance().stop();
                break;
            case R.id.bt_reset_app:
                boolean b1 = checkTimeFence();
                LogUtils.e("检测时间围栏：" + b1);
                break;
        }
    }

    private void start() {
//        LogUtils.e("start");
//        Intent intent = new Intent("com.yangyong.didi2.EasyService");
//        // 注意在 Android 5.0以后，不能通过隐式 Intent 启动 service，必须制定包名
//        intent.setPackage("com.yangyong.aotosize");
//        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
//
//        aidlCallBack();
        CopyThread copyThread = new CopyThread();
        mExecutorService.execute(copyThread);
    }

    private void aidlCallBack() {
        try {
            if (mIEasyService != null) {
//                        int i = mIEasyService.addSum(Integer.parseInt(s1), Integer.parseInt(s2));
//                        tv_sum.setText("远程计算结果："+i);

                String mesg = mIEasyService.getMesg();
                tv_sum.setText(mesg);
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    private IMyAidlInterface mIEasyService;


    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.e("onServiceConnected");
            mIEasyService = IMyAidlInterface.Stub.asInterface(service);
            aidlCallBack();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.e("onServiceDisconnected");
            mIEasyService = null;
        }
    };

    private void reqper() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(3000);
                        boolean b = AppUtil.getInstance().checkPermission(FragmentActivity.this, permissions[0]);
                        LogUtils.e("qunxian:" + b);
                    }
                }
        ).start();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setArm() {
        Intent intent = new Intent(this, T1Activity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //设置当前时间
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        // 根据用户选择的时间来设置Calendar对象
        c.set(Calendar.HOUR_OF_DAY, 15);
        c.set(Calendar.MINUTE, 37);
        // ②设置AlarmManager在Calendar对应的时间启动Activity
//        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);

//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime(), pi);
//        System.currentTimeMillis()+1000*60*3

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
//        LogUtils.e(c.getTimeInMillis() + "");   //这里的时间是一个unix时间戳
        // 提示闹钟设置完毕:
        Toast.makeText(this, "闹钟设置完毕~" + c.getTimeInMillis(), Toast.LENGTH_SHORT).show();
    }

    private void threadDown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtils.e("开始多线程下载");
                    String req = DownLoadUtils.getInstance().createReq();
                    long contentLength = DownLoadUtils.getInstance().getContentLength(downLoadPath2, req);
                    File rootFile = FileUtils.getRootFile();
                    String name = downLoadPath1.substring(downLoadPath1.lastIndexOf("/") + 1);
                    File file = new File(rootFile, name); //很正常的File() 方法
                    new RandomAccessFile(file, "rwd").setLength(contentLength);//实例化一下我们的RandomAccessFile()方法

                    processDownload(downLoadPath1, contentLength, null);
                } catch (Exception e) {
                    LogUtils.e("Exception: " + e.toString());
                }
            }
        }).start();

    }

    private void sendBroadcast1() {
        Intent intent = new Intent();
        intent.setAction(Constants.MUSTINSTALLAPP);
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        AppUtil.getInstance().showDialog(this, "应用宝");
    }


    private void test1(final int i) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.e("当前线程id:" + Thread.currentThread().getId());
                        if (i < 2) {
                            write(1);
                        } else if (i >= 2 && i < 4) {
                            write(2);
                        } else if (i >= 4 && i < 6) {
                            write(3);
                        } else if (i >= 6 && i < 8) {
                            write(4);
                        } else if (i >= 8 && i < 10) {
                            write(5);
                        }
                    }
                }
        ).start();
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
////                        mWriteCounter.incrementAndGet();
//                        LogUtils.e("当前线程id:" + Thread.currentThread().getId());
//                        write("101");
//                    }
//                }
//        ).start();
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
////                        mWriteCounter.incrementAndGet();
//                        LogUtils.e("当前线程id:" + Thread.currentThread().getId());
//                        write("102");
//                    }
//                }
//        ).start();
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        mWriteCounter.incrementAndGet();
//                        LogUtils.e("当前线程id:" + Thread.currentThread().getId());
//
//                    }
//                }
//        ).start();
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        mWriteCounter.incrementAndGet();
//                        LogUtils.e("当前线程id:" + Thread.currentThread().getId());
//
//                    }
//                }
//        ).start();
    }

    void write(int con) {
//        try {
//            if (mWriteCounter.incrementAndGet() == 1) {
//            aa=aa-sum;
//            Thread.sleep(1000);
//            LogUtils.e("数据库写入完成:"+aa);
////                mWriteCounter.decrementAndGet();
//            }
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
        boolean exists = DownLoadDao.getInstance().exists(con + "");
        if (!exists) {
            ThreadInfo info = new ThreadInfo(con + "", 100, 100, 100);
            DownLoadDao.getInstance().addThread(info);
        }

    }

//    private class MustInstallAppReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (TextUtils.equals(action, Constants.MUSTINSTALLAPP)) {
////                int type = intent.getIntExtra("type", 3);
////                List<MustAppInfo> applist = (List<MustAppInfo>) intent.getSerializableExtra("applist");
//
////                LogUtils.e("未装应用数：" + count);
////                List<String> pks = new ArrayList<>();
////                pks.add("com.everhomes.android.jmrh");
////                pks.add("com.netease.cloudmusic");
////                pks.add("com.yangyong.didi2");
////                if (applist == null || applist.size() == 0) {
////                    return;
////                }
////                if (type == 0) {
////                    InstallAppUtils.getInstance().showInstallDialog(Launcher.this, 0, applist);
////                } else if (type == 1) {
////                    InstallAppUtils.getInstance().showInstallDialog(Launcher.this, 1, applist);
////                }
//                AppUtil.getInstance().showDialog(context, "应用宝");
//            }
//        }
//    }

    private void regReceiver() {
        mustInstallAppReceiver = new MustInstallAppReceiver();
        IntentFilter appFilter = new IntentFilter();
        appFilter.addAction(Constants.MUSTINSTALLAPP);
        registerReceiver(mustInstallAppReceiver, appFilter);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        nteWorkChangeReceive = new NteWorkChangeReceive();
        registerReceiver(nteWorkChangeReceive, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mustInstallAppReceiver);
//        unregisterReceiver(nteWorkChangeReceive);

    }

    private void processDownload(String url, long length, DownLoadUtils.IProgress callback) {
        //100    2    50    0-49   50-99
        long threadDownloadSize = length / CORE_THREAD;
        for (int i = 0; i < CORE_THREAD; i++) {
            long startSize = i * threadDownloadSize;
            long endSize = 0;
            if (i == CORE_THREAD - 1) {
                endSize = length - 1;
            } else {
                endSize = (i + 1) * threadDownloadSize - 1;
            }
            LogUtils.e("线程详细数据第" + i + "个线程   " + "startSize : " + startSize + "endSize : " + endSize);
//            sThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callback));
            DownLoadUtils.getInstance().start(url, startSize, endSize);
        }
    }

    /**
     * 通知栏更新下载进度
     */
    void sendMsg(int pro) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationChannel channel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);


                remoteViews.setProgressBar(R.id.pb_down, 100, pro, false);
                Notification notification = new Notification.Builder(this, ID)
                        .setContentTitle("下载app")
//                        .setContentText("应用需保持运行")
                        .setSmallIcon(R.mipmap.mm)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.mm))
                        .setCustomContentView(remoteViews)
                        .build();


//                notification.contentView=remoteViews;

                manager.notify(id, notification);
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    private class DownLoadThread extends Thread {
        @Override
        public void run() {
            super.run();
            LogUtils.e("启动下载线程：" + Thread.currentThread().getId());
            synchronized (AppUtil.getInstance().object) {
                try {
                    AppUtil.getInstance().object.wait();
                } catch (InterruptedException e) {
                    LogUtils.e(e.toString());
                }
                LogUtils.e("测试线程运行接收，下载线程继续");
                mCount = mCount + 1;
            }
        }
    }

    private class CopyThread extends Thread {
        @Override
        public void run() {
            super.run();
            LogUtils.e("启动拷贝线程：" + Thread.currentThread().getId());
            FileUtils.getDataFile(FragmentActivity.this, mHandler);
        }
    }

    private class TestThread extends Thread {

        @Override
        public void run() {
            super.run();
            LogUtils.e("启动测试线程：" + Thread.currentThread().getId());
//            synchronized (object) {
            mCount = mCount + 20;
            SystemClock.sleep(5000);
            //恢复下载线程,通知唤醒对象，释放锁
            synchronized (AppUtil.getInstance().object) {
                AppUtil.getInstance().object.notify();
            }
//            }
        }
    }

    void test() {
        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); //设置时区
        int curYear = calendar.get(Calendar.YEAR);//年
        int curMonth = calendar.get(Calendar.MONTH) + 1;//月要加1
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);//日

        int curHour = calendar.get(Calendar.HOUR_OF_DAY);//时
        int curMinute = calendar.get(Calendar.MINUTE);//分
        int curWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; //周几需要减一
//        int week_day = Calendar.getInstance(Locale.CHINA).get(Calendar.DAY_OF_WEEK) - 1;//当前星期 {1-7}
        LogUtils.e("当前年：" + curYear);
        LogUtils.e("当前月：" + curMonth);
        LogUtils.e("当前日：" + curDay);
        LogUtils.e("当前时：" + curHour);
        LogUtils.e("当前分：" + curMinute);
        LogUtils.e("当前星期几：" + curWeek);

    }

    /**
     * 检测是否在时间围栏范围内
     *
     * @return
     */
    private boolean checkTimeFence() {
//        MLimitTime limitTime = mRestrictInfo.time;
//        MLimitTime.LimitTime scope = limitTime.getScope();

        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);//年
        int curMonth = calendar.get(Calendar.MONTH) + 1;//月
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);//日
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);//时
        int curMinute = calendar.get(Calendar.MINUTE);//分
        int curWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//        int week_day = Calendar.getInstance(Locale.CHINA).get(Calendar.DAY_OF_WEEK) - 1;//当前星期 {1-7}

        //判断是永久有效还是自定义时间段有效
//        if ("0".equals(limitTime.getValid())) {//永久有效
        if (false) {//永久有效
            LogUtils.e("永久有效");
            return true;
//        } else if ("1".equals(limitTime.getValid())) {//自定义
        } else if (true) {//自定义
            String month = "";
            String day = "";
            if (curMonth < 10) {
                month = "0" + curMonth;
            } else {
                month = curMonth + "";
            }
            if (curDay < 10) {
                day = "0" + curDay;
            } else {
                day = curDay + "";
            }

            int nowDate = Integer.parseInt(curYear + month + day);//当前日期 20210324
//            int serverStartDate = Integer.parseInt(limitTime.getStart().replaceAll("-", ""));//开始日期
//            int serverEndDate = Integer.parseInt(limitTime.getEnd().replaceAll("-", ""));//结束日期

            int serverStartDate = 20210324;//开始日期
            int serverEndDate = 20210424;//结束日期

            String weekdays = "1,2,3,4,5,6,0";//周几执行
            String startTime = "00:00";//几点开始执行
            String endTime = "19:00";//几点结束执行
            //将后台时间段转化为18:00--->1800 这种形式
            int scopeStart = Integer.parseInt(startTime.replaceAll(":", ""));//首次执行时间
            int scopeEnd = Integer.parseInt(endTime.replaceAll(":", ""));
            int curTime = 0;
            if (curMinute < 10) {
                curTime = Integer.parseInt(curHour + "0" + curMinute);
            } else {
                curTime = Integer.parseInt(curHour + "" + curMinute);
            }

            if (nowDate >= serverStartDate && nowDate <= serverEndDate) {//判断日期范围
                if (weekdays.contains(curWeek + "")) {//判断星期范围
                    if (curTime >= scopeStart && curTime <= scopeEnd) {//判断时间范围
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
