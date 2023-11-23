package com.naruto.didi2.fileload;

import java.io.Serializable;

public class DownLoadFileinfo implements Serializable{

    /**
     * 下载文件信息类
     */
    private static final long serialVersionUID = 1L;
    public String url = "";//下载网络地址
    public String pkg = "";//下载apk文件包名
    public String filename = "";//保存在本地的文件名
    public long filelength = 0;//文件长度
    public long finish = 0;//完成的长度
    public String appid = "";
    public String versionname = "";
    public int versioncode = 0;
    public String token = "";
    public String userid = "";
    public String iconurl = "";
    public int reinforceStatus = 0;//0非加固应用 1加固应用

    @Override
    public String toString() {
        return "DownLoadFileinfo{" +
                "url='" + url + '\'' +
                ", pkg='" + pkg + '\'' +
                ", filename='" + filename + '\'' +
                ", filelength=" + filelength +
                ", finish=" + finish +
                ", appid='" + appid + '\'' +
                ", versionname='" + versionname + '\'' +
                ", versioncode=" + versioncode +
                ", token='" + token + '\'' +
                ", userid='" + userid + '\'' +
                ", iconurl='" + iconurl + '\'' +
                ", reinforceStatus=" + reinforceStatus +
                '}';
    }
}
