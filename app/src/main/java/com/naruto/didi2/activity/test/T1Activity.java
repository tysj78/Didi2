package com.naruto.didi2.activity.test;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.naruto.didi2.MyApp;
import com.naruto.didi2.R;
import com.naruto.didi2.activity.BaseActivity;
import com.naruto.didi2.bean.User;
import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.util.AppManager;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.MyActivityManager;
import com.naruto.didi2.util.PermissionUtils;

import java.lang.reflect.Method;

import io.reactivex.functions.Consumer;

public class T1Activity extends BaseActivity implements View.OnClickListener {

    private Button bt_tiao;
    private Button bt_exit;
    private Button mSysBt;
    private TextView mNetdataTv;
    private ProgressBar mProgressBar;
    private Button mStopTv;
    private ProgressDialog progressDialog;
    private Button mStartDownBt;
    private Button mSerTestBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t1);
        initView();

        LogUtils.e("T1Activity onCreate:"+ MyApp.mActivity);
        Activity currentActivity = MyActivityManager.getInstance().getCurrentActivity();
        if (currentActivity!=null){
            String simpleName = currentActivity.getClass().getSimpleName();
            LogUtils.e("simpleName:"+simpleName);
        }else {
            LogUtils.e("currentActivity null");
        }


        // 注册网络回调
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(networkCallback);
            }
        }

        User uer=null;
        try {
            LogUtils.e("user:"+uer.getAge());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            // 网络已连接
            LogUtils.e("网络已连接");
        }

        @Override
        public void onLost(Network network) {
            // 网络已断开
            LogUtils.e("网络已断开");
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("T1Activity onResume");
    }

    public int getMobileDataState(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method getMobileDataEnabledMethod = telephonyManager.getClass().getDeclaredMethod("getDataEnabled");
            getMobileDataEnabledMethod.setAccessible(true);
            boolean isMobileDataEnabled = (boolean) getMobileDataEnabledMethod.invoke(telephonyManager);

            if (isMobileDataEnabled) {
                return TelephonyManager.DATA_CONNECTED;
            } else {
                return TelephonyManager.DATA_DISCONNECTED;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TelephonyManager.DATA_UNKNOWN;
    }


    private void initView() {
        bt_tiao = (Button) findViewById(R.id.bt_tiao);

        bt_tiao.setOnClickListener(this);
        bt_exit = (Button) findViewById(R.id.bt_exit);
        bt_exit.setOnClickListener(this);
        mSysBt = (Button) findViewById(R.id.bt_sys);
        mSysBt.setOnClickListener(this);
        mNetdataTv = (TextView) findViewById(R.id.tv_netdata);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mStopTv = (Button) findViewById(R.id.tv_stop);
        mStartDownBt = (Button) findViewById(R.id.bt_start_down);
        mStartDownBt.setOnClickListener(this);
        mSerTestBt = (Button) findViewById(R.id.bt_ser_test);
        mSerTestBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_tiao:
//                startActivity(new Intent(this, T2Activity.class));
//                finish();
//                sendBroadcast1();
                PermissionUtils.requestPermissions((FragmentActivity) AppManager.getAppManager().currentActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            LogUtils.e("开启存储权限");
                        }
                    }
                });
                break;
            case R.id.bt_exit:
//                AppExitUtils.getInstance().exit();
                break;
            case R.id.bt_sys:// TODO 23/06/01
             /*   new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                PermissionUtils.requestPermissionsOnThread2(AppManager.getAppManager().currentActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean aBoolean) throws Exception {
                                        if (aBoolean){
                                            LogUtils.e("同意了权限");
                                        }else {
                                            LogUtils.e("拒绝了权限");
                                        }

                                    }
                                });

//                                if (ContextCompat.checkSelfPermission(T1Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                                } else {
//                                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                                    int requestCode = 1; // 可以自定义请求码
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                        requestPermissions(permissions, requestCode);
//                                    }
//                                }
                            }
                        }
                ).start();
*/

//                int mobileDataState = getMobileDataState(this);
//                LogUtils.e("手机移动网络状态：" + (mobileDataState == 2));
//                mNetdataTv.setText("移动网络：" + (mobileDataState == 2 ? "开" : "关"));

                // 在任务开始前显示进度对话框
                progressDialog = ProgressDialog.show(this, "请稍候", "正在执行任务...", true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 5000);
                break;
            case R.id.tv_stop:
                break;
            case R.id.bt_start_down:// TODO 23/06/08
//                Intent intent = new Intent(this, DownLoadFileService.class);
//                intent.putExtra("fileUrl", "https://imtt.dd.qq.com/16891/apk/7884ACB68E413A5B22A7FE044DD3BF3C.apk");
//                startService(intent);


//                startActivity(new Intent(this, DownLoadActivity.class));

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mNetdataTv.setText("移动网络：2222");
//                    }
//                }, 5000);
//                break;

                AppUtil.getInstance().eraseDevice(this);
            case R.id.bt_ser_test:// TODO 23/06/30
//                serTest();
                break;
        }
    }

    private void serTest() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予了所请求的权限
                // ...
                LogUtils.e("同意了权限");
            } else {
                // 用户拒绝了所请求的权限
                LogUtils.e("拒绝了权限");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e("t1 onDestroy");
    }

    private void sendBroadcast1() {
        Intent intent = new Intent();
        intent.setAction(Constants.MUSTINSTALLAPP);
        sendBroadcast(intent);
    }
}
