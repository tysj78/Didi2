package com.naruto.didi2.activity.test;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class FanSheActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mMethodCall;
    private Button mMethodNoneCall;
    private Button mAttributePublicGet;
    private Button mAttributeAllGet;
    private Button mConstructionAllGet;
    private Button mMethodPrivateCall;
    private Button mHotPointClose;
    private Button mHotPoint1Close;
    private Button mPhoneCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_she);
        initView();

        request();
    }

    private void request() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE}, 321);
    }

    private void initView() {
        mMethodCall = (Button) findViewById(R.id.call_method);
        mMethodCall.setOnClickListener(this);
        mMethodNoneCall = (Button) findViewById(R.id.call_method_none);
        mMethodNoneCall.setOnClickListener(this);
        mAttributePublicGet = (Button) findViewById(R.id.get_attribute_public);
        mAttributePublicGet.setOnClickListener(this);
        mAttributeAllGet = (Button) findViewById(R.id.get_attribute_all);
        mAttributeAllGet.setOnClickListener(this);
        mConstructionAllGet = (Button) findViewById(R.id.get_construction_all);
        mConstructionAllGet.setOnClickListener(this);
        mMethodPrivateCall = (Button) findViewById(R.id.call_method_private);
        mMethodPrivateCall.setOnClickListener(this);
        mHotPointClose = (Button) findViewById(R.id.close_hot_point);
        mHotPointClose.setOnClickListener(this);
        mHotPoint1Close = (Button) findViewById(R.id.close_hot_point1);
        mHotPoint1Close.setOnClickListener(this);
        mPhoneCall = (Button) findViewById(R.id.call_phone);
        mPhoneCall.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_method:
                // TODO 21/07/15
                try {
                    Class<?> qClass = Class.forName("com.naruto.didi2.activity.test.Q");
                    Constructor<?> constructor = qClass.getConstructor(String.class, int.class);
                    Object qObject = constructor.newInstance("杨勇", 26);

                    Method getAge = qClass.getMethod("getAge");
                    int age = (int) getAge.invoke(qObject);
                    LogUtils.e("年龄：" + age);
                } catch (Exception e) {
                    LogUtils.e(e.toString());
                }
                Intent intent = new Intent(this, ThreadTestActivity.class);
                startActivity(intent);
                break;
            case R.id.call_method_none:// TODO 21/07/15
                try {
                    Class<?> qClass = Class.forName("com.naruto.didi2.activity.test.Q");
                    Object o = qClass.newInstance();
                    Method setName = qClass.getMethod("setName", String.class);

                    setName.invoke(o, "无畏");
                    Method getName = qClass.getMethod("getName");
                    Object invoke = getName.invoke(o);
                    LogUtils.e("名字：" + invoke);
                } catch (Exception e) {
                    LogUtils.e("" + e.toString());
                }
                break;
            case R.id.get_attribute_public:// TODO 21/07/15
                try {
                    Class<?> qClass = Class.forName("com.naruto.didi2.activity.test.Q");
                    Field[] fields = qClass.getFields();
                    for (Field field : fields) {
                        LogUtils.e("属性：" + field.getName());
                    }
                } catch (Exception e) {
                    LogUtils.e("" + e.toString());
                }
                break;
            case R.id.get_attribute_all:// TODO 21/07/15
                try {
                    Class<?> qClass = Class.forName("com.naruto.didi2.activity.test.Q");
                    Field[] fields = qClass.getDeclaredFields();
                    for (Field field : fields) {
                        LogUtils.e("属性：" + field.getName());
                    }
                } catch (Exception e) {
                    LogUtils.e("" + e.toString());
                }
                break;
            case R.id.get_construction_all:// TODO 21/07/15
                try {
                    Class<?> qClass = Class.forName("com.naruto.didi2.activity.test.Q");
                    Constructor<?>[] declaredConstructors = qClass.getDeclaredConstructors();
                    for (Constructor constructor : declaredConstructors) {
                        Parameter[] parameters = constructor.getParameters();
                        for (Parameter parameter : parameters) {
                            Class<?> type = parameter.getType();
                            LogUtils.e("构造方法参数：" + type.getTypeName());
                        }

                    }
                } catch (Exception e) {
                    LogUtils.e("" + e.toString());
                }
                break;
            case R.id.call_method_private:// TODO 21/07/15
                try {
                    Class<?> qClass = Class.forName("com.naruto.didi2.activity.test.Q");
                    Method fly = qClass.getDeclaredMethod("fly");
                    Object o = qClass.newInstance();

                    fly.setAccessible(true);
                    Object invoke = fly.invoke(o);
                    LogUtils.e("方法结果：" + invoke);
                } catch (Exception e) {
                    LogUtils.e("" + e.toString());
                }
                break;
            case R.id.close_hot_point:// TODO 21/07/15
//                try {
//                    Class<?> tetheringClass = Class.forName("android.net.TetheringManager");
//                    Method stopTethering = tetheringClass.getMethod("stopTethering", int.class);
//
//
//                    ConnectivityManager conman = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                    Class<?> connectivityClass = Class.forName("android.net.ConnectivityManager");
//
//
//                    Field[] fields = connectivityClass.getDeclaredFields();
//                    for (Field field : fields) {
//                        LogUtils.e("属性：" + field.getName());
//                    }
//
//                    Field mTetheringManager = connectivityClass.getDeclaredField("mTetheringManager");
//                    mTetheringManager.setAccessible(true);
//
//                    Object object = mTetheringManager.get(conman);
//
//                    stopTethering.invoke(object, 0);
//
//                } catch (Exception e) {
//                    LogUtils.e("" + e.toString());
//                }
                AppUtil.getInstance().setWifiApEnabled(this, false);
                break;
            case R.id.close_hot_point1:// TODO 21/07/15
                try {
//                    Class<?> tetheringClass = Class.forName("android.net.TetheringManager");
//                    Method stopTethering = tetheringClass.getMethod("stopTethering", int.class);


                    ConnectivityManager conman = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    Class<?> connectivityClass = Class.forName("android.net.ConnectivityManager");


//                    Field[] fields = connectivityClass.getDeclaredFields();
//                    for (Field field : fields) {
//                        LogUtils.e("属性：" + field.getName());
//                    }

                    Method stopTethering = connectivityClass.getDeclaredMethod("stopTethering", int.class);


                    stopTethering.invoke(conman, 0);

                } catch (Exception e) {
                    LogUtils.e("" + e.toString());
                    e.printStackTrace();
                }
                break;
            case R.id.call_phone:// TODO 21/07/16
                // 开始直接拨打电话
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
//                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "10086"));
//                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent2);
//                Toast.makeText(this, "拨打电话！", Toast.LENGTH_SHORT).show();
                try {
                    // 延迟5秒后自动挂断电话
                    // 首先拿到TelephonyManager
                    TelephonyManager telMag = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    Class<TelephonyManager> c = TelephonyManager.class;

                    // 再去反射TelephonyManager里面的私有方法 getITelephony 得到 ITelephony对象
                    Method mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
                    //允许访问私有方法
                    mthEndCall.setAccessible(true);
                    final Object obj = mthEndCall.invoke(telMag, (Object[]) null);

                    // 再通过ITelephony对象去反射里面的endCall方法，挂断电话
                    Method mt = obj.getClass().getMethod("endCall");
                    //允许访问私有方法
                    mt.setAccessible(true);
                    mt.invoke(obj);
                    Toast.makeText(FanSheActivity.this, "挂断电话！", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    LogUtils.e("关闭电话异常：" + e.toString());
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
