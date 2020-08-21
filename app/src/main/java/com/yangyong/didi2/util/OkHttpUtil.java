package com.yangyong.didi2.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.yangyong.didi2.Constants;
import com.yangyong.didi2.MyApp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yangyong on 2019/5/7/0007.
 */

public class OkHttpUtil {
    private static volatile OkHttpUtil util = null;
    private OkHttpClient okHttpClient;
    public static boolean isFirst = true;
    private static Context mContext;

    private OkHttpUtil() {
        //创建OkHttpClient请求对象
        mContext = MyApp.mContext;
//        okHttpClient = getOkHttpClient();
//        Log.e(Constants.TAG, "=============: " + okHttpClient);
    }

    public static OkHttpUtil getInstance() {
        if (util == null) {
            synchronized (OkHttpUtil.class) {
                if (util == null) {
                    util = new OkHttpUtil();
                }
            }
        }
        return util;
    }

    private OkHttpClient getOkHttpClient() throws Exception {
        //跳过ssl证书校验
//        if (okHttpClient == null) {
        if (true) {
            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    Log.e(Constants.TAG, "开始验证=====");
                    boolean verifyResult = false;
                    try {
                        InputStream certInputStream = new BufferedInputStream(mContext.getAssets().open("tomcat.cer"));
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
//                            if (string.equals(string2)) {
//                                verifyResult=true;
//                                break;
//                            }
                        }
                    } catch (Exception e) {
                        Log.e(Constants.TAG, "---验证异常" + e.toString());
//                        throw new CertificateException("证书校验异常");
                    }
//                    if (!verifyResult) {
//                        throw new CertificateException("证书校验失败");
//                    }
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    X509Certificate[] x509Certificates = new X509Certificate[1];
                    return x509Certificates;
                }
            };

            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");

                sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };


            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(8, TimeUnit.SECONDS)
                    .readTimeout(8, TimeUnit.SECONDS)
                    .writeTimeout(8, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .hostnameVerifier(DO_NOT_VERIFY)
                    //.cache(new Cache(sdcache, cacheSize))
                    .build();
        }
        return okHttpClient;
    }

    /**
     * get请求
     * 参数1 url
     * 参数2 回调Callback
     */

    public void doGet(String url, final DataCallBack callback) {
        try {
            //创建Request
            Request request = new Request.Builder().url(url).build();
            //得到Call对象
            okhttp3.Call call = getOkHttpClient().newCall(request);
            //执行异步请求
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
            LogUtils.e("Exception: " + e.toString());
        }
    }

    private void runUI(final DataCallBack callback, final boolean isOK, final String json) {
        Handler handler = new Handler(Looper.getMainLooper());
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


//            okhttp3.Call call = okHttpClient.newCall(request);
            okhttp3.Call call = getOkHttpClient().newCall(request);
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
            String url = "/upload";
            File file = new File("/sdcard/emm.zip");
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            MultipartBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("uploadfile", "emm.zip", fileBody)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Response response = getOkHttpClient().newCall(request).execute();
            int code = response.code();
            LogUtils.e("back code :" + code);
        } catch (Exception e) {
            LogUtils.e("error :" + e.toString());
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
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "获取imserver ip和port失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
