package com.yangyong.didi2.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangyong.didi2.R;
import com.yangyong.didi2.constant.Constants;
import com.yangyong.didi2.MyApp;
import com.yangyong.didi2.bean.LocationModel;
import com.yangyong.didi2.bean.OperationModel;
import com.yangyong.didi2.bean.ThreadInfo;
import com.yangyong.didi2.dbdao.DownLoadDao;
import com.yangyong.didi2.intf.CallBack;
import com.yangyong.didi2.intf.ProgressCallBack;
import com.yangyong.didi2.view.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
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

    private Set<String> dialogList = new HashSet<>();
    private Set<MyDialog> dialogSet = new HashSet<>();
    private int sum = 50;

    public CallBack callBack;

    public ProgressCallBack mProCallBack;

    public void regCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void regProCallBack(ProgressCallBack callBack) {
        mProCallBack = callBack;
    }

    public int getSum() {
        return sum;
    }

    private AppUtil() {
//        LogUtils.e("创建apputil");
//        list = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            list.add("第" + i);
//        }
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
            msg.setRecipients(javax.mail.Message.RecipientType.CC, "tysj78@yeah.net");
            msg.setRecipients(javax.mail.Message.RecipientType.TO, "2553601218@qq.com");
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
        Toast.makeText(MyApp.getContext(), msg, Toast.LENGTH_SHORT).show();
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
            ArrayList<ThreadInfo> threadInfos = DownLoadDao.getInstance().selectAll();
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
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint("MissingPermission")
    public void callPhone(Context context, String phoneNum) {
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

    public void isPingSuccess(String m_strForNetAddress, Handler mHandler) {
        StringBuffer tv_PingInfo = new StringBuffer();
        String pingResult = "";
        try {

            Process p = Runtime.getRuntime()
                    .exec("/system/bin/ping -c 5 "
                            + m_strForNetAddress); // 10.83.50.111
            // m_strForNetAddress
            int status = p.waitFor();
            String result = "";
            if (status == 0) {
                result = "success";
            } else {
                result = "failed";
                android.os.Message msg = new android.os.Message();
                msg.obj = result;
                msg.what = 3;
                mHandler.sendMessage(msg);
                return;
            }
//            String lost = new String();
//            String delay = new String();
            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));

            String str = new String();
            // 读出所有信息并显示
            while ((str = buf.readLine()) != null) {
                str = str + "\r\n";
                tv_PingInfo.append(str);
            }

            pingResult = tv_PingInfo.toString();
            android.os.Message msg = new android.os.Message();
            msg.obj = pingResult;
            msg.what = 4;
            mHandler.sendMessage(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
            android.os.Message msg = new android.os.Message();
            msg.obj = ex.toString();
            msg.what = 4;
//            pingResult = "拼通了，但是有异常";
            mHandler.sendMessage(msg);
        }

    }

    /**
     * 设置透明状态栏
     *
     * @param activity 目标界面
     */
    public void setTransparentForWindow(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        try {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
        return 0;
    }

    /**
     * 是否在调试
     *
     * @return
     */
    public boolean isDdBug() {
        return Debug.isDebuggerConnected();
    }

    /*
     * 手机存储空间或者SD卡已用空间
     */
    public String getAvailDataSize() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //内部存储
            File path = Environment.getExternalStorageDirectory();
            //sd
//            File path = new File("/mnt/ext_sdcard");
            StatFs stat = new StatFs(path.getPath());
//            long blockSize = stat.getBlockSize();
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            LogUtils.e("blockSize*totalBlocks--->" + blockSize + "==" + totalBlocks + "==" + blockSize * totalBlocks);
//            LogUtils.i(TAG, "blockSize*totalBlocks--->" + Formatter.formatFileSize(mContext, blockSize * totalBlocks));
            LogUtils.e("blockSize*totalBlocks--->" + fromatG(blockSize * totalBlocks));
//			return Formatter.formatFileSize(mContext, blockSize * totalBlocks);
            long availableBlocks = stat.getAvailableBlocks();

            long availDataSize = (blockSize * totalBlocks) - (blockSize * availableBlocks);
            LogUtils.e("availableBlocks--->" + fromatG(blockSize * availableBlocks));
            return availDataSize + "";
        } else {
            //不存在SD卡
            return "0";
        }

    }

    long fromatG(long b) {
        long l = b / 1024 / 1024 / 1024;
        return l;
    }

    public String getSdPath(Context context) {
//        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
//        String[] paths = (String[]) sm.getClass().getMethod("getVolumePaths", null).invoke(sm, null);

        File storageDir = new File("/mnt/ext_sdcard");
        if (storageDir.isDirectory()) {
            LogUtils.e("sdpath:" + storageDir.getAbsolutePath());
            return storageDir.getAbsolutePath();
//            String[] dirList = storageDir.list();
            //TODO some type of selecton method?
//            for (int i = 0; i < dirList.length; i++) {
//                LogUtils.e("sd:" + dirList[i]);
//                if (dirList[i].equals("sdcard")) {
//
//                }
//            }
        }
        return "";
    }

    public void getSize(Context context) {
        String sdPath = getSdPath(context);
        File file = new File(sdPath);
        long totalSpace = file.getTotalSpace();
        long l = fromatG(totalSpace);
        LogUtils.e(l + "G");
    }

    public void showDialog(Context context, String pkg) {
        try {

            //查询内存中是否有这个dialog，如果有这个dialog就将它取消掉
            Iterator<MyDialog> iterator = dialogSet.iterator();
//            while (iterator.hasNext()) {
//                MyDialog dialog = iterator.next();
//                String pkg1 = dialog.getPkg();
//                if (pkg.equals(pkg1)) {
//                    dialog.cancel();
//                    LogUtils.e("取消旧的dialog");
//                }
//            }
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < dialogSet.size(); i++) {
                MyDialog dialog = iterator.next();
                String pkg1 = dialog.getPkg();
                if (pkg.equals(pkg1)) {
                    dialog.cancel();
                    LogUtils.e("取消旧的dialog");
                    iterator.remove();
                    i--;
                }
            }
            long currentTimeMillis = System.currentTimeMillis();
            LogUtils.e("清理用时：" + (currentTimeMillis - startTime));
            final MyDialog myDialog = new MyDialog(context, R.style.myDialogTheme);
            myDialog.setContentView(R.layout.dialog);

            TextView ins_no = myDialog.findViewById(R.id.tv_install_no);
            TextView ins_ok = myDialog.findViewById(R.id.tv_install_ok);
            TextView ins_tit = myDialog.findViewById(R.id.tv_dialog_title);

            myDialog.setPkg(pkg);
//            dialogList.add(pkg);
            ins_tit.setText(pkg + "\n" + pkg + "为必装应用，是否下载并安装？");

            ins_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.cancel();
                }
            });

            ins_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.cancel();

                }
            });
            myDialog.show();
            dialogSet.add(myDialog);
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }

    }

    public synchronized void writeDate(int i) {
        try {
            Thread.sleep(1000);
            sum = sum - i;
            LogUtils.e("写入数据完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
