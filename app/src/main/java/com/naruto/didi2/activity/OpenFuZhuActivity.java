package com.naruto.didi2.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;


public class OpenFuZhuActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private Button openbt;
    private Button landi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_fu_zhu);
        initView();
        landi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e(TAG, "onLongClick长点击事件: ");
                return false;
            }
        });
    }

    public void onOpen(View view) {
        try {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        } catch (Exception e) {
            startActivity(new Intent(Settings.ACTION_SETTINGS));
            e.printStackTrace();
        }
    }

    private void initView() {
        openbt = (Button) findViewById(R.id.openbt);

        openbt.setOnClickListener(this);
        landi = (Button) findViewById(R.id.landi);
        landi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openbt:
                openBt();
                break;
            case R.id.landi:
                Log.e(TAG, "onClick单击事件: ");
                break;
        }
    }

    private void openBt() {
        BluetoothAdapter mBluetoothAdapter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            BluetoothManager bm = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = bm.getAdapter();
        } else {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        mBluetoothAdapter.disable();
    }
}
