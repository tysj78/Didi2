package com.yangyong.didi2.bean;

/**
 * Created by yangyong on 2019/12/27/0027.
 */

public class User {
    private String name;
    private String pwd;
    private String sex;

    public User(String name, String pwd, String sex) {
        this.name = name;
        this.pwd = pwd;
        this.sex = sex;
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

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
