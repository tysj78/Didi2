package com.naruto.didi2.activity.third;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.DPoint;
import com.baidu.mapapi.model.LatLng;
import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class GeoFenceActivity extends AppCompatActivity implements View.OnClickListener, GeoFenceListener {
    // 地理围栏客户端
    private GeoFenceClient fenceClient = null;
    // 地理围栏的广播action
    private static final String GEOFENCE_BROADCAST_ACTION = "com.example.geofence.polygon";
    private List<GeoFence> fenceList = new ArrayList<>();
    // 多边形围栏的边界点
    private List<LatLng> polygonPoints = new ArrayList<LatLng>();

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    StringBuffer sb = new StringBuffer();
                    sb.append("添加围栏成功");
                    String customId = (String) msg.obj;
                    if (!TextUtils.isEmpty(customId)) {
                        sb.append("customId: ").append(customId);
                    }
                    Toast.makeText(getApplicationContext(), sb.toString(),
                            Toast.LENGTH_SHORT).show();
//                    drawFence2Map();
                    break;
                case 1:
                    int errorCode = msg.arg1;
                    Toast.makeText(getApplicationContext(),
                            "添加围栏失败 " + errorCode, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String statusStr = (String) msg.obj;
                    tv_rectangle.setVisibility(View.VISIBLE);
                    tv_rectangle.append(statusStr + "\n");
                    break;
                default:
                    break;
            }
        }
    };
    private TextView tv_rectangle;
    private Button add_rectangle;
    private Button get_allrectangle;
    private Button mAllrectangleRemove;
    private Button mRectangle2Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fence);
        initView();

        PermissionUtils.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    // 初始化地理围栏
                    fenceClient = new GeoFenceClient(getApplicationContext());
                    init();
                }
            }
        });
    }

    private void init() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(GEOFENCE_BROADCAST_ACTION);
        registerReceiver(mGeoFenceReceiver, filter);
        /**
         * 创建pendingIntent
         */
        fenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
        fenceClient.setGeoFenceListener(this);
        /**
         * 设置地理围栏的触发行为,默认为进入
         */
        fenceClient.setActivateAction(GeoFenceClient.GEOFENCE_IN | GeoFenceClient.GEOFENCE_OUT | GeoFenceClient.GEOFENCE_STAYED);
    }

    /**
     * 创建围栏回调
     *
     * @param geoFenceList
     * @param errorCode
     * @param customId
     */
    @Override
    public void onGeoFenceCreateFinished(final List<GeoFence> geoFenceList, int errorCode, String customId) {
        Message msg = Message.obtain();
        if (errorCode == GeoFence.ADDGEOFENCE_SUCCESS) {
            fenceList.addAll(geoFenceList);
            msg.obj = customId;
            msg.what = 0;
        } else {
            msg.arg1 = errorCode;
            msg.what = 1;
        }
        handler.sendMessage(msg);
    }

    /**
     * 接收触发围栏后的广播,当添加围栏成功之后，会立即对所有围栏状态进行一次侦测，如果当前状态与用户设置的触发行为相符将会立即触发一次围栏广播；
     * 只有当触发围栏之后才会收到广播,对于同一触发行为只会发送一次广播不会重复发送，除非位置和围栏的关系再次发生了改变。
     */
    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收广播
            if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                Bundle bundle = intent.getExtras();
                String customId = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                String fenceId = bundle.getString(GeoFence.BUNDLE_KEY_FENCEID);
                //status标识的是当前的围栏状态，不是围栏行为
                int status = bundle.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
                StringBuffer sb = new StringBuffer();
                switch (status) {
                    case GeoFence.STATUS_LOCFAIL:
                        int errorCode = bundle.getInt(GeoFence.BUNDLE_KEY_LOCERRORCODE);
                        sb.append("定位失败:" + errorCode);
                        break;
                    case GeoFence.STATUS_IN:
                        sb.append("进入围栏 ");
                        break;
                    case GeoFence.STATUS_OUT:
                        sb.append("离开围栏 ");
                        break;
                    case GeoFence.STATUS_STAYED:
                        sb.append("停留在围栏内 ");
                        break;
                    default:
                        break;
                }
                if (status != GeoFence.STATUS_LOCFAIL) {
                    if (!TextUtils.isEmpty(customId)) {
                        sb.append(" customId: " + customId);
                    }
                    sb.append(" fenceId: " + fenceId);
                }
                String str = sb.toString();
                Message msg = Message.obtain();
                msg.obj = str;
                msg.what = 2;
                handler.sendMessage(msg);
            }
        }
    };

    private void initView() {
        tv_rectangle = (TextView) findViewById(R.id.tv_rectangle);
        add_rectangle = (Button) findViewById(R.id.add_rectangle);

        add_rectangle.setOnClickListener(this);
        get_allrectangle = (Button) findViewById(R.id.get_allrectangle);
        get_allrectangle.setOnClickListener(this);
        mAllrectangleRemove = (Button) findViewById(R.id.remove_allrectangle);
        mAllrectangleRemove.setOnClickListener(this);
        mRectangle2Add = (Button) findViewById(R.id.add_rectangle2);
        mRectangle2Add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_rectangle:
                addFence("军民融合产业园区1");
                break;
            case R.id.remove_allrectangle:// TODO 22/01/10
                if (fenceClient != null) {
                    fenceClient.removeGeoFence();
                    LogUtils.e("所有地理围栏已移除");
                }
                break;
            case R.id.add_rectangle2:// TODO 22/01/10
                addFence("军民融合产业园区2");
                break;
            default:
                break;
            case R.id.get_allrectangle:
                List<GeoFence> allGeoFence = fenceClient.getAllGeoFence();
                if (allGeoFence.size() == 0) {
                    LogUtils.e("查询围栏列表为空");
                }
                for (int i = 0; i < allGeoFence.size(); i++) {
                    String customId = allGeoFence.get(i).getCustomId();
                    LogUtils.e("已添加的围栏：" + customId );
                }
                break;
        }
    }

    /**
     * 激活定位
     */
//    @Override
//    public void activate(OnLocationChangedListener listener) {
//        mListener = listener;
//        if (mlocationClient == null) {
//            mlocationClient = new AMapLocationClient(this);
//            mLocationOption = new AMapLocationClientOption();
//            // 设置定位监听
//            mlocationClient.setLocationListener(this);
//            // 设置为高精度定位模式
//            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
//            // 只是为了获取当前位置，所以设置为单次定位
//            mLocationOption.setOnceLocation(true);
//            // 设置定位参数
//            mlocationClient.setLocationOption(mLocationOption);
//            mlocationClient.startLocation();
//        }
//    }

    /**
     * 定位成功后回调函数
     */
//    @Override
//    public void onLocationChanged(AMapLocation amapLocation) {
//        if (amapLocation != null) {
//            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
//                tv_rectangle.setText("定位成功11");
//            } else {
//                String errText = "定位失败," + amapLocation.getErrorCode() + ": "
//                        + amapLocation.getErrorInfo();
//                tv_rectangle.setText(errText);
//            }
//        }
//    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//    }

    /**
     * 添加围栏
     *
     * @author hongming.wang
     * @since 3.2.0
     */
    private void addFence(String customId) {
//        addPolygonFence();
        addRoundGonFence(customId);
    }

    /**
     * 添加多边形围栏
     *
     * @author hongming.wang
     * @since 3.2.0 纬度 经度
     */
    private void addPolygonFence() {
        polygonPoints.clear();
        String customId = "123";
        polygonPoints.add(new LatLng(39.95005715257012, 116.27724433700925));
        polygonPoints.add(new LatLng(39.95007668640395, 116.27747634808506));
        polygonPoints.add(new LatLng(39.949958455219154, 116.27750585238373));
        polygonPoints.add(new LatLng(39.94995125853173, 116.27729798118862));
        if (null == polygonPoints || polygonPoints.size() < 3) {
            Toast.makeText(getApplicationContext(), "参数不全", Toast.LENGTH_SHORT).show();
//            btAddFence.setEnabled(true);
            return;
        }
        List<DPoint> pointList = new ArrayList<DPoint>();
        for (LatLng latLng : polygonPoints) {
            pointList.add(new DPoint(latLng.latitude, latLng.longitude));
        }
        fenceClient.addGeoFence(pointList, customId);
    }

    /**
     * 添加圆形围栏
     *
     * @author hongming.wang
     * @since 3.2.0 纬度 经度
     */
    private void addRoundGonFence(String customId) {
        DPoint centerPoint = new DPoint();
        //设置中心点纬度116.277297,39.950037
        centerPoint.setLatitude(39.950037D);
        //设置中心点经度
        centerPoint.setLongitude(116.277297D);
        fenceClient.addGeoFence(centerPoint, 1500F, customId);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(mGeoFenceReceiver);
        } catch (Throwable e) {
        }

        if (null != fenceClient) {
            fenceClient.removeGeoFence();
        }
    }
}
