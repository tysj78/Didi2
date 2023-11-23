package com.naruto.didi2.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.json.JSONObject;


import android.util.Log;

import javax.net.ssl.HttpsURLConnection;

/**
 * 20200220
 *
 * @author guoxuebing 拆装件上传图片的工具类
 */
public class HttpUploadUtil {
    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    public static final String SUCCESS = "0";
    public static final String FAILURE = "1";

    public static String uploadFile(File file, String onOff, String imType, String imageId) {
        String BOUNDARY = UUID.randomUUID().toString();// 边界标识 随机生成
        String PREFIX = "--";
        String LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";// 内容类型
        // 改为StringBuffer拼接
//		String RequestURL = "https://192.168.220.48:8445/mcdm/McdmServlet/FileAction?method=uploadImage&" + "onOff="
//				+ onOff + "&imType=" + imType + "&imageId=" + imageId;
        String RequestURL = "https://192.168.220.48:8445/mcdm/McdmServlet/FileAction?method=uploadImage&" + "onOff="
                + onOff + "&imType=" + imType + "&imageId=" + imageId;
        Log.e("gxb", "RequestURL: " + RequestURL);
//		try {
//			RequestURL= URLEncoder.encode(RequestURL, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}


        try {
            URL url = new URL(RequestURL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);// 允许输入流
            conn.setDoOutput(true);// 允许输出流
            conn.setUseCaches(false);// 不允许使用缓存
            conn.setRequestMethod("POST");// 请求方式

//			conn.setRequestProperty("Content-type", "text/html");
//			conn.setRequestProperty("Accept-Charset", "utf-8");
//     		conn.setRequestProperty("contentType", "utf-8");

            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (file != null) {
                String name = file.getName();
                long lnn = file.length();
                Log.e("gxb", "文件名：" + name + "--" + lnn);
                /**
                 * 当文件不为null时把文件包装并上传
                 */
//				OutputStream outputStream = conn.getOutputStream();

//				String s = streamToString(conn.getInputStream());


				/*DataOutputStream dos = new DataOutputStream(outputStream);
                StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				*//**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比k:abc.png
                 *//*
				sb.append("Content-Disposition: form-data; name=\".png\"; filename=\"" + file.getName() + "\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
				dos.write(end_data);
				dos.flush();*/
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.e("gxb--HttpUploadUtil", "上传文件之后接口返回码-------：" + res);
                String results = streamToString(conn.getInputStream());
                Log.e("gxb--HttpUploadUtil", "flggg上传文件之后接口返回的结果：" + results);
                if (res == 200) {
                    String result = streamToString(conn.getInputStream());
                    Log.e("gxb--HttpUploadUtil", "flggg上传文件之后接口返回的结果：" + result);

                    JSONObject jsonObject = new JSONObject(result);
                    String retCode = jsonObject.getString("retCode");
                    if ("0".equals(retCode)) {
                        return SUCCESS;
                    } else {
                        return FAILURE;
                    }
                }
            } else {
                Log.e("gxb--HttpUploadUtil", "flggg上传的文件为空");
                return FAILURE;
            }
        } catch (MalformedURLException e) {
            Log.e("gxb--HttpUploadUtil", "flgggMalformedURLException：" + e.toString());
//			e.printStackTrace();
//			Utils.printException(e);
        } catch (IOException e) {
            Log.e("gxb--HttpUploadUtil", "flgggIOException：" + e.toString());
//			Utils.printException(e);
            // e.printStackTrace();
        } catch (Exception e) {
            Log.e("gxb--HttpUploadUtil", "flgggJSONException：" + e.toString());
            // TODO Auto-generated catch block
//			Utils.printException(e);
//			e.printStackTrace();
        }
        return FAILURE;
    }

    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e("gxb", e.toString());
            return null;
        }
    }



}
