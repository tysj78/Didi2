package com.naruto.didi2.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.naruto.didi2.R;


public class DateActivity extends AppCompatActivity {

    private static final String TAG = "yy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        SharedPreferences sp = getSharedPreferences("didi_sp", 0);
        long stime = sp.getLong("ddate", 0);
        long current = System.currentTimeMillis();
        if (stime == 0) {
            Log.e(TAG, "onCreate首次上传，保存当前时间: "+current );
            sp.edit().putLong("ddate",current).commit();
        }else {
            if (current>=stime) {
                sp.edit().putLong("ddate",current).commit();
                Log.e(TAG, "onCreate环境正常，可上传,更新时间记录: " );
            }else {
                Log.e(TAG, "onCreate时间异常，取消上传: " );
            }
        }
//        Log.e(TAG, "onCreatel: " + stime);

    }
}
