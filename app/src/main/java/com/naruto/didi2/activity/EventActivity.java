package com.naruto.didi2.activity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.naruto.didi2.R;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.view.MyButton;
import com.naruto.didi2.view.MyLinearLayout;

public class EventActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_chend;
    private MyLinearLayout ll_parent;
    private TextView tv_ipaddress;
    private MyButton bt_mbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        initView();
    }

    private void initView() {
        bt_chend = (Button) findViewById(R.id.bt_chend);
        ll_parent = (MyLinearLayout) findViewById(R.id.ll_parent);

        bt_chend.setOnClickListener(this);
        ll_parent.setOnClickListener(this);
        tv_ipaddress = (TextView) findViewById(R.id.tv_ipaddress);
        tv_ipaddress.setOnClickListener(this);
        bt_mbt = (MyButton) findViewById(R.id.bt_mbt);
        bt_mbt.setOnClickListener(this);
    }

    /**
     * 或取本机的ip地址
     */
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
            case R.id.bt_chend:
                LogUtils.e("bt_chend");
                String ip = getlocalip();
                tv_ipaddress.setText(ip);
                break;
            case R.id.ll_parent:
                LogUtils.e("点击了自定义布局");
                break;
            case R.id.bt_mbt:
                LogUtils.e("点击了自定义按钮");
                AppUtil.getInstance().toast("点击了自定义按钮");
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.e("activity dispatchTouchEvent--");
        return super.dispatchTouchEvent(ev);
    }
}
