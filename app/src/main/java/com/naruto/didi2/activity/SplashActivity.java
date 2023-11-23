package com.naruto.didi2.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.PermissionUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import io.reactivex.functions.Consumer;

public class SplashActivity extends AppCompatActivity {
    private String[] strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        strings = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        reqPer();
    }

    private void reqPer() {
        PermissionUtils.requestPermissions(this, strings, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
//                    startActivity(new Intent(SplashActivity.this, X5Activity.class));
//                    finish();
                    initX5();
                } else {
                    Toast.makeText(SplashActivity.this, "未开启存储和访问权限", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initX5() {
        if (QbSdk.canLoadX5(this)) {
            startActivity(new Intent(SplashActivity.this, X5Activity.class));
            finish();
            return;
        }


        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.e(" onViewInitFinished is " + arg0);
                startActivity(new Intent(SplashActivity.this, X5Activity.class));
                finish();
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
                LogUtils.e("内核初始化完成");
            }
        };

        QbSdk.setDownloadWithoutWifi(true);
        //x5内核初始化接口
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                LogUtils.e("onDownloadFinish:" + i);
            }

            @Override
            public void onInstallFinish(int i) {
                LogUtils.e("onInstallFinish:" + i);
            }

            @Override
            public void onDownloadProgress(int i) {
                LogUtils.e("onDownloadProgress:" + i);
            }
        });
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

}
