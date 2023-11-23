package com.naruto.didi2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


import com.naruto.didi2.R;
import com.naruto.didi2.bean.LocationModel;
import com.naruto.didi2.bean.OperationModel;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestureActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private static final String TAG = "yy";
    private Button bt_one;
    private ScrollView sv_view;
    private Button bt_two;
    private List<LocationModel> list;
    private OperationModel operationModel;
    private long dateStart;
    private LocationModel locationModel;
    private long dateEnd;
    private long during;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    readGesture();
                    break;
            }
        }
    };
    private TextView tv_screen;
    private EditText et_point;
    private TextView tv_dpoint;
    private Button bt_transform;

    private void readGesture() {
        try {
            if (operationModel != null) {
//            List<LocationModel> list = operationModel.getList();
//            LogUtils.e(operationModel.delayTime + ".." + operationModel.getDurationTime());
//            for (LocationModel model : list) {
//                LogUtils.e("记录的手势x：" + model.getX() + "  y:" + model.getY());
//            }
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
                Intent intent = new Intent("com.yangyong.access");
                Bundle bundle = new Bundle();
                bundle.putSerializable("operation", operationModel);
                intent.putExtras(bundle);
                sendBroadcast(intent);
            } else {
                LogUtils.e("记录的手势x：null");
            }
        } catch (Exception e) {
            Log.e("yy", "Exception: " + e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        initView();

        list = new ArrayList<>();
        operationModel = new OperationModel();
        initHandler();
        String screen = AppUtil.getInstance().getScreen(this);
        tv_screen.setText(screen);
    }

    private void initHandler() {
        handler.sendEmptyMessageDelayed(1, 10 * 1000);
    }

    /**
     * 前往开启辅助服务界面
     */
    public void goAccess() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openOtherApp(String s) {
        PackageManager packageManager = getPackageManager();
        Intent i = packageManager.getLaunchIntentForPackage(s);
        startActivity(i);
    }

    private void initView() {
        bt_one = (Button) findViewById(R.id.bt_one);

        bt_one.setOnClickListener(this);
        sv_view = (ScrollView) findViewById(R.id.sv_view);
        bt_two = (Button) findViewById(R.id.bt_two);
        bt_two.setOnClickListener(this);
        sv_view.setOnTouchListener(this);
        tv_screen = (TextView) findViewById(R.id.tv_screen);
        tv_screen.setOnClickListener(this);
        et_point = (EditText) findViewById(R.id.et_point);
        et_point.setOnClickListener(this);
        tv_dpoint = (TextView) findViewById(R.id.tv_dpoint);
        tv_dpoint.setOnClickListener(this);
        bt_transform = (Button) findViewById(R.id.bt_transform);
        bt_transform.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_one:
                goAccess();
                break;
            case R.id.bt_two:
//                sendCom(500, 200);
                String getlocalip = getlocalip();
                LogUtils.e("getlocalip:" + getlocalip);
                break;
            case R.id.bt_transform:
                try {
                    String s = et_point.getText().toString();
                    String[] split = s.split(",");
                    String s1 = split[0];
                    String s2 = split[1];

                    String s3 = AppUtil.getInstance().webTransformAndroid(this, Integer.parseInt(s1), Integer.parseInt(s2));
                    tv_dpoint.setText(s3);
                } catch (Exception e) {
                    Log.e("yy", "Exception: " + e.toString());
                }
                break;
        }
    }

    private void sendCom(int x, int y) {
        Intent it = new Intent("com.yangyong.access");
        Bundle bundle = new Bundle();
        bundle.putInt("x", x);
        bundle.putInt("y", y);
        it.putExtras(bundle);
        sendBroadcast(it);
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        Date date = new Date();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (list.size() != 0)
                    list.clear();
                if (operationModel != null) {
//                        operationModel.clear();
                    operationModel = null;
                }
                dateStart = date.getTime();
                locationModel = new LocationModel((int) motionEvent.getX(), (int) motionEvent.getY());
                list.add(locationModel);
                LogUtils.e((int) motionEvent.getX() + "  " + (int) motionEvent.getY());
                LogUtils.e((int) motionEvent.getRawX() + "  " + (int) motionEvent.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: getAction:" + motionEvent.getAction());
                Log.d(TAG, "onTouchEvent: getX:" + motionEvent.getX());
                Log.d(TAG, "onTouchEvent: getY:" + motionEvent.getY());
                locationModel = new LocationModel((int) motionEvent.getX(), (int) motionEvent.getY());
                list.add(locationModel);

            case MotionEvent.ACTION_UP:
                dateEnd = date.getTime();
                during = dateEnd - dateStart;
                locationModel = new LocationModel((int) motionEvent.getX(), (int) motionEvent.getY());
                list.add(locationModel);
                operationModel = new OperationModel();
                operationModel.setDelayTime(0);
                operationModel.setDurationTime((int) during);
                operationModel.setList(list);
                Log.d(TAG, "onTouch: during:" + during);
                break;
        }
        return false;
    }

    /**
     * 或取本机的ip地址
     */
    private String getlocalip() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        //  Log.d(Tag, "int ip "+ipAddress);
        if (ipAddress == 0) return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }

}
