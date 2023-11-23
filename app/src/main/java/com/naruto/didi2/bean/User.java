package com.naruto.didi2.bean;

import java.io.Serializable;

/**
 * Created by yangyong on 2019/12/27/0027.
 */

public class User implements Serializable {

    private static final long serialVersionUID = 6366653968169186218L;
    private String name;
    private String pwd;
    private String sex;
    private String age;
    private String groupId;
    private String isUpload="0";


    public User(String name, String pwd, String sex) {
        this.name = name;
        this.pwd = pwd;
        this.sex = sex;
    }

    public User(String name, String pwd, String sex, String age) {
        this.name = name;
        this.pwd = pwd;
        this.sex = sex;
        this.age = age;
    }

    public String getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(String isUpload) {
        this.isUpload = isUpload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", groupId='" + groupId + '\'' +
                ", isupload='" + isUpload + '\'' +
                '}';
    }
}
