package com.naruto.didi2.activity.third;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;

import java.lang.ref.WeakReference;

public class HandlerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button m111Bt;
    private String aa = "呼哈";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        initView();

    }

    private void initView() {
        m111Bt = (Button) findViewById(R.id.bt_111);
        m111Bt.setOnClickListener(this);
    }

    private void methodHandler() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                LogUtils.e("111");
//                Looper.prepare();
//                LogUtils.e("222");
//                Looper.myLooper().quitSafely();
//                Looper.loop();
//                LogUtils.e("333");
               /* LogUtils.e(Thread.currentThread().getName());
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.e(Thread.currentThread().getName());
                        Toast.makeText(HandlerActivity.this, "喝啊", Toast.LENGTH_SHORT).show();
                    }
                });*/
//                AppUtil.getInstance().showToast("欧拉");
                Looper.prepare();
                Toast.makeText(HandlerActivity.this, "流量", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_111:
//                methodHandler();
//                new MyHandler(this).sendEmptyMessage(0);
                break;
            default:
                break;
        }
    }

    public static class MyHandler extends Handler {
        private WeakReference<HandlerActivity> weakReference;

        private MyHandler(HandlerActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    HandlerActivity handlerActivity = weakReference.get();
                    LogUtils.e("收到0");

                    Toast.makeText(handlerActivity, "匹配123" + handlerActivity.aa, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
