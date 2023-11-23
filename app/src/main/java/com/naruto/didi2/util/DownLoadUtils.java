package com.naruto.didi2.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naruto.didi2.bean.ThreadInfo;
import com.naruto.didi2.dbdao.DownLoadDao;
import com.naruto.didi2.fileload.ReqApkDownObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 断点续传下载器 class
 *
 * @author yangyong
 * @date 2020/7/2/0002
 */

public class DownLoadUtils {
    private File rootFile;//文件的路径
    //    private File file;//文件
//    private long downLoadSize;//下载文件的长度
//    private ThreadPoolExecutor executor;// 线程池
    private boolean isDown = false; //是否已经下载过了（下载后点击暂停） 默认为false
    //    private String name; //名称
//    private String path;// 下载的网址
//    private RandomAccessFile raf; // 读取写入IO方法
//    private long totalSize = 0;
//    private MyThread thread;//线程
    //    private Handler handler;//Handler 方法
//    private IProgress progress;// 下载进度方法，内部定义的抽象方法
    private DownLoadDao mDownLoadDao;
    private static final DownLoadUtils instance = new DownLoadUtils();
    private ExecutorService executor;
    //    private ThreadInfo mThreadInfo;
//    private Context mContext;
    private int CORE_THREAD = 2;

    private DownLoadUtils() {
//        mContext = context;
//        this.path = path;
//        this.progress = progress;
//        this.handler = new Handler();
//        this.name = path.substring(path.lastIndexOf("/") + 1);
        rootFile = FileUtils.getRootFile();
        mDownLoadDao = DownLoadDao.getInstance();
//        executor = new ThreadPoolExecutor(5, 5, 50, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3000));
        //替换为cache线程池
        executor = Executors.newCachedThreadPool();
    }

    public static DownLoadUtils getInstance() {
        return instance;
    }

    /**
     * 线程开启方法
     */
    public void start(String path, long startSize, long endSize) {
//        if (thread == null) {
//        MyThread thread = new MyThread(path);
        MyThread thread = new MyThread(path, startSize, endSize);
        isDown = true;
        executor.execute(thread);
//        }
    }

    /**
     * 线程开启方法
     */
    public void start(String path) {
        MyThread thread = new MyThread(path);
        isDown = true;
        executor.execute(thread);
    }

    /**
     * 线程停止方法
     */
    public void stop() {
//        if (thread != null) {
        isDown = false;
//            executor.remove(thread);
//            thread = null;
//        }
    }

    /**
     * 重置下载
     */
    public void reSet() {
        mDownLoadDao.deleteAll();
    }

    /**
     * 自定义线程
     */
    class MyThread extends Thread {
        String path;
        long startSize;
        long endSize;

        public MyThread(String path) {
            this.path = path;
        }

        public MyThread(String path, long startSize, long endSize) {
            this.path = path;
            this.startSize = startSize;
            this.endSize = endSize;
        }

        @Override
        public void run() {
            super.run();
            downLoadFile(path);
//            downLoadFile(path, startSize, endSize, null);
        }
    }

    /**
     * 这就是下载方法
     */
    private void downLoadFile(String path) {

        //构建标准版应用商店app下载
//        String req = createReq();


        LogUtils.e("start download");
//        String name = path.substring(path.lastIndexOf("/") + 1);
        String name="111.apk";
        ThreadInfo mThreadInfo;
        long totalSize;
        File file = null;
        long downLoadSize = 0;

        Response response = null;
        InputStream ins = null;
        RandomAccessFile raf = null;

//        BufferedInputStream bis = null;
        //由文件判断改为数据库判断
        boolean select = mDownLoadDao.exists(path);
        try {


            totalSize = getContentLength(path,"");//获取文件的大小
            if (!select) {
                mThreadInfo = new ThreadInfo(path, 0, totalSize, 0);
                mDownLoadDao.addThread(mThreadInfo);
                //未下载过
                LogUtils.e("文件未下载过");
                file = new File(rootFile, name); //很正常的File() 方法
                raf = new RandomAccessFile(file, "rwd");//实例化一下我们的RandomAccessFile()方法
            } else {
                //下载过
                file = new File(rootFile, name);
                //判断文件是否存在，不存在则重新下载
                if (!file.exists()) {
                    LogUtils.e("文件被删除，重新下载app");
                    mThreadInfo = new ThreadInfo(path, 0, totalSize, 0);
                    mDownLoadDao.update(mThreadInfo);
                    raf = new RandomAccessFile(file, "rwd");//实例化一下我们的RandomAccessFile()方法
                } else {
                    //                downLoadSize = file.length();// 文件的大小
                    mThreadInfo = mDownLoadDao.select(path);
                    int t_id = mThreadInfo.getT_id();
                    downLoadSize = mThreadInfo.getFinished();
                    LogUtils.e("文件下载过，从已下载进度下载：" + downLoadSize + "==t_id" + t_id);

                    raf = new RandomAccessFile(file, "rwd");
                    raf.seek(downLoadSize);
                }
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

//            FormBody.Builder builder = new FormBody.Builder();
//            builder.add("json",req);

            Request request = new Request.Builder().url(path)
                    .addHeader("Range", "bytes=" + downLoadSize + "-" + totalSize)
//                    .post(builder.build())
                    .addHeader("Connection", "Keep-Alive")
                    .build();
            response = client.newCall(request).execute();
            ins = response.body().byteStream();
//            bis = new BufferedInputStream(ins);
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
                if (downLoadSize >= totalSize) {
                    LogUtils.e("下载完成--");
                    mThreadInfo.setFinished(downLoadSize);
                    mDownLoadDao.update(mThreadInfo);
//                    progress.onProgress(100);
                    if (AppUtil.getInstance().mProCallBack != null) {
                        AppUtil.getInstance().mProCallBack.updateProgress(100);
                    }
                    break;
                }
                if (System.currentTimeMillis() - endTime > 1000) {
                    endTime = System.currentTimeMillis();
                    final double dd = downLoadSize / (totalSize * 1.0);
                    DecimalFormat format = new DecimalFormat("#0.00");
                    String value = format.format((dd * 100)) + "%";//计算百分比
                    LogUtils.e("==================" + name + "==" + value);
                    mThreadInfo.setFinished(downLoadSize);
                    mDownLoadDao.update(mThreadInfo);
//                    handler.post(new Runnable() {//通过Handler发送消息到UI线程，更新
//                        @Override
//                        public void run() {
//                            mThreadInfo.setFinished(downLoadSize);
//                            mDownLoadDao.update(mThreadInfo);
//                            progress.onProgress((int) (dd * 100));
//                        }
//                    });
                    //使用接口回调方式更新进度条
                    if (AppUtil.getInstance().mProCallBack != null) {
                        double p = dd * 100;
                        AppUtil.getInstance().mProCallBack.updateProgress((int)p);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e("error:" + e.toString());
//            downLoadFile();
            e.printStackTrace();
        } finally {
//            if (bis != null) {
//                try {
//                    bis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String createReq() {
        String timestamp = String.valueOf(System.currentTimeMillis());
//        String[] temp = {timestamp, mFileInfo.token};
//        Arrays.sort(temp);
        //String signature = new SHA1Handler().getDigestOfString((temp[0]+temp[1]).getBytes());
//        String signature = SM3Handler.getDigestOfString(temp[0] + temp[1]);
//        List<NameValuePair2> pairs = new ArrayList<NameValuePair2>();
        ReqApkDownObject req = new ReqApkDownObject();
        req.signature = "e39da21a57f3b4edc3df90ce62edcab975405e6e29c83e362ac998bee6514be2";
        req.timestamp = timestamp;
        req.udid = "8813fc10c7af3abc01151c5424016f43a64dde77";
        req.content.appid ="211";
        req.content.startpoint = "0";
        return (String)toJsonObject(req);
    }

    //通过OkhttpClient获取文件的大小
    public long getContentLength(String url,String par) throws IOException {
//        OkHttpClient client = new OkHttpClient();
        OkHttpClient client = OkHttpUtil.getInstance().getHttpsClient2();

        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("json",par);

        Request request = new Request.Builder().url(url)
                .post(builder.build())
                .build();
        Response response = client.newCall(request).execute();
        long length = response.body().contentLength();
        LogUtils.e("获取到下载文件长度：" + length);
        response.close();
        return length;
    }

    public interface IProgress {
        void onProgress(int progress);
    }

    private void processDownload(String url, long length, IProgress callback) {
        //100    2    50    0-49   50-99
        long threadDownloadSize = length / CORE_THREAD;
        for (int i = 0; i < CORE_THREAD; i++) {
            long startSize = i * threadDownloadSize;
            long endSize = 0;
            if (i == CORE_THREAD - 1) {
                endSize = length - 1;
            } else {
                endSize = (i + 1) * threadDownloadSize - 1;
            }
            LogUtils.e("线程详细数据第" + i + "个线程   " + "startSize : " + startSize + "endSize : " + endSize);
//            sThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callback));
        }
    }

    /**
     * 这就是下载方法
     */
    public void downLoadFile(String path, long startSize, long endSize, IProgress callback) {
        LogUtils.e("start download");
        String name = path.substring(path.lastIndexOf("/") + 1);
        ThreadInfo mThreadInfo;
        long totalSize = endSize;
        File file = null;
        long downLoadSize = startSize;

        Response response = null;
        InputStream ins = null;
        RandomAccessFile raf = null;
        //由文件判断改为数据库判断
//        boolean select = mDownLoadDao.exists(path);
        boolean select = false;
        try {

//            totalSize = getContentLength(path);//获取文件的大小
            if (!select) {
//                mThreadInfo = new ThreadInfo(path, (int) startSize, totalSize, 0);
//                mDownLoadDao.addThread(mThreadInfo);
                //未下载过
//                LogUtils.e("文件未下载过");
                file = new File(rootFile, name); //很正常的File() 方法
                raf = new RandomAccessFile(file, "rwd");//实例化一下我们的RandomAccessFile()方法

                raf.seek(downLoadSize);
            } else {
                //下载过
                file = new File(rootFile, name);
                //判断文件是否存在，不存在则重新下载
                if (!file.exists()) {
                    LogUtils.e("文件被删除，重新下载app");
                    mThreadInfo = new ThreadInfo(path, 0, totalSize, 0);
                    mDownLoadDao.update(mThreadInfo);
                    raf = new RandomAccessFile(file, "rwd");//实例化一下我们的RandomAccessFile()方法
                } else {
                    //                downLoadSize = file.length();// 文件的大小
                    mThreadInfo = mDownLoadDao.select(path);
                    int t_id = mThreadInfo.getT_id();
                    downLoadSize = mThreadInfo.getFinished();
                    LogUtils.e("文件下载过，从已下载进度下载：" + downLoadSize + "==t_id" + t_id);

                    raf = new RandomAccessFile(file, "rwd");
                    raf.seek(downLoadSize);
                }
            }
            if (downLoadSize == totalSize + 1) {// 判断是否下载完成
                //已经下载完成
                LogUtils.e("文件已下载");
                return;
            }

            OkHttpClient client = new OkHttpClient();
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .connectTimeout(20,TimeUnit.SECONDS)
//                    .readTimeout(20,TimeUnit.SECONDS)
//                    .writeTimeout(20,TimeUnit.SECONDS).build();

            Request request = new Request.Builder().url(path).
                    addHeader("Range", "bytes=" + downLoadSize + "-" + totalSize)
                    .addHeader("Connection", "Keep-Alive")

                    .build();

            response = client.newCall(request).execute();
            ins = response.body().byteStream();
            //上面的就是简单的OKHttp连接网络，通过输入流进行写入到本地
            int len = 0;
            byte[] by = new byte[1024 * 8];
            long endTime = System.currentTimeMillis();
            while ((len = ins.read(by)) != -1) {//如果下载没有出错并且已经开始下载，循环进行以下方法
//                if (!isDown) {
//                    //暂停下载记录下载位置
//                    mThreadInfo.setFinished(downLoadSize);
//                    mDownLoadDao.update(mThreadInfo);
//                    LogUtils.e("暂停下载，保存下载进度：" + downLoadSize);
//                    break;
//                }
                raf.write(by, 0, len);
                downLoadSize += len;
                //每两秒更新一下进度
                if (downLoadSize == totalSize + 1) {
//                    mThreadInfo.setFinished(downLoadSize);
//                    mDownLoadDao.update(mThreadInfo);
                    LogUtils.e("分段下载完成1");
//                    progress.onProgress(100);
                    break;
                }
               /* if (System.currentTimeMillis() - endTime > 1000) {
                    endTime = System.currentTimeMillis();
                    final double dd = downLoadSize / (totalSize * 1.0);
                    DecimalFormat format = new DecimalFormat("#0.00");
                    String value = format.format((dd * 100)) + "%";//计算百分比
                    LogUtils.e("==================" + name + "==" + value);
                    mThreadInfo.setFinished(downLoadSize);
                    mDownLoadDao.update(mThreadInfo);
//                    handler.post(new Runnable() {//通过Handler发送消息到UI线程，更新
//                        @Override
//                        public void run() {
//                            mThreadInfo.setFinished(downLoadSize);
//                            mDownLoadDao.update(mThreadInfo);
//                            progress.onProgress((int) (dd * 100));
//                        }
//                    });
                }*/
            }
//            LogUtils.e("分段下载完成:");
        } catch (Exception e) {
            LogUtils.e("error:" + e.toString());
//            downLoadFile();
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public Object toJsonObject(Object object) {
        // TODO Auto-generated method stub
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        String obj = gson.toJson(object);
        return obj;
    }

}
