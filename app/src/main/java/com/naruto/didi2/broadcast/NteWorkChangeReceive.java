package com.naruto.didi2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/11/19/0019
 */

public class NteWorkChangeReceive extends BroadcastReceiver {
    private static boolean isNetAvailable = false;  //声明布尔变量控制广播执行
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        LogUtils.e("收到网络变化广播");
        //网络连接成功执行此段代码
        if (networkInfo != null && networkInfo.isAvailable()) {
            if (!isNetAvailable) {
                isNetAvailable = true;
                Toast.makeText(context, "网络连接", Toast.LENGTH_SHORT).show();
                if (AppUtil.getInstance().callBack != null) {
                    AppUtil.getInstance().callBack.doEvent("网络已连接");
                }
                LogUtils.e("连接");
            }
        } else {
            //网络连接失败执行此段代码
            if (isNetAvailable) {
                isNetAvailable = false;
                if (AppUtil.getInstance().callBack != null) {
                    AppUtil.getInstance().callBack.doEvent("网络已断开");
                }
                Toast.makeText(context, "网络断开", Toast.LENGTH_SHORT).show();
                LogUtils.e("断开");
            }
        }
    }

//    public interface CallBack {
//        public void doEvent(String str);
//    }

//    CallBack callBack;
//
//    public void regCallBack(CallBack callBack) {
//        this.callBack = callBack;
//    }
}
