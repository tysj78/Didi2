package com.naruto.didi2.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by yangyong on 2019/9/2/0002.
 */

public class Test {

    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private FileOutputStream fileOutputStream;
    private BufferedOutputStream bufferedOutputStream;

    public static void main(String[] args) {
//        System.out.println("aaaaa");
        writeString("ss");
    }

    public void copyFile(String from, String toPath) {
        System.out.println("开始复制文件...");
        long start = System.currentTimeMillis();
        try {
            fileInputStream = new FileInputStream(from);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            fileOutputStream = new FileOutputStream(toPath);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long d = System.currentTimeMillis();
        System.out.println("复制文件结束,用时：" + (d = start));
    }

    public static void writeString(String ss){
        String str="hello world!";
        FileWriter writer;
        try {
            writer = new FileWriter("E:/token.txt",true);
            writer.append(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
