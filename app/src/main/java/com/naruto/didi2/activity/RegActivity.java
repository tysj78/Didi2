package com.naruto.didi2.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;

//import com.naruto.didi2.activity.third.IjkPlayerActivity;
import com.naruto.didi2.util.PermissionUtils;

import io.reactivex.functions.Consumer;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText main_pwd;
    private Button main_sure;
    private int GPS_REQUEST_CODE = 1;
    private String[] strings = {
            Manifest.permission.ACCESS_FINE_LOCATION,//定位
            Manifest.permission.ACCESS_COARSE_LOCATION,//定位
            Manifest.permission.READ_EXTERNAL_STORAGE,//文件
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();

        openGPSSEtting();
    }

    private void initView() {
        main_pwd = (EditText) findViewById(R.id.main_pwd);
        main_sure = (Button) findViewById(R.id.main_sure);

        main_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_sure:
                PermissionUtils.requestPermissions(this, strings, new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            login();
                        } else {
                            Toast.makeText(RegActivity.this, "开启权限完成登录", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    private void login() {
        String s = main_pwd.getText().toString();
        if (s.equals("000001")) {
//                    preferences.edit().putBoolean("isLogin", true).commit();
//            startActivity(new Intent(RegActivity.this, IjkPlayerActivity.class));
            finish();
        } else {
            Toast.makeText(RegActivity.this, "密码错误，登入失败", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkGpsIsOpen() {
        boolean isOpen = false;
        try {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
        }
        return isOpen;
    }

    private void openGPSSEtting() {
        if (checkGpsIsOpen()) {
            Toast.makeText(this, "应用初始化成功", Toast.LENGTH_SHORT).show();
        } else {
            new AlertDialog.Builder(this).setTitle("应用初始化")
                    .setMessage("1.位置校验\n应用需开启位置功能才可完成校验 ")
                    //  取消选项
//                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
////                            Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
//                            // 关闭dialog
////                            dialogInterface.dismiss();
//                            Toast.makeText(RegActivity.this, "需开启位置服务才能完成校验", Toast.LENGTH_SHORT).show();
//                        }
//                    })
                    //  确认选项
                    .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //跳转到手机原生设置页面
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, GPS_REQUEST_CODE);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            openGPSSEtting();
        }
    }

    public void getRecords() {
        ContentResolver contentResolver = getContentResolver();
//        Browser.BOOKMARKS_URI;
        Cursor cursor = contentResolver.query(
                Uri.parse("content://browser/bookmarks"), new String[]{
//                        "title", "url", "date"}, "date!=?",
                        "title", "url"}, null,
//                new String[]{"null"}, "date desc");
                null, "date desc");
        while (cursor != null && cursor.moveToNext()) {
            String url = null;
            String title = null;
//            String time = null;
//            String date = null;

            StringBuilder recordBuilder = new StringBuilder();
            title = cursor.getString(cursor.getColumnIndex("title"));
            url = cursor.getString(cursor.getColumnIndex("url"));

//            date = cursor.getString(cursor.getColumnIndex("date"));

//            SimpleDateFormat dateFormat = new SimpleDateFormat(
//                    "yyyy-MM-dd hh:mm;ss");
//            Date d = new Date(Long.parseLong(date));
//            time = dateFormat.format(d);

            Log.e("yyy", "getRecords: " + title + url);
            System.out.println(title + url);
        }
    }

    private void initPer() {
        PermissionUtils.requestPermissions(this, strings, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        });
    }
}
