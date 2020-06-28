package com.yangyong.didi2.util;

import android.content.Context;
import android.os.Environment;

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

    public static void getDataFile(Context context) {
        FileInputStream fileInputStream=null;
        FileOutputStream fileOutputStream=null;
        try {
            File file = new File(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir);
            File targetFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/apk/didi2.apk");
//            LogUtils.e("filedir:" + file.getAbsolutePath());

            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(targetFile);

            LogUtils.e("文件copy开始");
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            LogUtils.e("文件copy结束");
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
}
