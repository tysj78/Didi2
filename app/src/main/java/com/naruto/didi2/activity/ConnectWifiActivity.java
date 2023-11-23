package com.naruto.didi2.activity;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;

import com.naruto.didi2.bean.WifiInfos;
import com.naruto.didi2.util.WifiUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ConnectWifiActivity extends AppCompatActivity implements View.OnClickListener {

    private Button connect;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_wifi);
        initView();
//        test();
    }

    private void test() {
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(1);
        objects.add(2);
        objects.add(3);
        ArrayList<Integer> two = new ArrayList<>();
        two.add(5);
        objects.addAll(two);
        objects.addAll(two);
        Log.e(Constants.TAG, "test: " + objects.size());
    }

    private void initView() {
        connect = (Button) findViewById(R.id.connect);

        connect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect:
//                connectWifi();
//                connectWifi1();
//                checkLevel();
                WifiUtils wifiUtils = new WifiUtils(this);
               List<WifiInfos> macs = new ArrayList<>();
                macs.add(new WifiInfos("","40:e2:30:05:82:f7",-101,"m1234567"));
                macs.add(new WifiInfos("","d4:ee:07:26:5d:03",-32,"12345678"));
                macs.add(new WifiInfos("","74:05:a5:49:f9:a6",-58,"12345678"));
                macs.add(new WifiInfos("","74:05:a5:49:f9:a7",-70,"12345678"));
                macs.add(new WifiInfos("","94:0e:6b:54:2c:4c",0,"199508170"));
                wifiUtils.bubbleSort(macs);
//                connectWifi2("honor9", "199508170");
                break;
        }
    }

//    private WifiInfos checkLevel(List<WifiInfos> existMac) {
//
//
//    }

    private void connectWifi2(String ssid, String passWord) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
//        wifiManager.disconnect();
//        wifiManager.disconnect(); //断开当前连接, 一段时间后还会自动连接
//        int mNetworkId = connectionInfo.getNetworkId();
//        Log.e(Constants.TAG, "connectWifi2: " + mNetworkId);
//        boolean b = wifiManager.disableNetwork(mNetworkId);//指定热点断开连接，同时不再连接
//        Log.e(Constants.TAG, "disableNetwork :" + b);

        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = "\"" + ssid + "\"";
//        wifiConfig.SSID = "\"xiaomi6\"";
//        wifiConfig.BSSID="\"94:0e:6b:54:2c:4c\"";
        wifiConfig.preSharedKey = "\"" + passWord + "\"";
//        wifiConfig.preSharedKey = "\"" + "66666666" + "\"";
//        wifiConfig.preSharedKey = "\"" + "m1234567" + "\"";
        wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
//        wifiConfig.priority=30;
        try {
            wifiConfig.status = WifiConfiguration.Status.ENABLED;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        WifiConfiguration tempConfig = IsExsits(wifiConfig.SSID);
        if (tempConfig != null) {
            Log.e(Constants.TAG, "检测到wifi已存在: " + wifiConfig.SSID + "开始删除。。");
//            wifiManager.removeNetwork(tempConfig.networkId);

//            int mNetworkId = connectionInfo.getNetworkId();
//            wifiManager.disableNetwork(mNetworkId); //指定热点断开连接，同时不再连接
//            wifiManager.disconnect(); //断开当前连接, 一段时间后还会自动连接
//            Log.e(Constants.TAG, "检测到wifi已存在: " + tempConfig.networkId);
//            wifiManager.disconnect();
//            boolean enabled = wifiManager.enableNetwork(tempConfig.networkId, true);
//            wifiManager.reconnect();
//            Log.e(Constants.TAG, "enableNetwork status enable=" + enabled);
//            return;
            boolean b = wifiManager.removeNetwork(tempConfig.networkId);
            Log.e(Constants.TAG, "removeNetwork: " + b);
            if (!b) {
                Log.e(Constants.TAG, "删除他人创建网络失败，开始直连networkId: " + tempConfig.networkId);
                wifiManager.disconnect();
                SystemClock.sleep(1000);
                boolean b1 = wifiManager.enableNetwork(tempConfig.networkId, true);
                Log.e(Constants.TAG, "直连: " + b1);
                return;
            }
        }
        Log.e(Constants.TAG, "正在连接至新的wifi: " + wifiConfig.SSID);

//        wifiManager.disconnect();


//        wifiManager.disableNetwork(mNetworkId); //指定热点断开连接，同时不再连接
//        wifiManager.disconnect(); //断开当前连接, 一段时间后还会自动连接


        try {
            Log.e(Constants.TAG, "检测到wifi不存在: " + wifiConfig.SSID + "开始添加。。");
            int netID = wifiManager.addNetwork(wifiConfig);
            Log.e(Constants.TAG, "addNetworknetID: " + netID);
            if (netID > 0) {
                wifiManager.disconnect();
                SystemClock.sleep(1000);
                boolean b = wifiManager.enableNetwork(netID, true);
                Log.e(Constants.TAG, "enableNetwork: " + b);
//                wifiManager.reconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void connectWifi1() {
//        final WifiNetworkSuggestion suggestion3 =
//                new WifiNetworkSuggestion.Builder()
//                        .setSsid("test333333")
//                        .setWpa3Passphrase("test6789")
//                        .setIsAppInteractionRequired() // Optional (Needs location permission)
//                        .build();
//
//        final List suggestionsList =new ArrayList() {};
//            suggestionsList.add(suggestion3);
//
//
//
//        final WifiManager wifiManager =
//                (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//
//        final int status = wifiManager.addNetworkSuggestions(suggestionsList);
//        if (status != WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
//// do error handling here…
//        }
//
//// Optional (Wait for post connection broadcast to one of your suggestions)
//        final IntentFilter intentFilter =
//                new IntentFilter(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION);
//
//        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (!intent.getAction().equals(
//                        WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)) {
//                    return;
//                }
//                // do post connect processing here..
//            }
//        };
//        context.registerReceiver(broadcastReceiver, intentFilter);
//    }

//    private void connectWifi() {
//        if (Build.VERSION.SDK_INT >= 29) {
//        final NetworkSpecifier specifier =
//                new NetworkSpecifier.Builder()
////                        .setSsidPattern(new PatternMatcher("honor9", PatternMatcher.PATTERN_PREFIX))
////                        .setBssidPattern(MacAddress.fromString("10:03:23:00:00:00"), MacAddress.fromString("ff:ff:ff:00:00:00"))
//                        .setSsid("honor9")
//                        .setWpa2Passphrase("199508170")
//                        .build();
//
//            final NetworkRequest request =
//                    new NetworkRequest.Builder()
//                            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//                            .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//                            .setNetworkSpecifier(specifier)
//                            .build();
//
//
//            final ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
//                @Override
//                public void onAvailable(Network network) {
//                }
//
//                @Override
//                public void onUnavailable() {
//
//                }
//            };
//            connectivityManager.requestNetwork(request, networkCallback);
//            // Release the request when done.
//            connectivityManager.unregisterNetworkCallback(networkCallback);
//        }
//    }


    /**
     * <p>
     * 判断以前是否配置过该ssid的网络
     */
    public WifiConfiguration IsExsits(String SSID) {
        Log.e(Constants.TAG, "传入的ssid: " + SSID);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();
//        Log.e(Constants.TAG, "existingConfigs: "+existingConfigs.size() );
        if (existingConfigs == null) {
            return null;
        }
        for (WifiConfiguration existingConfig : existingConfigs) {
            String ssid = existingConfig.SSID;
            Log.e(Constants.TAG, "系统已保存的ssid: " + ssid);
            if (SSID.equals(ssid)) {
                return existingConfig;
            }
        }
        return null;
    }


    private Method connectWifiByReflectMethod(int netId) {
        Method connectMethod = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            Logger.i(TAG, "connectWifiByReflectMethod road 1");
            // 反射方法： connect(int, listener) , 4.2 <= phone's android version
            for (Method methodSub : wifiManager.getClass()
                    .getDeclaredMethods()) {
                if ("connect".equalsIgnoreCase(methodSub.getName())) {
                    Class<?>[] types = methodSub.getParameterTypes();
                    if (types != null && types.length > 0) {
                        if ("int".equalsIgnoreCase(types[0].getName())) {
                            connectMethod = methodSub;
                        }
                    }
                }
            }
            if (connectMethod != null) {
                try {
                    connectMethod.invoke(wifiManager, netId, null);
                    Log.e(Constants.TAG, "反射调用成功: ");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(Constants.TAG, "反射调用失败: ");
//                    Logger.i(TAG, "connectWifiByReflectMethod Android "
//                            + Build.VERSION.SDK_INT + " error!");
                    return null;
                }
            }
        }
        return connectMethod;
    }

}
