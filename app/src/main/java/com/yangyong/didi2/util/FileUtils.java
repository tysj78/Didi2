package com.yangyong.didi2.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

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
        InputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            //取得指定包名的base.apk com.tencent.mobileqq
            File file = new File(context.getPackageManager().getApplicationInfo("com.gaeagame.cn.xj", 0).sourceDir);
            LogUtils.e(file.getAbsolutePath());

            File targetPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/apk");
            if (!targetPath.exists()) {
                boolean mkdirs = targetPath.mkdirs();
                LogUtils.e("创建目录：" + mkdirs);
            }
//            LogUtils.e("filedir:" + file.getAbsolutePath());
//            fileInputStream = context.getAssets().open("recordbox-v3.0.1478.apk");
            fileInputStream = new FileInputStream(file);
            File targetFile = new File(targetPath, "xj.apk");
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
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
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
    }

    //判断是否安装SDCard
    public static boolean isSdOk() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    //创建一个文件夹，用来存放下载的文件
    public static File getRootFile() {
        File sd = Environment.getExternalStorageDirectory();
        File rootFile = new File(sd, "apk");
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        return rootFile;
    }

    public static void saveBitmap(Bitmap bm) {
        LogUtils.e("Ready to save picture");
//指定我们想要存储文件的地址
        String TargetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/alibaba";
//判断指定文件夹的路径是否存在
        if (!fileIsExist(TargetPath)) {
            LogUtils.e("TargetPath isn't exist");
        } else {
//如果指定文件夹创建成功，那么我们则需要进行图片存储操作
            long l = System.currentTimeMillis();
            File saveFile = new File(TargetPath, l + ".png");

            try {
                FileOutputStream saveImgOut = new FileOutputStream(saveFile);
// compress - 压缩的意思
                bm.compress(Bitmap.CompressFormat.JPEG, 100, saveImgOut);
//存储完成后需要清除相关的进程
                saveImgOut.flush();
                saveImgOut.close();
                LogUtils.e("The picture is save to your phone!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static boolean fileIsExist(String fileName) {
//传入指定的路径，然后判断路径是否存在
        File file = new File(fileName);
        if (file.exists())
            return true;
        else {
//file.mkdirs() 创建文件夹的意思
            return file.mkdirs();
        }
    }

    public static void getFile(Context context) {
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/apk";
//        LogUtils.e(path);com.iflytek.recinbox
//        File file = new File(path + "/recordbox-v3.0.1478.apk");com.yangyong.aotosize
        File file = null;
        try {
            file = new File(context.getPackageManager().getApplicationInfo("com.yangyong.aotosize", 0).sourceDir);
            String md5 = getFileMD5(file);
            LogUtils.e("获取md5:" + md5);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static void copyFile(final String source, final String target) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(source);
                        InputStream is = null;
                        FileOutputStream os = null;
                        try {
                            is = new FileInputStream(file);
                            os = new FileOutputStream(target);
                            int len;
                            byte[] b = new byte[1024];
                            while ((len = is.read(b)) != -1) {
                                os.write(b, 0, len);
                            }
                            LogUtils.e("复制文件完成..");
                        } catch (IOException e) {
                            e.printStackTrace();
                            LogUtils.e("复制文件失败：" + e.toString());
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
                    }
                }
        ).start();
    }
}
