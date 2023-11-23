package com.naruto.didi2.bean;

/**
 * Created by DELL on 2021/11/16.
 */

public class AppCopy {
    private String appName;
    private String pkg;

    public AppCopy(String appName, String pkg) {
        this.appName = appName;
        this.pkg = pkg;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    @Override
    public String toString() {
        return "AppCopy{" +
                "appName='" + appName + '\'' +
                ", pkg='" + pkg + '\'' +
                '}';
    }
}
