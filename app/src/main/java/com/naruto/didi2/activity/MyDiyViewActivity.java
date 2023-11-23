package com.naruto.didi2.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MyDiyViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mCounttimeBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diy_view);
        initView();
    }

    private void initView() {
        mCounttimeBt = (Button) findViewById(R.id.bt_counttime);
        mCounttimeBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_counttime:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        countTime();
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    public void countTime() {
        String SIBaseVersion = " ";
        //apk名字
        String str = "appcopy.apk";
        long starTime = System.currentTimeMillis();
        //APK在PC上的位置（根据具体情况修改）
//        String a = "C:\\XXX\\com.netease.newsreader.activity.1508181252.apk";
        String a = Environment.getExternalStorageDirectory().getAbsolutePath() + "/appcopy.apk";
        //abd 安装指令
        String command1 = "adb install push " + a;
        //安装时会出现log,根据关键字确定何时安装，何时结束
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("adb shell su");
            //执行命令，有一段传输数据的时间，不能计算在内
            Process getSIBaseVersionProcess1 = runtime.exec(command1);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getSIBaseVersionProcess1.getInputStream()));
            List<String> tmp = new ArrayList<String>();
            while ((SIBaseVersion = bufferedReader.readLine()) != null) {
                if (isHave(SIBaseVersion, str)) {
                    String[] array = SIBaseVersion.split("/");
                    for (int i = 0; i < array.length; i++) {
                        if (array[i].length() != 0) {
                            tmp.add(array[i]);
                        }
                    }
                    // 取list集合中的数据
                    String T = tmp.get(4);
                    LogUtils.e(T);
                    //开始安装
                    if (T.equals(str)) {
                        long endTime = System.currentTimeMillis();
                        long time1 = endTime - starTime;
                        LogUtils.e(time1 + "");
                    }
                    long endTime = System.currentTimeMillis();
                    long time1 = endTime - starTime;
                    //安装成功
                    if (SIBaseVersion.equals("Success")) {
                        //时间
                        long endTime2 = System.currentTimeMillis();
                        long time = endTime2 - time1;
                        double Time = 1.0 * time / 1000;
                        LogUtils.e("Time=" + Time + " S");
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e("[Error][Install]" + e.getMessage());
        }
    }

    //判断是否含有指定的内容
    public boolean isHave(String strs, String s) {
        for (int i = 0; i < strs.length(); i++) {
            if (strs.indexOf(s) != -1) {
                return true;
            }
        }
        return false;
    }
}
