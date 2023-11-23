package com.naruto.didi2.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.util.AppManager;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/2/22/0022
 */

public class MustInstallAppReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, Constants.MUSTINSTALLAPP)) {
            LogUtils.e("接收到弹窗广播");
//                int type = intent.getIntExtra("type", 3);
//                List<MustAppInfo> applist = (List<MustAppInfo>) intent.getSerializableExtra("applist");

//                LogUtils.e("未装应用数：" + count);
//                List<String> pks = new ArrayList<>();
//                pks.add("com.everhomes.android.jmrh");
//                pks.add("com.netease.cloudmusic");
//                pks.add("com.yangyong.didi2");
//                if (applist == null || applist.size() == 0) {
//                    return;
//                }
//                if (type == 0) {
//                    InstallAppUtils.getInstance().showInstallDialog(Launcher.this, 0, applist);
//                } else if (type == 1) {
//                    InstallAppUtils.getInstance().showInstallDialog(Launcher.this, 1, applist);
//                }
            Activity activity = AppManager.getAppManager().currentActivity();
            AppUtil.getInstance().showDialog(activity, "应用宝");
        }
    }
}
