package com.naruto.didi2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.naruto.didi2.R;
import com.naruto.didi2.util.AppUtil;

public class HtmlActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_jiexi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        initView();
    }

    private void initView() {
        bt_jiexi = (Button) findViewById(R.id.bt_jiexi);

        bt_jiexi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_jiexi:
                AppUtil.getInstance().getUrl();
                break;
        }
    }
}
