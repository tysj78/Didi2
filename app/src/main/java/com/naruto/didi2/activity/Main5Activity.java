package com.naruto.didi2.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.naruto.didi2.R;
import com.naruto.didi2.activity.test.T1Activity;
import com.naruto.didi2.bean.ThreadInfo;
import com.naruto.didi2.dbdao.DownLoadDao;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;

import java.io.File;
import java.util.ArrayList;

public class Main5Activity extends BaseActivity implements View.OnClickListener {

    private Button main_ss;
    private TextView tv_current_time;
    private Button main_send;
    private Button bt_tiao;
    private Button bt_exit;
    private Button bt_delete;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    open();
                    break;
                case 2:
                    tv_current_time.setText("hello");
                    LogUtils.e("get main thread msg");
                    break;
            }
        }
    };
    private Button bt_handler;
    private Handler tHandler;
    private DownLoadDao mDownLoadDao;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (true) {
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        initView();
        LogUtils.e("onCreate");
        mDownLoadDao = DownLoadDao.getInstance();
    }

    /**
     * 判断服务是否开启
     *
     * @return
     */
    public boolean isServiceRunning(Context context, String ServiceName) {
        if (TextUtils.isEmpty(ServiceName)) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(1000);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }

    private void initView() {
        main_ss = (Button) findViewById(R.id.main_ss);

        main_ss.setOnClickListener(this);
        tv_current_time = (TextView) findViewById(R.id.tv_current_time);
        tv_current_time.setOnClickListener(this);
        main_send = (Button) findViewById(R.id.main_send);
        main_send.setOnClickListener(this);
        bt_tiao = (Button) findViewById(R.id.bt_tiao);
        bt_tiao.setOnClickListener(this);
        bt_exit = (Button) findViewById(R.id.bt_exit);
        bt_exit.setOnClickListener(this);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        bt_delete.setOnClickListener(this);
        bt_handler = (Button) findViewById(R.id.bt_handler);
        bt_handler.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_ss:
//                Intent start = new Intent(this, TimerService.class);
//                if (Build.VERSION.SDK_INT >= 26) {
//                    startForegroundService(start);
//                }
                mDownLoadDao.addThread(new ThreadInfo("baidu.com", 1, 1, 1));
                break;
            case R.id.main_send:
//                String time = SpUtils.getStringValue(this, "TIME");
//                tv_current_time.setText(time);
//                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 100);

                ArrayList<ThreadInfo> threadInfos = mDownLoadDao.selectAll();
                int a = threadInfos.size();
                LogUtils.e("线程数" + a);
                break;
            case R.id.bt_tiao:
//                startActivity(new Intent(this, T1Activity.class));
//                stopService(new Intent(this, TimerService.class));
                AppUtil.getInstance().clearAppData(this);
//                mDownLoadDao.closeDb();
//                mDownLoadDao = null;
                break;
            case R.id.bt_exit:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                break;
            case R.id.bt_delete:
                String s = Environment.getExternalStorageDirectory() + "/app";
                File file = new File(s);
                String[] list = file.list();
                LogUtils.e("文件夹下文件数量：" + list.length);
//                boolean b = AppUtil.getInstance().deleteDir(file);
//                LogUtils.e("删除："+b);
                break;
            default:
                break;
            case R.id.bt_handler:
//                sendMsg2();
//                AppUtil.getInstance().isServiceStarted(this, "com.didi2.yangyong");
                initDb();
                break;
        }
    }

    private void initDb() {
        mDownLoadDao = DownLoadDao.getInstance();
    }

    private void sendMsg() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        tHandler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                switch (msg.what) {
                                    case 10:
                                        String msgs = (String) msg.obj;
                                        LogUtils.e("get msg:" + msgs + Thread.currentThread().getId());
                                        break;
                                }
                            }
                        };
                        Looper.loop();
                    }
                }
        ).start();
    }

    private void sendMsg2() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //do something..
                        try {
                            Thread.sleep(3000);
                            Message obtain = Message.obtain();
                            obtain.what = 10;
                            obtain.obj = "下载完成";
                            tHandler.sendMessage(obtain);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

    private void open() {
        Intent intent = new Intent();
        intent.setClass(this, T1Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    Handler mHandler;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final int reCode = requestCode;
//        if (requestCode == 100) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
//                Toast.makeText(this.getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
//
//            } else {
//                Toast.makeText(this.getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();
//
//            }
//        }

        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && reCode == 100) {
                    boolean nOpen = Settings.canDrawOverlays(Main5Activity.this);
                    if (nOpen) {
                        //开启
                    } else {
                        //关闭
                    }
                    LogUtils.e("PermissionRequest open = " + nOpen);
                }
            }
        }, 500);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String tag = intent.getStringExtra("EXIT_TAG");
        if (TextUtils.equals(tag, "SINGLETASK")) {
            //退出程序——
            LogUtils.e("onNewIntent");
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("onResume");
    }
}
