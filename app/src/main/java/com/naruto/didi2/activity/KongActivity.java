package com.naruto.didi2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;


public class KongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置透明沉浸状态栏
//        if (Build.VERSION.SDK_INT>=21)
//        {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE); //使背景图与状态栏融合到一起，这里需要在setcontentview前执行
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_kong);
        //设置1像素
//        Window window = getWindow();
//        window.setGravity(Gravity.LEFT | Gravity.TOP);
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.x = 0;
//        params.y = 0;
//        params.height = 1;
//        params.width = 1;
//        window.setAttributes(params);
        Log.e(Constants.TAG, "onCreate: " );
    }

}
