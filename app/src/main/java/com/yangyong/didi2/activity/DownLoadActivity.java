package com.yangyong.didi2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yangyong.didi2.R;

public class DownLoadActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView filename;
    private ProgressBar jindu;
    private Button down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load2);
        initView();


    }

    private void initView() {
        filename = (TextView) findViewById(R.id.filename);
        jindu = (ProgressBar) findViewById(R.id.jindu);
        down = (Button) findViewById(R.id.down);

        down.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.down:

                break;
        }
    }
}
