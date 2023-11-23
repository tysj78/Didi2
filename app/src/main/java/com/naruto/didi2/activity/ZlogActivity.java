package com.naruto.didi2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.zlog.ZLog;


public class ZlogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlog);
        ZLog.e(Constants.TAG, "我是主线程log");
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        ZLog.e(Constants.TAG, "我是子线程log");
                    }
                }
        ).start();
    }
}
