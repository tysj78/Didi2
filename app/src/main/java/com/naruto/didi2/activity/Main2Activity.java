package com.naruto.didi2.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.naruto.didi2.R;


public class Main2Activity extends AppCompatActivity {
    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
            Toast.makeText(Main2Activity.this, "打开位置开关后使用本应用", Toast.LENGTH_LONG).show();
            finish();
        }else {
            requestP();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    private void requestP() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, permissions, 321);
            }else {
                Main2Activity.this.startActivity(new Intent(Main2Activity.this, LocationActivity.class));
                finish();
            }
        }else {
            Main2Activity.this.startActivity(new Intent(Main2Activity.this, LocationActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            //获取到权限
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //跳转到定位页面
                Main2Activity.this.startActivity(new Intent(Main2Activity.this, LocationActivity.class));
            } else {
                //未获取到权限，继续获取
                ActivityCompat.requestPermissions(this, permissions, 321);
            }
        }

    }

}
