package com.naruto.didi2.activity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_get_boothmac;
    private TextView tv_mac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        initView();
    }

    private void initView() {
        bt_get_boothmac = (Button) findViewById(R.id.bt_get_boothmac);

        bt_get_boothmac.setOnClickListener(this);
        tv_mac = (TextView) findViewById(R.id.tv_mac);
        tv_mac.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_get_boothmac:
                String macAddress = getBluetoothMacAddress();
                tv_mac.setText(macAddress);
                break;
        }
    }

    private String queryMac() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Field field = bluetoothAdapter.getClass().getDeclaredField("mService");
            // 参数值为true，禁用访问控制检查
            field.setAccessible(true);
            Object bluetoothManagerService = field.get(bluetoothAdapter);
            if (bluetoothManagerService == null) {
                return null;
            }
            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
            Object address = method.invoke(bluetoothManagerService);
            if (address != null && address instanceof String) {
                return (String) address;
            } else {
                return null;
            }
            //抛一个总异常省的一堆代码...
        } catch (Exception e) {
            LogUtils.e("queryMac:" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取蓝牙地址
     */
    private String getBluetoothMacAddress() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String bluetoothMacAddress = "";
        try {
            Field mServiceField = bluetoothAdapter.getClass().getDeclaredField("mService");
            mServiceField.setAccessible(true);

            Object btManagerService = mServiceField.get(bluetoothAdapter);

            if (btManagerService != null) {
                bluetoothMacAddress = (String) btManagerService.getClass().getMethod("getAddress").invoke(btManagerService);
            }
        } catch (Exception e) {
            LogUtils.e("queryMac:" + e.toString());
        }
        return bluetoothMacAddress;
    }

}
