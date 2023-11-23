package com.naruto.didi2.activity.third;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.model.LatLng;
import com.naruto.didi2.R;
import com.naruto.didi2.bean.Legion;
import com.naruto.didi2.broadcast.AppInstallReceiver;
import com.naruto.didi2.dbdao.LegionDao;
import com.naruto.didi2.util.FileUtils;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.OkHttpUtil;
import com.naruto.didi2.util.ThreadPoolUtil;
import com.naruto.didi2.util.WorkThread;
import com.naruto.didi2.view.EmojiEditText;
import com.naruto.func.ClockUpdateReceiver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MnActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yylog";
    private RecyclerView mContentRv;
    private Button mReqBt;
    private String[] per = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private Button mReqgcBt;
    private EmojiEditText mFeifaEjed;
    private EditText mOkEt;
    private Button mFileBt;
    private Button mCompressorBt;
    private Button mRequestBt;
    private int mType = 10020;
    private AppInstallReceiver appInstallReceiver;
    private ClockUpdateReceiver clockUpdateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mn);
        initView();

//        String start = SpUtils.getStringValue(this, "start");
//        if (!"true".equals(start)) {
//            Toast.makeText(this, "请在安全门户进行证书验证后使用本应用", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }

//        try {
//            String s1 = SM.doSM4EncryptData("97F6556B775119E0DE09E7314F64021D");
//            String s = SM.doSM4DecryptData(s1);
//            LogUtils.e("签名加密：" + s1 + "\n" + "签名解密：" + s);
//            String localSignal = (String) AppUtil.getInstance().getStringFromAssets(this, "signal");
//            LogUtils.e("本地签名：" + localSignal);
//        } catch (Exception e) {
//            LogUtils.e("" + e.toString());
//        }
        setEt();


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 123);

//        appInstallReceiver = new AppInstallReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
//        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
//        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
//        intentFilter.addDataScheme("package");
//        registerReceiver(appInstallReceiver, intentFilter);


        clockUpdateReceiver = new ClockUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter("naruto.intent.clockupdate");
        registerReceiver(clockUpdateReceiver, intentFilter);

        startClock();
//        startClockByTimer();
    }

    private void startClockByTimer() {
        try {
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
//                    mHandler.sendEmptyMessage(0);
//                    Intent intent = new Intent("naruto.intent.clockupdate");
                    Intent intent = new Intent("naruto.intent.permission");
                    String packageName = getPackageName();
                    LogUtils.e("发送广播：" + packageName);

                    intent.addFlags(0x01000000);
//                    intent.setComponent(new ComponentName(packageName, "com.naruto.func.ClockUpdateReceiver"));
//                    intent.setComponent(new ComponentName(MnActivity.this, PermissionReceiver.class));
                    sendBroadcast(intent);
                }
            };
            timer.schedule(timerTask, 0, 10 * 1000);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }


    private void setEt() {
        mOkEt.addTextChangedListener(new TextWatcher()
                //如果用户名改变，清空密码
        {
            /**
             * 分别监测了文本"改变前"、"改变时"、"改变中"三种状态。
             * @param s
             * @param start
             * @param count
             * @param after
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: ");
//                Log.d(TAG, "beforeTextChanged: +Textsum=" + s.toString().length() + ",start=" + start + ",count=" + count + ",after=" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d(TAG, "onTextChanged: ");
//                Log.d(TAG, "onTextChanged: +Textsum=" + s.toString().length() + ",start=" + start + ",count=" + count + ",befor=" + before);
//                passwordEditText.setText("");  // 当用户名发生变化，清空密码。如果要限定字数，可以xml中用maxlength
                String s1 = s.toString();
                boolean containSpecialChar = isContainSpecialChar(s1);
                if (containSpecialChar) {
                    LogUtils.e("输入了特殊字符：" + s1);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: ");
//                Log.d(TAG, "afterTextChanged=" + s.toString().length());
            }
        });
    }


    private void startClock() {
        try {
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (manager == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent("naruto.intent.clockupdate");
//                Intent intent = new Intent("naruto.intent.permission");
//                intent.addFlags(0x01000000);
//                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);

//                intent.setComponent(new ComponentName(getPackageName(), "com.naruto.func.ClockUpdateReceiver"));//应用间广播
//                intent.setComponent(new ComponentName(this, ClockUpdateReceiver.class));//应用间广播
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);
//                manager.cancel(pendingIntent);
            }


           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent("naruto.intent.clockupdate");
//                intent.addFlags(0x01000000);
                intent.setComponent(new ComponentName(getPackageName(), "com.naruto.func.ClockUpdateReceiver"));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//                manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);
                manager.cancel(pendingIntent);
            }*/
        } catch (Throwable e) {
            LogUtils.e(e.toString());
        }
    }

    /**
     * 校验是否包含特殊字符
     *
     * @param str
     * @return
     */
    public boolean isContainSpecialChar(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(str);
        boolean b = matcher.find();
        return b;
    }

    private void initView() {
        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
        mReqBt = (Button) findViewById(R.id.bt_req);
        mReqBt.setOnClickListener(this);
        mReqgcBt = (Button) findViewById(R.id.bt_reqgc);
        mReqgcBt.setOnClickListener(this);
        mFeifaEjed = (EmojiEditText) findViewById(R.id.ejed_feifa);
        mOkEt = (EditText) findViewById(R.id.et_ok);
        mFileBt = (Button) findViewById(R.id.bt_file);
        mFileBt.setOnClickListener(this);
        mCompressorBt = (Button) findViewById(R.id.bt_compressor);
        mCompressorBt.setOnClickListener(this);
        mRequestBt = (Button) findViewById(R.id.bt_request);
        mRequestBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_req:
                Intent intent = new Intent("emm.intent.testone");
//                intent.setComponent(new ComponentName(this,ClockUpdateReceiver.class));
                sendBroadcast(intent);
                break;
            case R.id.bt_file:// TODO 21/10/27
//                PackageInfo applicationInfo = AppUtil.getInstance().getApplicationInfo(this, "com.didi2.myapplication");
//
//                if (applicationInfo != null) {
//                    LogUtils.e("获取应用信息：" + applicationInfo.packageName);
//                    LogUtils.e("获取应用信息：" + applicationInfo.versionName);
//                    LogUtils.e("获取应用信息：" + applicationInfo.versionCode);
//                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    LogUtils.e(e.toString());
                }
                break;
            case R.id.bt_compressor:// TODO 21/10/28
                LocationClient locationClient = new LocationClient(getApplicationContext());
                String version = locationClient.getVersion();
                LogUtils.e("定位版本：" + version);
                break;
            case R.id.bt_reqgc:// TODO 22/04/14
                setCameraDisabled(true);
                break;
            case R.id.bt_request:// TODO 22/04/14
                startTask();
                break;
            default:
                break;
        }
    }

    private String getContent() {
        return "mingren";
    }

    private String getContent1() {
        return "chutian";
    }

    private void startTask() {
        try {
            Legion legion = new Legion();
            legion.setName("{\n" +
                    "\t\"state\": \"0\",\n" +
                    "\t\"list\": [\n" +
                    "\t\t\"94:0E:6B:54:19:94\"\n" +
                    "\t]\n" +
                    "}");
            LegionDao.getInstance().add(legion);
            LegionDao.getInstance().query();
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (appInstallReceiver != null) {
//            unregisterReceiver(appInstallReceiver);
//        }

//        unregisterReceiver(clockUpdateReceiver);
    }

    private void test() {
        String data = MediaStore.Video.Media.DATA;
        ContentValues contentValues = new ContentValues();
        contentValues.put(data, "heihei");

        Uri insert = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
        LogUtils.e("insert:" + insert);
        Cursor cursor = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null);
                if (cursor == null)
                    return;
                int columnCount = cursor.getColumnCount();
                String[] columnNames = cursor.getColumnNames();

                LogUtils.e("数据列：" + columnCount + Arrays.toString(columnNames));
                while (cursor.moveToNext()) {
                    String display_name = cursor.getString(cursor.getColumnIndex("_display_name"));
                    String _size = cursor.getString(cursor.getColumnIndex("_size"));
                    LogUtils.e("文件：" + display_name + _size);
                }
            }
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void test63() {
        try {
            ArrayList<String> devices = new ArrayList<>();
            devices.add("56:58:69:25:66:77");
            ArrayList<String> maclist = new ArrayList<>();
            for (String d : devices) {
                JSONObject jsonObject = new JSONObject();
                String replace = d.replace(':', '-');
                jsonObject.put("Mac", replace);
                maclist.add(jsonObject.toString());
            }
            LogUtils.e(maclist.toString());
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

//        [{
//            "permision":"CALL_PHONE"，"mode":"DISALLOW"
//        }，{
//            "permision":"SEND_SMS"，"mode":"ALLOWED"
//        } ]

        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("permision", "CAMERA");
            jsonObject.put("mode", "ALLOWED");
            jsonArray.put(jsonObject);
            LogUtils.e(jsonArray.toString());
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }

    }

    void apn(String numeric) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject apn = new JSONObject();
            apn.put("name", "test12测试");
            apn.put("apn", "test12");
            apn.put("type", "default");
            apn.put("numeric", numeric);
            apn.put("mcc", "MCC");
            apn.put("mnc", "NMC");
            apn.put("proxy", "");
            apn.put("port", "20221");
            apn.put("mmsproxy", "");
            apn.put("mmsport", "23001");
            apn.put("user", "tysj79");
            apn.put("server", "");
            apn.put("password", "abcd1234");
            apn.put("mmsc", "MMSC");
            jsonObject.put("apnInfo", apn);

            LogUtils.e("apn:" + jsonObject.toString());
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    public boolean setCameraDisabled(boolean disabled) {
        try {
        } catch (Throwable e) {
            LogUtils.exception(e);
        }
        return false;
    }

    private void operationFile() {
        ThreadPoolUtil.getInstance().start(new WorkThread() {
            @Override
            public void runInner() {
                try {
                    long start = System.currentTimeMillis();
                    String file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/apk";
                    long folderSize = FileUtils.getFolderSize(new File(file));

                    long currentTimeMillis = System.currentTimeMillis();
                    long fSize = folderSize / 1024 / 1024;
                    LogUtils.e("文件夹大小：" + fSize + "m  用时：" + (currentTimeMillis - start));

                    if (fSize > 10) {
                        LogUtils.e("文件夹超出限定大小，需清空");
                        long dStart = System.currentTimeMillis();
                        FileUtils.deleteFolderFile(file, true);
                        long dCurrentTimeMillis = System.currentTimeMillis();
                        LogUtils.e("清空文件夹用时：" + (dCurrentTimeMillis - dStart));
                    }
                } catch (Exception e) {
                    LogUtils.exception(e);
                }
            }
        });
    }

    private void compressor() {

    }

    private void fileTest() {
        ThreadPoolUtil.getInstance().start(new WorkThread() {
            @Override
            public void runInner() {
                try {
                    String path = Environment.getExternalStorageDirectory().getPath() + "/pic/pp.jpg";
                    List<String> objects = new ArrayList<>();
                    objects.add(path);
                    OkHttpUtil.getInstance().uploadImage("", objects);
                } catch (Exception e) {
                    LogUtils.e("" + e.toString());
                }
            }
        });

    }

    private void configLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

        try {

            locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, true, true,

                    false, true, true, true, 0, 5);

        } catch (Exception e) {

            e.printStackTrace();

        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            try {

                locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        try {

            locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
//            116.195215,39.996127
            LatLng latLng = new LatLng(39.996127d, 116.195215d);
            locationManager.setTestProviderLocation(provider.getName(), generateLocation(latLng));
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

    }

    public Location generateLocation(LatLng latLng) {
        Location loc = new Location("gps");


        loc.setAccuracy(2.0F);
        loc.setAltitude(55.0D);
        loc.setBearing(1.0F);
        Bundle bundle = new Bundle();
        bundle.putInt("satellites", 7);
        loc.setExtras(bundle);


        loc.setLatitude(latLng.latitude);
        loc.setLongitude(latLng.longitude);

        loc.setTime(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= 17) {
            loc.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }
        return loc;
    }

}
