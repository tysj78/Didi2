package com.naruto.didi2.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.naruto.didi2.R;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private Button request_p;
    private boolean result = false;
    private RxPermissions permission = null;
    private Button thread_switch;
    private String[] ps;

    //    permission:android.permission.READ_EXTERNAL_STORAGE
//    permission:android.permission.WRITE_EXTERNAL_STORAGE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        initView();
        //文件
//                Manifest.permission.READ_PHONE_STATE,//imei
//                Manifest.permission.ACCESS_FINE_LOCATION,//定位
//                Manifest.permission.ACCESS_COARSE_LOCATION,//定位
//                Manifest.permission.CALL_PHONE,
//                Manifest.permission.SEND_SMS,
//                Manifest.permission.RECEIVE_SMS,
//                Manifest.permission.READ_CONTACTS,
//                Manifest.permission.WRITE_CONTACTS,
//                Manifest.permission.READ_SMS,
//文件
//                Manifest.permission.CAMERA,
//                Manifest.permission.GET_ACCOUNTS
        ps = new String[]{
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,//文件
//                Manifest.permission.READ_PHONE_STATE,//imei
                Manifest.permission.ACCESS_FINE_LOCATION,//定位
                Manifest.permission.ACCESS_COARSE_LOCATION,//定位


//                Manifest.permission.CALL_PHONE,
//                Manifest.permission.SEND_SMS,
//                Manifest.permission.RECEIVE_SMS,
//                Manifest.permission.READ_CONTACTS,
//                Manifest.permission.WRITE_CONTACTS,
//                Manifest.permission.READ_SMS,
//                Manifest.permission.READ_EXTERNAL_STORAGE,//文件
//                Manifest.permission.CAMERA,
//                Manifest.permission.GET_ACCOUNTS
        };
    }

    private void initView() {
        request_p = (Button) findViewById(R.id.request_p);

        request_p.setOnClickListener(this);
        thread_switch = (Button) findViewById(R.id.thread_switch);
        thread_switch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.request_p:
//                requestPermissions(new String[]{});
//                getRomTotalSize();
                requestPermissions(new String[0], new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Log.e(TAG, "accept: ");
//                            //请求成功
//                            show("获取权限成功");
//                            writerExter();
//                            readExter();
//                            result = true;
                        } else {
                            Log.e(TAG, "noaccept: ");
//                            //请求失败
//                            show("未获取权限");
//                            result = false;
                        }
                    }
                });

                requestPermissions(new String[0], new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Log.e(TAG, "accept1: ");
//                            //请求成功
//                            show("获取权限成功");
//                            writerExter();
//                            readExter();
//                            result = true;
                        } else {
//                            Log.e(TAG, "noaccept: ");
//                            //请求失败
//                            show("未获取权限");
//                            result = false;
                        }
                    }
                });

                requestPermissions(new String[0], new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Log.e(TAG, "accept2: ");
//                            //请求成功
//                            show("获取权限成功");
//                            writerExter();
//                            readExter();
//                            result = true;
                        } else {
//                            Log.e(TAG, "noaccept: ");
//                            //请求失败
//                            show("未获取权限");
//                            result = false;
                        }
                    }
                });


//                writerExter();
//                readExter();
//                Log.e(Constants.TAG, "onClick1: " + b);
//                if (b) {
//                    Log.e(Constants.TAG, "onClick2: ");
////                    writerExter();
////                    readExter();
//                }

                break;
            case R.id.thread_switch:
                thread_sw();
                break;
        }
    }

    private void thread_sw() {
        final Handler handler = new Handler(Looper.getMainLooper());
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "run我在1: " + Thread.currentThread().getId());
                        SystemClock.sleep(2000);
                        Looper.prepare();
                        final Handler t_handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                Log.e(TAG, "run我在3: " + Thread.currentThread().getId());
                            }
                        };
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "run我在2: " + Thread.currentThread().getId());
                                t_handler.sendEmptyMessage(1);
                            }
                        });

                        Looper.loop();

                    }
                }
        ).start();
    }

    public void requestPermissions(String[] permissions, Consumer<Boolean> consumer) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        //RxPermission在目标activity里面添加了一个fragment用于拦截权限申请结果
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                permission = new RxPermissions(PermissionActivity.this);
//            }
//        });
//        permissions.setLogging(true);
        String[] s = new String[]{
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,//文件
                Manifest.permission.READ_PHONE_STATE,//imei
//                Manifest.permission.ACCESS_FINE_LOCATION,//定位
//                Manifest.permission.ACCESS_COARSE_LOCATION,//定位


//                Manifest.permission.CALL_PHONE,
//                Manifest.permission.SEND_SMS,
//                Manifest.permission.RECEIVE_SMS,
//                Manifest.permission.READ_CONTACTS,
//                Manifest.permission.WRITE_CONTACTS,
//                Manifest.permission.READ_SMS,
//                Manifest.permission.READ_EXTERNAL_STORAGE,//文件
//                Manifest.permission.CAMERA,
//                Manifest.permission.GET_ACCOUNTS
        };
        permission = new RxPermissions(PermissionActivity.this);
        Observable<Boolean> request = permission.request(s);
        request.subscribe(consumer);
    }

    private void show(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /*
     * 手机存储空间或者SD卡总大小
	 */
    private String getRomTotalSize() {
        String state = Environment.getExternalStorageState();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //存在sd卡
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            Log.i("yy", "blockSize*totalBlocks--->" + blockSize * totalBlocks);
            Log.i("yy", "blockSize*totalBlocks--->" + Formatter.formatFileSize(this, blockSize * totalBlocks));
//			return Formatter.formatFileSize(mContext, blockSize * totalBlocks);
            return blockSize * totalBlocks + "";
        } else {
            //不存在SD卡
            return "0";
        }
    }

    private void readExter() {
//        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
//        Log.e(Constants.TAG, "readExter: "+absolutePath );
        // 读取SD卡文件里面的内容
        FileReader fr = null;
        try {
            fr = new FileReader("/storage/emulated/0/file.txt");
            BufferedReader r = new BufferedReader(fr);
            String result = r.readLine();
            Log.d("yy", "外部存储文件里面的内容:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writerExter() {
        // 在SD卡目录下的文件，写入内容
        File file = new File(Environment.getExternalStorageDirectory(), "file.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write("xinxinxixni新1.....");
            fw.close();
            // Toast.makeText(MainActivity.this, "SD卡写入内容完成...",Toast.LENGTH_LONG).show();
            Log.d("yy", "SD卡写入内容完成...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
