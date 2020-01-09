package com.yangyong.didi2;

import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yangyong.didi2.util.AppExitUtils;
import com.yangyong.didi2.util.HookSetOnClickListenerHelper;
import com.yangyong.didi2.util.NetUtils;

public class HookActivity extends BaseActivity implements View.OnClickListener {

    private Button main_hook;
    private Button main_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        initView();
        NetUtils.getInstance().uu();
        HookSetOnClickListenerHelper.hook(this, main_hook);
    }

    private void initView() {
        main_hook = (Button) findViewById(R.id.main_hook);

        main_hook.setOnClickListener(this);
        main_exit = (Button) findViewById(R.id.main_exit);
        main_exit.setOnClickListener(this);
    }

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
        }
    }
}
