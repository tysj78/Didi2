package com.naruto.didi2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.naruto.didi2.R;
import com.naruto.didi2.service.TimerService;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imv_img;
    private Button xiumian;
    private Button goto1;
//    private GridLayout gl_new_type_attach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initView();
        imv_img.setImageResource(R.drawable.peiqi);

        imv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ImageActivity.this);
                View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
                final AlertDialog dialog = new AlertDialog.Builder(ImageActivity.this).create();
                ImageView img1 = (ImageView) imgEntryView.findViewById(R.id.large_image);
                img1.setImageResource(R.drawable.peiqi);
                dialog.setView(imgEntryView); // 自定义dialog
                dialog.show();
            }
        });
//        AppInstallReceiver appInstallReceiver = new AppInstallReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
//        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
//        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
//        intentFilter.addDataScheme("package");
//        registerReceiver(appInstallReceiver, intentFilter);
        Intent iIntent = new Intent(this, TimerService.class);
        startService(iIntent);
    }

    void reust() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 5; i++) {
                            try {
                                Log.e("yy", i + "============1");
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Log.e("yy", "run: " + e.toString());
//                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i < 2; i++) {
                            try {
                                Log.e("yy", i + "============2");
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("yy", "循环结束=====");
                    }
                }
        ).start();

    }


    private void initView() {
        imv_img = (ImageView) findViewById(R.id.imv_img);
        xiumian = (Button) findViewById(R.id.xiumian);
        xiumian.setOnClickListener(this);
        goto1 = (Button) findViewById(R.id.goto1);
        goto1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xiumian:
                reust();
                break;
            case R.id.goto1:
                startActivity(new Intent(this,Main7Activity.class));
                finish();
                break;
        }
    }
}
