package com.naruto.didi2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.activity.test.T2Activity;
import com.naruto.didi2.util.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private String downLoadUrl = "https://imtt.dd.qq.com/16891/apk/27DC0BD401D13E8744161DE683030401.apk";
    private String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private EditText main_ed;
    private ProgressBar main_pb;
    private TextView main_txt;
    private Button main_down;
    //下载所使用的线程数
    protected static final int threadCount = 3;
    //下载完毕的标记
    public static final int downloadOver = 1;
    //更新下载进度标记
    public static final int UPDATE_PROGRESS = 0;
    //当前累计下载的数据
    int curDownCount = 0;
    //当前活动的下载线程数
    protected static int activeThread;
    //加入消息处理机制
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case downloadOver:
                    Toast.makeText(DownLoadActivity.this, "文件已下载完毕！", Toast.LENGTH_LONG).show();
                    main_txt.setText("下载完成");
                    break;
                case UPDATE_PROGRESS:
                    //更新进度显示
                    main_txt.setText("当前进度：" + (main_pb.getProgress() * 100 / main_pb.getMax()) + "%");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        initView();


    }

    private void initView() {
        main_ed = (EditText) findViewById(R.id.main_ed);
        main_pb = (ProgressBar) findViewById(R.id.main_pb);
        main_txt = (TextView) findViewById(R.id.main_txt);
        main_down = (Button) findViewById(R.id.main_down);

        main_down.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                main_txt.setText("更新view");
            }
        }, 5000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_down:
//                downLoad();
                startActivity(new Intent(this, T2Activity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e("download onDestroy");
    }

    private void downLoad() {
        //获取下载资源的路径
//        final String path = main_ed.getText().toString().trim();
        //判断资源路径是否为空
        if (TextUtils.isEmpty(downLoadUrl)) {
            Toast.makeText(this, "请输入下载资源的路径", Toast.LENGTH_LONG).show();
            return;
        }
        //开启一个线程进行下载
        new Thread() {
            public void run() {
                try {
                    //构造URL地址
                    URL url = new URL(downLoadUrl);
                    //打开连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //设置请求超时的时间
                    conn.setConnectTimeout(5000);
                    //设置请求方式
                    conn.setRequestMethod("GET");
                    //获取相应码
                    int code = conn.getResponseCode();
                    if (code == 200) {//请求成功
                        //获取请求数据的长度
                        int length = conn.getContentLength();
                        //设置进度条的最大值
                        main_pb.setMax(length);
//                        //在客户端创建一个跟服务器文件大小相同的临时文件
                        RandomAccessFile raf = new RandomAccessFile(sdPath + "/apk/qq2019.apk", "rwd");
//                        //指定临时文件的长度
                        raf.setLength(length);
                        raf.close();
                        //假设3个线程去下载资源
                        //平均每一个线程要下载的文件的大小
                        int blockSize = length / threadCount;
                        for (int threadId = 1; threadId <= threadCount; threadId++) {
                            //当前线程下载数据的开始位置
                            int startIndex = blockSize * (threadId - 1);
                            //当前线程下载数据的结束位置
                            int endIndex = blockSize * threadId - 1;
                            //确定最后一个线程要下载数据的最大位置
                            if (threadId == threadCount) {
                                endIndex = length;
                            }
                            //显示下载数据的区间
                            Log.e(TAG, "线程【" + threadId + "】开始下载：" + startIndex + "---->" + endIndex);
                            //开启下载的子线程
                            new DownloadThread(downLoadUrl, threadId, startIndex, endIndex).start();
                            //当前下载活动的线程数加1
                            activeThread++;
                            Log.e(TAG, "当前活动的线程数：" + activeThread);
                        }

                    } else {//请求失败
                        System.out.println("服务器异常，下载失败！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("服务器异常，下载失败！");
                }
            }

            ;
        }.start();
    }

    /**
     * 下载文件的子线程 每一个文件都下载对应的数据
     *
     * @author YUANYUAN
     */
    public class DownloadThread extends Thread {
        private String path;
        private int threadId;
        private int startIndex;
        private int endIndex;

        /**
         * 构造方法
         *
         * @param path       下载文件的路径
         * @param threadId   下载文件的线程
         * @param startIndex 下载文件开始的位置
         * @param endIndex   下载文件结束的位置
         */
        public DownloadThread(String path, int threadId, int startIndex,
                              int endIndex) {
            this.path = path;
            this.threadId = threadId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {
            //构造URL地址
            try {

                File tempFile = new File(sdPath + "/" + threadId + ".txt");
                //检查记录是否存在,如果存在读取数据，设置真实下载开始的位置
                if (tempFile.exists()) {
                    FileInputStream fis = new FileInputStream(tempFile);
                    byte[] temp = new byte[1024];
                    int length = fis.read(temp);
                    //读取到已经下载的位置
                    int downloadNewIndex = Integer.parseInt(new String(temp, 0, length));
                    //计算出已经下载的数据长度
                    int areadyDown = downloadNewIndex - startIndex;
                    //累加已经下载的数据量
                    curDownCount += areadyDown;
                    //设置进度条已经下载的数据量
                    main_pb.setProgress(curDownCount);
                    //设置重新开始下载的开始位置
                    startIndex = downloadNewIndex;
                    fis.close();
                    //显示真实下载数据的区间
                    System.out.println("线程【" + threadId + "】真实开始下载数据区间：" + startIndex + "---->" + endIndex);
                }

                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                //设置请求属性，请求部分资源
                conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
                int code = conn.getResponseCode();
                Log.e(TAG, "响应码: " + code);
                if (code == 206) {//下载部分资源，正常返回的状态码为206
                    InputStream is = conn.getInputStream();//已经设置了请求的位置，所以返回的是对应的部分资源
                    //构建随机访问文件
                    RandomAccessFile raf = new RandomAccessFile(sdPath + "/apk/qq2019.apk", "rwd");
                    //设置 每一个线程随机写文件开始的位置
                    raf.seek(startIndex);
                    //开始写文件
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    //该线程已经下载数据的长度
                    int total = 0;

                    while ((len = is.read(buffer)) != -1) {//读取输入流
                        //记录当前线程已下载数据的长度
                        RandomAccessFile file = new RandomAccessFile(sdPath + "/" + threadId + ".txt", "rwd");
                        raf.write(buffer, 0, len);//写文件
                        total += len;//更新该线程已下载数据的总长度
                        System.out.println("线程【" + threadId + "】已下载数据：" + (total + startIndex));
                        //将已下载数据的位置记录写入到文件
                        file.write((startIndex + total + "").getBytes());
                        //累加已经下载的数据量
                        curDownCount += len;
                        //更新进度条【进度条的更新可以在非UI线程直接更新，具体见底层源代码】
                        main_pb.setProgress(curDownCount);

                        //更新下载进度
                        Message msg = Message.obtain();
                        msg.what = UPDATE_PROGRESS;
                        handler.sendMessage(msg);

                        file.close();
                    }
                    is.close();
                    raf.close();
                    //提示下载完毕
                    System.out.println("线程【" + threadId + "】下载完毕");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("线程【" + threadId + "】下载出现异常！！");
            } finally {
                //活动的线程数减少
                activeThread--;
                if (activeThread == 0) {
                    for (int i = 1; i <= threadCount; i++) {
                        File tempFile = new File(sdPath + "/" + i + ".txt");
                        tempFile.delete();
                    }
                    System.out.println("下载完毕，已清除全部临时文件");
                    //界面消息提示下载完毕
                    Message msg = new Message();
                    msg.what = downloadOver;
                    handler.sendMessage(msg);
                }
            }

        }
    }
}
