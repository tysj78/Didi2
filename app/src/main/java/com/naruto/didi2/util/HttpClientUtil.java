package com.naruto.didi2.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yangyong on 2019/8/30/0030.
 */

public class HttpClientUtil {


    /**
     * 98      * 把Web站点返回的响应流转换为字符串格式
     * 99      * @param inputStream 响应流
     * 100      * @param encode 编码格式
     * 101      * @return 转换后的字符串
     * 102
     * InputStream inputStream = httpResponse.getEntity().getContent();
     */
    private String changeInputStream(InputStream inputStream,
                                     String encode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, len);
                }
                result = new String(outputStream.toByteArray(), encode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
