package com.naruto.didi2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;


public class InstanceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button testbug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance);
        initView();
    }

    private void initView() {
        testbug = (Button) findViewById(R.id.testbug);

        testbug.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testbug:
                modted();
                break;
        }
    }

    private void modted() {
        for (int i = 0; i < 5; i++) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
//                            Log.e(Constants.TAG, "threadid: "+Thread.currentThread().getId() );
                        }
                    }
            ).start();
        }
    }
}
