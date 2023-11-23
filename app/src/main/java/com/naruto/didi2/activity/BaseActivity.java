package com.naruto.didi2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.naruto.didi2.util.AppManager;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.SpUtils;

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
       /* if (!this.isTaskRoot()) {
            LogUtils.e("检测到非栈activity:"+getClass().getSimpleName());
//            // 判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
//            // 如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                LogUtils.e("CATEGORY_LAUNCHER finish");
                finish();
                return;// finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }*/
       LogUtils.e("BaseActivity onCreate");
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
        LogUtils.e("BaseActivity onResume");

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
