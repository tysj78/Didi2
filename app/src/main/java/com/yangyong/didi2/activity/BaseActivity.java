package com.yangyong.didi2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yangyong.didi2.util.AppExitUtils;

/**
 * Created by yangyong on 2019/9/18/0018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onStop() {
        super.onStop();
//        try {
//            if (this.getLocalClassName().equals("Main3Activity")) {
//                return;
//            }
//        } catch (Exception e) {
//            Log.e(Constants.TAG, "Exception: " + e.getMessage());
//        }
//        Log.e(Constants.TAG, "BaseActivity_onStop: ");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppExitUtils.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppExitUtils.getInstance().removeActivity(this);
    }
}
