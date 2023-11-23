package com.naruto.didi2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

import com.naruto.didi2.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadFileService extends Service {
    public DownLoadFileService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 获取下载文件的URL和保存路径
        String fileUrl = intent.getStringExtra("fileUrl");
//        String outputPath = intent.getStringExtra("outputPath");

        // 调用文件下载方法
        downloadFile(fileUrl);
//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    private void downloadFile(String fileUrl) {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(fileUrl)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // 处理响应结果
//                    if (response.isSuccessful()) {
                    int code = response.code();
                    LogUtils.e("code:" + code+Thread.currentThread().getId());
                    if (response.isSuccessful()) {
                        // 处理成功的响应
                        // 获取响应的输入流
                        InputStream inputStream = response.body().byteStream();

                        // 创建文件输出流
                        File outputFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/apk/aa.apk");
                        FileOutputStream outputStream = new FileOutputStream(outputFile);

                        // 读取输入流并写入输出流，实现文件下载
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        LogUtils.e("应用下载完成");

                        // 关闭输入流和输出流
                        inputStream.close();
                        outputStream.close();
                    } else {
                        // 处理失败的响应
                        LogUtils.e("response error");
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    // 处理请求失败
                    LogUtils.e("接口访问失败");
                }
            });

        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }
}
