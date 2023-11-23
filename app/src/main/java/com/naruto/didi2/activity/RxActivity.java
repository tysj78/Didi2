package com.naruto.didi2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.naruto.didi2.R;
import com.naruto.didi2.util.RxTimer;

public class RxActivity extends AppCompatActivity implements View.OnClickListener,RxTimer.RxAction {

    private Button check_net;
    private TextView contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        initView();

    }

    private void initView() {
        check_net = (Button) findViewById(R.id.check_net);
        contents = (TextView) findViewById(R.id.contents);

        check_net.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_net:
                RxTimer rxTimer = new RxTimer();
                rxTimer.interval(5000,RxActivity.this);
                break;
        }
    }

    @Override
    public void action(Long number) {
        contents.setText(number+"");
    }
}
