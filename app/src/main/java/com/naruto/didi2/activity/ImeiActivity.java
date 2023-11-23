package com.naruto.didi2.activity;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;


public class ImeiActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private Button imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imei2);
        initView();
    }

    private void initView() {
        imei = (Button) findViewById(R.id.imei);

        imei.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imei:
                String string1 = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
                Log.e(TAG, "android_id: "+string1 );
                break;
        }
    }
}
