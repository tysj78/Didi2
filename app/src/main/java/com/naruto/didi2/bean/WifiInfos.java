package com.naruto.didi2.bean;

/**
 * Created by yangyong on 2019/11/27/0027.
 */

public class WifiInfos {
    private String ssid;
    private String bssid;
    private int level;
    private String passWord;

    public WifiInfos(String ssid, String bssid, int level, String passWord) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.level = level;
        this.passWord = passWord;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "WifiInfos{" +
                "ssid='" + ssid + '\'' +
                ", bssid='" + bssid + '\'' +
                ", level=" + level +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
