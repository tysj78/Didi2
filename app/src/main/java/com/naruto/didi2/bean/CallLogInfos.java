package com.naruto.didi2.bean;

/**
 * Created by yangyong on 2019/12/2/0002.
 */

public class CallLogInfos{
    private String contact;
    private String mobile;
    private String area;
    private String duration;
    private String type;
    private String createTime;

    public CallLogInfos(String contact, String mobile, String area, String duration, String type, String createTime) {
        this.contact = contact;
        this.mobile = mobile;
        this.area = area;
        this.duration = duration;
        this.type = type;
        this.createTime = createTime;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CallLogInfo{" +
                "contact='" + contact + '\'' +
                ", mobile='" + mobile + '\'' +
                ", area='" + area + '\'' +
                ", duration='" + duration + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                '}';
    }
}
