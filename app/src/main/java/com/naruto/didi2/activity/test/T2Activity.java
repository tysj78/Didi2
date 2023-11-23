package com.naruto.didi2.activity.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.naruto.didi2.R;
import com.naruto.didi2.activity.BaseActivity;
import com.naruto.didi2.util.AppExitUtils;

public class T2Activity extends BaseActivity implements View.OnClickListener {

    private Button bt_tiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t2);
        initView();
    }

    private void initView() {
        bt_tiao = (Button) findViewById(R.id.bt_tiao);

        bt_tiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_tiao:
//                Intent intent = new Intent(this, Main5Activity.class);
//                intent.putExtra("EXIT_TAG", "SINGLETASK");
//                startActivity(intent);

//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                startActivity(intent);

                AppExitUtils.getInstance().exit();
                break;
        }
    }
}
