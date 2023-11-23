package com.naruto.didi2.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.naruto.didi2.MyApp;
import com.naruto.didi2.R;

import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static android.provider.Settings.EXTRA_CHANNEL_ID;

/**
 * Created by DELL on 2022/1/19.
 */

public class NotificationUtil {
    public static final int NOTIFY_ID = 2001;
    private static final String NOTIFICATION_CHANNEL_NAME = "AMapBackgroundLocation";
    private static NotificationManager notificationManager = null;
    private static boolean isCreatedChannel = false;

    /**
     * 创建一个通知栏，API>=26时才有效
     *
     * @param context
     * @return
     */
    public static Notification buildNotification(Context context) {
        try {
            Context mContext = context.getApplicationContext();
            Notification.Builder builder = null;
            Notification notification = null;
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
                if (null == notificationManager) {
                    notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                }
                String channelId = mContext.getPackageName();
                if (!isCreatedChannel) {
                    NotificationChannel notificationChannel = new NotificationChannel(channelId,
                            NOTIFICATION_CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_DEFAULT);
                    notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
                    notificationChannel.setLightColor(Color.BLUE); //小圆点颜色
                    notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                    notificationManager.createNotificationChannel(notificationChannel);
                    isCreatedChannel = true;
                }
                builder = new Notification.Builder(mContext, channelId);
            } else {
                builder = new Notification.Builder(mContext);
            }

            builder.setSmallIcon(R.mipmap.naruto)
                    .setContentTitle(getAppName(mContext))
                    .setContentText("正在后台运行")
                    .setWhen(System.currentTimeMillis());

            notification = builder.build();
            return notification;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取app的名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String appName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            appName = context.getResources().getString(labelRes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return appName;
    }

    /**
     * 功用：检查是否已经开启了通知权限
     */
    public static boolean checkNotifySetting(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(MyApp.getContext());
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        boolean isOpened = manager.areNotificationsEnabled();

        if (isOpened) {
            Toast.makeText(context, "通知权限已开启", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "通知权限未打开", Toast.LENGTH_SHORT).show();
        }
        return isOpened;
    }

    /**
     *
     */
    public static void initClickListener(Context context) {
        //CnPeng 2018/7/12 上午7:08 跳转到通知设置界面
        try {
            // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
            Intent intent = new Intent();
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            if (Build.VERSION.SDK_INT >= 26) {
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(EXTRA_APP_PACKAGE, context.getPackageName());
                intent.putExtra(EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);
            }else {
                //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                intent.putExtra("app_package", context.getPackageName());
                intent.putExtra("app_uid", context.getApplicationInfo().uid);
            }

            // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
            //  if ("MI 6".equals(Build.MODEL)) {
            //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            //      Uri uri = Uri.fromParts("package", getPackageName(), null);
            //      intent.setData(uri);
            //      // intent.setAction("com.android.settings/.SubSettings");
            //  }
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtils.e("打开通知权限页面异常");
            // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
            Intent intent = new Intent();

            //下面这种方案是直接跳转到当前应用的设置界面。
            //https://blog.csdn.net/ysy950803/article/details/71910806
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }
}
