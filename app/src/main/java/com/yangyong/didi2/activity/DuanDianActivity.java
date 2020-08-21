package com.yangyong.didi2.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yangyong.didi2.NotificationUtils;
import com.yangyong.didi2.R;
import com.yangyong.didi2.bean.ThreadInfo;
import com.yangyong.didi2.dbdao.DownLoadDao;
import com.yangyong.didi2.notificationQueue.NoticeQueue;
import com.yangyong.didi2.util.AppUtil;
import com.yangyong.didi2.util.DownLoadUtils;
import com.yangyong.didi2.util.LogUtils;
import com.yangyong.didi2.util.PermissionUtils;
import com.yangyong.didi2.util.SpUtils;
import com.yangyong.didi2.zlog.LogBean;

import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.yangyong.didi2.zlog.LogType.VERBOSE;

public class DuanDianActivity extends AppCompatActivity implements View.OnClickListener, DownLoadUtils.IProgress {

    private ProgressBar pb_progress;
    private Button bt_download;
    private Button bt_pasu;
    //    private String downLoadPath = "https://1d01e7614d4c86ee39238460437b3f51.dlied1.cdntips.com/dlied1.qq.com/qqweb/QQ_1/android_apk/Android_8.3.3.4515_537063791.apk";
    private String downLoadPath = "https://gmapf-t.gameco.com.cn:8445/clientapp/AndroidMCDMgmapt.apk";
    //    private String downLoadPath = "http://luyin.voicecloud.cn/a";
    private DownLoadUtils downLoadUtils;
    private Button bt_openxiaomi;
    public String[] EXTERNAL_STORAGE = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private Button btn_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duan_dian);
        initView();
//        downLoadUtils = new DownLoadUtils(this, downLoadPath, this);
//        initDownProgress();
    }

    private void initDownProgress() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final long contentLength = downLoadUtils.getContentLength(downLoadPath);
                            ThreadInfo info = new DownLoadDao(DuanDianActivity.this).select(downLoadPath);
                            if (info == null) {
                                LogUtils.e("null");
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
    }

    private void openEmm() {
        try {
            Intent intent = new Intent();
//            intent.setData(Uri.parse("emm://com.mobilewise.mobileware/setxiaomimdm?type=220"));
            intent.setData(Uri.parse("emm://com.mobilewise.mobileware/sethuaweimdm?type=220"));
//        intent.putExtra("", "");//这里Intent当然也可传递参数,但是一般情况下都会放到上面的URL中进行传递
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            LogUtils.e("Exception打开设置页面异常: " + e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_download:
//                downLoadUtils.start();
                break;
            case R.id.bt_pasu:
//                downLoadUtils.stop();
                break;
            case R.id.bt_openxiaomi:
//                openEmm();
//                read();
//                testPermissions();

//                AppUtil.getInstance().checkRunOnAppPermission(this);
//                getDpi();
//                readSp();
//                NotificationUtils.getInstance(this).sendNotification(10002, "新英雄", "阿古朵");
                AppUtil.getInstance().release();
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
        }
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
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.AppTask> appTaskList = null;
                appTaskList = activityManager.getAppTasks();
                for (ActivityManager.AppTask appTask : appTaskList) {
                    appTask.finishAndRemoveTask();
                }
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void testPermissions() {
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
        AppUtil.getInstance().getStringFromAssets(this, "blueToothStr");
    }

    @Override
    public void onProgress(int progress) {
        pb_progress.setProgress(progress);
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

}
