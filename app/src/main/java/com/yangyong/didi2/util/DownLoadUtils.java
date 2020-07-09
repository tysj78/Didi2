package com.yangyong.didi2.util;

import android.content.Context;
import android.os.Handler;

import com.yangyong.didi2.bean.ThreadInfo;
import com.yangyong.didi2.dbdao.DownLoadDao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/7/2/0002
 */

public class DownLoadUtils {
    private File rootFile;//文件的路径
    private File file;//文件
    private long downLoadSize;//下载文件的长度
    private ThreadPoolExecutor executor;// 线程池
    private boolean isDown = false; //是否已经下载过了（下载后点击暂停） 默认为false
    private String name; //名称
    private String path;// 下载的网址
    private RandomAccessFile raf; // 读取写入IO方法
    private long totalSize = 0;
    private MyThread thread;//线程
    private Handler handler;//Handler 方法
    private IProgress progress;// 下载进度方法，内部定义的抽象方法
    private final DownLoadDao mDownLoadDao;
    private ThreadInfo mThreadInfo;
    private Context mContext;

    public DownLoadUtils(Context context, String path, IProgress progress) {
        mContext=context;
        this.path = path;
        this.progress = progress;
        this.handler = new Handler();
        this.name = path.substring(path.lastIndexOf("/") + 1);
        rootFile = FileUtils.getRootFile();
        mDownLoadDao = new DownLoadDao(context);
        executor = new ThreadPoolExecutor(5, 5, 50, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3000));
    }

    /**
     * 线程开启方法
     */
    public void start() {
        if (thread == null) {
            thread = new MyThread();
            isDown = true;
            executor.execute(thread);
        }
    }

    /**
     * 线程停止方法
     */
    public void stop() {
        if (thread != null) {
            isDown = false;
            executor.remove(thread);
            thread = null;
        }
    }

    /**
     * 自定义线程
     */
    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            downLoadFile();
        }
    }

    /**
     * 这就是下载方法
     */
    private void downLoadFile() {
        LogUtils.e("start download");
        Response response = null;
//        File apkFile = new File(rootFile, name); //很正常的File() 方法
        //由文件判断改为数据库判断
        boolean select = mDownLoadDao.exists(path);
        try {
            totalSize = getContentLength(path);//获取文件的大小
            if (!select) {
                mThreadInfo = new ThreadInfo(path, 0, totalSize, 0);
                mDownLoadDao.addThread(mThreadInfo);
                //未下载过
                LogUtils.e("文件未下载过");
                file = new File(rootFile, name); //很正常的File() 方法
                raf = new RandomAccessFile(file, "rwd");//实例化一下我们的RandomAccessFile()方法
            } else {
                //下载过
                if (file == null) {
                    file = new File(rootFile, name);
                }
//                downLoadSize = file.length();// 文件的大小
                mThreadInfo = mDownLoadDao.select(path);
                int t_id = mThreadInfo.getT_id();
                downLoadSize = mThreadInfo.getFinished();
                LogUtils.e("文件下载过，从已下载进度下载：" + downLoadSize + "==t_id" + t_id);
                if (raf == null) {//判断读取是否为空
                    raf = new RandomAccessFile(file, "rwd");
                }
                raf.seek(downLoadSize);
            }
            if (downLoadSize == totalSize) {// 判断是否下载完成
                //已经下载完成
                LogUtils.e("文件已下载");
                return;
            }

            OkHttpClient client = new OkHttpClient();
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .connectTimeout(10,TimeUnit.SECONDS)
//                    .readTimeout(10,TimeUnit.SECONDS)
//                    .writeTimeout(10,TimeUnit.SECONDS).build();
            Request request = new Request.Builder().url(path).
                    addHeader("Range", "bytes=" + downLoadSize + "-" + totalSize)
                    .addHeader("Connection", "Keep-Alive")
                    .build();
            response = client.newCall(request).execute();
            InputStream ins = response.body().byteStream();
            //上面的就是简单的OKHttp连接网络，通过输入流进行写入到本地
            int len = 0;
            byte[] by = new byte[1024 * 8];
            long endTime = System.currentTimeMillis();
            while ((len = ins.read(by)) != -1) {//如果下载没有出错并且已经开始下载，循环进行以下方法
                if (!isDown) {
                    //暂停下载记录下载位置
                    mThreadInfo.setFinished(downLoadSize);
                    mDownLoadDao.update(mThreadInfo);
                    LogUtils.e("暂停下载，保存下载进度：" + downLoadSize);
                    break;
                }
                raf.write(by, 0, len);
                downLoadSize += len;
                //每两秒更新一下进度
                if (downLoadSize == totalSize) {
                    mThreadInfo.setFinished(downLoadSize);
                    mDownLoadDao.update(mThreadInfo);
                    progress.onProgress(100);
                    break;
                }
                if (System.currentTimeMillis() - endTime > 1000) {
                    endTime = System.currentTimeMillis();
                    final double dd = downLoadSize / (totalSize * 1.0);
                    DecimalFormat format = new DecimalFormat("#0.00");
                    String value = format.format((dd * 100)) + "%";//计算百分比
                    LogUtils.e("==================" + value);
                    handler.post(new Runnable() {//通过Handler发送消息到UI线程，更新
                        @Override
                        public void run() {
                            mThreadInfo.setFinished(downLoadSize);
                            mDownLoadDao.update(mThreadInfo);
                            progress.onProgress((int) (dd * 100));
                        }
                    });
                }
            }
        } catch (Exception e) {
            LogUtils.e("error:" + e.toString());
            downLoadFile();
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }

    }

    //通过OkhttpClient获取文件的大小
    public long getContentLength(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        long length = response.body().contentLength();
        LogUtils.e("获取到下载文件长度：" + length);
        response.close();
        return length;
    }

    public interface IProgress {
        void onProgress(int progress);
    }
}
