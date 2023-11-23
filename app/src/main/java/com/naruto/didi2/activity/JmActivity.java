package com.naruto.didi2.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import com.naruto.didi2.R;
import com.naruto.didi2.util.FileUtils;
import com.naruto.didi2.util.InstallUtils;

import java.io.File;

import io.reactivex.functions.Consumer;

public class JmActivity extends AppCompatActivity implements View.OnClickListener {

    private Button jm;
    private RxPermissions rxPermissions;
    private JmActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jm);
        initView();
        baseDataInit();
        checkUserAllPermissions();
    }

    private void initView() {
        jm = (Button) findViewById(R.id.jm);

        jm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jm:
                if (true) {
                    //Toast.makeText(mContext, "有root权限", Toast.LENGTH_SHORT).show();
                    installSilent();
                    Toast.makeText(mContext, "开始静默安装", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "没有root权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void installSilent() {
        new Thread() {
            @Override
            public void run() {
                 String apk = FileUtils.getSDPath();
                InstallUtils.installSilent(apk, mContext, getPackageManager());
            }
        }.start();
    }

    private void baseDataInit() {
        mContext = this;
        rxPermissions = new RxPermissions(this);
    }

    /**
     * 检查并获取用户权限
     */
    private void checkUserAllPermissions() {
        rxPermissions
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.REQUEST_INSTALL_PACKAGES,
                        Manifest.permission.INSTALL_PACKAGES
                )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {

                    }
                });
    }

    private boolean isRoot() {
        if (new File("/system/bin/su").exists() || new File("/system/xbin/su").exists()) {
            return true;
        }
        return false;
    }

}
