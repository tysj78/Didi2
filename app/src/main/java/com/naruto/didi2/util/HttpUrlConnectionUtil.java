package com.naruto.didi2.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by yangyong on 2019/9/2/0002.
 */

public class HttpUrlConnectionUtil {
    public static Bitmap getBitmap(final String url) {
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();//得到网络访问对象
            connection.setRequestMethod("GET");//设置请求方式
            connection.setConnectTimeout(5000);//设置超时时间
            int code = connection.getResponseCode();
            if (code == 200) {//正常响应
                InputStream is = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postData(String url, String pram) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url1 = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) url1.openConnection();//得到网络访问对象
            // 设置连接超时时间
            conn.setConnectTimeout(5 * 1000);
            // 设置从主机读取数据超时
            conn.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            conn.setDoOutput(true);
            // 设置请求允许输入 默认是true
            conn.setDoInput(true);
            // Post请求不能使用缓存
            conn.setUseCaches(false);
            // 设置为Post请求
            conn.setRequestMethod("POST");
            // 设置本次连接是否自动处理重定向
            conn.setInstanceFollowRedirects(true);
            // 配置请求Content-Type
            conn.setRequestProperty("contentType", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("Content-Length", String.valueOf(postData.length));

            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            conn.setSSLSocketFactory(ssf);

            // 当有数据需要提交时
            if (null != pram) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(pram.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    private static class MyX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
