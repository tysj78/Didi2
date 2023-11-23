package com.naruto.didi2.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;

import com.naruto.didi2.broadcast.NetInfoReceiver;
import com.naruto.didi2.util.OkHttpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UIActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private String path = "https://192.168.220.48:8445/mcdm/McdmServlet/FileAction?method=engFileServlet&fileId=1&start=87575000";
    private String path2 = "https://d5a85c63fe7396d1dec83f0f9001f38d.dd.cdntips.com/imtt.dd.qq.com/16891/apk/FFF8E319FDE2A0E9BB7307C848C77201.apk";
    private String path1 = "https://192.168.220.48:8445/mcdm/McdmServlet/FileAction?method=engFileServlet&start=0&fileId=1";
    private Button bt;
    private Context context;
    private static boolean num = false;
    private Handler handler2;
    private Handler handler1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        context = this;
        initView();
//        String serviceUrl = UrlConfig.getServiceUrl();
//        Log.e(Constants.TAG, "onCreate: "+serviceUrl );
//        UrlConfig urlConfig = new UrlConfig();
//        moe();
//        regBroadcast();
    }

    private void regBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        NetInfoReceiver receiver = new NetInfoReceiver();
        registerReceiver(receiver,intentFilter);
    }

    private void UrlConn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(3000);
                    conn.setRequestMethod("GET");
                    // 设置下载位置
//        int start = mThreadInfo.getStart() + mThreadInfo.getFinished();

//        Log.e("gxb", "文件开始下载================：" + start+"===="+mThreadInfo.getEnd());
//                    conn.setRequestProperty("Range", "bytes=" + 0 + "-" + 200);
                    // 设置文件写入位置
//        File file = new File(DownloadService.DOWNLOAD_PATH, mFileInfo.getFileName());
//        raf = new RandomAccessFile(file, "rwd");
//        raf.seek(start);
                    // 开始下载
                    // if (conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL)
//        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // 读取数据
//            input = conn.getInputStream();
                    //跳过已下载部分
//            input.skip(start);
//            int lent=conn.getContentLength();
//            Log.e("gxb", "文件开始下载的长度================：" + lent);
                    int responseCode = conn.getResponseCode();

                    InputStream inputStream = conn.getInputStream();

                    int lent = conn.getContentLength();
                    Log.e(Constants.TAG, "文件开始下载的长度================：" + lent + "====响应码：" + responseCode);
                } catch (Exception e) {
                    Log.e(TAG, "请求异常: " + e.getMessage());
                }
            }
        }).start();
    }


    private void getDate() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(path).get().
                                addHeader("Range", "bytes=" + 0 + "-" + 200).build();
                        Response response = null;
                        try {
                            response = client.newCall(request).execute();
                            long l = response.body().contentLength();
                            int code = response.code();
                            Log.e(TAG, "断点下载长度============: " + l + "===========" + code);
                        } catch (IOException e) {
//                            e.printStackTrace();
                            Log.e(TAG, "请求异常: " + e.getMessage());
                        }
                    }
                }
        ).start();

    }

    private void moe() {
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                continue;
            }
            Log.e(TAG, "moe: " + i);
        }
        Log.e(TAG, "我是循环外面的moe: ");
    }

    private void initView() {
        bt = (Button) findViewById(R.id.bt);

        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
//                UrlConn();
//                getDate();
//                meoth();
//                while (!num){
//
//                }
//                Log.e(TAG, "代码执行完毕: ");
//                sss();
//                threadIm();
                break;
        }
    }

    private void threadIm() {
         /*new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                handler2.sendEmptyMessage(10);
                Looper.prepare();
                handler1 = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == 20) {
                            Log.e(TAG, "handleMessage: 接收到线程2的反馈消息");
                        }
                    }
                };
                Looper.loop();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler2 = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == 10) {
                            Log.e(TAG, "handleMessage: 接收到线程1发来的消息");
                            SystemClock.sleep(3000);
                            handler1.sendEmptyMessage(20);
                        }
                    }
                };
                Looper.loop();
            }
        }).start();*/

        Thread t1 = new Thread(r1);
        t1.setName("a");
        Thread t2 = new Thread(r1);
        t2.setName("b");
        Thread t3 = new Thread(r1);
        t3.setName("c");
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        t3.start();
        t1.start();


    }

    Runnable r1 = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG, "我线程" + Thread.currentThread().getName());
        }
    };


    private void method() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpUtil.getInstance().Toast(UIActivity.this);
                            Thread.sleep(5000);
                            num = true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();

    }

    private void sss() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String appPackageName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        String className = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        Log.e("gxb---AppUpdateActivity", "当前运行在最上面的应用包名：" + appPackageName);
        Log.e("gxb---AppUpdateActivity", "当前运行在最上面的Activity的类名：" + className);
        if (appPackageName.equals("com.yangyong.didi2")) {
            if (!className.equals("com.mobilewise.gameco.UserLoginActivity")) {//add by gxb 20190711 end
                openBrowser(this, "www.baidu.com");//add by gxb 20190812
            }
        }
    }

    public static void openBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            Log.e("gxb", "ComponentName---" + componentName.getClassName());
            /**
             * update by gxb 20190820
             * 添加了addFlags，因为是用context进行跳转Activity，防止程序会崩溃，需要添加addFlags
             */
            context.startActivity(Intent.createChooser(intent, "请选择浏览器").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else {
            Toast.makeText(context, "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }

}
