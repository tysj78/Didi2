package com.naruto.didi2.activity;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.naruto.didi2.R;

import java.util.List;

public class EncryptActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext = null;
    private Button createbug;


    private static final int REQUESTCODE_USE = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        initView();
        mContext = this;
        /*String[] strings = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        PermissionUtils.requestPermissions((Activity) mContext, strings, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Toast.makeText(mContext, "同意了权限", Toast.LENGTH_SHORT).show();
            }
        });*/

        if (!hasEnable(this)) {
                Intent intent_usage = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
                startActivityForResult(intent_usage, REQUESTCODE_USE);
        }




    }

    private void initView() {
        createbug = (Button) findViewById(R.id.createbug);

        createbug.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createbug:
//                Log.e("yy", (1 / 0) + "");
//                throw new Throwable("抛出一个自建异常");
//                new Throwable("自建异常");

                method();
                break;
        }
    }

    private void method() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Object oo=null;
                        Log.e("oo", oo.toString() );
                    }
                }
        ).start();
    }

    public boolean hasEnable(Context context) {
        if (isNoOption(context)) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {   // 如果大于等于5.0 再做判断
                long ts = System.currentTimeMillis();
                UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Service.USAGE_STATS_SERVICE);
                List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, ts);
                if (queryUsageStats == null || queryUsageStats.isEmpty()) {
                    Log.e( "yy","list>>>>>>>>>>>hasEnable-false");
                    return false;
                }
            }
        }
        Log.e( "yy","list>>>>>>>>>>>hasEnable-true");
        return true;
    }

    /**
     * 判断当前设备中是否有"有权查看使用情况的应用程序"这个选项：
     *
     * @return
     */
    public boolean isNoOption(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        Log.e( "yy","list>>>>>>>>>>>" + list.toString());
        return list.size() > 0;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == REQUESTCODE_USE) {
            if (hasEnable(this)) {
                Log.e("yy", "onActivityResult开启了使用情况权限: " );
            } else {
                Log.e("yy", "onActivityResult未开启使用情况权限: " );
            }
        }
    }
}
