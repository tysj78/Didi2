package com.naruto.didi2.bean;

/**
 * Created by yangyong on 2019/12/5/0005.
 */

public class ContactsInfo {
    private String name;
    private String mobile;

    public ContactsInfo(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "ContactsInfo{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
