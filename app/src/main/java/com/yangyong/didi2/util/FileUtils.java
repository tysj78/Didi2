package com.yangyong.didi2.util;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yangyong on 2019/9/16/0016.
 */

public class FileUtils {

    public static String getSDPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/apk/apps.apk";
//        File file = new File(storageDirectory, "apk/apps.apk");
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        return file;
    }

    public static String getApk(Context context) {
        File file = new File(getSDPath(), "apps.apk");
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = context.getAssets().open("apps.apk");
            os = new FileOutputStream(file);
            int len;
            byte[] b = new byte[1024];
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }

    public static void getDataFiles(Context context) {
        File filesDir = context.getFilesDir();
        LogUtils.e("filesDir:" + filesDir.getAbsolutePath());
//        String[] strings = context.fileList();
//        for (String fname : strings) {
//            LogUtils.e(fname);
//        }
    }

    public static void getDataFile(Context context, Handler mHandler) {
        InputStream fileInputStream=null;
        FileOutputStream fileOutputStream=null;
        try {
//            File file = new File(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir);

            File targetFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/recordbox-v3.0.1478.apk");
//            LogUtils.e("filedir:" + file.getAbsolutePath());
             fileInputStream = context.getAssets().open("recordbox-v3.0.1478.apk");
//            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(targetFile);

            LogUtils.e("文件copy开始");
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            LogUtils.e("文件copy结束");
            mHandler.sendEmptyMessage(1);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fileInputStream!=null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream!=null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //判断是否安装SDCard
    public static boolean isSdOk(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }
    //创建一个文件夹，用来存放下载的文件
    public static File getRootFile(){
        File sd = Environment.getExternalStorageDirectory();
        File rootFile = new File(sd,"apk");
        if (!rootFile.exists()){
            rootFile.mkdirs();
        }
        return rootFile;
    }
}
