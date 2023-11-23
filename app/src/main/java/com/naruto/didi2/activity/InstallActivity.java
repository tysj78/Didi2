package com.naruto.didi2.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.util.AppUtil;

import java.io.File;

public class InstallActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_install1;
    private Button bt_install2;
    private static final int INSTALL_PERMISS_CODE = 101;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/recordbox-v3.0.1478.apk");
                    AppUtil.installAPK(InstallActivity.this, file);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);
        initView();
    }

    private void initView() {
        bt_install1 = (Button) findViewById(R.id.bt_install1);
        bt_install2 = (Button) findViewById(R.id.bt_install2);

        bt_install1.setOnClickListener(this);
        bt_install2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_install1:
                break;
            case R.id.bt_install2:
//                openPer();
                AppUtil.getInstance().commdDevice();
                break;
        }
    }

    private void openPer() {
        boolean haveInstallPermission = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
        }
        if (!haveInstallPermission) {
            Uri packageURI = Uri.parse("package:" + getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
            startActivityForResult(intent, INSTALL_PERMISS_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK && requestCode == INSTALL_PERMISS_CODE) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/recordbox-v3.0.1478.apk");
            AppUtil.installAPK(this, file);
        }
    }
}
