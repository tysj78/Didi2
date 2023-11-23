package com.naruto.didi2.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.bean.MustApp;
import com.naruto.didi2.bean.ThreadInfo;
import com.naruto.didi2.broadcast.AppInstallReceiver;
import com.naruto.didi2.broadcast.NetInfoReceiver;
import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.dbdao.DownLoadDao;
import com.naruto.didi2.intf.ProgressCallBack;
import com.naruto.didi2.notificationQueue.NoticeQueue;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.DownLoadUtils;
import com.naruto.didi2.util.FileUtils;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.PermissionUtils;
import com.naruto.didi2.util.SpUtils;
import com.naruto.didi2.zlog.LogBean;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.functions.Consumer;

import static com.naruto.didi2.zlog.LogType.VERBOSE;

public class DuanDianActivity extends BaseActivity implements View.OnClickListener {

    private ProgressBar pb_progress;
    private Button bt_download;
    private Button bt_pasu;
    //    private String downLoadPath1 = "https://1d01e7614d4c86ee39238460437b3f51.dlied1.cdntips.com/dlied1.qq.com/qqweb/QQ_1/android_apk/Android_8.3.3.4515_537063791.apk";
    private String downLoadPath1 = "https://imtt.dd.qq.com/16891/apk/7884ACB68E413A5B22A7FE044DD3BF3C.apk";
    //    private String downLoadPath = "https://gmapf-t.gameco.com.cn:8445/clientapp/AndroidMCDMgmapt.apk";
//    private String downLoadPath2 = "http://luyin.voicecloud.cn/a";
    private String downLoadPath2 = "https://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk";
    private String downLoadPath3 = "https://imtt.dd.qq.com/16891/apk/27DC0BD401D13E8744161DE683030401.apk";
    private DownLoadUtils downLoadUtils;
    private Button bt_openxiaomi;
    public String[] EXTERNAL_STORAGE = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private Button btn_skip;
    private Button bt_recycler;
    private Button bt_apply;
    private int[] arrays;
    private Button bt_clear;
    private String[] pathList;
    private Button bt_query;
    private NetInfoReceiver receiver;
    private AppInstallReceiver appInstallReceiver;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    AppUtil.getInstance().toast("复制完成");
                    break;
            }
        }
    };
    private int gress;
    private MustInstallAppReceiver mustInstallAppReceiver;
    private ImageView iv_tu;
    private Button bt_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duan_dian);
        initView();
        initDownProgress();
        checkPermissions();
        regBroadcast();

    }

    private void updatePg() {
        gress = 0;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                gress++;
                if (gress > 100) {
                    return;
                }
                LogUtils.e("设置下载进度：" + gress + "==" + pb_progress.toString());
                pb_progress.setProgress(gress);
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    private void initDownProgress() {
        pathList = new String[]{downLoadPath1, downLoadPath2,downLoadPath3};
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final long contentLength = DownLoadUtils.getInstance().getContentLength(pathList[2],"");
                            ThreadInfo info = DownLoadDao.getInstance().select(pathList[2]);
//                            ThreadInfo info = new DownLoadDao(DuanDianActivity.this).select(downLoadPath);

                            if (info == null) {
                                LogUtils.e("threadInfo null");
                                return;
                            }
                            long finished = info.getFinished();
                            double v = finished / (contentLength * 1.0);
                            final int d = (int) (v * 100);
                            LogUtils.e("设置下载进度：" + d);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    LogUtils.e("设置下载进度：" + d);
                                    pb_progress.setProgress(d);
                                }
                            });
                        } catch (Exception e) {
                            LogUtils.e(e.toString());
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
        AppUtil.getInstance().regProCallBack(new ProgressCallBack() {
            @Override
            public void updateProgress(int pro) {
                LogUtils.e("回调进度：" + pro);
                pb_progress.setProgress(pro);
            }
        });
    }

    private void initView() {
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        bt_download = (Button) findViewById(R.id.bt_download);
        bt_pasu = (Button) findViewById(R.id.bt_pasu);

        bt_download.setOnClickListener(this);
        bt_pasu.setOnClickListener(this);
        bt_openxiaomi = (Button) findViewById(R.id.bt_openxiaomi);
        bt_openxiaomi.setOnClickListener(this);
        btn_skip = (Button) findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(this);
        bt_recycler = (Button) findViewById(R.id.bt_recycler);
        bt_recycler.setOnClickListener(this);
        bt_apply = (Button) findViewById(R.id.bt_apply);
        bt_apply.setOnClickListener(this);
        bt_clear = (Button) findViewById(R.id.bt_clear);
        bt_clear.setOnClickListener(this);
        bt_query = (Button) findViewById(R.id.bt_query);
        bt_query.setOnClickListener(this);
        iv_tu = (ImageView) findViewById(R.id.iv_tu);
        iv_tu.setOnClickListener(this);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_download:
                startDownLoad();
                break;
            case R.id.bt_pasu:
                DownLoadUtils.getInstance().stop();
                break;
            case R.id.bt_openxiaomi:
//                uploadP();
//                AppUtil.getInstance().xiezai(this, "com.iflytek.recinbox");
//                updatePg();
//                boolean b = AppUtil.getInstance().checkApkExist(this, "com.iflytek.recinbox");
//                if (b) {
//                    AppUtil.getInstance().toast("应用已安装");
//                }else {
//                    AppUtil.getInstance().toast("应用未安装");
//                }
                createThread();
                Intent intent = new Intent();
                intent.setAction(Constants.MUSTINSTALLAPP);
//                intent.putExtra("count", 5);
                List<MustApp> list = new ArrayList<>();
                MustApp mustApp = new MustApp("1", "2", "3", "4", "5", "6", "7");
                list.add(mustApp);
                intent.putExtra("applist", (Serializable) list);
                sendBroadcast(intent);
                break;
            case R.id.btn_skip:
//                exitApp(this);
//                checkFile();
//                printLog();

//                saveSp();
//                String s1 = AppUtil.getInstance().readConfig(this, "encryptCode");
//
//                byte[] decode = AppUtil.getInstance().decode(s1);
//                try {
//                    String s2 = new String(decode, "UTF-8");
//                    LogUtils.e(s2);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                for (int i = 0; i < 15; i++) {
//                    NotificationUtils.getInstance(this).sendNotification(i, "新英雄00"+i, "阿古朵");
//                }
                for (int i = 0; i < 15; i++) {
                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    LogBean logBean = new LogBean(Thread.currentThread().getId() + "", VERBOSE);

                                    NoticeQueue.getInstance().add(logBean);
//                                    AppUtil.getInstance();
                                }
                            }
                    ).start();
                }
                break;
            case R.id.bt_recycler:
//                new Thread(
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                FileUtils.getDataFile(DuanDianActivity.this, mHandler);
//                            }
//                        }
//                ).start();
                read();
                break;
            case R.id.bt_apply:
                arrays = new int[1000];
                for (int i = 0; i < 1000; i++) {
                    arrays[i] = i;
                }
                break;
            case R.id.bt_clear:
                DownLoadDao.getInstance().deleteAll();
                break;
            case R.id.bt_query:
                ArrayList<ThreadInfo> threadInfos = DownLoadDao.getInstance().selectAll();
                LogUtils.e("查询到记录：" + threadInfos.size());
                break;
            case R.id.bt_login:
                startActivity(new Intent(this, PicActivity.class));
                finish();
                break;
        }
    }

    private void createThread() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            int i = 8 * 2 * 2 * 2;
                            int i1 = i + i;
                            int i2 = i + i;
                            LogUtils.e(i1 + i2 + "");

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

    private void uploadP() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
//                        OkHttpUtil.getInstance().uploadFile();
                        FileUtils.getFile(DuanDianActivity.this);
                    }
                }
        ).start();
    }

    private class MustInstallAppReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, Constants.MUSTINSTALLAPP)) {
//                int count = intent.getIntExtra("count", 0);
                List<MustApp> applist = (List<MustApp>) intent.getSerializableExtra("applist");
                LogUtils.e("收到广播数据：" + applist.get(0).toString());
                openAlert(5);
            }
        }
    }

    private void openAlert(int msg) {
        //创建alert
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
//                .setTitle("操作title" + msg)
//                .setMessage("操作message")
                .setCancelable(false)
                .setPositiveButton("确定", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(DuanDianActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        canCloseDialog(dialogInterface,false);
                    }
                });
        alertDialog.show();
    }

    private void canCloseDialog(DialogInterface dialogInterface, boolean close) {
        try {
            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialogInterface, close);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void test() {
//        LogUtils.e("using 移动网络...");
//        if (lastTime > 0) {
//            long currentTimeMillis = System.currentTimeMillis();
//            if ((currentTimeMillis - lastTime) > 1000) {
//                lastTime = currentTimeMillis;
//                LogUtils.e("using 可用...");
//            }
//        } else {
//            lastTime = System.currentTimeMillis();
//            LogUtils.e("using 可用...");
//        }
//    }

    private void startDownLoad() {
            DownLoadUtils.getInstance().start(pathList[2]);
    }

    private void readSp() {
        SharedPreferences sharedPreferences = getSharedPreferences(SpUtils.DIDISP, 0);
        String name = sharedPreferences.getString("name", "");
        String age = sharedPreferences.getString("age", "");
        String six = sharedPreferences.getString("six", "");
        String ability = sharedPreferences.getString("ability", "");
        String weapon = sharedPreferences.getString("weapon", "");
        LogUtils.e("name:" + name + " age:" + age + " six:" + six + " ability:" + ability + " weapon:" + weapon);
    }

    private void saveSp() {
        SharedPreferences sharedPreferences = getSharedPreferences(SpUtils.DIDISP, 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("name", "路明非");
        edit.putString("age", "22");
        edit.putString("six", "男");
        LogUtils.e((0 / 0) + "");
        edit.putString("ability", "戒律");
        edit.putString("weapon", "七宗罪");
        edit.commit();
    }

    private void printLog() {
//        String val = this.getIntent().getExtras().getString("val");
        try {
//            int value = Integer.parseInt(val);
            String log = "twenty-one%0a%0aINFO:+User+logged+out%3dbadguy";
            Log.e("yy", vaildLog(log));
        } catch (NumberFormatException nfe) {
//            Log.e("yy", NFE);
        }
    }

    private void checkFile() {
        File file = new File("");
        if (file.exists()) {
            LogUtils.e("cunzai");
        } else {
            LogUtils.e("bucunzai");
        }
    }


    public static void exitApp(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
                List<ActivityManager.AppTask> appTaskList = null;
                appTaskList = activityManager.getAppTasks();
                for (ActivityManager.AppTask appTask : appTaskList) {
                    appTask.finishAndRemoveTask();
                }
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
        Process.killProcess(Process.myPid());
    }

    private void checkPermissions() {
        PermissionUtils.requestPermissions(this, EXTERNAL_STORAGE, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    LogUtils.e("同意了权限");
                } else {
                    Toast.makeText(DuanDianActivity.this, "请开启存储权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void read() {
        AppUtil.getInstance().getStringFromAssets(this, "adb");
    }

    private void regBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        receiver = new NetInfoReceiver();
        registerReceiver(receiver, intentFilter);

        appInstallReceiver = new AppInstallReceiver();
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter1.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter1.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter1.addDataScheme("package");
        registerReceiver(appInstallReceiver, intentFilter1);

        mustInstallAppReceiver = new MustInstallAppReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.MUSTINSTALLAPP);
        registerReceiver(mustInstallAppReceiver, filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getDpi() {
        DisplayMetrics DM = getResources().getDisplayMetrics();
        LogUtils.e("手机dpi====" + DM.densityDpi + "");
    }

    public static final String NFE = "Failed to parse val. The input is required to be an integer value.";

    /**
     * Log Forging漏洞校验
     *
     * @param logs
     * @return
     */
    public String vaildLog(String logs) {
        List<String> list = new ArrayList<String>();
        list.add("%0d");
        list.add("%0a");
        list.add("%0A");
        list.add("%0D");
        list.add("\r");
        list.add("\n");
        String normalize = Normalizer.normalize(logs, Normalizer.Form.NFKC);
        for (String str : list) {
            normalize = normalize.replace(str, "");
        }
        return normalize;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        unregisterReceiver(appInstallReceiver);

        unregisterReceiver(mustInstallAppReceiver);
    }
}
