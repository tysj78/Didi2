package com.yangyong.didi2.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yangyong.didi2.R;
import com.yangyong.didi2.activity.BaseActivity;
import com.yangyong.didi2.util.AppExitUtils;

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
                startActivity(new Intent(this, T2Activity.class));

                break;
            case R.id.bt_exit:
                AppExitUtils.getInstance().exit();
                break;
        }
    }
}
