package com.yangyong.didi2.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import com.yangyong.didi2.R;
import com.yangyong.didi2.bean.ThreadInfo;
import com.yangyong.didi2.broadcast.NteWorkChangeReceive;
import com.yangyong.didi2.constant.Constants;
import com.yangyong.didi2.dbdao.DownLoadDao;
import com.yangyong.didi2.fragment.HomeFragment;
import com.yangyong.didi2.fragment.MyFragment;
import com.yangyong.didi2.intf.ProgressCallBack;
import com.yangyong.didi2.util.AppUtil;
import com.yangyong.didi2.util.DownLoadUtils;
import com.yangyong.didi2.util.FileUtils;
import com.yangyong.didi2.util.LogUtils;
import com.yangyong.didi2.util.PermissionUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.functions.Consumer;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {

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
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AppUtil.getInstance().showDialog(FragmentActivity.this, "应用宝");
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
    private Button bt_stop_app;
    private Button bt_reset_app;
    private int CORE_THREAD = 2;
    private ProgressBar pb_load;
    private static final String ID = "DIDI2";
    private static final CharSequence NAME = "下载进度更新";
    private final int id=107;
    private RemoteViews remoteViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initView();
        initEvent();
        setFg();

//        regReceiver();
        regCb();
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
                LogUtils.e("回调进度："+pro);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_fg1:
                setChoiceItem(0);
                DownLoadDao.getInstance().deleteAll();
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
                PermissionUtils.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            DownLoadUtils.getInstance().start(downLoadPath1);
//                            threadDown();
                        }
                    }
                });
//                processDownload(null,10159880,null);
                break;
            case R.id.bt_stop_app:
                DownLoadUtils.getInstance().stop();
                break;
            case R.id.bt_reset_app:
                DownLoadUtils.getInstance().reSet();
                break;
        }
    }

    private void threadDown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtils.e("开始多线程下载");
                    long contentLength = DownLoadUtils.getInstance().getContentLength(downLoadPath1);
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

    private void submit() {
        // validate
        String input = et_input.getText().toString().trim();
        if (TextUtils.isEmpty(input)) {
            Toast.makeText(this, "输入", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


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

    private class MustInstallAppReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, Constants.MUSTINSTALLAPP)) {
//                int type = intent.getIntExtra("type", 3);
//                List<MustAppInfo> applist = (List<MustAppInfo>) intent.getSerializableExtra("applist");

//                LogUtils.e("未装应用数：" + count);
//                List<String> pks = new ArrayList<>();
//                pks.add("com.everhomes.android.jmrh");
//                pks.add("com.netease.cloudmusic");
//                pks.add("com.yangyong.didi2");
//                if (applist == null || applist.size() == 0) {
//                    return;
//                }
//                if (type == 0) {
//                    InstallAppUtils.getInstance().showInstallDialog(Launcher.this, 0, applist);
//                } else if (type == 1) {
//                    InstallAppUtils.getInstance().showInstallDialog(Launcher.this, 1, applist);
//                }
                AppUtil.getInstance().showDialog(context, "应用宝");
            }
        }
    }

    private void regReceiver() {
//        mustInstallAppReceiver = new MustInstallAppReceiver();
//        IntentFilter appFilter = new IntentFilter();
//        appFilter.addAction(Constants.MUSTINSTALLAPP);
//        registerReceiver(mustInstallAppReceiver, appFilter);

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


                remoteViews.setProgressBar(R.id.pb_down,100,pro,false);
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
}
