package com.naruto.didi2.hook;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wzl on 11/22/19.
 */

public class Os {
    public static int checkPermission(Context context, String permission){
        Object ret = RefInvoke.invokeStaticDeclaredMethod("android.support.v4.content.ContextCompat", "checkSelfPermission",
                new Class[]{Context.class, String.class},
                new Object[]{context, permission});
        if(ret == null)
            return 0;
        return (int)ret;
    }

    public static void getPersimmions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();

            if(activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if(activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (permissions.size() > 0) {
                activity.requestPermissions(permissions.toArray(new String[permissions.size()]), 1);
            }
        }
    }

    public static String getCurrentTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
        Date date = new Date();

        return sdf.format(date);
    }

    public static String getSdard(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }

        if(sdDir == null)
            return null;
        return sdDir.getAbsolutePath();
    }
}
