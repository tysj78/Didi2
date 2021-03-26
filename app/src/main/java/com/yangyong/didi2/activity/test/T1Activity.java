package com.yangyong.didi2.activity.test;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobilewise.didi2.R;
import com.yangyong.didi2.activity.BaseActivity;
import com.yangyong.didi2.constant.Constants;
import com.yangyong.didi2.util.AppExitUtils;
import com.yangyong.didi2.util.AppManager;
import com.yangyong.didi2.util.LogUtils;
import com.yangyong.didi2.util.PermissionUtils;

import io.reactivex.functions.Consumer;

public class T1Activity extends BaseActivity implements View.OnClickListener {

    private Button bt_tiao;
    private Button bt_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t1);
        initView();
    }

    private void initView() {
        bt_tiao = (Button) findViewById(R.id.bt_tiao);

        bt_tiao.setOnClickListener(this);
        bt_exit = (Button) findViewById(R.id.bt_exit);
        bt_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_tiao:
//                startActivity(new Intent(this, T2Activity.class));
//                finish();
//                sendBroadcast1();
                PermissionUtils.requestPermissions(AppManager.getAppManager().currentActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            LogUtils.e("开启存储权限");
                        }
                    }
                });
                break;
            case R.id.bt_exit:
                AppExitUtils.getInstance().exit();
                break;
        }
    }

    private void sendBroadcast1() {
        Intent intent = new Intent();
        intent.setAction(Constants.MUSTINSTALLAPP);
        sendBroadcast(intent);
    }
}
