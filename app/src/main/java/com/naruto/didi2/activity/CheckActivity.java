package com.naruto.didi2.activity;

import android.content.Context;
import android.os.Process;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;

import com.naruto.didi2.util.AppSignCheck;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CheckActivity extends AppCompatActivity {

    private static final String TAG = "yy";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        context = this;


//        checkDex();
//        checkSignature();

//        startThread();
        try {
            exTest();
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception2: " + e.toString());
        }
    }

    private void exTest() throws IOException {
//        try {
        throw new IOException("我是自定义的ioexception");
//        } catch (Exception e) {
//            Log.e(Constants.TAG, "Exception1: " + e.getMessage());
//        }
    }

    private void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: 1");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "应用异常即将退出1", Toast.LENGTH_SHORT).show();
                    }
                });
                SystemClock.sleep(1000);
                Process.killProcess(Process.myPid());
                System.exit(0);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run2: ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "应用异常即将退出2", Toast.LENGTH_SHORT).show();
                    }
                });
                SystemClock.sleep(1000);
                Process.killProcess(Process.myPid());
                System.exit(0);
            }
        }).start();
    }

    private void checkSignature() {
        AppSignCheck signCheck = new AppSignCheck(this, "F6:6A:09:98:EB:6B:63:98:28:5F:39:66:36:A1:69:1E:E1:F1:15:23");
        if (signCheck.check()) {
            Log.e(TAG, "checkSignature证书验证通过: ");
        } else {
//            newAlertDialog.Builder(this).setMessage("请前往官方渠道下载正版 app， http://.....").setPositiveButton("确定",null).show();
            Log.e(TAG, "checkSignature证书验证未通过: ");
        }
    }

    private void checkDex() {
        String apkPath = getPackageCodePath();
        Long dexCrc = Long.parseLong(getString(R.string.classesdex_crc));


        try {
            ZipFile zipfile = new ZipFile(apkPath);
            ZipEntry dexentry = zipfile.getEntry("classes.dex");
            Log.i("verification", "classes.dexcrc=" + dexentry.getCrc());
            if (dexentry.getCrc() != dexCrc) {
                Log.i("verification", "Dexhas been modified!");
            } else {
                Log.i("verification", "Dex hasn't been modified!");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
