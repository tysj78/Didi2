package com.naruto.didi2.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.naruto.didi2.MyApp;
import com.naruto.didi2.constant.Constants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yangyong on 2019/5/7/0007.
 */

public class OkHttpUtil {
    private static OkHttpUtil instance = null;
    private OkHttpClient okHttpClient;
    private Handler handler;

//    private Context mContext;

    private OkHttpUtil() {
        //创建OkHttpClient请求对象
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/okcache");
        if (!file.exists())
            file.mkdirs();
//        okHttpClient = getOkHttpClient();
//        okHttpClient = getTrusClient();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpUtil getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtil.class) {
                if (instance == null) {
                    instance = new OkHttpUtil();
                }
            }
        }
        return instance;
    }

    private OkHttpClient getOkHttpClient() throws Exception {
        //跳过ssl证书校验
//        if (okHttpClient == null) {
        if (true) {
            TrustManager[] xtm = {new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    Log.e(Constants.TAG, "开始验证=====");
                    boolean verifyResult = false;
                    try {
                        InputStream certInputStream = new BufferedInputStream(MyApp.getContext().getAssets().open("tomcat.cer"));
                        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                        final X509Certificate serverCertificate = (X509Certificate) certificateFactory
                                .generateCertificate(certInputStream);
                        if (chain == null) {
                            throw new IllegalArgumentException("checkServerTrusted x509Certificates is null ");
                        }
                        if (chain.length < 0) {
                            throw new IllegalArgumentException("checkServerTrusted x509Certificates is zero ");
                        }

                        for (X509Certificate cert : chain) {
                            String string = cert.getPublicKey().toString();
                            Log.e(Constants.TAG, "---服务器证公钥：" + string);
                            String string2 = serverCertificate.getPublicKey().toString();
                            Log.e(Constants.TAG, "---本地证书公钥：" + string2);
                            cert.checkValidity();
                            cert.verify(serverCertificate.getPublicKey());
                            if (string.equals(string2)) {
                                verifyResult = true;
                                break;
                            }
                        }
                    } catch (Exception e) {
                        Log.e(Constants.TAG, "---验证异常" + e.toString());
                        throw new CertificateException("证书校验异常");
                    }
                    if (!verifyResult) {
                        throw new CertificateException("证书校验失败");
                    }
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    X509Certificate[] x509Certificates = new X509Certificate[1];
                    return x509Certificates;
                }
            }};

            X509TrustManager trustManager = (X509TrustManager) xtm[0];

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());

            HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/okcache");
//            okHttpClient = new OkHttpClient.Builder()
//                    .connectTimeout(8, TimeUnit.SECONDS)
//                    .readTimeout(8, TimeUnit.SECONDS)
//                    .writeTimeout(8, TimeUnit.SECONDS)
//                    .addInterceptor(new LoggingInterceptor())
//                    .cache(new Cache(file, 1024 * 1024 * 10))
//                    .sslSocketFactory(sslContext.getSocketFactory())
//                    .hostnameVerifier(DO_NOT_VERIFY)
//                    .build();

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            okHttpClient = builder.connectTimeout(8, TimeUnit.SECONDS)
                    .readTimeout(8, TimeUnit.SECONDS)
                    .writeTimeout(8, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptor())
                    .cache(new Cache(file, 1024 * 1024 * 10))
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .hostnameVerifier(DO_NOT_VERIFY)
                    .build();
        }
//        }
        return okHttpClient;
    }


    public OkHttpClient createOKHttpClient() {
        TrustManager[] trustManagers = {new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.e(Constants.TAG, "开始验证=====mdm_serverTest");
                boolean verifyResult = false;
                try {
//                    InputStream certInputStream = new BufferedInputStream(MyApp.getContext().getAssets().open("mdm_serverTest.cer"));
//                    CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//                    final X509Certificate serverCertificate = (X509Certificate) certificateFactory
//                            .generateCertificate(certInputStream);
                    if (chain == null) {
                        throw new IllegalArgumentException("checkServerTrusted x509Certificates is null ");
                    }
                    if (chain.length < 0) {
                        throw new IllegalArgumentException("checkServerTrusted x509Certificates is zero ");
                    }

                    for (X509Certificate cert : chain) {
                        String string = cert.getPublicKey().toString();
                        Log.e(Constants.TAG, "---服务器证公钥：" + string);
//                        String string2 = serverCertificate.getPublicKey().toString();
//                        Log.e(Constants.TAG, "---本地证书公钥：" + string2);
//                        cert.checkValidity();
//                        cert.verify(serverCertificate.getPublicKey());
//                        if (string.equals(string2)) {
//                            verifyResult = true;
//                            break;
//                        }
                    }
                } catch (Exception e) {
                    LogUtils.exception(e);
                    throw new CertificateException("证书校验异常");
                }
//                if (!verifyResult) {
//                    throw new CertificateException("证书校验失败");
//                }
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
        try {
            // 为请求通TLS协议，生成SSLContext对象
            // 初始化SSLContext
            SSLContext sslctxt = SSLContext.getInstance("TLS");
            sslctxt.init(null, new TrustManager[]{trustManager}, new java.security.SecureRandom());
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.sslSocketFactory(sslctxt.getSocketFactory(), trustManager);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            //zq 20180321 OkHttp可以不用我们管理Cookie，自动携带，保存和更新Cookie
            final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
            okHttpClient = builder
                    .retryOnConnectionFailure(true)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                            cookieStore.put(httpUrl.host(), list);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                            List<Cookie> cookies = cookieStore.get(httpUrl.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))//防止应用商店下载应用报错：StreamResetException，导致应用下载失败
                    .build();
        } catch (Exception e) {
            LogUtils.exception(e);
        }
        return okHttpClient;
    }

    /**
     * 信任所有证书
     *
     * @return
     */

    public OkHttpClient getHttpsClient2() {
        OkHttpClient.Builder okhttpClient = new OkHttpClient().newBuilder();
        //主机名校验，
        okhttpClient.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession sslSession) {
                return true;
            }
        });
        //创建信任管理器
        TrustManager[] trustAllCerts = new TrustManager[]{

                new X509TrustManager() {//匿名类实现X509TrustManager接口
                    @Override
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] x509Certificates,
                            String s) throws java.security.cert.CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] x509Certificates,
                            String s) throws java.security.cert.CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
        try {
            //获取SSL上下文对象
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            okhttpClient.sslSocketFactory(sslContext.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return okhttpClient.build();
    }

    /**
     * 信任指定证书
     *
     * @return
     */
    private OkHttpClient getHttpsClient() {
        SSLContext sslContext = null;
        OkHttpClient.Builder okhttpClient = new OkHttpClient().newBuilder();
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

            //方法一：将证书放到assets文件夹里面然后获取
            InputStream certificates = MyApp.getContext().getAssets().open("didi2.cer");

            //方法二：为了不将证书打包到apk里面了，可以将证书内容定义成字符串常量，再将字符串转为流的形式
            //String wenzhibinBook = "拷贝证书里的内容到此处";
            //InputStream certificates = new ByteArrayInputStream(wenzhibinBook.getBytes("UTF-8"));

            // 创建秘钥，添加证书进去
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
//            String certificateAlias = Integer.toString(0);


            String certificateAlias = "didi2";
            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificates));

            // 创建信任管理器工厂并初始化秘钥，//让自己的证书受信任
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);


            //创建信任管理器
//            TrustManager[] trustAllCerts = new TrustManager[]{

            X509TrustManager x509TrustManager = new X509TrustManager() {//匿名类实现X509TrustManager接口
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    LogUtils.e("checkClientTrusted:");
                    //关于该回调函数的解释：校验客户端证书，由于我们app即为客户端，所以不会用到此方法
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    LogUtils.e("checkServerTrusted:");
                    try {
                        if (x509Certificates == null) {
                            throw new IllegalArgumentException("checkServerTrusted x509Certificates is null ");
                        }
                        if (x509Certificates.length < 0) {
                            throw new IllegalArgumentException("checkServerTrusted x509Certificates is null ");
                        }

                        InputStream certInputStream = new BufferedInputStream(MyApp.getContext().getAssets().open("didi2.cer"));
                        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

                        X509Certificate serverCertificate = (X509Certificate) certificateFactory
                                .generateCertificate(certInputStream);

                        for (X509Certificate cert : x509Certificates) {
                            String string = cert.getPublicKey().toString();
                            LogUtils.e("yy---服务器证公钥：" + string);
                            String string2 = serverCertificate.getPublicKey().toString();
                            LogUtils.e("yy---本地证书公钥：" + string2);
                            //校验证书是否过期
                            cert.checkValidity();
                            //是通过公钥获取私钥来校验的吗
                            cert.verify(serverCertificate.getPublicKey());
//                             if (string.equals(string2)) {
//                             verifyResult=true;
//                             break;
//                             }
                        }
                    } catch (Exception e) {
                        LogUtils.e("Exception: " + e.toString());
                        throw new CertificateException(e.toString());
                    }
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    //返回受信任的X509证书数组
                    return new X509Certificate[]{};
                }
            };
//            };


            //获取SSL上下文对象，并初始化信任管理器
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
//            sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());

            //okhttp设置sokect工厂，并校验主机名
            okhttpClient.sslSocketFactory(sslContext.getSocketFactory());
            okhttpClient.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession sslSession) {
                    return true;
                }
            });
        } catch (Exception e) {
            LogUtils.e("https证书验证失败：" + e.toString());
            e.printStackTrace();
        }
        return okhttpClient
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * 对外提供的获取支持自签名的okhttp客户端
     *
     * @return 支持自签名的客户端
     */
    public OkHttpClient getTrusClient() {
        X509TrustManager trustManager = null;
        SSLSocketFactory sslSocketFactory = null;
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        try {
            InputStream certificates = MyApp.getContext().getAssets().open("didi2.cer");

            trustManager = trustManagerForCertificates(certificates);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            //使用构建出的trustManger初始化SSLContext对象
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            //获得sslSocketFactory对象
            sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (GeneralSecurityException e) {
            LogUtils.e("证书验证失败：" + e.toString());
//            throw new RuntimeException(e);
        } catch (IOException e) {
            LogUtils.e("证书验证失败：" + e.toString());
        }
        return new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * 获去信任自签证书的trustManager
     *
     * @param in 自签证书输入流
     * @return 信任自签证书的trustManager
     * @throws GeneralSecurityException
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        //通过证书工厂得到自签证书对象集合
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }
        //为证书设置一个keyStore
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        //将证书放入keystore中
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }
        // Use it to build an X509 trust manager.
        //使用包含自签证书信息的keyStore去构建一个X509TrustManager
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);


        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(null, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * get请求
     * 参数1 url
     * 参数2 回调Callback
     */

    public void doGet(String url, final DataCallBack callback) {
        try {
            //创建Request
            Request request = new Request.Builder().url(url)
                    .build();
            //得到Call对象
            okhttp3.Call call = createOKHttpClient().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runUI(callback, false, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int code = response.code();
                    runUI(callback, true, "code:" + code + "  " + response.body().string());
                }
            });
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    private void runUI(final DataCallBack callback, final boolean isOK, final String json) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                setCallBack(callback);
                if (callBack != null) {
                    if (isOK) {
                        callBack.onSuccess(json);
                    } else {
                        callBack.onFailure(json);
                    }
                }
            }
        });
    }

    /**
     * post请求
     * 参数1 url
     * 参数2 回调Callback
     */

    public void doPost(String url, Map<String, String> params, final DataCallBack callback) {
        try {
            //3.x版本post请求换成FormBody 封装键值对参数
            FormBody.Builder builder = new FormBody.Builder();
            //遍历集合
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
            //创建Request
            Request request = new Request.Builder().url(url).post(builder.build()).build();


            okhttp3.Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runUI(callback, false, e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runUI(callback, true, response.body().string());
                }
            });
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.toString());
        }
    }

    public void uploadFile() {
        try {
//            String url = "https://api.crazymen.cn/api_sctp/";
            String url = "http://shenying.5gzvip.idcfengye.com/shenying/ImageUpload";
            String s = Environment.getExternalStorageDirectory() + "/pic/321.jpg";
            File file = new File(s);
            if (!file.exists()) {
                LogUtils.e("file not exist");
                return;
            }
//            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "321.jpg", fileBody)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Response response = getOkHttpClient().newCall(request).execute();
//            int code = response.code();
            String s1 = response.body().string();
            LogUtils.e("back datas :" + s1);
        } catch (Exception e) {
            LogUtils.e("error :" + e.toString());
        }
    }

    public void upLoadMultiImage(String url, String[] paths) {
        //参数类型
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();
//        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        MultipartBody.Builder builder = new MultipartBody.Builder();
//遍历map中所有参数到builder
//        for (String key : map.keySet()) {
//            builder.addFormDataPart(key, map.get(key));
//        }
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        for (String path : paths) {
            builder.addFormDataPart("upload", null, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
        }
        //构建请求体
        RequestBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(url)//地址
                .post(requestBody)//添加请求体
                .build();
        //发送异步请求，同步会报错，Android4.0以后禁止在主线程中进行耗时操作
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("response = " + response.body().string());
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    public void uploadImage(String url, List<String> imageList) {
        try {
            if (imageList == null || imageList.size() == 0) {
                return;
            }
            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
            //OkHttpClient okHttpClient = new OkHttpClient();
            //MultipartBody.Builder builder = new MultipartBody.Builder();
            OkHttpClient okHttpClient = new OkHttpClient();
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            //mHashMap是图片path的集合
            long start = System.currentTimeMillis();
            for (int i = 0; i < imageList.size(); i++) {
                File f = new File(imageList.get(i));
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    Bitmap bm = BitmapFactory.decodeFile(f.getAbsolutePath(), options);
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, new FileOutputStream(f));
                    bm.recycle();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                builder.addFormDataPart("img", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
            }
            long currentTimeMillis = System.currentTimeMillis();
            LogUtils.e("压缩结束:" + (currentTimeMillis - start));
//        builder.addFormDataPart("type", committype);
//        builder.addFormDataPart("id", spotid);
//        builder.addFormDataPart("cont", txt.getText().toString());
//        builder.addFormDataPart("userid", "2");
    /*        MultipartBody requesBody = builder.build();
//        RequestBody body = new FormBody.Builder()//这里跟后台协商的接口有关
//                .add("type", "sd")
//                .add("id", spotid)
//                .add("cont", txt.getText().toString())
//                .add("userid", "2")
//                .build();
            Request request = new Request.Builder().url(url).post(requesBody).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }

                @Override
                public void onFailure(Call call, IOException e) {

                }
            });*/
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }


    //zq 20180320 通过标记来取消指定的OKHttp请求 未验证过效果

    public synchronized void cancelCallByTag(String tag) {
//        if (client == null) {
//            client = okHttpClient;
//        }
        Dispatcher dispatcher = okHttpClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }


    public DataCallBack callBack;

    public interface DataCallBack {
        void onSuccess(String s);

        void onFailure(String f);
    }

    public void setCallBack(DataCallBack callBack) {
        this.callBack = callBack;
    }

    public void Toast(final Context context) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "获取imserver ip和port失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            LogUtils.e(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            LogUtils.e(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}
