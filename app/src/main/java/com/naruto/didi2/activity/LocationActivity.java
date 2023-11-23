package com.naruto.didi2.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.naruto.didi2.R;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mInfoLocation;
    private Button mStartLocation;
    //声明AMapLocationClient类对象

    AMapLocationClient mLocationClient = null;
    private Button mHightAccuracyBt;
    private Button mBatterySavingBt;
    private Button mDeviceSensorsBt;
    private TextView mModelTv;
    private Button mPrivacyBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initView();

        gotoDeviceAdminActivity(this);
    }

    /**
     * 高精度
     */
    private void initLocation() {
        try {
            mModelTv.setText("高精度模式");
            //声明AMapLocationClientOption对象
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置单次定位

            //声明定位回调监听器
            AMapLocationListener mLocationListener = new MyAMapLocationListener();
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());

            //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);


            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
            mLocationOption.setHttpTimeOut(10000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
            mLocationOption.setNeedAddress(true);//设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
            mLocationOption.setMockEnable(false); //设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
            mLocationOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
            mLocationOption.setOnceLocation(true);


            mLocationClient.setLocationOption(mLocationOption);

        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }


    private void gotoDeviceAdminActivity(Context context) {
        Intent intent = new Intent();
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.DeviceAdminSettings");//com.android.settings.MiuiSettings
        intent.setComponent(cm);
        /**
         *zq 20171016 配合FLAG_ACTIVITY_NEW_TASK和FLAG_ACTIVITY_NEW_DOCUMENT使用，在启动时不再寻找匹配的Activity，而是直接启动新任务。
         * 防止系统设置界面开启状态下 跳转设备管理器激活界面后 点击返回按钮要走系统设置界面逻辑 应该系统设置界面任务栈中已打开的Activity界面被加载进来 要全部返回
         * 才能退到原来EMM中的permissionsActivity界面
         */

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
//		intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        intent.setAction("android.intent.action.VIEW");
        context.startActivity(intent);
    }

    /**
     * 低功耗
     */
    private void batterySaving() {
        try {
            mModelTv.setText("低功耗模式");
            //声明AMapLocationClientOption对象
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置单次定位

            //声明定位回调监听器
            AMapLocationListener mLocationListener = new MyAMapLocationListener();
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());

            //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);


            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            mLocationOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
            mLocationOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
            mLocationOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
            mLocationOption.setOnceLocation(true);


            mLocationClient.setLocationOption(mLocationOption);

        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    /**
     * 仅设备
     */
    private void deviceSensors() {
        try {
            mModelTv.setText("仅设备模式");
            //声明AMapLocationClientOption对象
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置单次定位

            //声明定位回调监听器
            AMapLocationListener mLocationListener = new MyAMapLocationListener();
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());

            //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);


            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
            mLocationOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
            mLocationOption.setOnceLocation(true);


            mLocationClient.setLocationOption(mLocationOption);

        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    private void initView() {
        mInfoLocation = (TextView) findViewById(R.id.location_info);
        mStartLocation = (Button) findViewById(R.id.location_start);
        mStartLocation.setOnClickListener(this);
        mHightAccuracyBt = (Button) findViewById(R.id.bt_Hight_Accuracy);
        mHightAccuracyBt.setOnClickListener(this);
        mBatterySavingBt = (Button) findViewById(R.id.bt_Battery_Saving);
        mBatterySavingBt.setOnClickListener(this);
        mDeviceSensorsBt = (Button) findViewById(R.id.bt_Device_Sensors);
        mDeviceSensorsBt.setOnClickListener(this);
        mModelTv = (TextView) findViewById(R.id.tv_model);
        mPrivacyBt = (Button) findViewById(R.id.bt_privacy);
        mPrivacyBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_start:
                // TODO 21/11/11
                if (mInfoLocation != null) {
                    mInfoLocation.setText("");
                }
                //开始定位
//                if (mLocationClient != null) {
//                    mLocationClient.startLocation();
//                }
                AppUtil.getInstance().getThirdInstallAppList(this);
                break;
            case R.id.bt_Hight_Accuracy:// TODO 22/01/21
                initLocation();
                break;
            case R.id.bt_Battery_Saving:// TODO 22/01/21
                batterySaving();
                break;
            case R.id.bt_Device_Sensors:// TODO 22/01/21
                deviceSensors();
                break;
            case R.id.bt_privacy:// TODO 23/02/24
                try {
                    AMapLocationClient.updatePrivacyShow(this, true, true);
                    AMapLocationClient.updatePrivacyAgree(this, true);
                } catch (Exception e) {
                    LogUtils.exception(e);
                }
                break;
            default:
                break;
        }
    }

    class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    LogUtils.e("获取到定位信息：" + amapLocation.getLongitude() + "," + amapLocation.getLatitude() + "\n" +
                            amapLocation.getAddress() + "," + amapLocation.getLocationType() + "," + amapLocation.getAccuracy()
                    );
                    StringBuffer stringBuffer = new StringBuffer();
                    String des = getDes(amapLocation.getLocationType());
                    stringBuffer.append("获取到定位信息：").append("\n")
                            .append(amapLocation.getLongitude()).append(",").append(amapLocation.getLatitude())
                            .append("\n")
                            .append(amapLocation.getAddress()).append("\n")
                            .append(des).append(",").append(amapLocation.getAccuracy());

                    mInfoLocation.setText(stringBuffer);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    mInfoLocation.setText("location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
//                    LogUtils.e("location Error, ErrCode:"
//                            + amapLocation.getErrorCode() + ", errInfo:"
//                            + amapLocation.getErrorInfo());
                }
            }
        }
    }

    private String getDes(int code) {
        String des = "";
        switch (code) {
            case AMapLocation.LOCATION_TYPE_GPS:
                des = "卫星定位";
                break;
            case AMapLocation.LOCATION_TYPE_SAME_REQ:
                des = "前次定位";
                break;
            case AMapLocation.LOCATION_TYPE_FIX_CACHE:
                des = "缓存定位";
                break;
            case AMapLocation.LOCATION_TYPE_WIFI:
                des = "Wifi定位";
                break;
            case AMapLocation.LOCATION_TYPE_CELL:
                des = "基站定位";
                break;
            default:
                des = code + "";
                break;
        }
        return des;
    }
}
