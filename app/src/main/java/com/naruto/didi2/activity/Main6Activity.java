package com.naruto.didi2.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.service.LocationForcegroundService;
import com.naruto.didi2.util.NotificationUtil;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class Main6Activity extends BaseActivity implements View.OnClickListener {

    private Button main_b;
    private Button main_move;
    private Button bt_request;
    private String[] per = new String[]{Manifest.permission.READ_SMS};
    private Intent forcegroundIntent = null;
    private Button mRequestLocationBt;
    private String[] strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        initView();

        forcegroundIntent = new Intent(this, LocationForcegroundService.class);

        if (!NotificationUtil.checkNotifySetting(this)) {
            NotificationUtil.initClickListener(this);
        }

        strings = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    }

    private void initView() {
        main_b = (Button) findViewById(R.id.main_b);

        main_b.setOnClickListener(this);
        main_move = (Button) findViewById(R.id.main_move);
        main_move.setOnClickListener(this);
        bt_request = (Button) findViewById(R.id.bt_request);
        bt_request.setOnClickListener(this);
        mRequestLocationBt = (Button) findViewById(R.id.bt_request_location);
        mRequestLocationBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_b:
                File[] files;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    files = getExternalFilesDirs(Environment.MEDIA_MOUNTED);
                    for (File file : files) {
                        Log.e(Constants.TAG, file.getAbsolutePath());
                    }
                    String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    Log.e(Constants.TAG, "getExternalStorageDirectory:" + absolutePath);
                }
                break;
            case R.id.main_move:
                break;
            case R.id.bt_request:
                rePer();
                break;
            case R.id.bt_request_location:// TODO 22/01/19
                startActivity(new Intent(this,LocationActivity.class));
                break;
        }
    }

    private void rePer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(forcegroundIntent);
        } else {
            startService(forcegroundIntent);
        }
    }

    public void onMove(View view) {
//        File dexOutputDir = getDir("dex1", 0);
        String dexOutputDir = Environment.getExternalStorageDirectory() + File.separator + "testjar/dex1";
        String dexPath = Environment.getExternalStorageDirectory() + File.separator + "testjar/out.jar";
        DexClassLoader loader =
                new DexClassLoader(dexPath, dexOutputDir, null, getClassLoader());
        try {
            Class clz = loader.loadClass("com.naruto.didi2.bean.DexRes");
            Method dexRes = clz.getDeclaredMethod("getString");
            Toast.makeText(this, (CharSequence) dexRes.invoke(clz.newInstance()), Toast.LENGTH_LONG)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
