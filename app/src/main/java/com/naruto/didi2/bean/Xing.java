package com.naruto.didi2.bean;

import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/5/27/0027
 */

public class Xing {

    /**
     * createTime : 1622105657537
     * packageName : com.mobilewise.mobileware
     * deviceUUID : 8813fc10c7af3abc01151c5424016f43a64dde77
     * actionCode : 010804
     * ip : 192.168.191.3
     * MD5 : de1713cfa6727f48f86714014bdf943b
     * surfType : 1
     * location :
     * longitudeLatitude :
     * decide : ["1、<2021-05-27 16:54:17>检测到应用签名被篡改","2、<2021-05-27 16:54:17>获取到篡改应用签名：de1713cfa6727f48f86714014bdf943b","3、<2021-05-27 16:54:17>判定为篡改攻击"]
     * reaction : 2
     * description : 应用被篡改攻击
     * desCode : 0104
     */

    private String createTime;
    private String packageName;
    private String deviceUUID;
    private String actionCode;
    private String ip;
    private String MD5;
    private int surfType;
    private String location;
    private String longitudeLatitude;
    private String reaction;
    private String description;
    private String desCode;
    private List<String> decide;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public int getSurfType() {
        return surfType;
    }

    public void setSurfType(int surfType) {
        this.surfType = surfType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitudeLatitude() {
        return longitudeLatitude;
    }

    public void setLongitudeLatitude(String longitudeLatitude) {
        this.longitudeLatitude = longitudeLatitude;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesCode() {
        return desCode;
    }

    public void setDesCode(String desCode) {
        this.desCode = desCode;
    }

    public List<String> getDecide() {
        return decide;
    }

    public void setDecide(List<String> decide) {
        this.decide = decide;
    }
}
