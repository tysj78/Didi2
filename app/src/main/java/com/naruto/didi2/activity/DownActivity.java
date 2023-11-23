package com.naruto.didi2.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;


import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DownActivity extends AppCompatActivity implements View.OnClickListener {

    private Button main_down;
    private String downLoadUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
    private Executor executor = Executors.newFixedThreadPool(3);
    private static int count = 0;
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            count += what;
            if (count == 3) {
//                Toast.makeText(DownActivity.this, "下载完成。", Toast.LENGTH_SHORT).show();
                Log.e(Constants.TAG, "下载完成: ");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        initView();
        requestP();
    }

    private void initView() {
        main_down = (Button) findViewById(R.id.main_down);

        main_down.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_down:
                downLoad();
                break;
        }
    }

    private void downLoad() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String fileName = getFileName();
                            File directory = Environment.getExternalStorageDirectory();
                            File file = new File(directory, fileName);
                            URL url = new URL(downLoadUrl);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setReadTimeout(5000);
                            connection.setRequestMethod("GET");
                            int contentLength = connection.getContentLength();
                            Log.e(Constants.TAG, "文件长度: " + contentLength);
                            int block = contentLength / 3;
                            for (int i = 0; i < 3; i++) {
                                long start = i * block;
                                long end = (i + 1) * block - 1;
                                if (i == 2) {
                                    end = contentLength;
                                }
                                DownLoadRunnable downLoadRunnable = new DownLoadRunnable(downLoadUrl, file.getAbsolutePath(), start, end);
                                executor.execute(downLoadRunnable);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

    private String getFileName() {
        return downLoadUrl.substring(downLoadUrl.lastIndexOf("/") + 1);
    }

    static class DownLoadRunnable implements Runnable {
        private String url;
        private String fileName;
        private long start;
        private long end;

        public DownLoadRunnable(String url, String fileName, long start, long end) {
            this.url = url;
            this.fileName = fileName;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            try {
                URL urls = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
                connection.setReadTimeout(5000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
                RandomAccessFile access = new RandomAccessFile(new File(fileName), "rwd");
                access.seek(start);
                InputStream inputStream = connection.getInputStream();
                byte[] bytes = new byte[1024 * 4];
                int len = 0;
                while ((len = inputStream.read(bytes)) != -1) {
                    access.write(bytes, 0, len);
                    start+=len;
                    Log.e(Constants.TAG, "len: "+start );
                }
                inputStream.close();
                access.close();
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void requestP() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            int u = ContextCompat.checkSelfPermission(this, permissions[1]);
            int y = ContextCompat.checkSelfPermission(this, permissions[1]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED || u != PackageManager.PERMISSION_GRANTED || y != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, permissions, 321);
            }
        }
    }
}
