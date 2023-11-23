package com.naruto.didi2.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.storage.StorageManager;
import android.provider.CallLog;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lahm.library.EasyProtectorLib;
import com.lahm.library.EmulatorCheckCallback;
import com.naruto.didi2.MyApp;
import com.naruto.didi2.R;
import com.naruto.didi2.bean.AppCopy;
import com.naruto.didi2.bean.DPoint;
import com.naruto.didi2.bean.LocationModel;
import com.naruto.didi2.bean.MScreenPasswordInfo;
import com.naruto.didi2.bean.MWifiInfo;
import com.naruto.didi2.bean.OperationModel;
import com.naruto.didi2.bean.ThreadInfo;
import com.naruto.didi2.bean.User;
import com.naruto.didi2.broadcast.EMMDeviceAdminReceiver;
import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.dbdao.DownLoadDao;
import com.naruto.didi2.intf.CallBack;
import com.naruto.didi2.intf.ProgressCallBack;
import com.naruto.didi2.view.MyDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import static android.bluetooth.BluetoothProfile.GATT_SERVER;
import static android.content.Context.TELEPHONY_SERVICE;


/**
 * Created by yangyong on 2019/12/26/0026.
 */

public class AppUtil {
    //    public static Activity mActivity = null;
//    private static final AppUtil instance =null;
//    private static final Object LUCK = new Object();

    private Activity activity;
    private List<String> list;
    private final ExecutorService mExecutorService;
    public Map<String, String> mAppMap;
    private static double EARTH_RADIUS = 6371.393;
    private String TAG = "yylog";

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

    private Handler mHandler = new Handler(Looper.getMainLooper());


    private Set<String> dialogList = new HashSet<>();
    private Set<MyDialog> dialogSet = new HashSet<>();
    private int sum = 50;

    public CallBack callBack;

    public ProgressCallBack mProCallBack;

    public final Object object = new Object();

    public void regCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void callBack(String con) {
        if (callBack != null) {
            callBack.doEvent(con);
        }
    }

    public void regProCallBack(ProgressCallBack callBack) {
        mProCallBack = callBack;
    }

    public int getSum() {
        return sum;
    }

    public static String ap;

    private AppUtil() {
        //初始化一个固定数量线程的线程池
        mExecutorService = Executors.newFixedThreadPool(3);
        LogUtils.e("apputil初始化");
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
        RxPermissions permission = new RxPermissions((FragmentActivity) activity);
        Observable<Boolean> request = permission.request(s);
        request.subscribe(consumer);
    }

    //下载到本地后执行安装
    public static void installAPK(Context context, File file) {
        try {
            if (!file.exists()) return;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = null;
//        Uri uri = Uri.parse("file://" + file.toString());//7.0以上已废弃
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".appfileProvider", file);
            } else {
                uri = Uri.parse("file://" + file.toString());
            }

            Log.e(Constants.TAG, "filepath: " + file.getAbsolutePath());
            Log.e(Constants.TAG, "uri: " + uri);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            //在服务中开启activity必须设置flag,后面解释
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(Constants.TAG, "安装异常: " + e.getMessage());
        }

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
        /*String myString = null;
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
        return myString;*/
        return "";
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
        java.lang.Process process = null;
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
    public Object getStringFromAssets(Context context, String key) {
        StringBuffer stringBuffer = new StringBuffer();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("config.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
        } catch (IOException e) {
            LogUtils.e(e.toString());
        }
//        LogUtils.e(stringBuffer.toString());
        Object value = null;
        if (!stringBuffer.toString().isEmpty()) {
            JSONObject jsonObject = null;     //返回的数据形式是一个Object类型，所以可以直接转换成一个Object
            try {
                jsonObject = new JSONObject(stringBuffer.toString());
//                JSONArray array = jsonObject.getJSONArray(key);
                value = jsonObject.getString(key);
//                LogUtils.e(array);
            } catch (JSONException e) {
                LogUtils.e(e.toString());
            }
        }
        return value;
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

    public boolean checkAppExist(Context con, String packagename) {
        if (packagename == null || "".equals(packagename)) {
            return false;
        }
        try {
            con.getPackageManager().getApplicationInfo(packagename, 0);
            return true;
        } catch (Exception e) {
            LogUtils.e(e.toString());
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


    public boolean isEMUI() {
        try {
            String manufacturer = Build.MANUFACTURER;
            //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
            if ("huawei".equalsIgnoreCase(manufacturer)) {
                return true;
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
            if (mHandler != null) {
                Message obtain = Message.obtain();
                obtain.what = 3;
                obtain.obj = e.toString();
                mHandler.sendMessage(obtain);
            }
        }
        return false;
    }

    public boolean isOPPO() {
        try {
            String manufacturer = Build.MANUFACTURER;
            //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
            if ("oppo".equalsIgnoreCase(manufacturer)) {
                return true;
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
            if (mHandler != null) {
                Message obtain = Message.obtain();
                obtain.what = 3;
                obtain.obj = e.toString();
                mHandler.sendMessage(obtain);
            }
        }
        return false;
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
            java.lang.Process p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name");
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

            java.lang.Process p = Runtime.getRuntime()
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
    public void setTransparentForWindow(Activity activity) {
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

    /**
     * 获取手机sd真实路径，兼容性未测试
     *
     * @return
     */
    public String getSdPath() {
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
        String sdPath = getSdPath();
        File file = new File(sdPath);
        long totalSpace = file.getTotalSpace();
        long l = fromatG(totalSpace);
        LogUtils.e(l + "G");
    }

    public void showDialog(Activity context, String pkg) {
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

    public void doInBackSomething(final Handler handler) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LogUtils.e("doInBackSomething..");
                SystemClock.sleep(5000);
                handler.sendEmptyMessage(1);
            }
        };

        mExecutorService.execute(runnable);
    }

    /**
     * 程序是否处于后台
     *
     * @param context
     * @return
     */
    public boolean isBackground(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = ((ActivityManager.RunningTaskInfo) tasks.get(0)).topActivity;
            LogUtils.e("前台应用名称:" + topActivity.getPackageName());
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否开启了权限
     */
    public boolean checkPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 启动emm安全桌面
     *
     * @param context
     */
    public void openEmm(Context context) {
        try {
            Intent intent = new Intent();
//            intent.setData(Uri.parse("emm://com.mobilewise.mobileware/setxiaomimdm?type=220"));
            intent.setData(Uri.parse("emm://com.mobilewise.mobileware/setmdm?type=220"));
//        intent.putExtra("", "");//这里Intent当然也可传递参数,但是一般情况下都会放到上面的URL中进行传递
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtils.e("Exception打开设置页面异常: " + e.toString());
        }
    }

    /**
     * 获取已经安装的 app 的 MD5 签名信息
     *
     * @param context
     * @param pkgName
     * @return
     */
    public String getAppSignatureMD5(Context context, String pkgName) {
        return getAppSignature(context, pkgName, "MD5");
    }

    public String getAppSignatureSHA1(Context context, String pkgName) {
        return getAppSignature(context, pkgName, "SHA1");
    }

    public String getAppSignatureSHA256(Context context, String pkgName) {
        return getAppSignature(context, pkgName, "SHA256");
    }

    public String getAppSignature(Context context, String pkgName, String algorithm) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    pkgName, PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            String signStr = hexDigest(sign.toByteArray(), algorithm);
            return signStr;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String hexDigest(byte[] bytes, String algorithm) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        byte[] md5Bytes = md5.digest(bytes);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public void getSingInfo(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.yangyong.aotosize", PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            parseSignature(sign.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseSignature(byte[] signature) {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(signature));
            String pubKey = cert.getPublicKey().toString();
            String signNumber = cert.getSerialNumber().toString();
            LogUtils.e("signName:" + cert.getSigAlgName());
            LogUtils.e("pubKey:" + pubKey);
            LogUtils.e("signNumber:" + signNumber);
            LogUtils.e("subjectDN:" + cert.getSubjectDN().toString());
        } catch (CertificateException e) {
            LogUtils.e(e.toString());
        }
    }

    private Signature[] getRawSignature(Context context, String packageName) {
        if (packageName == null || packageName.length() == 0) {
            return null;
        }
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (info != null) {
                return info.signatures;
            }
            //errout("info is null, packageName = " + packageName);
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            //errout("NameNotFoundException");
            return null;
        }
    }

    public void getAppSignatureMd5(Context context, String pkg) {
        //获取原始签名
        Signature[] signs = getRawSignature(context, pkg);
        try {
            //获取原始签名MD5
            String signValidString = getSignValidString(signs[0].toByteArray());
            LogUtils.e("获取原始签名MD5:" + signValidString);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    private String getSignValidString(byte[] paramArrayOfByte) throws NoSuchAlgorithmException {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(paramArrayOfByte);
        return toHexString(localMessageDigest.digest());
    }

    public String toHexString(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null) {
            return null;
        }
        StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
        for (int i = 0; ; i++) {
            if (i >= paramArrayOfByte.length) {
                return localStringBuilder.toString();
            }
            String str = Integer.toString(0xFF & paramArrayOfByte[i], 16);
            if (str.length() == 1) {
                str = "0" + str;
            }
            localStringBuilder.append(str);
        }
    }

    /**
     * 判断手机是否root，不弹出root请求框<br/>
     * zq 20171115
     */
    public boolean checkRootState() {
        //使用第三方库EasyProtectorLib判断设备是否root
//        EasyProtectorLib.checkIsRoot();
        int secureProp = getroSecureProp();//eng/userdebug版本，自带root权限
        if (secureProp == 0) {
            return true;
        } else return isSUExist();//user版本，继续查su文件
    }

    private int getroSecureProp() {
        int secureProp;
        String roSecureObj = CommandUtil.getSingleInstance().getProperty("ro.secure");
        if (roSecureObj == null) secureProp = 1;
        else {
            if ("0".equals(roSecureObj)) secureProp = 0;
            else secureProp = 1;
        }
        return secureProp;
    }

    private boolean isSUExist() {
        File file = null;
        String[] paths = {"/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/su/bin/su",
                "/su/xbin/su",
                "/data/local/su"};
        for (String path : paths) {
            file = new File(path);
            if (file.exists() && isExecutable(file.toString())) {
                LogUtils.e("file is exits name:" + file);
                return true;
            }
        }
        return false;
    }

    /**
     * 检测是否是root文件
     *
     * @param filePath
     * @return
     */
    public boolean isExecutable(String filePath) {
        java.lang.Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            LogUtils.e(e.toString());
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }


    /**
     * 内置的几个连接往外获取ip地址的网址
     */
    private static String[] platforms = {
            "http://pv.sohu.com/cityjson",
            "http://pv.sohu.com/cityjson?ie=utf-8"

    };

    /**
     * 循环访问外网网址，获取设备外网的ip地址
     *
     * @param context
     * @param index   外网ip地址数组中的索引
     * @return
     */
    public String getOutNetIP(Context context, int index) {
        //判断是否连接网络
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            //有网络
            LogUtils.e("有网络");
            if (index < platforms.length) {
                BufferedReader buff = null;
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(platforms[index]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(5000);//读取超时
                    urlConnection.setConnectTimeout(5000);//连接超时
                    urlConnection.setDoInput(true);
                    urlConnection.setUseCaches(false);

                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {//找到服务器的情况下,可能还会找到别的网站返回html格式的数据
                        InputStream is = urlConnection.getInputStream();
                        buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));//注意编码，会出现乱码
                        StringBuilder builder = new StringBuilder();
                        String line = null;
                        while ((line = buff.readLine()) != null) {
                            builder.append(line);
                        }

                        buff.close();//内部会关闭 InputStream
                        urlConnection.disconnect();

                        LogUtils.e(builder.toString());
                        if (index == 0 || index == 1) {
                            //截取字符串
                            int satrtIndex = builder.indexOf("{");//包含[
                            int endIndex = builder.indexOf("}");//包含]
                            String json = builder.substring(satrtIndex, endIndex + 1);//包含[satrtIndex,endIndex)
                            JSONObject jo = new JSONObject(json);
                            String ip = jo.getString("cip");

                            return ip;
                        } else if (index == 2) {
                            JSONObject jo = new JSONObject(builder.toString());
                            return jo.getString("ip");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //获取内网ip
                LogUtils.e("neiwang");
                return getLocalIpV4Address(context);
            }
            return getOutNetIP(context, ++index);
        } else {
            //无网络
            LogUtils.e("无网络");
            return "0.0.0.0";
        }
    }

    /**
     * 获取本地Ipv4地址
     *
     * @param context
     * @return
     */
    public String getLocalIpV4Address(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        try {
            if (info != null && info.isConnected()) {
                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    try {
                        String ipv4;
                        //获取本地系统上所有可用的网络接口的列表
                        ArrayList<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
                        for (NetworkInterface ni : nilist) {
                            //获取绑定在此网络接口的地址的枚举
                            ArrayList<InetAddress> ialist = Collections.list(ni.getInetAddresses());
                            for (InetAddress address : ialist) {
                                if (!address.isLoopbackAddress() && !address.isLinkLocalAddress()) {
                                    ipv4 = address.getHostAddress();
                                    return ipv4;
                                }
                            }
                        }

                    } catch (SocketException ex) {
                        LogUtils.e("localip" + ex.toString());
                    }
                } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    String ipAddress = intToIp(wifiInfo.getIpAddress());//得到IPV4地址
                    return ipAddress;
                }

            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

        //网络没连接
        return "";
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param
     * @return
     */

    private String intToIp(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 格式化当前时间
     *
     * @return 返回设置的格式化方式，如 yyyy:MM:dd HH:mm:ss 表示 年:月:日:时:分:秒
     */
    public String formatTime(Long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String formatTimeStr = formatter.format(date);
        return formatTimeStr;
    }

    public void initAppMap() {
        if (mAppMap == null) {
            mAppMap = new HashMap<>();
        }
    }

    public void uninstall(Context context, String pkg) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + pkg));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 设置设备锁屏密码
     *
     * @param screenPasswordInfo
     */
    public void setDeviceScreenPassword(Context mContext, MScreenPasswordInfo screenPasswordInfo) {
        if (screenPasswordInfo == null) {
            return;
        }
        LogUtils.e("执行锁屏配置：" + screenPasswordInfo.toString());
        /**
         * 实际配置命令执行，添加了判空处理,Ting Chueng 2015-7-20
         */
        try {
//            int mSpiExpiredDay = Integer.parseInt(screenPasswordInfo.getSpiExpiredTime());
            int mSpiExpiredDay = 0;

            ComponentName componentName = new ComponentName(mContext, EMMDeviceAdminReceiver.class);
            DevicePolicyManager mDPM = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);

            if (mDPM == null) {
                return;
            }
            if (!screenPasswordInfo.getSpiLeastCharNum().equals("xxx")) {
                mDPM.setPasswordQuality(componentName, DevicePolicyManager.PASSWORD_QUALITY_COMPLEX);
                //密码最小长度
                if (screenPasswordInfo.getSpiMinLength().length() > 0)
                    mDPM.setPasswordMinimumLength(componentName, Integer.valueOf(screenPasswordInfo.getSpiMinLength()));//bug Integer.valueOf("");!!!


                switch (screenPasswordInfo.getPasswordComplexity()) {
                    case "0":
                        //字母数字
                        //最小字母数
                        if (screenPasswordInfo.getSpiLeastCharNum().length() > 0) {
                            mDPM.setPasswordMinimumLetters(componentName, 1);
                        }
                        //最小数字数量
                        if (screenPasswordInfo.getSpiLeastArabicNum().length() > 0) {
                            mDPM.setPasswordMinimumNumeric(componentName, 1);
                        }
                        //最小特殊字符数
                        if (screenPasswordInfo.getSpiLeastSymbolNum().length() > 0)
                            mDPM.setPasswordMinimumSymbols(componentName, 0);
                        LogUtils.e("执行字母数字：");
                        break;
                    case "1":
                        //字母特殊符号
                        //最小字母数
                        if (screenPasswordInfo.getSpiLeastCharNum().length() > 0)
                            mDPM.setPasswordMinimumLetters(componentName, 1);
                        //最小特殊字符数
                        if (screenPasswordInfo.getSpiLeastSymbolNum().length() > 0)
                            mDPM.setPasswordMinimumSymbols(componentName, 1);
                        //最小数字数量
                        if (screenPasswordInfo.getSpiLeastArabicNum().length() > 0) {
                            mDPM.setPasswordMinimumNumeric(componentName, 0);
                        }
                        LogUtils.e("执行字母特殊符号：");
                        break;
                    case "2":
                        //字母数字特殊符号
                        //最小字母数
                        if (screenPasswordInfo.getSpiLeastCharNum().length() > 0)
                            mDPM.setPasswordMinimumLetters(componentName, 1);
                        //最小数字数量
                        if (screenPasswordInfo.getSpiLeastArabicNum().length() > 0)
                            mDPM.setPasswordMinimumNumeric(componentName, 1);
                        //最小特殊字符数
                        if (screenPasswordInfo.getSpiLeastSymbolNum().length() > 0)
                            mDPM.setPasswordMinimumSymbols(componentName, 1);
                        LogUtils.e("执行字母数字特殊符号：");
                        break;
                    default:
                        break;
                }

                //最小大写字母数
                if (screenPasswordInfo.getSpiLeastUpCharNum().length() > 0)
                    mDPM.setPasswordMinimumUpperCase(componentName, 0);

                //最小小写字母数
                if (screenPasswordInfo.getSpiLeastLowCharNum().length() > 0)
                    mDPM.setPasswordMinimumLowerCase(componentName, 0);
                //最小字母数
//                if (screenPasswordInfo.getSpiLeastCharNum().length() > 0)
//                    mDPM.setPasswordMinimumLetters(componentName, Integer.valueOf(screenPasswordInfo.getSpiLeastCharNum()));
                //最小大写字母数
//                if (screenPasswordInfo.getSpiLeastUpCharNum().length() > 0)
//                    mDPM.setPasswordMinimumUpperCase(componentName, Integer.valueOf(screenPasswordInfo.getSpiLeastUpCharNum()));
                //最小小写字母数
//                if (screenPasswordInfo.getSpiLeastLowCharNum().length() > 0)
//                    mDPM.setPasswordMinimumLowerCase(componentName, Integer.valueOf(screenPasswordInfo.getSpiLeastLowCharNum()));

                //最小数字数量
//                if (screenPasswordInfo.getSpiLeastArabicNum().length() > 0)
//                    mDPM.setPasswordMinimumNumeric(componentName, Integer.valueOf(screenPasswordInfo.getSpiLeastArabicNum()));
                //最小特殊字符数
//                if (screenPasswordInfo.getSpiLeastSymbolNum().length() > 0)
//                    mDPM.setPasswordMinimumSymbols(componentName, Integer.valueOf(screenPasswordInfo.getSpiLeastSymbolNum()));


                //有效期
                if (screenPasswordInfo.getSpiExpiredTime().length() > 0)
                    mDPM.setPasswordExpirationTimeout(componentName, mSpiExpiredDay * 24 * 60 * 60 * 1000L);
                //最近历史密码数
                if (screenPasswordInfo.getSpiPWLimit().length() > 0)
                    mDPM.setPasswordHistoryLength(componentName, Integer.valueOf(screenPasswordInfo.getSpiPWLimit()));
                //最大尝试数，恢复出厂
//                if (screenPasswordInfo.getSpiMaxFailedAttemptsEraseDevice().length() > 0)
//                    mDPM.setMaximumFailedPasswordsForWipe(componentName, Integer.valueOf(screenPasswordInfo.getSpiMaxFailedAttemptsEraseDevice()));

                LogUtils.e("配置锁屏密码规则成功");
            } else {
                mDPM.setPasswordQuality(componentName, DevicePolicyManager.PASSWORD_QUALITY_NUMERIC);
                mDPM.setPasswordExpirationTimeout(componentName, mSpiExpiredDay * 24 * 60 * 60 * 1000L);//
                if (screenPasswordInfo.getSpiMaxFailedAttemptsEraseDevice().length() > 0)
                    mDPM.setMaximumFailedPasswordsForWipe(componentName, Integer.valueOf(screenPasswordInfo.getSpiMaxFailedAttemptsEraseDevice()));
                mDPM.setPasswordMinimumLength(componentName, 0);
                if (screenPasswordInfo.getPinPass() != null && screenPasswordInfo.getPinPass().length() > 0) {
                    boolean booResult = false;
                    try {
                        booResult = mDPM.resetPassword(screenPasswordInfo.getPinPass(), DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
                    } catch (Exception e) {
                        LogUtils.e(e.toString());
                    }
                    LogUtils.e("设置锁屏密码：" + booResult);
                }
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

    }

    public void setScreenPd(Context mContext, String pass) {
        ComponentName componentName = new ComponentName(mContext, EMMDeviceAdminReceiver.class);
        DevicePolicyManager policyManager = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (Build.VERSION.SDK_INT > 23) {
            try {
                policyManager.resetPassword(pass, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }
        } else if (Build.VERSION.SDK_INT == 23) {
            try {
                policyManager.resetPassword("", 2);
                policyManager.resetPassword(pass, 2);
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }
        } else {
            try {
                policyManager.resetPassword(pass, 1);
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }
        }
    }

    /**
     * <p>
     * 擦除设备，即恢复出场设置
     */
    public boolean eraseDevice(Context mContext) {
        //获取设备管理服务
        DevicePolicyManager policyManager = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
        // DevicesAdminReceiver 继承自 DeviceAdminReceiver
        ComponentName componentName = new ComponentName(mContext, EMMDeviceAdminReceiver.class);
        boolean active = policyManager.isAdminActive(componentName);
        if (active) {
            policyManager.wipeData(0);
            return true;
        }
        return false;
    }

    /**
     * 根据传入的URL获取一级域名
     *
     * @param url
     * @return
     */
    public String getDomain(String url) {
        String domain = "";
//        if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
        if (!TextUtils.isEmpty(url)) {
            try {
//                String host = Uri.parse(url).getHost();
                String host = new URL(url).getHost();
//                if (!TextUtils.isEmpty(host) && host.contains(".")) {
//                    domain = host.substring(host.indexOf("."), host.length());
//                }
                domain = host;
            } catch (Exception ex) {
                LogUtils.e("获取域名：" + ex.toString());
            }
        }
        LogUtils.e("获取域名：" + domain);
        return domain;
    }

    /**
     * 开启或者关闭蓝牙
     *
     * @param state
     */
    public boolean setBluetooth(Context mContext, boolean state) {
        LogUtils.e("setBluetooth...");
        boolean result = false;
        BluetoothAdapter mBluetoothAdapter = null;
        BluetoothManager bm = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bm.getAdapter();
        if (mBluetoothAdapter == null) {
            LogUtils.e("本机不支持蓝牙：没有蓝牙模块");
            return result;
        }
        if (state) {
            if (!mBluetoothAdapter.isEnabled()) {
                result = mBluetoothAdapter.enable();
                if (result) {
                    LogUtils.e("蓝牙已经开启...");
                }
            }

        } else {
            if (mBluetoothAdapter.isEnabled()) {
                result = mBluetoothAdapter.disable();
                if (result) {
                    LogUtils.e("蓝牙已经关闭...");
                }
            }
        }
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            LogUtils.e(e.toString());
//        }
        return result;
    }

    /**
     * 断开当前wifi
     *
     * @param mContext
     */
    public void closeWifi(Context mContext) {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();

        //String mMacAddress = connectionInfo.getMacAddress();
        int mNetworkId = connectionInfo.getNetworkId();

        boolean b = wifiManager.disableNetwork(mNetworkId);//指定热点断开连接，同时不再连接 11返回false
        boolean disconnect = wifiManager.disconnect();//断开当前连接, 一段时间后还会自动连接
        LogUtils.e("disableNetwork:" + b + "disconnect:" + disconnect);
    }

    /**
     * 设置个人热点状态
     * add by gxb 20190130
     * 20190319 增加8.0系统的适配
     */
    public void setWifiApEnabled(Context context, boolean enabled) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            //兼容android 11关闭热点
            if (Build.VERSION.SDK_INT == 30 && !enabled) {
                try {
                    ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    Class<?> connectivityClass = Class.forName("android.net.ConnectivityManager");
                    Method stopTethering = connectivityClass.getDeclaredMethod("stopTethering", int.class);

                    stopTethering.invoke(conman, 0);

                } catch (Exception e) {
                    LogUtils.exception(e);
                }
                return;
            }


            //热点的配置类
            WifiConfiguration apConfig = new WifiConfiguration();
            if (Build.VERSION.SDK_INT >= 26) {
                LogUtils.e("手机是8.0系统");
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                Field iConnMgrField = connectivityManager.getClass().getDeclaredField("mService");
                iConnMgrField.setAccessible(true);
                Object iConnMgr = iConnMgrField.get(connectivityManager);
                Class<?> iConnMgrClass = Class.forName(iConnMgr.getClass().getName());
                if (enabled) {
                    Method startTethering = iConnMgrClass.getMethod("startTethering", int.class, ResultReceiver.class, boolean.class);
                    startTethering.invoke(iConnMgr, 0, null, true);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                        //8.1和9.0版本接口有变化
                        Method stopTethering = iConnMgrClass.getMethod("stopTethering", int.class, String.class);
                        stopTethering.invoke(iConnMgr, 0, context.getPackageName());
                    } else {
                        //8.0接口
                        Method stopTethering = iConnMgrClass.getMethod("stopTethering", int.class);
                        stopTethering.invoke(iConnMgr, 0);
                    }

                }
            } else {
//                if (enabled) { // disable WiFi in any case
//                    //wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
//                    wifiManager.setWifiEnabled(false);
//                    LogUtils.e(":关闭wifi");
//                } else {
//                    wifiManager.setWifiEnabled(true);
//                }
                //通过反射调用设置热点
                Method method = wifiManager.getClass().getMethod(
                        "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
                method.invoke(wifiManager, apConfig, enabled);
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 获取一段时间范围内的手机通话记录
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public void getCallLogInfo(Context context) {
//        long lastUploadCallLogTime = SharedPreferencesUtils.getIntance().getLastUploadCallLogTime(SharedPreferencesUtils.CALLLASTTIME);
        long lastUploadCallLogTime = 0;
//        LogUtils.e("getlast=========call: " + lastUploadCallLogTime);


        boolean checkVersion = true;
        ContentResolver cr = context.getContentResolver();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            checkVersion = true;
            projection = new String[]{
                    CallLog.Calls.CACHED_NAME,
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.GEOCODED_LOCATION,
                    CallLog.Calls.DURATION,
                    CallLog.Calls.TYPE,
                    CallLog.Calls.DATE,
            };
        } else {
            //5.0以下不获取归属地
            checkVersion = false;
            projection = new String[]{CallLog.Calls.CACHED_NAME,
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.DURATION,
                    CallLog.Calls.TYPE,
                    CallLog.Calls.DATE,
            };
        }
        Cursor cursor = null;
        try {
            long current = System.currentTimeMillis();
            String condition = "date > " + lastUploadCallLogTime + " and date <= " + current;
            cursor = cr.query(uri, projection, condition, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    if (checkVersion) {
                        //姓名
                        String contact = cursor.getString(0);
                        if (contact == null)
                            contact = "";
                        //联系人号码
                        String mobile = cursor.getString(1);
                        //区域
                        String area = cursor.getString(2);
                        if (area == null)
                            area = "";
                        //时长
                        String duration = cursor.getString(3);
                        //通话类型
                        int type = cursor.getInt(4);
                        String cType = checkCallType(type);
                        //日期
                        long createTime = cursor.getLong(5);

                    } else {
                        //姓名
                        String contact = cursor.getString(0);
                        if (contact == null) {
                            contact = "";
                        }
                        //联系人号码
                        String mobile = cursor.getString(1);
                        //时长
                        String duration = cursor.getString(2);
                        //通话类型
                        int type = cursor.getInt(3);
                        String cType = checkCallType(type);
                        //日期
                        long createTime = cursor.getLong(4);
//                        infos.add(new MCallLog(contact, mobile, "", duration, cType, createTime + "", "", "0"));
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private static String checkCallType(int type) {
        String callType = "2";
        switch (type) {
            case CallLog.Calls.INCOMING_TYPE:
                callType = "1";
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                callType = "0";
                break;
            case CallLog.Calls.MISSED_TYPE:
                callType = "1";
                break;
            case CallLog.Calls.REJECTED_TYPE:
                callType = "1";
                break;
        }
        return callType;
    }


    /**
     * 设置设备锁屏密码
     *
     * @param screenPasswordInfo
     */
  /*  public void setDeviceScreenPassword(MScreenPasswordInfo screenPasswordInfo) {
        LogUtils.e("执行刷屏配置：" + screenPasswordInfo.toString());
        *//**
     * 实际配置命令执行，添加了判空处理,Ting Chueng 2015-7-20
     *//*
        try {
            int mSpiExpiredDay = Integer.parseInt(screenPasswordInfo.getSpiExpiredTime());

            ComponentName componentName = new ComponentName(mContext, DevicesAdminReceiver.class);
            DevicePolicyManager mDPM = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
            if (!screenPasswordInfo.getSpiLeastCharNum().equals("xxx")) {
                mDPM.setPasswordQuality(componentName, DevicePolicyManager.PASSWORD_QUALITY_COMPLEX);
                if (screenPasswordInfo.getSpiMinLength().length() > 0)
                    mDPM.setPasswordMinimumLength(componentName, Integer.valueOf(screenPasswordInfo.getSpiMinLength()));//bug Integer.valueOf("");!!!
                if (screenPasswordInfo.getSpiLeastCharNum().length() > 0)
                    mDPM.setPasswordMinimumLetters(componentName, Integer.valueOf(screenPasswordInfo.getSpiLeastCharNum()));
                if (screenPasswordInfo.getSpiLeastUpCharNum().length() > 0)
                    mDPM.setPasswordMinimumUpperCase(componentName, Integer.valueOf(screenPasswordInfo.getSpiLeastUpCharNum()));
                if (screenPasswordInfo.getSpiLeastLowCharNum().length() > 0)
                    mDPM.setPasswordMinimumLowerCase(componentName, Integer.valueOf(screenPasswordInfo.getSpiLeastLowCharNum()));
                if (screenPasswordInfo.getSpiLeastArabicNum().length() > 0)
                    mDPM.setPasswordMinimumNumeric(componentName, Integer.valueOf(screenPasswordInfo.getSpiLeastArabicNum()));
                if (screenPasswordInfo.getSpiLeastSymbolNum().length() > 0)
                    mDPM.setPasswordMinimumSymbols(componentName, Integer.valueOf(screenPasswordInfo.getSpiLeastSymbolNum()));
                if (screenPasswordInfo.getSpiExpiredTime().length() > 0)
                    mDPM.setPasswordExpirationTimeout(componentName, mSpiExpiredDay * 24 * 60 * 60 * 1000L);
                if (screenPasswordInfo.getSpiPWLimit().length() > 0)
                    mDPM.setPasswordHistoryLength(componentName, Integer.valueOf(screenPasswordInfo.getSpiPWLimit()));
                if (screenPasswordInfo.getSpiMaxFailedAttemptsEraseDevice().length() > 0)
                    mDPM.setMaximumFailedPasswordsForWipe(componentName, Integer.valueOf(screenPasswordInfo.getSpiMaxFailedAttemptsEraseDevice()));
            } else {
                mDPM.setPasswordQuality(componentName, DevicePolicyManager.PASSWORD_QUALITY_NUMERIC);
                mDPM.setPasswordExpirationTimeout(componentName, mSpiExpiredDay * 24 * 60 * 60 * 1000L);//
                if (screenPasswordInfo.getSpiMaxFailedAttemptsEraseDevice().length() > 0)
                    mDPM.setMaximumFailedPasswordsForWipe(componentName, Integer.valueOf(screenPasswordInfo.getSpiMaxFailedAttemptsEraseDevice()));
                mDPM.setPasswordMinimumLength(componentName, 0);
                if (screenPasswordInfo.getPinPass() != null && screenPasswordInfo.getPinPass().length() > 0) {
                    boolean booResult = false;
                    try {
                        booResult = mDPM.resetPassword(screenPasswordInfo.getPinPass(), DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
                    } catch (Exception e) {
                        LogUtils.exception(e);
                    }
                    LogUtils.e("" + booResult);
                }
            }
        } catch (Exception e) {
            LogUtils.exception(e);
        }

    }*/

    /**
     * 获取IP
     *
     * @param context
     * @return
     */
    public String getIP(Context context) {
        String ip = "0.0.0.0";
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info == null) {
                LogUtils.e("获取网络信息为空，检查网络连接开关是否打开");
                return "获取网络信息为空，检查网络连接开关是否打开";
            }
            int type = info.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                ip = getWifiIP(context);
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                ip = getEtherNetIP();
            }
        } catch (Throwable e) {
            LogUtils.exception(e);
        }
        return ip;
    }

    /**
     * 获取有线地址
     */
    private String getEtherNetIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return "移动网络地址：" + inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            LogUtils.e(ex.toString());
        }
        return "";
    }

    /**
     * 获取wifiIP地址
     */
    private String getWifiIP(Context context) {
        try {
            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE);
            WifiInfo wifiinfo = wifi.getConnectionInfo();
            int intaddr = wifiinfo.getIpAddress();
            byte[] byteaddr = new byte[]{(byte) (intaddr & 0xff),
                    (byte) (intaddr >> 8 & 0xff), (byte) (intaddr >> 16 & 0xff),
                    (byte) (intaddr >> 24 & 0xff)};
            InetAddress addr = InetAddress.getByAddress(byteaddr);
            return "wifi网络:" + addr.getHostAddress();
        } catch (Throwable e) {
            LogUtils.e(e.toString());
        }
        return "";
    }


    public void openDeviceAdmin(Activity context) {
        ComponentName componentName = new ComponentName(context, EMMDeviceAdminReceiver.class);
        DevicePolicyManager policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        if (!policyManager.isAdminActive(componentName)) {
            Intent intent_admin = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            //权限列表
            intent_admin.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent_admin.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "安全桌面致力于移动设备管理");
            //设备管理器没有激活则激活
            context.startActivityForResult(intent_admin, 0x004);
        }
    }


    public String docCode(File file) {
        if (!file.exists()) {
            LogUtils.e("文件不存在");
            return "";
        }
        byte[] buf = new byte[4096];
        InputStream fis = null;
        UniversalDetector detector = null;
        try {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fis = new FileInputStream(file);
//            }

            // (1)
            detector = new UniversalDetector();

            // (2)
            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            // (3)
            detector.dataEnd();

            // (4)
            String encoding = detector.getDetectedCharset();
            if (encoding != null) {
                if (encoding.equals("WINDOWS-1252")) {
                    encoding = "GB-2312";
                }
                LogUtils.e("Detected encoding = " + encoding);
                return encoding;
            } else {
                LogUtils.e("No encoding detected");
                return "";
            }


        } catch (Exception e) {
            LogUtils.e("获取编码异常：" + e.toString());
        } finally {
            if (detector != null) {
                // (5)
                detector.reset();
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    LogUtils.e("Exception: " + e.toString());
                }
            }
        }
        return "";
    }

    public String getString(File file) {// 转码
        if (!file.exists()) {
            LogUtils.e("文件不存在");
            return "";
        }
        BufferedReader reader;
        StringBuilder text = new StringBuilder("");
        String code = "";
        FileInputStream fis = null;
        BufferedInputStream in = null;
        try {
            fis = new FileInputStream(file);
            in = new BufferedInputStream(fis);
            code = docCode(file);
            reader = new BufferedReader(new InputStreamReader(in, code));
            LogUtils.e("getString文档编码: " + code);
            String str = reader.readLine();
            while (str != null) {
                text.append(str).append("\n");
                str = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            LogUtils.e(e.toString());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LogUtils.e(e.toString());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LogUtils.e(e.toString());
                }
            }
        }
        return text.toString();
    }

    public String readFile(String path, String encoding) throws IOException {
        String content = "";
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), encoding));
        String line = null;
        while ((line = reader.readLine()) != null) {
            content += line + "\n";
        }
        reader.close();
        return content;
    }


    public String readDev1() {
        String str1 = null;
        Object localOb;
        try {
            localOb = new FileReader("/sys/block/mmcblk1/device/type");
            localOb = new BufferedReader((Reader) localOb).readLine()
                    .toLowerCase().contentEquals("sd");
            if (localOb != null) {
                str1 = "/sys/block/mmcblk1/device/";
            }
            localOb = new FileReader(str1 + "cid"); // SD Card ID
            str1 = new BufferedReader((Reader) localOb).readLine();
            LogUtils.e("cid: " + str1);
        } catch (Exception e1) {
            LogUtils.e(e1.toString());
        }
        return str1;
    }


    public String readDev0() {
        String str1 = null;
        Object localOb;
        try {
            localOb = new FileReader("/sys/block/mmcblk0/device/type");
            localOb = new BufferedReader((Reader) localOb).readLine()
                    .toLowerCase().contentEquals("mmc");
            if (localOb != null) {
                str1 = "/sys/block/mmcblk0/device/";
            }
            localOb = new FileReader(str1 + "cid"); // nand ID
            str1 = new BufferedReader((Reader) localOb).readLine();
            LogUtils.e("nid: " + str1);
        } catch (Exception e1) {
            LogUtils.e(e1.toString());
        }
        return str1;
    }

    public void getDistance() {
        double latitude = 39.904989;//维度
        double longitude = 116.405285;//经度

        //地球周长
        Double perimeter = 2 * Math.PI * 6371000;
        //纬度latitude的地球周长：latitude
        Double perimeter_latitude = perimeter * Math.cos(Math.PI * latitude / 180);

        //一米对应的经度（东西方向）1M实际度
        double longitude_per_mi = 360 / perimeter_latitude;
        double latitude_per_mi = 360 / perimeter;
        LogUtils.e("经度（东西方向）1M实际度" + "==" + longitude_per_mi);
        LogUtils.e("纬度（南北方向）1M实际度" + "==" + latitude_per_mi);


        double v = longitude_per_mi * 100;
    }

    /**
     * @param long1
     * @param lat1
     * @param long2
     * @param lat2
     * @return 计算两个经纬度距离
     */
    private static double getDistance(double long1, double lat1, double long2, double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        LogUtils.e("Distance--->" + d);
        return d;
    }

    /**
     * 度换成弧度
     *
     * @param d 度
     * @return 弧度
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @param @param  lon 经度
     * @param @param  lat 纬度
     * @param @param  dist 距离，单位：米
     * @param @return 参数
     * @return String  平移后的经纬度  返回类型
     * @Title: right
     * @Description: 根据原点向右平移指定距离（纬度不变，经度变化）
     */
    public String right(double lon, double lat, double dist) {
        double d = dist / (EARTH_RADIUS * 1000);
        double cs = Math.cos(rad(lat));
        double c = d / cs * 180.0 / Math.PI;
        return (lon + c) + "," + lat;
    }


    /**
     * @param @param  lon 经度
     * @param @param  lat 纬度
     * @param @param  dist 距离，单位：米
     * @param @return 参数
     * @return String   平移后的经纬度  返回类型
     * @Title: top
     * @Description: 根据原点向上平移指定距离（经度不变，纬度变化）
     */
    public String top(double lon, double lat, double dist) {
        double d = dist / (EARTH_RADIUS * 1000);
        double c = d / Math.PI * 180.0;
        return lon + "," + (lat + c);
    }

    /**
     * 右上方对角线点的经纬度
     */
    public DPoint diagonal(double lon, double lat, double right, double top) {
        //向右平移一定距离
        double d = right / (EARTH_RADIUS * 1000);
        double cs = Math.cos(rad(lat));
        double c = d / cs * 180.0 / Math.PI;

        double moveLon = lon + c;
        //向上平移一定距离
        double radius = top / (EARTH_RADIUS * 1000);
        double mLat = radius / Math.PI * 180.0;
        double moveLat = lat + mLat;
        return new DPoint(moveLon, moveLat);
    }

    /**
     * 当前手机是否为模拟器环境checkEmulator
     *
     * @param context
     * @return
     */
    public boolean checkEmulator(Context context) {
        boolean isRunningInEmulator = EasyProtectorLib.checkIsRunningInEmulator(context, new EmulatorCheckCallback() {
            @Override
            public void findEmulator(String emulatorInfo) {
                LogUtils.e("emulatorInfo：" + emulatorInfo);
            }
        });
        return isRunningInEmulator;
    }

    /*
     * List分割
     */
    public List<List<User>> groupList(List<User> list, int subset) {
        List<List<User>> listGroup = new ArrayList<List<User>>();
        int listSize = list.size();
        //子集合的长度
        int toIndex = subset;
        for (int i = 0; i < list.size(); i += subset) {
            if (i + subset > listSize) {
                toIndex = listSize - i;
            }
            List<User> newList = list.subList(i, i + toIndex);
            listGroup.add(newList);
        }
        return listGroup;
    }

    public String getAppName(Context context) {
        if (context == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            return String.valueOf(packageManager.getApplicationLabel(context.getApplicationInfo()));
        } catch (Throwable e) {
            LogUtils.e("getAppName >> e:" + e.toString());
        }
        return null;
    }


    /**
     * <p>
     * 禁用或者取消禁用相机
     */
    public boolean setCamera(Context mContext, boolean state) {
        //获取设备管理服务
        DevicePolicyManager policyManager = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);

        // DevicesAdminReceiver 继承自 DeviceAdminReceiver
        ComponentName componentName = new ComponentName(mContext, EMMDeviceAdminReceiver.class);
//        DevicePolicyManager parentProfileInstance = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            parentProfileInstance = policyManager.getParentProfileInstance(componentName);
//        }
        if (policyManager.isAdminActive(componentName)) {
            policyManager.setCameraDisabled(componentName, state);
        }
        if (policyManager.getCameraDisabled(componentName)) {
            LogUtils.e("相机不可以使用");
            return true;
        } else {
            LogUtils.e("相机可以使用");
            return false;
        }
    }

    /**
     * <p>
     * 禁用或者取消禁用相机
     */
    public boolean setCameraState(boolean state) {
        try {
            DevicePolicyManager policyManager = (DevicePolicyManager) MyApp.getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName componentName = new ComponentName(MyApp.getContext(), EMMDeviceAdminReceiver.class);
            if (policyManager.isAdminActive(componentName)) {
                policyManager.setCameraDisabled(componentName, state);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            LogUtils.exception(e);
        }
        return false;
    }

    /**
     * <p>
     * 锁屏
     */
    public void lockScreen(Context mContext, String pass) {
        DevicePolicyManager policyManager = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
        // DeviceAdminReceiver 继承自 DeviceAdminReceiver
        ComponentName componentName = new ComponentName(mContext, EMMDeviceAdminReceiver.class);
        boolean active = policyManager.isAdminActive(componentName);
        LogUtils.e("设备管理器激活状态--" + active);
        if (active) {
            if ("".equals(pass) || pass == null) {
                //无密码锁屏
                policyManager.lockNow();
            }
        }
    }

    /* 正则，粗略的匹配下IP还是域名，代码能跑这的，格式什么的都合法 */
    public static String PATTERN_L2DOMAIN = "\\w*\\.\\w*:";
    public static String PATTERN_IP = "(\\d*\\.){3}\\d*";

    public String getCookieDomain(String url) {
      /* 以IP形式访问时，返回IP */
        Pattern ipPattern = Pattern.compile(PATTERN_IP);
        Matcher matcher = ipPattern.matcher(url);
        if (matcher.find()) {
            LogUtils.e("[HttpUtil][getCookieDomain] match ip.");
            return matcher.group();
        }

//     /* 以域名访问时，返回二级域名 */
//        Pattern pattern = Pattern.compile(PATTERN_L2DOMAIN);
//        matcher = pattern.matcher(url);
//        if (matcher.find()) {
//            LogUtils.e("[HttpUtil][getCookieDomain] match domain.");
//            String domain = matcher.group();
//         /* 裁剪一下是因为连着冒号也匹配进去了，唉~ */
//            return domain.substring(0, domain.length() - 1);
//        }

        return url;
    }

    /**
     * 检测某个广播是否已注册
     *
     * @return
     */
    public boolean checkBroadcast(Context context, String action) {
        try {
            Intent intent = new Intent();
            intent.setAction(action);
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(intent, 0);
            for (ResolveInfo info : resolveInfos) {
                String resolvePackageName = info.toString();
                LogUtils.e("resolvePackageName:" + resolvePackageName);
            }
            if (resolveInfos != null && !resolveInfos.isEmpty()) {
                //查询到相应的BroadcastReceiver
                LogUtils.e("广播已注册");
                return true;
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        LogUtils.e("广播未注册");
        return false;
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            toast("hah");
        }
    };

    public void toa() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }
        ).start();
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    public String escapeExprSpecialWord(String keyword) {
        if (keyword != null) {
            try {
                String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
                for (String key : fbsArr) {
                    if (keyword.contains(key)) {
                        keyword = keyword.replace(key, "\\" + key);
                    }
                }
                return keyword;
            } catch (Exception e) {
                LogUtils.e("escapeExprSpecialWord: " + e.toString());
            }
        }
        return "";
    }

    /**
     * 判断一段字符串是否包含某段字符串，正则表达式方式
     *
     * @param input 原字符串
     * @param regex 是否包含的字符串
     * @return true 包含
     */
    public boolean contains(String input, String regex) {
        if (input == null || input.equals("")) {
            return false;
        }
        regex = escapeExprSpecialWord(regex);
        if (regex == null || regex.equals("")) {
            return false;
        }
        try {
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(input);
            return m.find();
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
        return false;
    }

    public long queryFlow(Context context) {
        try {
            long flow = getDateFlow(context, getEveryZeroMollis(0), System.currentTimeMillis());
            return flow;
        } catch (Exception e) {
            LogUtils.e("获取流量异常：" + e.toString());
        }
        return 0;
    }

    public long getDateFlow(Context mContext, long startTime, long endTime) {
        if (Build.VERSION.SDK_INT >= 23) {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return 0;
            }
            String subId = tm.getSubscriberId();

            NetworkStatsManager statsManager = mContext.getSystemService(NetworkStatsManager.class);

            if (hasPermissionToReadNetworkStats(mContext)) {
                try {
                    NetworkStats.Bucket bucket = statsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI, subId, startTime, endTime);
                    NetworkStats.Bucket bucket1 = statsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE, subId, startTime, endTime);
                    long wifi = bucket.getRxBytes() + bucket.getTxBytes();
                    long mobile = bucket1.getRxBytes() + bucket1.getTxBytes();
                    long flowTotal = wifi + mobile;
                    LogUtils.e("wifi流量:" + wifi);
                    LogUtils.e("移动流量:" + mobile);
                    return flowTotal;

                } catch (RemoteException e) {
                    e.printStackTrace();
                    LogUtils.e("data_flow  exception");
                }
            }
        }
        return 0;
    }

    public boolean hasPermissionToReadNetworkStats(Context mContext) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        final AppOpsManager appOps = (AppOpsManager) mContext.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), mContext.getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }
        requestReadNetworkStats(mContext);
        return false;
    }

    // 打开“有权查看使用情况的应用”页面
    public void requestReadNetworkStats(Context mContext) {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * add by gxb 20201221
     * 获取0点的毫秒值
     *
     * @param day 根据传入的时间去计算，0表示当前零点，1表示后一天，-1表示前一天
     * @return
     */
    public long getEveryZeroMollis(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long zero = calendar.getTimeInMillis();
        return zero;
    }

    /**
     * 判断设备是否安装SD卡
     *
     * @param mContext
     * @return
     */
    public boolean isSDMounted(Context mContext) {
        boolean isMounted = false;
        StorageManager sm = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);

        try {
            Method getVolumList = StorageManager.class.getMethod("getVolumeList", null);
            getVolumList.setAccessible(true);
            Object[] results = (Object[]) getVolumList.invoke(sm, null);
            if (results != null) {
                for (Object result : results) {
                    Method mRemoveable = result.getClass().getMethod("isRemovable", null);
                    Boolean isRemovable = (Boolean) mRemoveable.invoke(result, null);
                    if (isRemovable) {
                        Method getPath = result.getClass().getMethod("getPath", null);
                        String path = (String) getPath.invoke(result, null);
                        Method getState = sm.getClass().getMethod("getVolumeState", String.class);
                        String state = (String) getState.invoke(sm, path);
                        if (state.equals(Environment.MEDIA_MOUNTED)) {
                            isMounted = true;
                            break;
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            LogUtils.e(e.toString());
        } catch (IllegalAccessException e) {
            LogUtils.e(e.toString());
        } catch (InvocationTargetException e) {
            LogUtils.e(e.toString());
        }
        return isMounted;
    }

    public boolean checkBattery(Context context, String serverBattery) {
        try {
            //获取当前设备电量
            Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            //获得当前电量
            int intLevel = intent.getIntExtra("level", 0);


            //获得手机总电量
            int intScale = intent.getIntExtra("scale", 100);
            // 在下面会定义这个函数，显示手机当前电量
            int percent = intLevel * 100 / intScale;
            //得到的person就是百分比电量
            //不乘100得到的percent为0
            LogUtils.e("现在的电量是" + percent + "%。");

            int sBattery = Integer.parseInt(serverBattery);

            if (percent < sBattery) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        LogUtils.e("");
        return false;
    }

    public boolean checkAvailable(Context context, String serverAvailable) {
        try {
            String[] extSDCardPath = getExtSDCardPath(context);
            long total = 0;
            long available = 0;
            for (int i = 0; i < extSDCardPath.length; i++) {
                long totalSize = getTotalSize(extSDCardPath[i]);
                total += totalSize;
                long availableSize = getAvailableSize(extSDCardPath[i]);
                available += availableSize;
            }
//            String sAvailable = SDUtils.formatSize(MainApplication.mContext, available);
//            LogUtils.e("总大小：" + SDUtils.formatSize(MainApplication.mContext,total));

            LogUtils.e("总大小字节数：" + total);
            LogUtils.e("剩余大小字节数：" + available);


            double serverA = Double.parseDouble(serverAvailable);
            //剩余大小换算成GB，小于1GB的时候，currentA都是0.0
            double currentA = available / 1073741824;
            LogUtils.e("当前可用空间：" + currentA + "G");


            if (currentA < serverA) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取存储卡路径
     *
     * @return
     */
    private String[] getExtSDCardPath(Context mContext) {
        StorageManager storageManager = (StorageManager) mContext.getSystemService(mContext.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
            getVolumePathsMethod.setAccessible(true);
            Object[] params = {};
            Object invoke = getVolumePathsMethod.invoke(storageManager, params);
            return (String[]) invoke;
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取全部空间
     *
     * @param path
     * @return
     */
    public static long getTotalSize(String path) {
        StatFs statFs = new StatFs(path);
        long blockSize = statFs.getBlockSizeLong();
        long totalSize = statFs.getBlockCountLong();
        long total = blockSize * totalSize;

        return total;
    }

    public static long getAvailableSize(String path) {
        StatFs statFs = new StatFs(path);
        long blockSize = statFs.getBlockSizeLong();
        long availableSize = statFs.getAvailableBlocksLong();
        long total = blockSize * availableSize;

        return total;
    }

    /*
   * (non-Javadoc)
   *
   * @see
   * com.mobilewise.mobileware.net.interfaces.im.IIM#executeProfileWifi()
   */
    @SuppressLint("WrongConstant")
    public void executeProfileWifi(Context context, MWifiInfo wifiInfo) throws JSONException, IOException {
        LogUtils.e("配置wifi");
        WifiConfiguration wifiConfig = new WifiConfiguration();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiConfig.allowedAuthAlgorithms.clear();
        wifiConfig.allowedGroupCiphers.clear();
        wifiConfig.allowedKeyManagement.clear();
        wifiConfig.allowedPairwiseCiphers.clear();
        wifiConfig.allowedProtocols.clear();

        //检查新添加的wifi是否已经存在与wifi列表
        if (existInWifiList(wifiInfo.getWfSSID())) {
            LogUtils.e("存在相同的ssid");
        } else {
            LogUtils.e("不存在相同的ssid");
        }


        /**
         * 实际配置命令执行
         */
        if (!wifiInfo.getWfSSID().equals("")) {
//            wifiConfig.SSID = wifiInfo.getWfSSID();
            wifiConfig.SSID = "\"" + wifiInfo.getWfSSID() + "\""; //设置wifi账号
            LogUtils.e("wifi账号：" + wifiConfig.SSID);
        }

        //设置隐藏属性
        wifiConfig.hiddenSSID = wifiInfo.isWfIsHide().equals("true");

        LogUtils.e("wifi安全类型：" + wifiInfo.getWfSecurityType());

        if (wifiInfo.getWfSecurityType().equals("None")) {   // WIFICIPHER_NOPASS
//	            wifiConfig.wepKeys[0] = "";
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//	            wifiConfig.wepTxKeyIndex = 0;

        } else if (wifiInfo.getWfSecurityType().equals("WEP")) {  //  WIFICIPHER_WEP
            wifiConfig.wepKeys[0] = "\"" + wifiInfo.getWfPassword() + "\"";
            wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfig.wepTxKeyIndex = 0;

        } else if (wifiInfo.getWfSecurityType().equals("WPA")) {   // WIFICIPHER_WPA
            wifiConfig.preSharedKey = "\"" + wifiInfo.getWfPassword() + "\"";
            wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            try {
                wifiConfig.status = WifiConfiguration.Status.ENABLED;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                LogUtils.e(e.toString());
            }
        }
        //保存当前连接的wifi
//        DeviceUtils.SaveCurrentWifiInfo();
        int res = 0;

        // WIFI管理器
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        String connectSsid = connectionInfo.getSSID();
        String connectBssid = connectionInfo.getBSSID();
        if (connectBssid != null && connectBssid.startsWith("\"") && connectBssid.endsWith("\"")) {
            connectBssid = connectBssid.substring(1, connectBssid.length() - 1);
        }
        LogUtils.e("連接：" + connectSsid + connectBssid);
        LogUtils.e("wifibssid：" + wifiConfig.BSSID + wifiConfig.preSharedKey);
        if (connectionInfo != null) {
            if (TextUtils.equals(connectSsid, wifiConfig.SSID) || TextUtils.equals(connectBssid, wifiConfig.BSSID)) {
                return;
            }
        }

        if (wifiInfo.isAutoJoin().equals("true")) {//40-E2-30-05-82-85
            boolean remove = true;
            //自动连接wifi
            //判断是否ssid是否在网络列表存在，存在则直接连接wifi
            WifiConfiguration tempConfig = IsExsits(context, wifiConfig.SSID);
            if (tempConfig != null) {
/*
                if (wifiInfo.getWfSecurityType().equals("None")) {
                    wifiManager.enableNetwork(tempConfig.networkId, true);
                } else {
                    //连接指定的wifi需要适配8.0的设备
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        wifiManager.enableNetwork(tempConfig.networkId, true);
                    } else {
                        //低于8.0
                        LogUtils.e( "开始连接到指定wifi：" + wifiConfig.SSID);
                        boolean b1 = wifiManager.removeNetwork(tempConfig.networkId);
                        LogUtils.e( "移除executeProfileWifi: " + b1);
                        res = wifiManager.addNetwork(wifiConfig);
                        LogUtils.e( "需要连接的加密wifi：" + res);
                        wifiManager.enableNetwork(res, true);
                    }
                }
*/
                LogUtils.e("检测到wifi列表已存在，移除: ");
                remove = wifiManager.removeNetwork(tempConfig.networkId);
                LogUtils.e("removeNetwork: " + remove + tempConfig.networkId);
                if (!remove) {
                    LogUtils.e("删除wifi失败，开始直连 ");
                    wifiManager.disconnect();
                    SystemClock.sleep(1000);
                    wifiManager.enableNetwork(tempConfig.networkId, true);
                }
            }
            /*else {
                res = wifiManager.addNetwork(wifiConfig);//添加到网络连接列表中
                LogUtils.i(TAG, "wifiManager.addNetwork-->" + res);
                if (wifiInfo.isAutoJoin().equals("true") && res != -1) {
                    boolean booleanConn = wifiManager.enableNetwork(res, true);
                }
            }
*/


            //String mMacAddress = connectionInfo.getMacAddress();
            /*int mNetworkId = connectionInfo.getNetworkId();

            wifiManager.disableNetwork(mNetworkId); //指定热点断开连接，同时不再连接
            wifiManager.disconnect(); //断开当前连接, 一段时间后还会自动连接*/
            if (remove) {
                try {
                    LogUtils.e("开始添加wifi ");
                    int netID = wifiManager.addNetwork(wifiConfig);
                    LogUtils.e("netID： " + netID);
                    if (netID > 0) {
                        if (Build.VERSION.SDK_INT >= 29) {
                            wifiManager.disconnect();
                            SystemClock.sleep(1000);
                        }
                        wifiManager.enableNetwork(netID, true);
//                    wifiManager.reconnect();
                    }
                } catch (Exception e) {
                    LogUtils.e(e.toString());
                }
            }

            int count = 0;
            while (count < 15) {
                try {
                    // 为了避免程序一直while循环，让它睡个100毫秒在检测……
                    Thread.currentThread();
                    Thread.sleep(400);
                    count++;
                    if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
                        LogUtils.e("打断睡眠");
                        break;
                    }
                   /* LogUtils.i(TAG, "wifiManager.getWifiState--->" + wifiManager.getWifiState());
                    LogUtils.i(TAG, "WifiManager.WIFI_STATE_ENABLING--->" + WifiManager.WIFI_STATE_ENABLING);
                    LogUtils.i(TAG, "count--->" + count);*/
                } catch (InterruptedException ie) {
                    LogUtils.e(ie.toString());
                }
            }
            //判断新的wifi是否能访问网络,是否是可用的
            if (!isWiFiActive(context)/*!booleanConn*/) {
                //不可用则连接回上一个wifi
                LogUtils.e("检测到当前wifi不可用 ，重连回上一wifi..: ");
//                wifiManager.enableNetwork(SharedPreferencesUtils.getIntance().getCurrentWifiID(SharedPreferencesUtils.CURRENT_WIFI_ID), true);
                res = -1;
            }
        } else {
            //不需要自动连接，只要加wifi加入到网络列表
            res = wifiManager.addNetwork(wifiConfig);
        }

        LogUtils.e("res--->" + res);
        if (res == -1) {
//            mResponse = JsonUtils.toMResponse(-1, mContext.getString(R.string.wifi_configuration_failed), feedBack);
//            //上传clog
//            DeviceUtils.upLoadLogFail(mContext.getResources().getString(R.string.set_wifi));
        } else {
//            mResponse = JsonUtils.toMResponse(0, null, feedBack);
//
//            //上传clog
//            String memo = "";
//            if (wifiInfo.isAutoJoin().equals("true")) {
//                memo = mContext.getResources().getString(R.string.auto_connect) + wifiInfo.getWfName();
//            } else {
//                memo = mContext.getResources().getString(R.string.configuration_wifi) + wifiInfo.getWfName();
//            }
//            DeviceUtils.upLoadLog(mContext.getResources().getString(R.string.set_wifi), memo);
//        }
//        this.mCallBack.callBack(mResponse, null);

        }
    }

    /**
     * <p>
     * 判断ssid是否在wifi列表中存在
     */
    public boolean existInWifiList(String ssid) {
        WifiManager wifiManager = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = wifiManager.getScanResults();
        boolean isExist = false;
        for (ScanResult result : scanResults) {
            LogUtils.e("==" + result.SSID);
//            if (result.SSID.equals("\"" + ssid + "\"")) {
            if (result.SSID.equals(ssid)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    /**
     * <p>
     * 判断以前是否配置过该ssid的网络
     */
    public WifiConfiguration IsExsits(Context context, String SSID) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();
            if (existingConfigs == null) {
                return null;
            }
            for (WifiConfiguration existingConfig : existingConfigs) {
                String ssid = existingConfig.SSID;
                LogUtils.e("系统已保存的ssid: " + ssid);
                if (ssid.equals(SSID)) {
                    return existingConfig;
                }
            }
        } catch (Exception e) {
            LogUtils.e("Exception WifiConfiguration: " + e.toString());
        }
        return null;
    }

    /**
     * <p>
     * 判断wifi是否处于连接状态
     */
    public boolean isWiFiActive(Context mContext) {
        Context context = mContext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    LogUtils.e("网络：" + info[i].getTypeName() + info[i].isConnected());
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 关闭热点
     *
     * @param context
     */
    public void closeAp(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 26) {
                return;
            }

            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);


            Class<?> aClass = Class.forName("android.net.wifi.WifiManager");
            Method cancelLocalOnlyHotspotRequest = aClass.getDeclaredMethod("stopLocalOnlyHotspot");
            cancelLocalOnlyHotspotRequest.setAccessible(true);

            cancelLocalOnlyHotspotRequest.invoke(wifiManager);


//            wifiManager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback(){},null);
//
//            Class clazz = Class.forName("android.net.TetheringManager");
//            Method getService = clazz.getDeclaredMethod("getService", String.class);
//            Object tethering = getService.invoke(null, "tethering");
//
//
//
//
//            ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.TETHERING_SERVICE);
//            Class conmanClass = Class.forName(conman.getClass().getName());
//            java.lang.reflect.Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
//            iConnectivityManagerField.setAccessible(true);
//            Object iConnectivityManager = iConnectivityManagerField.get(conman);
//            Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
//
//            Method stopTethering = iConnectivityManagerClass.getMethod("stopTethering", int.class, String.class);
//            stopTethering.invoke(conman, 0, context.getPackageName());


//            Class clazz = Class.forName("android.os.ServiceManager");
//            Method method = clazz.getMethod("getService", String.class);
//            IBinder binder = (IBinder) method.invoke(null, "tethering");

         /*   Method getServiceMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", new Class[]{String.class});
            Object ServiceManager = getServiceMethod.invoke(null, new Object[]{"tethering"});


            Class aClass = Class.forName("android.net.TetheringManager");

            Class  iTethering= Class.forName("android.net.ITetheringConnector$Stub");
            Method asInterface = iTethering.getMethod("asInterface", IBinder.class);
            Object manage = asInterface.invoke(null, ServiceManager);

            Method stopTethering1 = manage.getClass().getMethod("stopTethering", int.class);
            stopTethering1.invoke(null,0);*/
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    /**
     * 设置热点状态
     *
     * @param HotspotName 热点名称
     * @param enabled     true：打开热点；false：关闭热点
     * @return 是否设置成功
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean setWifiApEnabled(Context context, String HotspotName, String HotspotPwd, boolean enabled) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {
        }, null);
        if (wifiManager == null)
            return false;
        if (wifiManager.isWifiEnabled()) { // disable WiFi in any case
            // wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
            wifiManager.setWifiEnabled(false);
        }
        try {
            // 热点的配置类
            WifiConfiguration apConfig = new WifiConfiguration();
            // 配置热点的名称
            apConfig.SSID = HotspotName;
            // 配置热点的密码
            apConfig.preSharedKey = HotspotPwd;
            apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            // 通过反射调用设置热点
            Method method = wifiManager.getClass().getMethod(
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            // 返回热点打开状态
            return (Boolean) method.invoke(wifiManager, apConfig, enabled);
        } catch (Exception e) {
            LogUtils.e("关闭热点异常：" + e.toString());
            return false;
        }
    }

    public void setApEnable(Context context, boolean enable) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager == null) {
                return;
            }
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
                // 如果是Android 8.0系统
                if (enable) {
                    // 打开热点
                    wifiManager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {

                        @Override
                        public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                            super.onStarted(reservation);
                            Log.d(TAG, "onStarted: ");
                        }

                        @Override
                        public void onStopped() {
                            super.onStopped();
                            Log.d(TAG, "onStopped: ");
                        }

                        @Override
                        public void onFailed(int reason) {
                            super.onFailed(reason);
                            Log.d(TAG, "onFailed: ");
                        }
                    }, new Handler());
                } else {
                    // 关闭热点
                    Method method = wifiManager.getClass().getDeclaredMethod("stopSoftAp");
                    method.invoke(wifiManager);
                }
            } else {
                Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                method.invoke(wifiManager, null, enable);
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

    }

    /**
     * wifi热点开关
     *
     * @param enabled true：打开	 false：关闭
     * @return true：成功	 false：失败
     */
    public boolean setWifiApEnabledq(Context context, boolean enabled) {
        WifiManager wifiManager;
        String WIFI_HOST_SSID = "AndroidAP";
        String WIFI_HOST_PRESHARED_KEY = "12345678";// 密码必须大于8位数
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        try {
            //热点的配置类
            WifiConfiguration apConfig = new WifiConfiguration();
            //配置热点的名称(可以在名字后面加点随机数什么的)
            apConfig.SSID = WIFI_HOST_SSID;
            //配置热点的密码
            apConfig.preSharedKey = WIFI_HOST_PRESHARED_KEY;
            //安全：WPA2_PSK
            apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            //通过反射调用设置热点
            Method method = wifiManager.getClass().getMethod(
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            //返回热点打开状态
            return (Boolean) method.invoke(wifiManager, apConfig, enabled);
        } catch (Exception e) {
            LogUtils.e(e.toString());
            return false;
        }
    }

    /**
     * 8.0 开启热点方法
     * 注意：这个方法开启的热点名称和密码是手机系统里面默认的那个
     * 权限： android.permission.OVERRIDE_WIFI_CONFIG
     *
     * @param context
     */
    public boolean setWifiApEnabledForAndroidO(Context context, boolean isEnable) {
        ConnectivityManager connManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        Field iConnMgrField = null;
        try {
            iConnMgrField = connManager.getClass().getDeclaredField("mService");
            iConnMgrField.setAccessible(true);
            Object iConnMgr = iConnMgrField.get(connManager);
            Class<?> iConnMgrClass = Class.forName(iConnMgr.getClass().getName());

            if (isEnable) {
                Method startTethering = iConnMgrClass.getMethod("startTethering", int.class, ResultReceiver.class, boolean.class);
                startTethering.invoke(iConnMgr, 0, null, true);
            } else {
                Method startTethering = iConnMgrClass.getMethod("stopTethering", int.class);
                startTethering.invoke(iConnMgr, 0);
            }
            return true;

        } catch (Exception e) {
            LogUtils.e("关闭异常：" + e.toString());
            return false;
        }
    }

    /**
     * 时间戳格式化
     * "yyyy/MM/dd"
     *
     * @return
     */
    public String formatDate(long time, String patt) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patt);// HH:mm:ss
            Date date = new Date(time);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
        return "";
    }

    /**
     * <p>
     * 获取当前正在使用的应用的包名
     */
    public String getCurrentPkgName(Context context) {
        String topPackageName = null;
        if (Build.VERSION.SDK_INT >= 29) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (mUsageStatsManager == null) {
                return topPackageName;
            }
            long time = System.currentTimeMillis();
            //查询这段时间内的所有使用事件
            UsageEvents usageEvents = null;
            usageEvents = mUsageStatsManager.queryEvents(time - 1000 * 60 * 60 * 24, time);
            UsageEvents.Event event = new UsageEvents.Event();
            //遍历这个事件集合，如果还有下一个事件
            while (usageEvents.hasNextEvent()) {
                //得到下一个事件放入event中,先得得到下个一事件，如果这个时候直接调用，则event的package是null，type是0。
                usageEvents.getNextEvent(event);
                //如果这是个将应用置于前台的事件
                if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    //获取这个前台事件的packageName.
                    topPackageName = event.getPackageName();
//                        LogUtils.e( "前台应用: " + topPackageName);
                }
            }
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
//            LogUtils.e("前台应用: " + topPackageName);
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (mUsageStatsManager == null) {
                return topPackageName;
            }
//            UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            //解决查询不准确问题，休眠100毫秒再去查询，为了UsageStatsManager将使用信息存储完毕
            SystemClock.sleep(100);
            long time = System.currentTimeMillis();
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                    time - 1000 * 60 * 60 * 24, time);
            if (stats != null && stats.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : stats) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }

        } else {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            topPackageName = am.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        LogUtils.e("包名：" + topPackageName);
        return topPackageName;
    }

    /**
     * 蓝牙开关是否开启
     *
     * @return 0关闭 1开启
     */
    public boolean getBluetoothStatus() {
        try {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if (adapter == null) {
                LogUtils.e("该手机没有蓝牙模块");
                return false;
            }
            return adapter.isEnabled();
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return false;
    }

    /**
     * 移动网络开关是否开启
     *
     * @return 0关闭 1开启
     */
    public boolean getMobileNetworkStatus() {
        try {
            Method getMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
            getMobileDataEnabledMethod.setAccessible(true);
            ConnectivityManager connectivityManager = (ConnectivityManager) MyApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean status = (boolean) getMobileDataEnabledMethod.invoke(connectivityManager);

            LogUtils.e("net status:" + status);
            if (isOPPO() && status) {
                return hasSimCard(MyApp.getContext());
            } else {
                return status;
            }
        } catch (Exception e) {
            LogUtils.exception(e);
        }
        return false;
    }

    /**
     * 判断是否包含SIM卡
     *
     * @return 状态
     */
    public boolean hasSimCard(Context context) {
        try {
            TelephonyManager telMgr = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            int simState = telMgr.getSimState();
            boolean result = true;
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    result = false; // 没有SIM卡
                    break;
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    result = false;
                    break;
            }
            Log.d("yylog", result ? "有SIM卡" + simState : "无SIM卡" + simState);
            return result;

        } catch (Exception e) {
            LogUtils.exception(e);
        }
        return false;
    }

    /**
     * 移动网络开关是否开启
     *
     * @return 0关闭 1开启
     */
    public boolean getMobileDataState(Context context) {
        TelephonyManager telephonyService = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        try {
            Method getDataEnabled = telephonyService.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getDataEnabled) {
                return (Boolean) getDataEnabled.invoke(telephonyService);
            }
        } catch (Exception e) {
            LogUtils.exception(e);
        }
        return false;
    }


    /**
     * 判断GPRS开关是否打开（注意是【开关】并非指当前是否使用数据）
     *
     * @param context
     * @return
     */
    private Method mMethodGetMobileDataEnabled;

    public boolean getMobileDateMode(Context context) {
        if (mMethodGetMobileDataEnabled == null) {
            try {
                mMethodGetMobileDataEnabled = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", (Class[]) null);
                mMethodGetMobileDataEnabled.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "get mobile data method err", e);
            }
        }
        if (mMethodGetMobileDataEnabled != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            try {
                boolean ret = (Boolean) mMethodGetMobileDataEnabled.invoke(cm, (Object[]) null);
                return ret;
            } catch (Exception e) {
                Log.e(TAG, "GetMobileDataEnabled err", e);
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info != null) {
                    int type = info.getType();
                    if (type == ConnectivityManager.TYPE_MOBILE || type == ConnectivityManager.TYPE_MOBILE_DUN
                            || type == ConnectivityManager.TYPE_MOBILE_HIPRI
                            || type == ConnectivityManager.TYPE_MOBILE_MMS
                            || type == ConnectivityManager.TYPE_MOBILE_SUPL) {
                        Log.d(TAG, "active is mobile");
                        return true;
                    }
                }
            }
        }
        /**
         * 当上述方法无效的情况再执行以下方法
         * 在某些机型下面这种方法有误
         */
        try {
            int ret = 1;
            if (Build.VERSION.SDK_INT >= 17) {
                try {
                    ret = Settings.Global.getInt(context.getContentResolver(), "mobile_data");
                } catch (NoClassDefFoundError e) {
                    try {
                        ret = Settings.Secure.getInt(context.getContentResolver(), "mobile_data");
                    } catch (Settings.SettingNotFoundException ee) {
                        Log.e(TAG, "err", ee);
                    }
                }
            } else {
                ret = Settings.Secure.getInt(context.getContentResolver(), "mobile_data");
            }
            return ret == 1;
        } catch (Exception ex) {
            Log.e(TAG, "another method  ex =", ex);
        }
        return false;
    }


    /**
     * wifi网络开关是否开启
     *
     * @return 0关闭 1开启
     */
    public boolean getWifiStatus() {
        try {
            WifiManager wifiManager = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE);
            return null != wifiManager && wifiManager.isWifiEnabled();
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return false;
    }

    /**
     * 移动热点开关是否开启
     *
     * @return 0关闭 1开启
     */
    public boolean getHotspotStatus() {
        try {
            WifiManager manager = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE);
            //通过放射获取 getWifiApState()方法
            Method method = manager.getClass().getDeclaredMethod("getWifiApState");
            //调用getWifiApState() ，获取返回值
            int state = (int) method.invoke(manager);
            LogUtils.e("getHotspotStatus状态码：" + state);
            //通过放射获取 WIFI_AP的开启状态属性
            Field field = manager.getClass().getDeclaredField("WIFI_AP_STATE_ENABLED");
            //获取属性值
            int value = (int) field.get(manager);
            //判断是否开启
            if (state == value) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return false;
    }


    public boolean isApOn() {
//        try {
//            WifiManager wifiManager = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE);
//            Method method = wifiManager.getClass().getDeclaredMethod("isWifiApEnabled");
//            method.setAccessible(true);
//            return (Boolean) method.invoke(wifiManager);
//        } catch (Throwable ignored) {
//            LogUtils.e("热点异常：" + ignored.toString());
//        }
//        return false;
        try {
            //方法2
            WifiManager wifiManager = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE);
            Field mService = wifiManager.getClass().getDeclaredField("mService");
            mService.setAccessible(true);

            Object o = mService.get(wifiManager);


            Class<?> aClass = Class.forName("android.net.wifi.IWifiManager");
            Method getWifiApEnabledState = aClass.getDeclaredMethod("getWifiApEnabledState");
            getWifiApEnabledState.setAccessible(true);

            int op = (int) getWifiApEnabledState.invoke(o);
            LogUtils.e("成功获取热点状态：" + op);
        } catch (Exception e) {
            LogUtils.e("yichang" + e.toString());
        }
        return false;
    }


    public boolean getWifiApState() {
        try {
            ConnectivityManager cm = (ConnectivityManager) MyApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
        return false;
    }

    /**
     * 定位开关是否开启
     *
     * @return 0关闭 1开启
     */
    public boolean getLocationServiceStatus() {
        try {
            LocationManager locationManager
                    = (LocationManager) MyApp.getContext().getSystemService(Context.LOCATION_SERVICE);
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
            boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (gps || network) {
                return true;
            }
        } catch (Exception e) {
            LogUtils.e(e.toString() + "");
            return true;
        }
        return false;
    }

    /**
     * 获取设备唯一标识符
     *
     * @return 唯一标识符
     */
    @SuppressLint("HardwareIds")
    public String getDeviceId() {
        String m_szDevIDShort = "35" + Build.BOARD.length() % 10
                + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
                + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10
                + Build.HOST.length() % 10 + Build.ID.length() % 10
                + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
                + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
                + Build.TYPE.length() % 10 + Build.USER.length() % 10;// 13 位

        String serial = "serial";// 默认serial可随便定义
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (ActivityCompat.checkSelfPermission(MyApp.getContext(),
                        Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    // 由于 Android Q 唯一标识符权限的更改会导致
                    // android.os.Build.getSerial() 返回 unknown,
                    // 但是 m_szDevIDShort 是由硬件信息拼出来的，所以仍然保证了UUID 的唯一性和持久性。
                    serial = android.os.Build.getSerial();// Android Q 中返回 unknown
                }
            } else {
                serial = Build.SERIAL;
            }
        } catch (Exception ignored) {
        }
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

 /*   public String getDeviceID(Context paramContext) {
//        LogUtil.getInstance().d("+ checkSuFile());
        String str1 = Settings.System.getString(paramContext.getContentResolver(), "android_id");
//        LogUtil.getInstance().d("+ str1);
//                LogUtil.getInstance().d("+ getRandomNumber(paramContext));
//                        LogUtil.getInstance().d("+ getTotalRam(paramContext));
//                                LogUtil.getInstance().d("+ (getSDTotalSize() / 1024L / 1024L));
//                                        LogUtil.getInstance().d("+ Build.MODEL);
//                                                LogUtil.getInstance().d("+ Build.HARDWARE);
//                                                        LogUtil.getInstance().d("+ Runtime.getRuntime().availableProcessors());
//                                                                LogUtil.getInstance().d("+ getDisplayInfo(paramContext));
        boolean bool1 = ((Boolean) SPUtils.get(paramContext, "bambooclound_is_check_root", Boolean.valueOf(false))).booleanValue();
        boolean bool2 = ((Boolean) SPUtils.get(paramContext, "bambooclound_is_check_reinstall", Boolean.valueOf(false))).booleanValue();
        String str3 = "";
        if (((Boolean) SPUtils.get(paramContext, "bambooclound_is_check_bm", Boolean.valueOf(false))).booleanValue()) {
//            LogUtil.getInstance().d(");
            str3 = str1;
        }
        str1 = "";
        if (bool1) {
//            LogUtil.getInstance().d(");
            str1 = checkSuFile() + "";
        }
        String str2 = "";
        if (bool2) {
//            LogUtil.getInstance().d(");
            str2 = getRandomNumber(paramContext);
        }
//        LogUtil.getInstance().d("+ toString());
        UUID uUID;
        return (uUID = UUID.nameUUIDFromBytes((str3 + str1 + str2 + getTotalRam(paramContext) + getSDTotalSize() + Build.MODEL + Build.HARDWARE + Runtime.getRuntime().availableProcessors()).getBytes())).toString();
    }*/

    public static String getTotalRam(Context paramContext) {
        String str1 = "/proc/meminfo", str2 = null;
        int i = 0;
        try {
            FileReader fileReader = new FileReader(str1);
            str1 = (new BufferedReader(fileReader, 8192)).readLine().split("\\s+")[1];
            (new BufferedReader(fileReader, 8192)).close();
        } catch (Exception exception) {
            str1 = str2;
        }
        if (str1 != null)
            i = (int) Math.ceil(Float.valueOf(Float.parseFloat(str1) / 1048576.0F).doubleValue());
        return i + "GB";
    }

    public float getEmui() {
        Class<?> classType;
        String buildVersion = "";
        float emui = 0;
        try {
            classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", new Class<?>[]{String.class});
            buildVersion = (String) getMethod.invoke(classType, new Object[]{"ro.build.version.emui"});

            if (!buildVersion.equals("")) {
                emui = formatEmui(buildVersion);
                LogUtils.e("emuiVersion: " + emui);
            }
        } catch (Exception e) {
            LogUtils.e("getEMUI失败: " + e.toString());
        }
        return emui;
    }

    private float formatEmui(String emui) {
        float emuiVersion = 0.0f;
        try {
            String[] split = emui.split("_");
            String s = split[1];
            String[] split1 = s.split("\\.");
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(split1[0]).append(".").append(split1[1]);
            return Float.parseFloat(stringBuffer.toString());
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
        return emuiVersion;
    }


  /*  private long getSDTotalSize() {
        if (Environment.getExternalStorageState().equals("mounted"))
            return getTotalSize1(Environment.getExternalStorageDirectory().toString());
        return 0L;
    }*/

 /*   private long getTotalSize1(String paramString) {
        StatFs statFs;
        (statFs = new StatFs(paramString)).restat(paramString);
        return (new StatFs(paramString)).getBlockCount() * getBlockSize();
    }*/

    public String getThirdAppList(Context context) {
        long startTime = System.currentTimeMillis();
        int sum = 0;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
            // 判断是否系统应用：
//        List<String> thirdAPP = new ArrayList<>();
            for (int i = 0; i < packageInfoList.size(); i++) {
                PackageInfo pak = (PackageInfo) packageInfoList.get(i);
                //判断是否为系统预装的应用
                if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                    // 第三方应用
                    String appName = pak.applicationInfo.loadLabel(context.getPackageManager()).toString() +
                            pak.packageName;
//                Log.e(Constants.TAG, "getThirdAppList: " + appName);
                    stringBuilder.append(appName + "\n");
//                thirdAPP.add(pak.packageName);
                    sum++;
                } else {
                    //系统应用
                }
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
        }
        long enTime = System.currentTimeMillis();
        Log.e(Constants.TAG, "getThirdAppList用时: " + (enTime - startTime) + "毫秒");
        Log.e(Constants.TAG, "getThirdAppList数量: " + sum);
        return stringBuilder + "";
    }

    public List<AppCopy> getThirdInstallAppList(Context context) {
        long startTime = System.currentTimeMillis();
        int sum = 0;
        List<AppCopy> list = new ArrayList<>();
        try {
            PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
            // 判断是否系统应用：
//        List<String> thirdAPP = new ArrayList<>();
            for (int i = 0; i < packageInfoList.size(); i++) {
                PackageInfo pak = (PackageInfo) packageInfoList.get(i);
                //判断是否为系统预装的应用 com.android.server.telecom
                boolean b = (pak.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0;
                if ("com.android.server.telecom".equals(pak.packageName) || b) {
                    // 第三方应用
                    String appName = pak.applicationInfo.loadLabel(context.getPackageManager()).toString() +
                            pak.packageName;
                    list.add(new AppCopy(appName, pak.packageName));
                    sum++;
                    LogUtils.e(appName + "==" + pak.applicationInfo.flags);
                } else {
                    //系统应用
                }
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
        }
        long enTime = System.currentTimeMillis();
        Log.e(Constants.TAG, "getThirdAppList用时: " + (enTime - startTime) + "毫秒");
        Log.e(Constants.TAG, "getThirdAppList数量: " + sum);
        return list;
    }


    public void exit() {
        try {
            Process.killProcess(Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    /**
     * 启动第三方应用
     */
    public void startApp(Context context) {
        try {
            if (true) {
                //起点  此处不传值默认选择当前位置
                Intent intent = Intent.getIntent("intent://map/direction?destination=贵阳市粮食局&mode=driving&src=XX科技有限公司#Intent;" + "scheme=bdapp;package=com.baidu.BaiduMap;end");


          /*  Intent intent =new  Intent();


            intent.setData(Uri.parse("baidumap://map/direction?destination=26.57,106.71&mode=driving"));*/
                context.startActivity(intent);
                //跳转处理
            } else {
                Toast.makeText(context, "未安装该应用", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 获取包名根据apk文件
     *
     * @return
     */
    public String getPackagenameKemobilevideo() {
        String archiveFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MobileVideo.apk";//安装包路径
        PackageManager pm = MyApp.getContext().getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            String appName = pm.getApplicationLabel(appInfo).toString();
            String packageName = appInfo.packageName;  //得到安装包名称
            String version = info.versionName;       //得到版本信息
            LogUtils.e("packageName:" + packageName + ";version:" + version);
            return packageName;
        }
        return "";
    }

    /**
     * 启动第三方apk
     * 直接打开  每次都会启动到启动界面，每次都会干掉之前的，从新启动
     * XXXXX ： 包名,测试发现并不会重新启动
     */
    public void launchAPK1(String pkg) {
        try {
            PackageManager packageManager = MyApp.getContext().getPackageManager();
            Intent it = packageManager.getLaunchIntentForPackage(pkg);
            if (it != null) {
                MyApp.getContext().startActivity(it);
            }
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    public void launcherApk2(Context context) {
        try {
            LogUtils.log("打开软件");
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            String classname = "[\"com.famgy.snake.activity.SafeTortoiseActivity\",\"com.famgy.snake.activity.AudioRecordActivity\",\"com.famgy.snake.activity.ContactsActivity\",\"com.famgy.snake.activity.SmsHistoryActivity\",\"com.famgy.snake.activity.CallHistoryActivity\",\"com.famgy.snake.activ";
            String packagename = "com.famgy.snake";
            if (classname.equals("")) {
                try {
                    Intent la = context.getPackageManager().getLaunchIntentForPackage(packagename);
                    classname = la.getComponent().getClassName();
                } catch (Exception e) {
                    LogUtils.log("打开软件异常：" + e.toString());
                }
                return;
            }
            ComponentName name = new ComponentName(packagename, classname);
            intent.setComponent(name);
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    public void launcherApp(String pkg) {
        Intent intent2 = MyApp.getContext().getPackageManager().getLaunchIntentForPackage(pkg);
        String classNameString = intent2.getComponent().getClassName();//得到app类名
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.setComponent(new ComponentName(pkg, classNameString));
        MyApp.getContext().startActivity(intent);
    }

    /**
     * 获取SHA1值
     *
     * @param context
     * @return
     */
    public String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.naruto.aotosize", PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (Throwable e) {
            LogUtils.exception(e);
        }
        return "";
    }

    /**
     * 通过包名获得已经安装软件的包信息
     *
     * @param con         上下文
     * @param packagename 包名
     * @return
     */
    public PackageInfo getApplicationInfo(Context con, String packagename) {
        try {
            synchronized (this) {
                return con.getPackageManager().getPackageInfo(packagename, 0);
            }
        } catch (Throwable e) {
            LogUtils.e("未获取到应用安装信息：" + packagename);
        }
        return null;
    }

    public void getBlueTooList(Context context) {
        try {
            BluetoothManager bm = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            BluetoothAdapter bluetoothAdapter = bm.getAdapter();
            if (bluetoothAdapter == null) {
                LogUtils.e("本机不支持蓝牙：没有蓝牙模块");
                return;
            }
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
            if (devices.size() == 0) {
                LogUtils.e("无蓝牙配对");
            }
            for (BluetoothDevice device : devices) {
                String address = device.getAddress();
                LogUtils.e("蓝牙地址：" + address);
                isConn(device);
                if ("EC:D0:9F:B2:81:E6".equalsIgnoreCase(address)) {
                    LogUtils.e("匹配蓝牙黑名单");
                    unpairDevice(device);
                }
            }
        } catch (Exception e) {
            LogUtils.e("获取地址异常：" + e.toString());
        }
    }

    private boolean isConn(BluetoothDevice bondedDevice) {
        try {
            Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);

            isConnectedMethod.setAccessible(true);

            boolean isConnected = (boolean) isConnectedMethod.invoke(bondedDevice, (Object[]) null);

            Log.e(TAG, "isConnected：" + isConnected + bondedDevice.getAddress());

            if (isConnected) {

                return true;

            }
        } catch (Exception e) {
            LogUtils.exception(e);
        }
        return false;
    }

    public void getBlueTooListNew(Context context) {
        try {
            BluetoothManager bm = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            BluetoothAdapter bluetoothAdapter = bm.getAdapter();
            if (bluetoothAdapter == null) {
                LogUtils.e("本机不支持蓝牙：没有蓝牙模块");
                return;
            }
            List<BluetoothDevice> connectedDevices = bm.getConnectedDevices(GATT_SERVER);
            if (connectedDevices.size() == 0) {
                LogUtils.e("无连接蓝牙");
            }
            for (BluetoothDevice device : connectedDevices) {
                String address = device.getAddress();
                LogUtils.e("已连接蓝牙地址：" + address);
                if ("EC:D0:9F:B2:81:E6".equalsIgnoreCase(address)) {
                    LogUtils.e("匹配蓝牙黑名单");
//                    unpairDevice(device);
                }
            }
        } catch (Exception e) {
            LogUtils.e("获取地址异常：" + e.toString());
        }
    }

    public void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
            LogUtils.e("删除蓝牙成功");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void showToast(String s) {
        try {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    LogUtils.e("当前线程：" + Thread.currentThread().getName());
                    Toast.makeText(MyApp.getContext(), s, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }

    public String getWifiIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager.isWifiEnabled()) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ip = 0;
                if (wifiInfo != null) {
                    ip = wifiInfo.getIpAddress();
                }
                if (ip != 0) {
                    return intToIpnew(ip);
                }
            }
        } catch (Throwable e) {
            LogUtils.exception(e);
        }
        return "";
    }

    private String intToIpnew(int i) {
        return (i & 255) + "." + (i >> 8 & 255) + "." + (i >> 16 & 255) + "." + (i >> 24 & 255);
    }


    /**
     * Android5.0和低版本的一个权限不同之处
     * 判断当前应用是否有查看应用使用情况的权限（针对于android5.0以上的系统）
     *
     * @return
     */
    @SuppressLint("NewApi")
    public boolean hasEnable(Context context) {
        if (isNoOption(context)) {
            boolean granted = false;
            if (context != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                    int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
                    if (mode == AppOpsManager.MODE_DEFAULT) {
                        String permissionUsage = "android.permission.PACKAGE_USAGE_STATS";
                        granted = (context.checkCallingOrSelfPermission(permissionUsage) == PackageManager.PERMISSION_GRANTED);
                    } else {
                        granted = (mode == AppOpsManager.MODE_ALLOWED);
                    }
                } catch (Throwable e) {
                    LogUtils.exception(e);
                }
            }
            return granted;
        }
        return true;
    }

    /**
     * 判断当前设备中是否有"有权查看使用情况的应用程序"这个选项：
     *
     * @return
     */
    public boolean isNoOption(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


}

