package com.yangyong.didi2.constant;

import com.yangyong.didi2.bean.Cat;

public class UrlConfig {
//    private static final UrlConfig ourInstance = new UrlConfig();
    //测试url
    public static String TUrl="www.baidu.com";
    //发布url
    public static String RUrl="www.baidu.com";

    private static final boolean isRelease=false;

    public static Cat cat=new Cat("mayun");
//    public static UrlConfig getInstance() {
//        return ourInstance;
//    }

//    private UrlConfig() {
//
//    }




    public static String getServiceUrl(){
        if (isRelease) {
            TUrl="www.baidu.com";
        }else {
            TUrl="www.qq.com";
        }
        return TUrl;
    }
}
