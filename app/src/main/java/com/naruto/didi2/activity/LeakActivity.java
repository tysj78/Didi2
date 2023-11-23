package com.naruto.didi2.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;


import java.io.File;

public class LeakActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private Button main_leak;
    private Button main_go;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            if (msg.what==1) {
////                Toast.makeText(LeakActivity.this, "收到message", Toast.LENGTH_SHORT).show();
//            }
        }
    };
    private TextView main_txt;
    private MediaPlayer mediaPlayer;
    private VideoView main_vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);
        initView();
//        NetUtils.getInstance(this).tos();
//        NetUtils.getInstance().uu();

    }

    private void initView() {
        main_leak = (Button) findViewById(R.id.main_leak);

        main_leak.setOnClickListener(this);
        main_go = (Button) findViewById(R.id.main_go);
        main_go.setOnClickListener(this);
        main_txt = (TextView) findViewById(R.id.main_txt);
        main_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_leak:
                startActivity(new Intent(this, KongActivity.class));
//                play();
//                startThread();
//                handler.sendEmptyMessageDelayed(1, 1000 * 60 * 5);
//                NetUtils.getInstance(this);
//                iHandler.setHandler(new MyHandler());
//                handler.sendEmptyMessage(1);
//                if ("".contains(":"))
//                    Log.e(TAG, "onClick: ");
//                try {
//                    getDevicesId();
//                } catch (Exception e) {
//                    Log.e(TAG, e.getMessage());
//                }
//                play2();
                break;
            case R.id.main_go:
//                android.os.Process.killProcess(Process.myPid());
                startActivity(new Intent(this, ClipboardActivity.class));
//                finish();
                break;
        }
    }

//    private class MyHandler implements MHandler{
//
//        @Override
//        public void handleMessage(Message msg) {
//
//        }
//    }
//    private void startThread() {
//        new Tt().start();
//    }
//
//    private static class Tt extends Thread {
//        @Override
//        public void run() {
//            super.run();
//            Log.e(TAG, "线程已开启: ");
////            SystemClock.sleep(5 * 60 * 1000);
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        handler.removeCallbacksAndMessages(null);
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }


    public void play() {
        //直接调用系统播放器进行播放
        Uri uri = null;
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloadfile/hanxin.mp4");
        if (!file.exists()) return;
        //测试使用的本地文件的path  add by gxb 20190918
//				Uri uri = Uri.parse("file://"+ Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera/VID_20190918_101423.mp4");

        //适配7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
        } else {
            uri = Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloadfile/hanxin.mp4");
        }
//        uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloadfile/hanxin.mp4");
        Log.e(Constants.TAG, "uri: " + uri);
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setDataAndType(uri, "video/mp4");
        //在服务中开启activity必须设置flag
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "没有默认播放器", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
//        String uri = ("android.resource://" + getPackageName() + "/" + R.raw.v1);
    }

    //    public interface MHandler {
//        public void handleMessage(Message msg);
//    }
    public void getDevicesId() {
        if (Build.VERSION.SDK_INT >= 29) {
            String string = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            main_txt.setText("android10:" + string);
        } else {
//            TelephonyManager telephonyManager = (TelephonyManager)
//                    getSystemService(Context.TELEPHONY_SERVICE);
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            String imei = telephonyManager.getDeviceId();
//            main_txt.setText(imei);
            getID();
        }
    }

    void getID() {
        //8.0动态权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int checkPermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1); //后面的1为请求码
            } else {
                main_txt.setText(getPhoneIMEI());
                Log.e(Constants.TAG, "imei: " + getPhoneIMEI());
            }
        }
    }

    @SuppressLint("MissingPermission")
    private String getPhoneIMEI() {
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    void play2() {
        main_vv.start();
    }

}
