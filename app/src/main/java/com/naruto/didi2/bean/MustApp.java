package com.naruto.didi2.bean;

import java.io.Serializable;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/10/13/0013
 */

public class MustApp implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private String apkName;
    private String packageName;
    private String versionname;
    private String mainActivcity;
    private String appid;

    @Override
    public String toString() {
        return "MustApp{" +
                "url='" + url + '\'' +
                ", apkName='" + apkName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionname='" + versionname + '\'' +
                ", mainActivcity='" + mainActivcity + '\'' +
                ", appid='" + appid + '\'' +
                ", iconURL='" + iconURL + '\'' +
                '}';
    }

    public MustApp(String url, String apkName, String packageName, String versionname, String mainActivcity, String appid, String iconURL) {
        this.url = url;
        this.apkName = apkName;
        this.packageName = packageName;
        this.versionname = versionname;
        this.mainActivcity = mainActivcity;
        this.appid = appid;
        this.iconURL = iconURL;
    }

    public MustApp() {
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getMainActivcity() {
        return mainActivcity;
    }

    public void setMainActivcity(String mainActivcity) {
        this.mainActivcity = mainActivcity;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    private String iconURL;
}
