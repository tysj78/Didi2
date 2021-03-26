package com.yangyong.didi2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yangyong.didi2.util.AppManager;
import com.yangyong.didi2.util.LogUtils;
import com.yangyong.didi2.util.SpUtils;

/**
 * Created by yangyong on 2019/9/18/0018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppExitUtils.getInstance().addActivity(this);
//        AppUtil.getInstance().setActivity(this);
        AppManager.getAppManager().addActivity(this);
//        PermissionUtils.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//
//            }
//        });
    }


    @Override
    protected void onPause() {
        super.onPause();

       /* //屏蔽检测页面
        String curSimpleClassName = getClass().getSimpleName();
        if ("NewRegisterActivity".equals(curSimpleClassName)) {
//                "LoadingActivity".equals(curSimpleClassName) ||
//                "NewRegisterActivity".equals(curSimpleClassName) ||
//                "GestureEditActivity".equals(curSimpleClassName) ||
//                "OriginLoginActivity".equals(curSimpleClassName) ||
//                "ToggleWeightWatcher".equals(curSimpleClassName) ||
//                "PermissionsActivity".equals(curSimpleClassName))
            return;
        }
        LogUtils.e("onPause:save time");

        long currentTime = System.currentTimeMillis();
        SpUtils.saveLongValue(this, SpUtils.SAVETIME, currentTime);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        AppExitUtils.getInstance().removeActivity(this);
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//            LogUtils.e("onResume:check validity");

           /* boolean val = checkLoginValidity(1l);
            if (!val) {
                //不合法跳转登录页
                AppUtil.getInstance().toast("登录超时，请重新登录");
                startActivity(new Intent(this, DuanDianActivity.class));
                finish();
                LogUtils.e("后续");
//                return;
            }*/

    }

    private boolean checkLoginValidity(long validity) {
//屏蔽检测页面
        String curSimpleClassName = getClass().getSimpleName();
        if ("DuanDianActivity".equals(curSimpleClassName)) {
//                "LoadingActivity".equals(curSimpleClassName) ||
//                "NewRegisterActivity".equals(curSimpleClassName) ||
//                "GestureEditActivity".equals(curSimpleClassName) ||
//                "OriginLoginActivity".equals(curSimpleClassName) ||
//                "ToggleWeightWatcher".equals(curSimpleClassName) ||
//                "PermissionsActivity".equals(curSimpleClassName))
            LogUtils.e("重置");
            SpUtils.saveLongValue(this, SpUtils.SAVETIME, 0);
            return true;
        }

        boolean val = true;
        long save = SpUtils.getLongValue(this, SpUtils.SAVETIME);
        long currentTime = System.currentTimeMillis();

        if (save == 0) {
            return true;
        }
//        long tmp = validity * 60 * 60 * 1000;
        //有效期5秒
        long tmp = 5 * 1000;

        long dff = currentTime - save;
        LogUtils.e("save time:" + save);
        if (dff > tmp) {
            val = false;
        }
        SpUtils.saveLongValue(this, SpUtils.SAVETIME, currentTime);
        return val;
    }
}
