package com.naruto.didi2.activity.test;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aixunyun.cybermdm.util.sm.SM;
import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.PermissionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class EncryptActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yylog";
    private Button encrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt2);
        initView();

        PermissionUtils.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        });
    }

    void encrypt() {
        String timestamp = "1619343052823";
        String nonce = "593BEC0C930BF1AFEB40B4A08C8FB242";
        String udid = "1ddfc322-6af9-3219-a00c-9cd415f412ba";

        String json = "{\"json\":{\"udid\":\"23067f8432f3a9a739f3c75cfc15b76e60692938\",\"timestamp\":\"1624330195782\",\"signature\":\"e08157b500f06ff6f4e570cb45fdecd348d38d8b62087f14b7c457f0ffe62abd\",\"content\":{\"list\":[{\"time\":\"1624330086450\",\"title\":\"百度一下\",\"type\":\"0\",\"url\":\"https://m.baidu.com/?from=844b&vit=fps\"}]}}}";

        String signature = timestamp + "\n" + nonce + "\n" + udid + "\n" + json;
        try {
            String s = SM.doSM3EncryptData(signature);
            LogUtils.e("加密成功：" + s);
        } catch (Exception e) {
            LogUtils.e("加密异常：" + e.toString());
        }
    }

    /**
     * 获取SIM卡的唯一标识
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static List<String> getSIMSerialNum(Context context) {
        ArrayList<String> list = new ArrayList<>();
        List<SubscriptionInfo> activeInfoList = getSimSubscriptionInfo(context);
        if (activeInfoList == null) {
            Log.e(TAG, "sim_id-- null");
            return list;
        }
        for (SubscriptionInfo info : activeInfoList) {
            String iccId = info.getIccId();
            list.add(iccId);
            Log.e(TAG, "sim_id--" + iccId);
        }

        return list;

    }

    void getSimNumber() {
        //获取sim卡序列号TelephoneManager
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//获取sim卡的序列卡号
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String simSerialNumber = manager.getSimSerialNumber();
        LogUtils.e("simSerialNumber:"+simSerialNumber);
    }

    void getSimNumber2(){
        String serial="";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
        LogUtils.e("serial:"+serial);
    }

    private static List<SubscriptionInfo> getSimSubscriptionInfo(Context context){
        if (Build.VERSION.SDK_INT >= 22){
            SubscriptionManager mSubscriptionManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            List<SubscriptionInfo> activeInfoList = mSubscriptionManager.getActiveSubscriptionInfoList();//手机SIM卡信息

            return activeInfoList;
        }else {
            return null;
        }
    }

    private void initView() {
        encrypt = (Button) findViewById(R.id.encrypt);

        encrypt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.encrypt:
                getSimNumber();
                getSIMSerialNum(this);
                getSimNumber2();
                break;
        }
    }
}
