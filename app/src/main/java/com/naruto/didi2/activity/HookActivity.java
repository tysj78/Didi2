package com.naruto.didi2.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.naruto.didi2.R;
import com.naruto.didi2.util.AppExitUtils;
import com.naruto.didi2.util.HookSetOnClickListenerHelper;

public class HookActivity extends BaseActivity implements View.OnClickListener {

    private Button main_hook;
    private Button main_exit;
    private Button bt_sendnoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        initView();
//        NetUtils.getInstance().uu();
        HookSetOnClickListenerHelper.hook(this, main_hook);
    }

    private void initView() {
        main_hook = (Button) findViewById(R.id.main_hook);

        main_hook.setOnClickListener(this);
        main_exit = (Button) findViewById(R.id.main_exit);
        main_exit.setOnClickListener(this);
        bt_sendnoti = (Button) findViewById(R.id.bt_sendnoti);
        bt_sendnoti.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_hook:
                Toast.makeText(this, "点击了按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_exit:
//                android.os.Process.killProcess(Process.myPid());
//                System.exit(0);
                AppExitUtils.getInstance().exit();
                break;
            case R.id.bt_sendnoti:
//                NotificationUtils notificationUtils = new NotificationUtils(this);
//                notificationUtils.sendNotification("新通知","小霸王");
                break;
        }
    }
}
