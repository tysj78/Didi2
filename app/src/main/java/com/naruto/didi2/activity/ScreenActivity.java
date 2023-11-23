package com.naruto.didi2.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.broadcast.AppInstallReceiver;
import com.naruto.didi2.service.Bind1Service;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.PermissionUtils;
import com.naruto.didi2.zlog.ZLog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import io.reactivex.functions.Consumer;

public class ScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private static final int INSTALL_PERMISS_CODE = 101;
    private LinearLayout llview;
    private Button btn_makelog;
    private Button btn_sendnotification;
    //    private int count = 0;
    private int sum = 0;
    private Button btn_sendhandler;
    private int addnum = 6;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tip.setText("延时后的文本");
                    break;
                case 2:
//                    xiaofei();
                    break;
                default:
                    break;
            }
        }
    };
    private TextView msgShow;
    private TextView tx_zbiao;
    private Button btn_moveing;
    private Handler handler;
    private LinkedBlockingQueue<Long> blockingQueue;
    private Button btn_addtodeque;
    private Button btn_tc;
    private TextView tv_emui;
    private Button btn_getemui;
    private Button btn_booth;
    private AppInstallReceiver appInstallReceiver;
    private Button btn_create_error;
    private Button btn_install;
    private CountDownLatch latch;
    private static long lastTi = 0;
    private ServiceConnection mServiceConnection;
    private Bind1Service.MyBinder myBinder;
    private Bind1Service mServer;
    private Button btn_bindService;
    private Button btn_getCount;
    private AlertDialog alertDialog;
    private Button btn_stop;
    private Intent intent;
    private Button btn_open;
    private Button btn_stp;

    private void xiaofei() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i >= 0; i++) {
                            try {
                                Long take = blockingQueue.take();
                                Log.e("yy", "take消费" + take);
                                SystemClock.sleep(3000);
                            } catch (InterruptedException e) {
//                                e.printStackTrace();
                                Log.e("yy", "take消费异常：" + e.toString());
                            }
                        }
                    }
                }
        ).start();

    }

    private TextView tip;
    private Button btn_blockingqueue;
//    private LinkedBlockingDeque<Integer> blockingDeque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        initView();

        Log.e(TAG, "onCreate: ");
//        AppUtil.mActivity = this;
//        requestPer();
//        llview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                float x = v.getX();
//                float y = v.getY();
//                Log.e("yy", "onClick- x:"+x+"y:"+y );
//            }
//        });
//        startEmmSet();
        appInstallReceiver = new AppInstallReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addDataScheme("package");
        registerReceiver(appInstallReceiver, intentFilter);

//        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//        startActivity(intent);
//        startTimer();
        initBind1Service();
    }

    private void initBind1Service() {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (Bind1Service.MyBinder) service;
                mServer = myBinder.getServer();
                mServer.setMusic();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mServer = null;
            }
        };
        intent = new Intent(this, Bind1Service.class);
    }

    private void startTimer() {
        Timer ptimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                Log.e(TAG, "run: " + AppUtil.mActivity);
            }
        };
        ptimer.schedule(timerTask, 0, 2000);
    }

    private void startEmmSet() {
        ComponentName componetName = new ComponentName(
                "com.mobilewise.mobileware",  //这个是另外一个应用程序的包名
                "com.mobilewise.mobileware.activity.set.HaweiTestActivity");   //这个参数是要启动的Activity的全路径名

        try {
            Intent intent = new Intent();
            intent.setComponent(componetName);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "未找到此应用！", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPer() {
        String[] strings = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        PermissionUtils.requestPermissions(this, strings, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
            }
        });
    }

    private void initView() {
        llview = (LinearLayout) findViewById(R.id.llview);
        btn_makelog = (Button) findViewById(R.id.btn_makelog);
        btn_makelog.setOnClickListener(this);
        btn_sendnotification = (Button) findViewById(R.id.btn_sendnotification);
        btn_sendnotification.setOnClickListener(this);
        btn_sendhandler = (Button) findViewById(R.id.btn_sendhandler);
        btn_sendhandler.setOnClickListener(this);
        tip = (TextView) findViewById(R.id.tip);
        tip.setOnClickListener(this);
        btn_blockingqueue = (Button) findViewById(R.id.btn_blockingqueue);
        btn_blockingqueue.setOnClickListener(this);
        msgShow = (TextView) findViewById(R.id.tx_zbiao);
        msgShow.setOnClickListener(this);
        tx_zbiao = (TextView) findViewById(R.id.tx_zbiao);
        tx_zbiao.setOnClickListener(this);
        btn_moveing = (Button) findViewById(R.id.btn_moveing);
        btn_moveing.setOnClickListener(this);
        btn_addtodeque = (Button) findViewById(R.id.btn_addtodeque);
        btn_addtodeque.setOnClickListener(this);
        btn_tc = (Button) findViewById(R.id.btn_tc);
        btn_tc.setOnClickListener(this);
        tv_emui = (TextView) findViewById(R.id.tv_emui);
        tv_emui.setOnClickListener(this);
        btn_getemui = (Button) findViewById(R.id.btn_getemui);
        btn_getemui.setOnClickListener(this);
        btn_booth = (Button) findViewById(R.id.btn_booth);
        btn_booth.setOnClickListener(this);
        btn_create_error = (Button) findViewById(R.id.btn_create_error);
        btn_create_error.setOnClickListener(this);
        btn_install = (Button) findViewById(R.id.btn_install);
        btn_install.setOnClickListener(this);
        btn_bindService = (Button) findViewById(R.id.btn_bindService);
        btn_bindService.setOnClickListener(this);
        btn_getCount = (Button) findViewById(R.id.btn_getCount);
        btn_getCount.setOnClickListener(this);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(this);
        btn_open = (Button) findViewById(R.id.btn_open);
        btn_open.setOnClickListener(this);
        btn_stp = (Button) findViewById(R.id.btn_stp);
        btn_stp.setOnClickListener(this);
    }

    float k = 500.0f;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_makelog:
                for (int i = 0; i < 10; i++) {
                    final int finalI = i;
                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    int count = 0;
//                                    Log.e("yy", "我是线程:" + finalI);
                                    while (true) {
                                        count++;
                                        if (count > 300) {
                                            break;
                                        }
                                        ZLog.i("我是loga++和++a 都属于自增运算符，区别是对变量a的值进行自增的时机不同。\n" +
                                                "a++是先进行取值，后进行自增。++a是先进行自增，后进行取值；a++和++a 都属于自增运算符，区别是对变量a的值进行自增的时机不同。\n" +
                                                "a++是先进行取值，后进行自增。++a是先进行自增，后进行取值；a++和++a 都属于自增运算符，区别是对变量a的值进行自增的时机不同。\n" +
                                                "a++是先进行取值，后进行自增。++a是先进行自增，后进行取值；a++和++a 都属于自增运算符，区别是对变量a的值进行自增的时机不同。\n" +
                                                "a++是先进行取值，后进行自增。++a是先进行自增，后进行取值；" + count);
                                        SystemClock.sleep(100);
                                    }
                                }
                            }
                    ).start();
                }
                break;
            case R.id.btn_sendnotification:
                switch (sum) {
                    case 0:
                        showDownloadNotificationUI("didi2", 59);
                        break;
                    case 1:
                        showDownloadNotificationUI("didi2", 69);
                        break;
                    case 2:
                        showDownloadNotificationUI("didi2", 89);
                        break;
                    default:
                        break;
                }
                sum++;
                break;
            case R.id.btn_sendhandler:
                mHandler.sendEmptyMessageDelayed(1, 10 * 1000);
//                startActivity(new Intent(this, Main7Activity.class));
//                finish();
                break;
            case R.id.btn_blockingqueue:
                testLinkedQueue();
                break;
            case R.id.btn_moveing:
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 5; i++) {
                                    inputRootCmd("input tap " + (k) + " " + 300.0);
                                    k += 50;
                                    SystemClock.sleep(1000);
                                }
                            }
                        }
                ).start();
                break;
            case R.id.btn_addtodeque:
                if (handler != null) {
                    handler.sendEmptyMessage(1);
                }
                break;
            case R.id.btn_tc:
                activeShowAppandWidget(this, 100);
                break;
            default:
                break;
            case R.id.btn_getemui:
                tv_emui.setText(getIMEI());
                break;
            case R.id.btn_booth:
                coppy();
                openPer();
                break;
            case R.id.btn_create_error:
                try {
//                    Log.e(TAG, "onClick: " + (0 / 0));
                    throw new Exception("自定义异常");
                } catch (Exception e) {
                    Log.e("yy", "Exception: " + e.toString());
                }
                break;
            case R.id.btn_install:
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/emmappstore/apkloads/recordbox-v3.0.1478.apk");
//                AppUtil.installApk(this, file);
//                test1();
//                boolean b = EasyProtectorLib.checkIsRunningInEmulator(this, new EmulatorCheckCallback() {
//                    @Override
//                    public void findEmulator(String emulatorInfo) {
//
//                    }
//                });
//                LogUtils.e(b ? "模拟器" : "非模拟器");
                break;
            case R.id.btn_bindService:
                startService(intent);
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_getCount:
                if (mServer != null) {
                    int count = mServer.getCount();
                    LogUtils.e("获取到Bind1Service count:" + count);
                } else {
                    LogUtils.e("服务还未进行bind");
                }
                break;
            case R.id.btn_stop:
                unbindService(mServiceConnection);
//                mServiceConnection=null;
                stopService(intent);
                break;
            case R.id.btn_open:
                mServer.start();
                break;
            case R.id.btn_stp:
                mServer.stop();
                break;
        }
    }

    private void openPer() {
        boolean haveInstallPermission = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
        }
        if (!haveInstallPermission) {
            Uri packageURI = Uri.parse("package:" + getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
            startActivityForResult(intent, INSTALL_PERMISS_CODE);
        }
    }


    private void coppy() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
//                        FileUtils.getDataFile(ScreenActivity.this);
                    }
                }
        ).start();
    }


    private void showUpdateDialog() {
        Log.e("tag", "showUpdateDialog: " + Thread.currentThread().getId());
        final AlertDialog.Builder builder = new AlertDialog.Builder(ScreenActivity.this);
        builder.setTitle("版本更新");
        builder.setCancelable(false);
        builder.setMessage("111111");
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("tag", "=======setPositiveButton-----");
                alertDialog.dismiss();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("tag", "=======setNegativeButton-----");
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    boolean boo = false;

    private String getEMUI() {
        Class<?> classType = null;
        String buildVersion = "";
        try {
            classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", new Class<?>[]{String.class});
            buildVersion = (String) getMethod.invoke(classType, new Object[]{"ro.build.version.emui"});
        } catch (Exception e) {
//            e.printStackTrace();
            buildVersion = "";
            Log.e("yy", "getEMUI失败: " + e.toString());
        }
        if (!buildVersion.equals("")) {
            float v = formatEmui(buildVersion);
            Log.e("yy", "buildVersion: " + v);
            if (v != 0.0f && v >= 9.0f) {
                Toast.makeText(this, "当前系统版本支持该api", Toast.LENGTH_SHORT).show();
            }
        }
        return buildVersion;
    }


    NotificationManager notificationManager;
    NotificationCompat.Builder ntfBuilder;

    private synchronized void showDownloadNotificationUI(String apkName, final int progress) {
        Log.e("yy", "创建通知栏展示");

//        if (Build.VERSION.SDK_INT >= ConstantsVersion.VERSION_EIGHT) {
//            ntfBuilder = NotificationUtils.showNotificationForVersionUpdate(this, apkName, progress,content);
//            return;
//        }
        if (notificationManager == null) {
            notificationManager = (NotificationManager) this
                    .getSystemService(this.NOTIFICATION_SERVICE);
        }
        //if (ntfBuilder == null) {
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, new Intent(), 0);
        ntfBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(this.getApplicationInfo().icon)
                .setTicker("开始下载...")
                .setContentTitle(apkName)
//                .setAutoCancel(true)
                .setContentIntent(contentIntent);
        //}
        ntfBuilder.setContentText("59");
        ntfBuilder.setProgress(100, progress, false);
        notificationManager.notify(33, ntfBuilder.build());
    }

    void testLinkedQueue() {
        blockingQueue = new LinkedBlockingQueue<>(5);
        for (int i = 1; i < 6; i++) {
            blockingQueue.offer(new Long((long) i));
        }

        xiaofei();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        try {
                            long ls = System.currentTimeMillis();
                            blockingQueue.put(ls);
                            Log.e("yy", "put入队，当前入队元素：" + ls);
                        } catch (InterruptedException e) {
                            Log.e("yy", "put异常：" + e.toString());
                        }
                    }
                };
                Looper.loop();
            }
        }).start();

        Log.e("yy", "blockingDeque入队完成");
    }

    void testTHread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }
//    class PutTHread extends Thread{
//        @Override
//        public void run() {
//            super.run();
//            try {
//                blockingDeque.put(55);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        msgShow.setText("点击位置的X坐标" + ev.getX() + "点击位置的Y坐标" + ev.getY());
//        switch (ev.getAction()) {
//            //  点击的开始位置
//            case MotionEvent.ACTION_DOWN:
//                msgShow.setText("起始位置：(" + ev.getX() + "," + ev.getY());
//                break;
//            //触屏实时位置
//            case MotionEvent.ACTION_MOVE:
//                msgShow.setText("实时位置：(" + ev.getX() + "," + ev.getY());
//                break;
//            //离开屏幕的位置
//            case MotionEvent.ACTION_UP:
//                msgShow.setText("结束位置：(" + ev.getX() + "," + ev.getY());
////                sendMsg(ev.getX(), ev.getY());
//                break;
//            default:
//                break;
//        }
//
//        //   注意返回值
//        //    true：view继续响应Touch操作；
//// false：view不再响应Touch操作，故此处若为false，只能显示起始位置，不能显示实时位置和结束位置
//
//        return super.dispatchTouchEvent(ev);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        if (appInstallReceiver != null) {
            unregisterReceiver(appInstallReceiver);
        }
//        if (mServer != null) {
//            unbindService(mServiceConnection);
//            mServer = null;
//        }
    }

    /**
     * 执行命令且得到结果
     */
    public String inputRootCmd(String cmd) {
        String result = "";
        DataOutputStream dos = null;
        DataInputStream dis = null;

        try {
            // 经过Root的android系统即有su命令
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dis = new DataInputStream(p.getInputStream());
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            String line = null;
            while ((line = dis.readLine()) != null) {
                Log.d("result", line);
                result += line;
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    public static void activeShowAppandWidget(Activity context, int resultcode) {
//        String spKey = LauncherAppState.getSharedPreferencesKey();
//        SharedPreferences sp = context.getSharedPreferences(spKey, Context.MODE_PRIVATE);
//        SharedPreferences.Editor ed = sp.edit();
//        ed.putBoolean("EMPTY_DATABASE_CREATED",true);
//        ed.commit();

        Intent intent;
        AppWidgetHost mAppWidgetHost = new AppWidgetHost(context, 1024);
        mAppWidgetHost.startListening();
//        AppWidgetManager mAppWidgetManager = AppWidgetManager.getInstance(context);
//        List<AppWidgetProviderInfo> installed = mAppWidgetManager.getInstalledProviders();
//        AppWidgetProviderInfo widgetInfo = installed.get(0);
//        PendingAddWidgetInfo createInfo = new PendingAddWidgetInfo(widgetInfo,
//                null,
//                null);
        int appWidgetId = mAppWidgetHost.allocateAppWidgetId();
        intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_BIND);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER, createInfo.componentName);
        // TODO: we need to make sure that this accounts for the options bundle.
//         intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_OPTIONS, options);
        Log.e("yy", "startActivityForResult(intent, REQUEST_BIND_APPWIDGET);");
        Log.e("yy", "授权弹窗口");
        context.startActivityForResult(intent, resultcode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK && requestCode == INSTALL_PERMISS_CODE) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/emmappstore/apkloads/recordbox-v3.0.1478.apk");
//            AppUtil.installApk(this, file);
        }
        if (requestCode == 100) {
//            LogUtils.e("REQUEST_BIND_APPWIDGETid==" + appWidgetId);
            if (resultCode == RESULT_CANCELED) {
//                LogUtils.e("REQUEST_BIND_APPWIDGET-RESULT_CANCELED");
//                requestBindPermission();
            } else if (resultCode == RESULT_OK) {
                //这种情况，因为是手动开启
//                LogUtils.e("RESULT_CANCELED-RESULT_OK");
                //重新绑定widget
//                startLauncher();
            }
        }
    }

    private float formatEmui(String emui) {
        float emuiVersion = 0.0f;
        try {
            String[] split = emui.split("_");
            String s = split[1];
            String[] split1 = s.split("\\.");
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(split1[0]).append(".").append(split1[1]);
            return Float.parseFloat(stringBuffer.toString());
        } catch (Exception e) {
            Log.e("yy", "Exception: " + e.toString());
        }
        return emuiVersion;
    }

    private void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    public String getIMEI() {
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            Log.e("yy", "imei: " + imei);
            return imei;
        } catch (Exception e) {
            Log.e("yy", "Exception: " + e.toString());
        }
        return "未知";
    }

    private void test1() {
        latch = new CountDownLatch(2);
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                Log.e("yy", "进入thread1");
                try {
                    Thread.sleep(5000);
                    Log.e("yy", "退出thread1");
                    latch.countDown();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                Log.e("yy", "进入thread2");
                try {
                    Thread.sleep(8000);
                    Log.e("yy", "退出thread2");
                    latch.countDown();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "子线程统计完成:继续执行主线程 ");
    }
}
