package com.naruto.didi2.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;

import com.naruto.didi2.bean.WifiInfos;
import com.naruto.didi2.util.WifiUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Search WIFI and show in ListView
 */
public class Main12Activity extends Activity implements OnClickListener,
        OnItemClickListener {
    private Button search_btn;
    private ListView wifi_lv;
    private WifiUtils mUtils;
    private List<ScanResult> result;
    private ProgressDialog progressdlg = null;
    private TextView wifi_count;
    private WifiAdapter wifiAdapter;
    private List<WifiInfos> macs;
    private WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);
        initView();
        mUtils = new WifiUtils(this);
        findViews();
        setLiteners();
        macs = new ArrayList<>();
        macs.add(new WifiInfos("","40:e2:30:05:82:f7",-101,"m1234567"));
        macs.add(new WifiInfos("","d4:ee:07:26:5d:03",-101,"12345678"));
        macs.add(new WifiInfos("","74:05:a5:49:f9:a6",-101,"12345678"));
        macs.add(new WifiInfos("","74:05:a5:49:f9:a7",-101,"12345678"));
        macs.add(new WifiInfos("","94:0e:6b:54:2c:4c",-101,"199508170"));
    }

    private void findViews() {
        this.search_btn = (Button) findViewById(R.id.search_btn);
        this.wifi_lv = (ListView) findViewById(R.id.wifi_lv);
    }

    private void setLiteners() {
        search_btn.setOnClickListener(this);
        wifi_lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_btn) {
            showDialog();
            new MyAsyncTask().execute();
        }
    }

    /**
     * init dialog and show
     */
    private void showDialog() {
        progressdlg = new ProgressDialog(this);
        progressdlg.setCanceledOnTouchOutside(false);
        progressdlg.setCancelable(false);
        progressdlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdlg.setMessage(getString(R.string.wait_moment));
        progressdlg.show();
    }

    /**
     * dismiss dialog
     */
    private void progressDismiss() {
        if (progressdlg != null) {
            progressdlg.dismiss();
        }
    }

    private void initView() {
        wifi_count = (TextView) findViewById(R.id.wifi_count);
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            //扫描附近WIFI信息
            result = mUtils.getScanWifiResult();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDismiss();
            initListViewData();
        }
    }

    @SuppressLint("ResourceType")
    private void initListViewData() {
//        if (null != result && result.size() > 0) {
        if (wifiAdapter == null) {
//            wifi_lv.setAdapter(new ArrayAdapter<String>(
//                    getApplicationContext(), R.layout.wifi_list_item,
//                    R.id.ssid, result));
            wifiAdapter = new WifiAdapter(this, result);
            wifi_lv.setAdapter(wifiAdapter);
            wifi_count.setVisibility(View.VISIBLE);
            wifi_count.setText("附近wifi数量：" + result.size());
        } else {
//            wifi_lv.setEmptyView(findViewById(R.layout.list_empty));
            wifiAdapter.notifyDataSetChanged();
            wifi_count.setVisibility(View.VISIBLE);
            wifi_count.setText("附近wifi数量：" + result.size());
        }

        //检测出附近存在mac
        List<WifiInfos> wifiInfos = mUtils.checkWifi(result, macs);
        //取信号强度最高的wifi连接
        List<WifiInfos> sortWifiInfo = mUtils.bubbleSort(wifiInfos);
        WifiInfos wifiInf = sortWifiInfo.get(0);
        connectWifi2(wifiInf.getSsid(),wifiInf.getPassWord());
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        TextView tv = (TextView) arg1.findViewById(R.id.ssid);
        if (!TextUtils.isEmpty(tv.getText().toString())) {
            Intent in = new Intent(Main12Activity.this, WifiConnectActivity.class);
            in.putExtra("ssid", tv.getText().toString());
            startActivity(in);
        }
    }

    class WifiAdapter extends BaseAdapter {
        private List<ScanResult> result;
        private LayoutInflater inflater;

        private WifiAdapter(Context context, List<ScanResult> results) {
            this.result = results;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return result != null ? result.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return result.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new MViewHolder();
                convertView = inflater.inflate(R.layout.wifi_list, null);
                viewHolder.ssid = convertView.findViewById(R.id.ssid);
                viewHolder.bssid = convertView.findViewById(R.id.bssid);
                viewHolder.level = convertView.findViewById(R.id.level);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MViewHolder) convertView.getTag();
            }
            ScanResult scanResult = result.get(position);
            viewHolder.ssid.setText("wifi名称：" + scanResult.SSID);
            viewHolder.bssid.setText("wifi地址：" + scanResult.BSSID);
            viewHolder.level.setText("信号强度：" +getLevel(scanResult.level));
            return convertView;
        }
    }

    private String getLevel(int level) {
        String wifiLevel = "";
        //根据获得信号的强度发送信息
        if (level <= 0 && level >= -50) {
            wifiLevel = "4";
        } else if (level < -50 && level >= -70) {
            wifiLevel = "3";
        } else if (level < -70 && level >= -80) {
            wifiLevel = "2";
        } else if (level < -80 && level >= -100) {
            wifiLevel = "1";
        } else {
            wifiLevel = "0";
        }
        return wifiLevel;
    }

    private class MViewHolder {
        private TextView ssid;
        private TextView bssid;
        private TextView level;
    }

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
}
