package com.naruto.didi2.activity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.naruto.didi2.R;
import com.naruto.didi2.thread.ServerThread;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;

public class SocketActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView yv_content;
    private Button bt_getip;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    LogUtils.e("接收到客户端");
                    String s = (String) msg.obj;
                    yv_content.setText(s);
                    if (s.contains("下拉状态栏")) {
                        AppUtil.getInstance().pullStatus(SocketActivity.this);
                    }
                    if (s.contains("点击")) {
                        AppUtil.getInstance().startClick(SocketActivity.this);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        initView();
        initServer();
    }

    private void initServer() {
        ServerThread thread = new ServerThread(handler);
        thread.start();
    }

    private void initView() {
        yv_content = (TextView) findViewById(R.id.yv_content);
        bt_getip = (Button) findViewById(R.id.bt_getip);
        bt_getip.setOnClickListener(this);
    }

    private String getlocalip() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        //  Log.d(Tag, "int ip "+ipAddress);
        if (ipAddress == 0) return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_getip:
                String getlocalip = getlocalip();
                yv_content.setText(getlocalip);
                AppUtil.getInstance().openAccess(this);
                break;
        }
    }
}
