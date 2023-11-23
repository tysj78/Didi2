package com.naruto.didi2.util;

import android.content.Context;
import android.widget.Toast;

import com.naruto.didi2.MyApp;

/**
 * 单例
 * Created by yangyong on 2019/9/16/0016.
 */

public class NetUtils {
    private static volatile NetUtils netUtils;
    private Context context;
    public String name = "yy";

    private NetUtils() {
        context= MyApp.getContext();
    }

    public static NetUtils getInstance() {
        if (netUtils == null) {
            synchronized (NetUtils.class) {
                if (netUtils == null) {
                    netUtils = new NetUtils();
                }
            }
        }
        return netUtils;
    }

    public void setName() {
        name="daye";
    }

    public void uu(){
        Toast.makeText(context, "uuuuuuuuuuu", Toast.LENGTH_SHORT).show();
    }

}
