package com.naruto.didi2.util;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by DELL on 2021/8/30.
 */

public class DownLoadFileUtil {
    private DownLoadFileUtil() {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dfile");
            if (!file.exists())
                file.mkdirs();
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }

    }

    private static DownLoadFileUtil instance = null;

    public static DownLoadFileUtil getInstance() {
        if (instance == null) {
            instance = new DownLoadFileUtil();
        }
        return instance;
    }

    public void downLoadFile(String url, DownLoadListener listener) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS).build();

        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    listener.onFail("下载异常：" + e.toString());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    ResponseBody body = response.body();
                    long contentLength = body.contentLength();
                    LogUtils.e("文件长度：" + contentLength);

                    inputStream = body.byteStream();
                    File file = new File(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dfile"), "haha.apk");
                    fileOutputStream = new FileOutputStream(file);
                    byte[] temp = new byte[1024 * 2];
                    int progress = 0;
                    long tempTime = 0;
                    int len = 0;
                    while ((len = inputStream.read(temp)) != -1) {
                        fileOutputStream.write(temp, 0, len);
                        progress += len;
                        int i = (int) (progress * 1.0f / contentLength * 100);

                        long currentTimeMillis = System.currentTimeMillis();
                        if (currentTimeMillis - tempTime > 1000 && listener != null) {
                            listener.onProgress(i);
                            tempTime = currentTimeMillis;
                        }
                    }
                    fileOutputStream.flush();
                    LogUtils.e("下载完成");
                    if (listener != null)
                        listener.onProgress(100);
                } catch (Exception e) {
                    LogUtils.e("" + e.toString());
                } finally {
                    try {
                        if (fileOutputStream != null)
                            fileOutputStream.close();
                    } catch (Exception e) {
                        LogUtils.e("" + e.toString());
                    }
                    try {
                        if (inputStream != null)
                            inputStream.close();
                    } catch (Exception e) {
                        LogUtils.e("" + e.toString());
                    }

                }
            }
        });
    }


    public interface DownLoadListener {
        void onSuccess();

        void onFail(String s);

        void onProgress(int p);
    }
}
