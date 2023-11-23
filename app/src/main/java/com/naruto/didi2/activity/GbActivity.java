package com.naruto.didi2.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naruto.didi2.R;


public class GbActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "yy";
    private String bdOne = "com.yangyong.bdOne";
    private String bdTwo = "com.yangyong.bdTwo";
    private Button gb_sendgb;
    private Button gb_send_two;
    private GbOne gbOne;
    private GbTwo gbTwo;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gb);
        initView();
        registGB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregistGb();
    }

    private void unregistGb() {
        if (gbOne != null) {
            unregisterReceiver(gbOne);
        }
        if (gbTwo != null) {
            unregisterReceiver(gbTwo);
        }
    }

    private void registGB() {
        gbOne = new GbOne();
        gbTwo = new GbTwo();
        IntentFilter intent = new IntentFilter();
        intent.addAction(bdOne);
        registerReceiver(gbOne, intent);

        IntentFilter intent1 = new IntentFilter();
        intent1.addAction(bdTwo);
        registerReceiver(gbTwo, intent1);

    }

    private void initView() {
        gb_sendgb = (Button) findViewById(R.id.gb_sendgb);
        gb_send_two = (Button) findViewById(R.id.gb_send_two);

        gb_send_two.setOnClickListener(this);
        gb_sendgb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gb_sendgb:
                sendBroadcast(new Intent(bdOne));
                break;
            case R.id.gb_send_two:
                sendBroadcast(new Intent(bdTwo));
                break;
        }
    }


    private class GbOne extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "GbOneonReceive: ");
            if (intent.getAction().equals(bdOne)) {
                Toast.makeText(context, "GbOne收到了", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GbTwo extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "GbTwoonReceive: ");
            if (intent.getAction().equals(bdTwo)) {
                Toast.makeText(context, "GbTwo收到了", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
