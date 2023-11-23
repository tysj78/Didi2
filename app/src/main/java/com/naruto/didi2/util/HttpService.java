package com.naruto.didi2.util;

import com.naruto.didi2.bean.FormFile;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by yangyong on 2020/3/3/0003.
 */

public class HttpService {
    public HttpService() {
    }

    public void postHttpImageRequest(final String netWorkAddress, final Map<String, Object> params, final FormFile[] files,
                                     final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    String BOUNDARY = "---------7d4a6d158c9"; //数据分隔线
                    String MULTIPART_FORM_DATA = "multipart/form-data";

                    URL url = new URL(netWorkAddress);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);//允许输入
                    connection.setDoOutput(true);//允许输出
                    connection.setUseCaches(false);//不使用Cache
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY);

                    StringBuilder sb = new StringBuilder();

                    //上传的表单参数部分
                    for (Map.Entry<String, Object> entry : params.entrySet()) {//构建表单字段内容
                        sb.append("--");
                        sb.append(BOUNDARY);
                        sb.append("\r\n");
                        sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
                        sb.append(entry.getValue());
                        sb.append("\r\n");
                    }
                    DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());
                    outStream.write(sb.toString().getBytes());//发送表单字段数据

                    //上传的文件部分
                    for (FormFile file : files) {
                        StringBuilder split = new StringBuilder();
                        split.append("--");
                        split.append(BOUNDARY);
                        split.append("\r\n");
                        split.append("Content-Disposition: form-data; name=\"" + file.getFormname() + "\"; filename=\"" + file.getFilname() + "\"\r\n");
                        split.append("Content-Type: " + file.getContentType() + "\r\n\r\n");
                        outStream.write(split.toString().getBytes());
                        outStream.write(file.getData(), 0, file.getData().length);
                        outStream.write("\r\n".getBytes());
                    }
                    byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();//数据结束标志
                    outStream.write(end_data);
                    outStream.flush();
                    int cah = connection.getResponseCode();
                    if (cah != 200) throw new RuntimeException("请求url失败");
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }
                    outStream.close();
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public interface HttpCallBackListener {
        void onFinish(String response);

        void onError(Exception e);
    }
}
