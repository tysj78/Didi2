package com.yangyong.didi2.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangyong.didi2.Constants;
import com.yangyong.didi2.MyApp;
import com.yangyong.didi2.activity.DuanDianActivity;
import com.yangyong.didi2.bean.LocationModel;
import com.yangyong.didi2.bean.OperationModel;
import com.yangyong.didi2.bean.ThreadInfo;
import com.yangyong.didi2.dbdao.DownLoadDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
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
    //    public static Activity mActivity = null;
//    private static AppUtil instance = null;
//    private static final Object LUCK = new Object();

    private Activity activity;
    private List<String> list;
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    private AppUtil() {
//        LogUtils.e("创建apputil");
        list=new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("第" + i);
        }
    }

//    public static AppUtil getInstance() {
//        if (instance == null) {
//            synchronized (LUCK) {
//                if (instance == null) {
//                    instance = new AppUtil();
//                }
//            }
//        }
//        return instance;
//    }

    //==========================单例第二种方式，静态内部类
    private static class Inner {
        private static AppUtil instance = new AppUtil();
    }

    public static AppUtil getInstance() {
        return Inner.instance;
    }

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
            Uri contentUri = FileProvider.getUriForFile(context, "com.yangyong.didi2.appfileProvider", apkPath);
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

    /**
     * 下拉状态栏
     *
     * @param context
     */
    public void pullStatus(Context context) {
        try {
            OperationModel operationModel = new OperationModel();
            LocationModel locationModel1 = new LocationModel();
            locationModel1.setX(600);
            locationModel1.setY(0);
            LocationModel locationModel2 = new LocationModel();
            locationModel2.setX(600);
            locationModel2.setY(2000);
            List<LocationModel> lis = new ArrayList<>();
            lis.add(locationModel1);
            lis.add(locationModel2);
            operationModel.setList(lis);
            operationModel.setDurationTime(2000);

            LogUtils.e("开始执行手势");
            Intent intent = new Intent("com.yangyong.access.move");
            Bundle bundle = new Bundle();
            bundle.putSerializable("operation", operationModel);
            intent.putExtras(bundle);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            Log.e("yy", "Exception: " + e.toString());
        }
    }

    public void openAccess(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public String getUrl() {
        String myString = null;
        StringBuffer sff = new StringBuffer();//一定要new一个，我刚开始搞忘了，出不来。
        try {
//            Document doc = Jsoup.connect("https://sku-market-gw.jd.com/css/pc/758288.css?t=1573053011900").get();
            Document doc = Jsoup.connect("http://www.baidu.com").get();
            Elements links = doc.select("a[href]");
            //注意这里是Elements不是Element。同理getElementById返回Element，getElementsByClass返回时Elements
            for (Element link : links) {
                //这里没有什么好说的。
                sff.append(link.attr("abs:href")).append("  ").append(link.text()).append(" ");
            }
            myString = sff.toString();
        } catch (Exception e) {
            myString = e.getMessage();
            e.printStackTrace();
        }
        LogUtils.e(myString == null ? "null" : myString);
        return myString;
    }

    public void startClick(Context context) {
        try {
            LogUtils.e("开始执行点击");
            Intent intent = new Intent("com.yangyong.access.click");
            context.sendBroadcast(intent);
        } catch (Exception e) {
            Log.e("yy", "Exception: " + e.toString());
        }
    }

    /**
     * 当前设备屏幕分辨率
     *
     * @param activity
     * @return
     */
    public String getScreen(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return "Resolution: " + dm.widthPixels + "*" + dm.heightPixels;
    }

    public String webTransformAndroid(Activity activity, int x, int y) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int deviceWidth = dm.widthPixels;
            int deviceHeight = dm.heightPixels;

            float widthRate = 540f / (float) deviceWidth;
            float heightRate = 960f / (float) deviceHeight;

            int deviceX = (int) (x / widthRate);
            int deviceY = (int) (y / heightRate);
            return deviceX + "," + deviceY;
        } catch (Exception e) {
            Log.e("yy", "Exception: " + e.toString());
        }
        return "";
    }

    public void commdDevice() {
        new Thread() {
            @Override
            public void run() {
                String s = execByRuntime("input keyevent 26");
                LogUtils.e("命令执行结果：" + s);
            }
        }.start();
    }

    /**
     * 执行shell 命令， 命令中不必再带 adb shell
     *
     * @param cmd
     * @return Sting  命令执行在控制台输出的结果
     */
    public String execByRuntime(String cmd) {
        Process process = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            inputStreamReader = new InputStreamReader(process.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            int read;
            char[] buffer = new char[4096];
            StringBuilder output = new StringBuilder();
            while ((read = bufferedReader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (Throwable t) {
                    //
                }
            }
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (Throwable t) {
                    //
                }
            }
            if (null != process) {
                try {
                    process.destroy();
                } catch (Throwable t) {
                    //
                }
            }
        }
    }

    /**
     * 读取配置文件
     *
     * @param context
     * @param key
     * @return
     */
    public void getStringFromAssets(Context context, String key) {
        StringBuffer stringBuffer = new StringBuffer();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("config.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
        } catch (IOException e) {
            LogUtils.e(e.toString());
        }
        LogUtils.e(stringBuffer.toString());
        Object value = null;
        if (!stringBuffer.toString().isEmpty()) {
            JSONObject jsonObject = null;     //返回的数据形式是一个Object类型，所以可以直接转换成一个Object
            try {
                jsonObject = new JSONObject(stringBuffer.toString());
//                JSONArray array = jsonObject.getJSONArray(key);
                String array = jsonObject.getString(key);
                LogUtils.e(array);
            } catch (JSONException e) {
                LogUtils.e(e.toString());
            }
        }
//        return value;
    }

    public boolean checkRunOnAppPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
            Toast.makeText(activity, "当前无权限，请授权", Toast.LENGTH_SHORT).show();
            activity.startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName())), 100);

//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//            activity.startActivity(intent);
            return false;
        }
        return true;
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, OnWindowPermissionListener onWindowPermissionListener) {
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
                Toast.makeText(activity.getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
                if (onWindowPermissionListener != null)
                    onWindowPermissionListener.onFailure();
            } else {
                Toast.makeText(activity.getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();
                if (onWindowPermissionListener != null)
                    onWindowPermissionListener.onSuccess();
            }
        }
    }

    public interface OnWindowPermissionListener {
        void onSuccess();

        void onFailure();
    }

    public String readConfig(Context context, String fileName) {
        StringBuffer stringBuffer = new StringBuffer();
        AssetManager assetManager = context.getAssets();
        String configStr = "";

        InputStream is = null;
        BufferedReader br = null;
        try {
            is = assetManager.open(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
        } catch (IOException e) {
            LogUtils.e(e.toString());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        configStr = stringBuffer.toString();
        return configStr;
    }

    public String readConfig(Context context, String fileName, String key) {
        StringBuffer stringBuffer = new StringBuffer();
        AssetManager assetManager = context.getAssets();
        String configStr = "";

        try {
            InputStream is = assetManager.open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
        } catch (IOException e) {
            LogUtils.e(e.toString());
        }
        configStr = stringBuffer.toString();

        String value = "";
        if (!configStr.isEmpty()) {
            JSONObject jsonObject = null;     //返回的数据形式是一个Object类型，所以可以直接转换成一个Object
            try {
                jsonObject = new JSONObject(configStr);
                value = jsonObject.getString(key);
            } catch (JSONException e) {
                LogUtils.e(e.toString());
            }
        }
        return value;
    }

    public String encrypt(byte[] bytes) {
        return android.util.Base64.encodeToString(bytes, 0);
    }

    public byte[] decode(String m) {
        return android.util.Base64.decode(m, 0);
    }

    public <T> T fromJsonToObject(String json, Class<T> type) {
        try {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
            return gson.fromJson(json, type);
        } catch (Exception e) {
            Log.e("yy", "json转换异常11: " + e.getMessage());
        }
        return null;
    }

    public void toast(String msg) {
        Toast.makeText(MyApp.mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public void setWifiState(Context context, boolean state) {
        WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        boolean b = mWifiManager.setWifiEnabled(state);
        LogUtils.e("设置wifi：" + b);
    }

    public boolean openWifi(Context context) {
        LogUtils.e("判断开启wifi：" + Thread.currentThread().getId());
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int i = 0;
        if (wm == null) {
            return false;
        }
        while (!wm.isWifiEnabled()) {
            try {
                if (i == 3) {
                    return false;
                }
                LogUtils.e("正在打开WIFI");
                Thread.sleep(1000);
                i++;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                LogUtils.e(e.toString());
            }
        }
        return true;
    }

    /**
     * 检测一个android程序是否在运行
     *
     * @param context
     * @param PackageName
     * @return
     */
    public boolean isServiceStarted(Context context, String PackageName) {
        boolean isStarted = false;
        try {
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            int intGetTastCounter = 1000;
            List<ActivityManager.RunningServiceInfo> mRunningService = mActivityManager.getRunningServices(intGetTastCounter);
            for (ActivityManager.RunningServiceInfo amService : mRunningService) {
                String name = amService.service.getPackageName();
                LogUtils.e("run app:" + name);
                if (0 == name.compareTo(PackageName)) {
                    isStarted = true;
                    break;
                }
            }
        } catch (SecurityException e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
        return isStarted;
    }

    public boolean isNeedDownLoad(Context context) {
        try {
            ArrayList<ThreadInfo> threadInfos = new DownLoadDao(context).selectAll();
            for (int i = 0; i < threadInfos.size(); i++) {
                ThreadInfo info = threadInfos.get(i);
                long finished = info.getFinished();
                long end = info.getEnd();
                if (finished < end) {
                    return false;
                }
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
        return false;
    }

    public void xiezai(Context context, String pkg) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + pkg));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public boolean checkApkExist(Context con, String packagename) {
        if (packagename == null || "".equals(packagename)) {
            return false;
        }
        try {
            con.getPackageManager().getApplicationInfo(packagename, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 清除应用数据
     */
    public void clearAppData(final Context context) {
        //清除数据开始
        /** 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)*/
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        /** * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)*/
//                        deleteFilesByDirectory(new File("/data/data/"+ context.getPackageName() + "/databases"));
                        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));

                    }
                }
        ).start();
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    public void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if (!item.isDirectory()) {
                    boolean su = item.delete();
                    if (su) {
                        LogUtils.e("删除sp成功");
                    } else {
                        LogUtils.e("删除sp失败");
                    }
                } else if (item.listFiles().length != 0) {
                    deleteFilesByDirectory(item);
                } else {
                    boolean su = item.delete();
                }
            }
        }
    }

    public boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    public void openAppPerSet(Context context) {
        try {
            // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception ex) {
            LogUtils.e(ex.toString());
        }
    }

    public void Xiaomi(Activity activity) {
        // 只兼容miui v5/v6 的应用权限设置页面，否则的话跳转应用设置页面（权限设置上一级页面）
        String miuiVersion = getMiuiVersion();
        Intent intent = null;
        if ("V5".equals(miuiVersion)) {
            Uri packageURI = Uri.parse("package:" + activity.getApplicationInfo().packageName);
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        } else if ("V6".equals(miuiVersion) || "V7".equals(miuiVersion)) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", activity.getPackageName());
        } else if ("V8".equals(miuiVersion)) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", activity.getPackageName());
        } else {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", activity.getPackageName());
        }

        if (null != intent)
            activity.startActivity(intent);
    }

    public String getMiuiVersion() {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name");
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        LogUtils.e(line);
        return line;
    }
    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    @SuppressLint("MissingPermission")
    public void callPhone(Context context, String phoneNum){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    //获取当前日期
    public String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");// yyyyMMdd HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
