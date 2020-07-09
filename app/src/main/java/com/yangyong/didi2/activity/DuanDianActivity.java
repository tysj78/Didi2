package com.yangyong.didi2.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.yangyong.didi2.R;
import com.yangyong.didi2.bean.ThreadInfo;
import com.yangyong.didi2.dbdao.DownLoadDao;
import com.yangyong.didi2.util.DownLoadUtils;
import com.yangyong.didi2.util.LogUtils;

import java.io.IOException;

public class DuanDianActivity extends AppCompatActivity implements View.OnClickListener, DownLoadUtils.IProgress {

    private ProgressBar pb_progress;
    private Button bt_download;
    private Button bt_pasu;
//    private String downLoadPath = "https://1d01e7614d4c86ee39238460437b3f51.dlied1.cdntips.com/dlied1.qq.com/qqweb/QQ_1/android_apk/Android_8.3.3.4515_537063791.apk";
    private String downLoadPath = "https://gmapf-t.gameco.com.cn:8445/clientapp/AndroidMCDMgmapt.apk";
    //    private String downLoadPath = "http://luyin.voicecloud.cn/a";
    private DownLoadUtils downLoadUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duan_dian);
        initView();
        downLoadUtils = new DownLoadUtils(this, downLoadPath, this);
        initDownProgress();
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
                            LogUtils.e("设置下载进度："+d);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    LogUtils.e("设置下载进度："+d);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_download:
                downLoadUtils.start();
                break;
            case R.id.bt_pasu:
                downLoadUtils.stop();
                break;
        }
    }

    @Override
    public void onProgress(int progress) {
        pb_progress.setProgress(progress);
    }
}
