package com.naruto.didi2.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.broadcast.LocalReceiver;
import com.naruto.didi2.broadcast.PermissionReceiver;
import com.naruto.didi2.util.LogUtils;

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_sendbroadcast;
    private Context mContext;
    private Receiver1 receiver1;
    private Receiver2 receiver2;
    private Receiver3 receiver3;
    private final String LOCALRECEIVER = "com.yangyong.local";
    private final String PERRECEIVER = "com.yangyong.per";
    private final String PERMISSION = "com.yangyong.permission";
    private LocalReceiver localReceiver;
    private Button bt_sendlocal;
    private PermissionReceiver permissionReceiver;
    private Button bt_sendper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        mContext = this;
        initView();
//        initReceiver();
//        initLocalRec();
        initPerRec();
    }

    private void initPerRec() {
        permissionReceiver = new PermissionReceiver();
        registerReceiver(permissionReceiver, new IntentFilter(PERRECEIVER), PERMISSION, null);
    }

    /**
     * 注册本地广播
     */
    private void initLocalRec() {
        localReceiver = new LocalReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, new IntentFilter(LOCALRECEIVER));
    }

    private void initReceiver() {
        receiver1 = new Receiver1();
        receiver2 = new Receiver2();
        receiver3 = new Receiver3();
        IntentFilter intentFilter1 = new IntentFilter("com.yangyong.receiver");
        IntentFilter intentFilter2 = new IntentFilter("com.yangyong.receiver");
        IntentFilter intentFilter3 = new IntentFilter("com.yangyong.receiver");
        intentFilter1.setPriority(100);
        intentFilter2.setPriority(99);
        intentFilter3.setPriority(98);

        registerReceiver(receiver1, intentFilter1);
        registerReceiver(receiver2, intentFilter2);
        registerReceiver(receiver3, intentFilter3);
    }

    private void initView() {
        bt_sendbroadcast = (Button) findViewById(R.id.bt_sendbroadcast);

        bt_sendbroadcast.setOnClickListener(this);
        bt_sendlocal = (Button) findViewById(R.id.bt_sendlocal);
        bt_sendlocal.setOnClickListener(this);
        bt_sendper = (Button) findViewById(R.id.bt_sendper);
        bt_sendper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sendbroadcast:
                Intent intent = new Intent("com.yangyong.receiver");
                intent.putExtra("key", "我是暗号857");
                sendOrderedBroadcast(intent, null);
                break;
            case R.id.bt_sendlocal:
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(LOCALRECEIVER));
                break;
            case R.id.bt_sendper:
                sendBroadcast(new Intent(PERRECEIVER));
                break;
        }
    }

    private class Receiver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = intent.getStringExtra("key");
            LogUtils.e("Receiver1:" + key);
            Bundle bundle = new Bundle();
            bundle.putString("two", key);
            setResultExtras(bundle);
        }
    }

    private class Receiver2 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String two = getResultExtras(true).getString("two");
            LogUtils.e("Receiver2:" + two);
            Bundle bundle = new Bundle();
            bundle.putString("three", two);
            setResultExtras(bundle);
        }
    }

    private class Receiver3 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = getResultExtras(true);
            String three = extras.getString("three");
            LogUtils.e("Receiver3:" + three);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unRegisterRec();
//        unRegisterLocalRec();
        unRegisterPerRec();
    }

    private void unRegisterPerRec() {
        unregisterReceiver(permissionReceiver);
    }

    private void unRegisterLocalRec() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver);
    }

    private void unRegisterRec() {
        unregisterReceiver(receiver1);
        unregisterReceiver(receiver2);
        unregisterReceiver(receiver3);
    }
}
